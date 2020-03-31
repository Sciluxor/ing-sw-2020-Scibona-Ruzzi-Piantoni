package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.LobbyTimerTask;
import it.polimi.ingsw.utils.Logger;

import java.io.IOException;
import java.util.*;

public class Server {


    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private final Object clientsLock = new Object();
    private ArrayList<Match> lobby = new ArrayList<>();
    private HashMap<Match,Object> locker = new HashMap<>();
    private HashMap<String,ClientHandler> clientsFromString = new HashMap<>();
    private Integer socketPort;
    private HashMap<ClientHandler,Timer> timerFromString = new HashMap<>();

    private Server(){



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

    public void onMessage(Message msg){
        Match actualMatch = lobby.getMatchfromName(msg.getSender());
        actualMatch.sendMsgToVirtualView(msg);
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

    public void firsLogin(ClientHandler connnection){
        synchronized (clientsLock) {
            clients.add(connnection);
            startLobbyTimer(connnection);
            connnection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.NICK,MessageSubType.REQUEST));
        }
    }

    public boolean checkValidConfig(String nick,int numberOfPlayer,ClientHandler connection){
        boolean isNickValid = true;
        boolean isNumberOfPlayerValid =true;

        if(nick.length()>ConstantsContainer.MAX_LENGHT_NICK || nick.length()<ConstantsContainer.MIN_LENGHT_NICK)
            isNickValid = false;
        if(numberOfPlayer > ConstantsContainer.MAXPLAYERLOBBY || numberOfPlayer < ConstantsContainer.MINPLAYERLOBBY)
            isNumberOfPlayerValid = false;
        if(!isNumberOfPlayerValid || isNickValid) {
            connection.sendMessage(new GameConfigMessage(ConstantsContainer.SERVERNAME, MessageSubType.ERROR, nick, numberOfPlayer, isNickValid, false, isNumberOfPlayerValid));
            return false;
        }

        return true;
    }

    public void InsertPlayerInGame(Message message,ClientHandler connection){
        synchronized (clientsLock) {
            stopLobbyTimer(connection);
            String nick = ((GameConfigMessage) message).getNickName();
            int numberOfPlayer = ((GameConfigMessage) message).getNumberOfPlayer();

            if(!checkValidConfig(nick,numberOfPlayer,connection))
                return;

            for(Match match: lobby){
                if(match.getNumberOfPlayer() == numberOfPlayer){
                    Match actualMacth = match;
                    match.addPlayer(connection,nick);
                    match.sendMsgToVirtualView(message);
                    return;
                }

            }
            newMatch();

        }
    }

    public void newMatch(String nickName,int numberOfPlayer,ClientHandler connection){
        lobby.add(new Match());

    }

    public void handleFirstPlayerConnection(ClientHandler connection,int numberOfPlayers){
        lobby.insertPlayerInWaitLobby(connection,numberOfPlayers);

    }

    public void handleLobbyNumber(Message message){
        synchronized (clientsLock) {
            stopLobbyTimer(getConnectionFromString(message.getSender()));
            int players =  ((PlayerNumberMessage) message).getPlayersNumber();
            if ( players >= ConstantsContainer.MINPLAYERLOBBY && players <= ConstantsContainer.MAXPLAYERLOBBY) {
                handleFirstPlayerConnection(getConnectionFromString(message.getSender()),players);
            } else {

                getConnectionFromString(message.getSender()).sendMessage(new Message(ConstantsContainer.SERVERNAME, MessageType.NUMBERPLAYER, MessageSubType.ERROR));
                startLobbyTimer(getConnectionFromString(message.getSender()));
            }
        }
    }

    public void handleClientDisconnectionBeforeStarting(Message message){
        synchronized (clientsLock) {
            ClientHandler connection = getConnectionFromString(message.getSender());
            if (connection.getView().isGameStarted())
                connection.sendMessage(new Message(ConstantsContainer.SERVERNAME, MessageType.DISCONNECTION, MessageSubType.ERROR));
            else {
                if(message.getSubType() == MessageSubType.REQUEST){
                    lobby.disconnectPlayer(connection, message.getSender());
                    clients.remove(connection);
                    connection.sendMessage(new Message(ConstantsContainer.SERVERNAME, MessageType.DISCONNECTION, MessageSubType.SETTED));
                    connection.closeConnection();
                }
                else {
                    lobby.moveBackPlayer(connection,message.getSender());
                    connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.NUMBERPLAYER,MessageSubType.REQUEST));
                    startLobbyTimer(connection);
                }
            }
        }
    }

    public void handleTimeLobbyEnded(ClientHandler connection){
        synchronized (clientsLock){
            clients.remove(connection);

            if(connection.isViewActive())
               lobby.removeTimeEndedPlayer(connection);

            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.DISCONNECTION,MessageSubType.TIMEENDED));
            connection.closeConnection();

        }
    }

    public ClientHandler getConnectionFromString(String nick){
        return clientsFromString.get(nick);
    }

    public void startLobbyTimer(ClientHandler connection){
        Timer timer = new Timer();
        timerFromString.put(connection,timer);
        LobbyTimerTask task = new LobbyTimerTask(connection,this);
        timerFromString.get(connection).schedule(task, ConstantsContainer.MAXWAITTIME);
    }

    public void stopLobbyTimer(ClientHandler connection){
        timerFromString.get(connection).cancel();
        timerFromString.remove(connection);

    }

}
//dividere le funzioni più grandi
