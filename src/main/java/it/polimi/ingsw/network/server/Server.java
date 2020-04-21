package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConfigLoader;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.FlowStatutsLoader;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.view.Server.VirtualView;

import java.io.IOException;
import java.util.*;

public class Server {

    private final Object clientsLock = new Object();
    private ArrayList<GameController> lobby = new ArrayList<>();
    private ArrayList<GameController> actualMatches = new ArrayList<>();
    private HashMap<String, GameController> controllerFromGameID = new HashMap<>();
    private HashMap<String, GameController> controllerFromUserID = new HashMap<>();
    private SocketHandler socketHandler;
    private ArrayList<ClientHandler> connections = new ArrayList<>();
    private Integer socketPort;
    private int numGameID;
    private int numUserID;

    private Server(){
        numGameID = 0;
        numUserID = 0;
    }

    public static void main( String[] args )
    {
        Logger.info("Welcome to Santorini Server");
        Logger.info("Server is starting...");
        Logger.info("Please choose a Port (Default is 4700): ");

        ConfigLoader.loadSetting();
        FlowStatutsLoader.loadFlow();  //aggiungere anche i controlli per gli errori

        Scanner in = new Scanner(System.in);
        String port = in.nextLine();

        int serverPort;

        if(port.equals(""))
            serverPort = ConfigLoader.getSocketPort();
        else
            serverPort = Integer.parseInt(port);  //gestire input sbagliati

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
            Logger.info("Server is listening on port: "+ port);
            closeServerIfRequested();
        }catch (IOException e){
            Logger.info("Impossible to start the Server");
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

            Logger.info("Server Shutted Down.");
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

    public void insertPlayerInGame(Message message,ClientHandler connection,boolean isFirstTime){
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
                  if (getNumberOfPlayer(match) == numberOfPlayer && !isFull(match)) {
                      if(checkNick(message,match)) {
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
        connection.setViewActive(true);
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

    public GameController newMatch(int numberOfPlayer){
        String gameID = ConstantsContainer.GAMEIDPREFIX + numGameID;
        numGameID++;
        GameController match = new GameController(numberOfPlayer,gameID);
        lobby.add(match);
        controllerFromGameID.put(gameID,match);
        return match;

    }

    public void handleDisconnection(String userID,ClientHandler connection,Message message) {      //spezzare questa in tre funzioni
        synchronized (clientsLock) {
            if (!userID.equalsIgnoreCase(ConstantsContainer.USERDIDDEF)) {
                GameController controller = getControllerFromUserID(userID);
                if (controller.isGameStarted()) {                             //mettere il caso di disconnection request se il game è già iniziato
                             handleDisconnectionDuringGame(controller);

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
                    connection.getView().removeObserver(controller);
                }
                else if ((message.getSubType().equals(MessageSubType.NICKMAXTRY))) {
                    connection.getView().removeObserver(controller);
                }

        }
    }
    }

    public synchronized void  handleDisconnectionDuringGame(GameController controller){
        //controller.stopStartedGame();
        synchronized (clientsLock) {
            actualMatches.remove(controller);

            for (Player player : controller.getActualPlayers()) {
                controllerFromUserID.remove(controller.getUserIDFromPlayer(player));
            }
            controllerFromGameID.remove(controller.getGameID());
            controller.stopStartedGame();
        }
    }

    public void closeServerIfRequested(){
        new Thread(() -> {
            String input = "";
            while (!input.equalsIgnoreCase("close")) {
                Logger.info("Type \"close\" to stop the server.");
                input = new Scanner(System.in).nextLine();
            }

            stopServer();

            if (socketHandler != null)
                socketHandler.close();
            System.exit(0);
        }).start();
    }
    }
