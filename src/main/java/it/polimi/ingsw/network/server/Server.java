package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.view.Server.VirtualView;

import java.io.IOException;
import java.util.*;

public class Server {

    private final Object clientsLock = new Object();
    private ArrayList<GameController> lobby = new ArrayList<>();
    private ArrayList<GameController> actualMatches = new ArrayList<>();
    private HashMap<String, GameController> controllerFromGameID = new HashMap<>();
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
        Logger.info("Please choose a Port: ");

        Scanner in = new Scanner(System.in);
        int port = Integer.parseInt(in.next());

        Server server = new Server();
        server.setSocketPort(port);
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
            SocketHandler socketHandler = new SocketHandler(port,this);
        }catch (IOException e){
            Logger.info("Impossible to start the Server");
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
            Logger.info(Integer.toString(lobby.size()));
            String nick = message.getNickName();
            int numberOfPlayer = ((GameConfigMessage) message).getNumberOfPlayer();

            if(!checkValidConfig(nick,numberOfPlayer,connection))
                return;

            String userID = ConstantsContainer.USERIDPREFIX + numUserID;
            numUserID ++;
            if(isFirstTime) {
                for (GameController match : lobby) {
                    if (getNumberOfPlayer(match) == numberOfPlayer && !isFull(match)) {
                        addPlayer(match,connection, message, userID);
                        return;
                    }
                }
            }
            else
            {
                for (GameController match : lobby) {
                  if (getNumberOfPlayer(match) == numberOfPlayer && !isFull(match)) {
                      if(checkNick(message,match)) {
                          addPlayer(match,connection, message, userID);
                          return;
                      }
                }
            }

            }
            GameController match = newMatch(numberOfPlayer);
            addPlayer(match,connection,message,userID);

        }
    }

    public int getNumberOfPlayer(GameController controller) {
        return controller.getNumberOfPlayers();
    }

    public GameController getControllerFromGameID(String gameId){
        return controllerFromGameID.get(gameId);
    }

    public void sendMsgToVirtualView(Message msg, VirtualView view) {
        view.processMessageReceived(msg);
    }

    public void addPlayer(GameController controller,ClientHandler connection,Message message,String userID){
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

    }}