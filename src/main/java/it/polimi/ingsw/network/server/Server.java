package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConfigLoader;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.FlowStatutsLoader;
import it.polimi.ingsw.view.server.VirtualView;
import java.io.IOException;
import java.util.*;

public class Server implements Runnable{

    private final Object clientsLock = new Object();
    private final List<GameController> lobby = new ArrayList<>();
    private final List<GameController> actualMatches = new ArrayList<>();
    private final Map<String, GameController> controllerFromGameID = new HashMap<>();
    private final Map<String, GameController> controllerFromUserID = new HashMap<>();
    private SocketHandler socketHandler;
    private final List<ClientHandler> connections = new ArrayList<>();
    private Integer socketPort;
    private int numGameID;
    private int numUserID;
    public static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("Server");

    private Server(){
        numGameID = 0;
        numUserID = 0;
    }

    public static void main( String[] args )
    {
        LOGGER.info("Welcome to Santorini Server");
        LOGGER.info("Server is starting...");
        LOGGER.info("Please choose a Port (Default is 4700): ");

        ConfigLoader.loadSetting();
        FlowStatutsLoader.loadFlow();  //aggiungere anche i controlli per gli errori

        Scanner in = new Scanner(System.in);
        int serverPort;

        try {
            String port = in.nextLine();
            if (port.equals(""))
                serverPort = ConfigLoader.getSocketPort();
            else {
                serverPort = Integer.parseInt(port);
                if(serverPort < 1024 || serverPort > 60000)  //rivedere questi numeri
                    serverPort = ConfigLoader.getSocketPort();
            }
        }catch (NumberFormatException numExc){
            serverPort = ConfigLoader.getSocketPort();
        }


        Server server = new Server();
        server.setSocketPort(serverPort);
        server.startSocketServer(server.getSocketPort());
    }


    public void setSocketPort(int port){
        this.socketPort = port;

    }

    public int getSocketPort(){
        return this.socketPort;
    }

    public void startSocketServer(int port){
        try {
            this.socketHandler = new SocketHandler(port,this);
            LOGGER.info("Server is listening on port: " + port);
            closeServerIfRequested();
            new Thread(this).start();
        }catch (IOException e){
            LOGGER.severe(e.getMessage());
        }
    }

    public void stopServer() {
        synchronized (clientsLock) {
            ArrayList<ClientHandler> toRemove = new ArrayList<>();

            for (ClientHandler connection : connections) {
                connection.closeConnectionFromServer();
                toRemove.add(connection);
            }

            for(ClientHandler connection: toRemove){
                removeFromConnections(connection);
            }

            LOGGER.info("Server Shutted Down.");
            System.exit(0);
        }
                                                               //vedere cosa fare per chiudere tutte le connessioni, bisogna inviare un mesaaggio.
    }

    public void removeFromConnections(ClientHandler connection){
         synchronized (clientsLock) {
             connections.remove(connection);
         }
    }

    public boolean checkValidConfig(String nick,int numberOfPlayer,ClientHandler connection){
        boolean isNickValid = true;
        boolean isNumberOfPlayerValid =true;

        if(nick.length()>ConstantsContainer.MAX_LENGHT_NICK || nick.length()<ConstantsContainer.MIN_LENGHT_NICK)
            isNickValid = false;
        if(numberOfPlayer > ConstantsContainer.MAXPLAYERLOBBY || numberOfPlayer < ConstantsContainer.MINPLAYERLOBBY)
            isNumberOfPlayerValid = false;
        if(!isNumberOfPlayerValid || !isNickValid) {
            connection.sendMessage(new GameConfigMessage(ConstantsContainer.SERVERNAME,nick ,MessageSubType.ERROR,numberOfPlayer, isNickValid, false, isNumberOfPlayerValid));
            return false;
        }
        return true;
    }

    public void moveGameStarted(){
        synchronized(clientsLock) {
            for (GameController match : lobby) {
                if (isStarted(match)) {
                    lobby.remove(match);
                    actualMatches.add(match);
                    break;
                }
            }
        }
    }

    public synchronized void removeGameEnded(){
        synchronized (clientsLock) {
            List<Player> toRemovePlayers;
            List<GameController> toRemoveController = new ArrayList<>();
            for (GameController match : actualMatches) {
                if (match.hasWinner() || match.hasStopper()) {
                    toRemoveController.add(match);
                }
            }
            for (GameController match : toRemoveController) {
                actualMatches.remove(match);
                toRemovePlayers = match.getActualPlayers();
                controllerFromGameID.remove(match.getGameID());
                for (Player player : toRemovePlayers) {
                    match.getViewFromNickName(player.getNickName()).removeObserver(match);
                    controllerFromUserID.remove(match.getUserIDFromPlayer(player));
                }
                for (Player player : match.getLosePlayers()) {
                    match.getViewFromNickName(player.getNickName()).removeObserver(match);
                    controllerFromUserID.remove(match.getUserIDFromPlayer(player));
                }
            }

        }
    }

