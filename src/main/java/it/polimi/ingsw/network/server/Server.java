package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.LobbyTimerTask;
import it.polimi.ingsw.utils.Logger;

import java.io.IOException;
import java.util.*;

public class Server {

    private final Object clientsLock = new Object();
    private ArrayList<Match> lobby = new ArrayList<>();
    private HashMap<Match,Object> locker = new HashMap<>();
    private Integer socketPort;
    private HashMap<ClientHandler,Timer> timerFromString = new HashMap<>();
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

    public void InsertPlayerInGame(Message message,ClientHandler connection){
        synchronized (clientsLock) {
            String nick = message.getNickName();
            int numberOfPlayer = ((GameConfigMessage) message).getNumberOfPlayer();

            if(!checkValidConfig(nick,numberOfPlayer,connection))
                return;

            String userID = ConstantsContainer.USERIDPREFIX + numUserID;
            numUserID ++;
            for(Match match: lobby){
                if(match.getNumberOfPlayer() == numberOfPlayer && !match.isFull()){
                    match.addPlayer(connection, message,userID);
                    return;
                }
            }
            Match match = newMatch(numberOfPlayer);
            match.addPlayer(connection,message,userID);

        }
    }

    public Match newMatch(int numberOfPlayer){
        String gameID = ConstantsContainer.GAMEIDPREFIX + numGameID;
        numGameID++;
        Match match = new Match(numberOfPlayer,gameID);
        lobby.add(match);
        return match;

    }}