package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.Logger;

import java.io.IOException;
import java.util.*;

public class Server {//eliminare i match e fare un arraylist di Gamecontroller

    private final Object clientsLock = new Object();
    private ArrayList<Match> lobby = new ArrayList<>();
    private ArrayList<Match> actualMatches = new ArrayList<>();
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
            for (Match match : lobby) {
                if (match.isStarted()) {
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
                for (Match match : lobby) {
                    if (match.getNumberOfPlayer() == numberOfPlayer && !match.isFull()) {
                        match.addPlayer(connection, message, userID);
                        return;
                    }
                }
            }
            else
            {
                for (Match match : lobby) {
                  if (match.getNumberOfPlayer() == numberOfPlayer && !match.isFull()) {
                      if(match.checkNick(message)) {
                          match.addPlayer(connection, message, userID);
                          return;
                      }
                }
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