    public void insertPlayerInGame(Message message,ClientHandler connection,boolean isFirstTime) {
        synchronized (clientsLock) {
            String nick = message.getNickName();
            int numberOfPlayer = ((GameConfigMessage) message).getNumberOfPlayer();

            if(!checkValidConfig(nick,numberOfPlayer,connection))
                return;

            if(isFirstTime) {
                connections.add(connection);
                for (GameController match : lobby) {
                    if (getNumberOfPlayer(match) == numberOfPlayer && !isFull(match)) {
                        String userID = ConstantsContainer.USERIDPREFIX + numUserID;
                        numUserID ++;
                        addPlayer(match,connection, message, userID);
                        controllerFromUserID.put(userID,match);
                        return;
                    }
                }
            }
            else
            {
                for (GameController match : lobby) {
                  if (getNumberOfPlayer(match) == numberOfPlayer && !isFull(match) && checkNick(message,match)) {
                      {
                          addPlayer(match,connection, message,message.getSender());
                          controllerFromUserID.remove(message.getSender());
                          controllerFromUserID.put(message.getSender(),match);
                          return;
                      }
                }
            }

            }
            String userID = ConstantsContainer.USERIDPREFIX + numUserID;
            numUserID ++;
            GameController match = newMatch(numberOfPlayer);
            addPlayer(match,connection,message,userID);
            controllerFromUserID.put(userID,match);

        }
    }

    public int getNumberOfPlayer(GameController controller) {
        return controller.getNumberOfPlayers();
    }

    public GameController getControllerFromGameID(String gameId){
        return controllerFromGameID.get(gameId);
    }

    public GameController getControllerFromUserID(String userID){
        return controllerFromUserID.get(userID);
    }

    public void sendMsgToVirtualView(Message msg, VirtualView view) {
        view.processMessageReceived(msg);
    }

    public void addPlayer(GameController controller,ClientHandler connection,Message message,String userID){ //aggiungere sincronizzazione?
        VirtualView view = new VirtualView(connection,controller);
        ((GameConfigMessage) message).setView(view);
        connection.setView(view);
        view.addObservers(controller);
        connection.setUserID(userID);
        controller.addUserID(view,userID);
        sendMsgToVirtualView(message,view);
    }

    public boolean isFull(GameController controller)
    {
        return controller.isFull();
    }

    public boolean isStarted(GameController controller){
        return controller.isGameStarted();
    }

    public boolean checkNick(Message message, GameController controller){
        String nick = message.getNickName();
        return controller.isFreeNick(nick);
    }

    public GameController newMatch(int numberOfPlayer) {
        String gameID = ConstantsContainer.GAMEIDPREFIX + numGameID;
        numGameID++;
        GameController match = new GameController(numberOfPlayer,gameID);
        lobby.add(match);
        controllerFromGameID.put(gameID,match);
        return match;

    }

    public void handleDisconnection(String userID,ClientHandler connection,Message message) {
        synchronized (clientsLock) {
            if(message.getSubType().equals(MessageSubType.LOSEEXITREQUEST)){
                GameController controller = getControllerFromUserID(message.getSender());
                controllerFromUserID.remove(message.getSender());
                controller.removeViewFromGame(message.getNickName());
                controller.resetPlayer(connection.getView());
            }
            else if (!userID.equalsIgnoreCase(ConstantsContainer.USERDIDDEF)) {
                GameController controller = getControllerFromUserID(userID);
                if (controller.isGameStarted()) {                             //mettere il caso di disconnection request se il game è già iniziato
                             handleDisconnectionDuringGame(controller,message,connection);

                } else {
                      handleDisconnectionBeforeGame(controller,userID,connection,message);
                }
            }
        }
    }

    public synchronized void handleDisconnectionBeforeGame(GameController controller,String userID,ClientHandler connection,Message message){
    synchronized (clientsLock) {
        controllerFromUserID.remove(userID);
        if ((message.getSubType().equals(MessageSubType.TIMEENDED))) {
            controller.handleLobbyTimerEnded(message);
        } else {
                controller.disconnectPlayerBeforeGameStart(message);
                if (message.getSubType().equals(MessageSubType.BACK)) {
                    controller.resetPlayer(connection.getView());
                }

        }
    }
    }

    public synchronized void  handleDisconnectionDuringGame(GameController controller,Message message,ClientHandler connection){
        synchronized (clientsLock) {
            if(!controller.isStillInGame(message.getNickName())){
                controllerFromUserID.remove(message.getSender());
                controller.removeViewFromGame(message.getNickName());
                controller.resetPlayer(connection.getView());
                return;
            }

            actualMatches.remove(controller);

            for (Player player : controller.getActualPlayers()) {
                controllerFromUserID.remove(controller.getUserIDFromPlayer(player));
            }
            controllerFromGameID.remove(controller.getGameID());
            if(message.getSubType().equals(MessageSubType.TIMEENDED))
                 controller.stopStartedGame(connection.getNickName(),Response.PLAYERTIMERENDED);
            else
                controller.stopStartedGame(connection.getNickName(),Response.GAMESTOPPED);
        }
    }

    public void closeServerIfRequested(){
        new Thread(() -> {
            String input = "";
            while (!input.equalsIgnoreCase("close")) {
                LOGGER.info("Type \"close\" to stop the server.");
                input = new Scanner(System.in).nextLine();
            }

            stopServer();

            if (socketHandler != null)
                socketHandler.close();
            System.exit(0);
        }).start();
    }

    @Override
    public void run() {
            while (!Thread.currentThread().isInterrupted()){
                synchronized (clientsLock){
                    removeGameEnded();
                    for(ClientHandler connection : connections){
                        if(connection.isConnectionActive())
                            connection.ping();
                    }
                    try{
                        clientsLock.wait(1500);
                    }catch (InterruptedException inter){
                        LOGGER.severe(inter.getMessage());
                        Thread.currentThread().interrupt();
                    }
            }
        }
    }
}
