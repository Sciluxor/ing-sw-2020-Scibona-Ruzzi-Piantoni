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
import java.util.logging.Level;

/**
 * Class that represents the Server of the game,Insert and Remove players from the games
 * @author alessandroruzzi
 * @version 1.0
 * @since 2020/06/19
 */

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

    /**
     * Private constructor that initialize server parameters -> numGameID and numUserID
     */

    private Server(){
        numGameID = 0;
        numUserID = 0;
    }

    /**
     * Starts the Server
     * @param args Args
     */

    public static void main( String[] args )
    {
        LOGGER.info("Welcome to Santorini Server");
        LOGGER.info("Server is starting...");
        LOGGER.info("Please choose a Port (Default is 4700): ");

        ConfigLoader.loadSetting();
        FlowStatutsLoader.loadFlow();

        Scanner in = new Scanner(System.in);
        int serverPort;

        try {
            String port = in.nextLine();
            if (port.equals(""))
                serverPort = ConfigLoader.getSocketPort();
            else {
                serverPort = Integer.parseInt(port);
                if(serverPort < 1024 || serverPort > 60000)
                    serverPort = ConfigLoader.getSocketPort();
            }
        }catch (NumberFormatException numExc){
            serverPort = ConfigLoader.getSocketPort();
        }


        Server server = new Server();
        server.setSocketPort(serverPort);
        server.startSocketServer(server.getSocketPort());
    }

    /**
     * Set the socketPort
     * @param port Port in which the server will listen for new connections
     */

    public void setSocketPort(int port){ this.socketPort = port;}

    /**
     * Get the socketPort
     * @return The port in which the server listen for new connections
     */

    public int getSocketPort(){
        return this.socketPort;
    }

    /**
     * Starts the SocketHandler that listen for new connections,and a new thread that listen for shutdown requests
     * @param port Port in which the server will listen for new connections
     */

    public void startSocketServer(int port){
        try {
            this.socketHandler = new SocketHandler(port,this);
            LOGGER.log(Level.INFO,"Server is listening on port: {0}",Integer.toString(port));
            closeServerIfRequested();
            new Thread(this).start();
        }catch (IOException e){
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Stop the Server when it is requested by the administrator, closing all connection
     */

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

    }

    /**
     * Remove the ClientHandler of a client from the list of connected clients
     * @param connection ClientHandler of the client that has to be removed
     */

    public void removeFromConnections(ClientHandler connection){
         synchronized (clientsLock) {
             connections.remove(connection);
         }
    }

    /**
     * Check if the configuration parameters received from the client are correct
     * @param nick Nickname used by the client during a single game
     * @param numberOfPlayer The modality that the client wants to play
     * @return True if the configuration parameters are valid, false otherwise
     */

    public boolean checkValidConfig(String nick,int numberOfPlayer){
        boolean isNickValid = true;
        boolean isNumberOfPlayerValid =true;

        if(nick.length()>ConstantsContainer.MAX_LENGHT_NICK || nick.length()<ConstantsContainer.MIN_LENGHT_NICK)
            isNickValid = false;
        if(numberOfPlayer > ConstantsContainer.MAXPLAYERLOBBY || numberOfPlayer < ConstantsContainer.MINPLAYERLOBBY)
            isNumberOfPlayerValid = false;
        return isNumberOfPlayerValid && isNickValid;
    }

    /**
     * Function that move a game started from the lobby to the list of game started(actualMatches)
     */

    public void moveGameStarted(){
        synchronized(clientsLock) {
            for (GameController match : lobby) {
                if (isStarted(match)) {
                    lobby.remove(match);
                    actualMatches.add(match);
                    LOGGER.log(Level.INFO,"Game Started -> || GameID: {0}", match.getGameID());
                    break;
                }
            }
        }
    }

    /**
     * Function that remove from the actual Matches the games where there is a winner or where someone has left the game
     * The function remove all link between controller of the match and the client
     */

    public synchronized void removeGameEnded(){
        synchronized (clientsLock) {
            List<Player> toRemovePlayers;
            List<GameController> toRemoveController = new ArrayList<>();
            for (GameController match : actualMatches) {
                if (match.hasWinner() || match.hasStopper()) {
                    toRemoveController.add(match);
                    LOGGER.log(Level.INFO,"Game Terminated -> || GameID: {0}", match.getGameID());
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

    /**
     * Function that insert a new player in a game, if the game is not yet started it will start a new game with the number of player chosen by the client
     * @param message Message received from the client with the nickname and number of player
     * @param connection ClientHandler of the client
     * @param isFirstTime Boolean used to see if the client has to be moved to another game, because he don't want to change nickName
     */

    public void insertPlayerInGame(Message message,ClientHandler connection,boolean isFirstTime) {
        synchronized (clientsLock) {
            String nick = message.getNickName();
            int numberOfPlayer = ((GameConfigMessage) message).getNumberOfPlayer();

            if(!checkValidConfig(nick,numberOfPlayer)) {
                nickError(connection,message);
                return;
            }

            if(isFirstTime) {
                connections.remove(connection);
                connections.add(connection);
                for (GameController match : lobby) {
                    if (getNumberOfPlayer(match) == numberOfPlayer && !isFull(match)) {
                        insertFirstTime(match,connection,message);
                        return;
                    }
                }
            }
            else
            {
                for (GameController match : lobby) {
                  if (getNumberOfPlayer(match) == numberOfPlayer && !isFull(match) && checkNick(message,match)) {
                      {
                          insertNotFirstTime(match,connection,message);
                          return;
                      }
                }
            }
            }
            insertNewMatch(connection,message,numberOfPlayer);
        }
    }

    /**
     * Function that close the connection with a client, if he send a nickname not allowed, ot select wrong number of player
     * @param connection ClientHandler of the client
     * @param message Message received from the client with the nickname and number of player
     */

    public void nickError(ClientHandler connection,Message message){
        synchronized (clientsLock) {
            if (getControllerFromUserID(connection.getUserID()) != null) {
                GameController match = getControllerFromUserID(connection.getUserID());
                match.disconnectPlayerBeforeGameStart(new Message(connection.getUserID(), connection.getNickName(), MessageType.DISCONNECTION, MessageSubType.STOPPEDGAMEERROR));
                controllerFromUserID.remove(connection.getUserID());
            }
            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME, MessageType.STOPPEDGAME, MessageSubType.STOPPEDGAMEERROR, message.getNickName()));
            removeFromConnections(connection);
            connection.setUserID(ConstantsContainer.USERDIDDEF);
            connection.setConnectionActive(false);
        }
    }

    /**
     * Function that insert a player into a match already created and that is waiting other player to start
     * @param match Controller of the match in which we are going to insert the player
     * @param connection ClientHandler of the client
     * @param message Message received from the client with the nickname and number of player
     */

    public void insertFirstTime(GameController match,ClientHandler connection,Message message){
        synchronized (clientsLock){
            String userID = ConstantsContainer.USERIDPREFIX + numUserID;
            numUserID ++;
            addPlayer(match,connection, message, userID);
            controllerFromUserID.put(userID,match);
        }
    }

    /**
     * Function that move a player in another match, if he keep sending the same nickname(already in use in that match)
     * @param match Controller of the match in which we are going to insert the player
     * @param connection ClientHandler of the client
     * @param message Message received from the client with the nickname and number of player
     */

    public void insertNotFirstTime(GameController match,ClientHandler connection,Message message){
        synchronized (clientsLock){
            addPlayer(match,connection, message,message.getSender());
            controllerFromUserID.remove(message.getSender());
            controllerFromUserID.put(message.getSender(),match);
        }
    }

    /**
     * Function called when there is not a game with the requested number of player,the function create a new game and insert the player in it
     * @param connection ClientHandler of the client
     * @param message Message received from the client with the nickname and number of player
     * @param numberOfPlayer Number of players of the match that the function will start
     */

    public void insertNewMatch(ClientHandler connection,Message message, int numberOfPlayer){
        synchronized (clientsLock){
            String userID = ConstantsContainer.USERIDPREFIX + numUserID;
            numUserID ++;
            GameController match = newMatch(numberOfPlayer);
            addPlayer(match,connection,message,userID);
            controllerFromUserID.put(userID,match);
        }
    }

    /**
     * Function that return the number of player(the modality) of a specific match
     * @param controller Controller of the match in which we are interested
     * @return The number of player of the match
     */

    public int getNumberOfPlayer(GameController controller) {
        return controller.getNumberOfPlayers();
    }

    /**
     * Function that return the controller of the match in which the Client with a specific UserID has been inserted
     * @param userID The UserID of the Client in which we are interested
     * @return The controller of the match of this UserID
     */

    public GameController getControllerFromUserID(String userID){
        return controllerFromUserID.get(userID);
    }

    /**
     * Function that send the message to the VirtualView of a specific client
     * @param msg Message to send to the virtualView
     * @param view The VirtualView of the client to which we want to forward the message
     */

    public void sendMsgToVirtualView(Message msg, VirtualView view) {
        view.processMessageReceived(msg);
    }

    /**
     * Function that add the player in match, and that does all the setup for that player
     * @param controller Controller of the macth in which we insert the player
     * @param connection ClientHandler of the client
     * @param message Message received from the client with the nickname and number of player
     * @param userID UserID of the player to insert
     */

    public void addPlayer(GameController controller,ClientHandler connection,Message message,String userID){
        VirtualView view = new VirtualView(connection,controller);
        ((GameConfigMessage) message).setView(view);
        connection.setView(view);
        view.addObservers(controller);
        connection.setUserID(userID);
        controller.addUserID(view,userID);
        sendMsgToVirtualView(message,view);
        String log = String.format("Inserted in game -> || GameID: %s || UserID: %s || NickName: %s",controller.getGameID(),userID,message.getNickName());
        LOGGER.info(log);
    }

    /**
     * Function that check if a specific game is full
     * @param controller Controller of the match to check
     * @return True if the game is full, false otherwise
     */

    public boolean isFull(GameController controller)
    {
        return controller.isFull();
    }

    /**
     * Function that check if a game is already started
     * @param controller Controller of the match to check
     * @return True if the game is started, false otherwise
     */

    public boolean isStarted(GameController controller){
        return controller.isGameStarted();
    }

    /**
     * Function that check if a nickname is already in use in a match
     * @param message Message received from the client
     * @param controller Controller of the match in which to search
     * @return True if is free, false otherwise
     */

    public boolean checkNick(Message message, GameController controller){
        String nick = message.getNickName();
        return controller.isFreeNick(nick);
    }

    /**
     * Function that create a new controller, to create a new match
     * @param numberOfPlayer Number of player of the new match created
     * @return The controller of the new match
     */

    public GameController newMatch(int numberOfPlayer) {
        String gameID = ConstantsContainer.GAMEIDPREFIX + numGameID;
        numGameID++;
        GameController match = new GameController(numberOfPlayer,gameID);
        lobby.add(match);
        controllerFromGameID.put(gameID,match);
        return match;

    }

    /**
     * Function that handle disconnection events, deliver the events to two specific function that handle disconnection before or during the game
     * @param userID UserID of the Client to disconnect
     * @param connection ClientHandler of the client to disconnect
     * @param message Message received to understand the type of disconnection
     */

    public void handleDisconnection(String userID,ClientHandler connection,Message message) {
        synchronized (clientsLock) {
            String log = String.format("Disconnected -> || UserID: %s || NickName: %s || Type: %s",userID,message.getNickName(),message.getSubType().toString());
            LOGGER.info(log);
            if(message.getSubType().equals(MessageSubType.LOSEEXITREQUEST)){
                GameController controller = getControllerFromUserID(message.getSender());
                controllerFromUserID.remove(message.getSender());
                controller.removeViewFromGame(message.getNickName());
                controller.resetPlayer(connection.getView());
            }
            else if (!userID.equalsIgnoreCase(ConstantsContainer.USERDIDDEF)) {
                GameController controller = getControllerFromUserID(userID);
                if (controller.isGameStarted()) {
                             handleDisconnectionDuringGame(controller,message,connection);

                } else {
                      handleDisconnectionBeforeGame(controller,userID,connection,message);
                }
            }
        }
    }

    /**
     * Function that handle disconnection event before the game is started
     * @param controller Controller of the match in which the player is
     * @param userID UserID of the player to disconnect
     * @param connection ClientHandler of the client to disconnect
     * @param message Message received to understand the type of disconnection
     */

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

    /**
     * Function that handle disconnection events during the game and stop the game
     * @param controller Controller of the match to stop, due to a disconnection
     * @param connection ClientHandler of the client to disconnect
     * @param message Message received to understand the type of disconnection
     */

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
            else if(message.getSubType().equals(MessageSubType.STOPPEDGAMEERROR))
                controller.stopStartedGame(connection.getNickName(),Response.GAMESTOPPEDERROR);
            else
                controller.stopStartedGame(connection.getNickName(),Response.GAMESTOPPED);

        }
    }

    /**
     * Specific thread that listen for a shutdown request by the administrator of the server
     */

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

    /**
     * Specific thread that ping all the clients connected to the Server
     */

    @Override
    public void run() {
            while (!Thread.currentThread().isInterrupted()){
                synchronized (clientsLock){
                    removeGameEnded();
                    for(ClientHandler connection : connections){
                        if(connection.isConnectionActive()) {
                            LOGGER.info("Ping: " + connection.getNickName());
                            connection.ping();
                        }
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
