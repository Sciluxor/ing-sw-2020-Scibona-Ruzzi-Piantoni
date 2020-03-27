package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server {

    private final int MAXWAITTIME = 1;
    private final int MINPLAYERLOBBY = 2;
    private final int MAXPLAYERLOBBY = 3;

    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private final Object clientsLock = new Object();
    private HashMap<Match,Object> locker = new HashMap<>();
    private HashMap<String,ClientHandler> clientsFromString = new HashMap<>();
    private Lobby lobby = new Lobby();
    private Integer socketPort;
    //LobbyTimer timer = new LobbyTimer;

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
            connnection.sendMessage(new Message("God",MessageType.NICK,MessageSubType.REQUEST));
        }

    }
    public void setNick(Message message,ClientHandler connection){
        synchronized (clientsLock) {
            String nick = ((NickNameMessage) message).getNickName();
            if (!lobby.setNickName(nick, connection)){
                connection.sendMessage(new Message("God",MessageType.NICK,MessageSubType.ERROR));
            }
           else {
                clientsFromString.put(nick,connection);
                connection.sendMessage(new NickNameMessage("God", MessageSubType.SETTED, nick));
                connection.sendMessage(new Message("God",MessageType.NUMBERPLAYER,MessageSubType.REQUEST));
                //startLobbyTimer();
            }
        }
    }

    public void handleFirstPlayerConnection(ClientHandler connection,int numberOfPlayers){
        lobby.insertPlayerInWaitLobby(connection,numberOfPlayers);

    }

    public void handleLobbyNumber(Message message){
        synchronized (clientsLock) {
            int players =  ((PlayerNumberMessage) message).getPlayersNumber();
            if ( players >= MINPLAYERLOBBY && players <= MAXPLAYERLOBBY) {
                handleFirstPlayerConnection(getConnectionFromString(message.getSender()),players);
            } else {

                getConnectionFromString(message.getSender()).sendMessage(new Message("God", MessageType.NUMBERPLAYER, MessageSubType.ERROR));
            }
        }
    }

    public void handleClientDisconnectionBeforeStarting(Message message){
        synchronized (clientsLock) {

            ClientHandler connection = getConnectionFromString(message.getSender());
            if (connection.getView().isGameStarted())
                connection.sendMessage(new Message("God", MessageType.DISCONNECTION, MessageSubType.ERROR));
            else {
                if(message.getSubType() == MessageSubType.REQUEST){
                    lobby.disconnectPlayer(connection, message.getSender());
                    clients.remove(connection);
                    connection.sendMessage(new Message("God", MessageType.DISCONNECTION, MessageSubType.SETTED));
                    connection.closeConnection();
                }
                else {
                    lobby.moveBackPlayer(connection,message.getSender());
                    connection.sendMessage(new Message("God",MessageType.NUMBERPLAYER,MessageSubType.REQUEST));
                }
            }
        }
    }

    public ClientHandler getConnectionFromString(String nick){
        return clientsFromString.get(nick);

    }

    public void startLobbyTimer(ClientHandler connection){
       //timer.start(this::firstPlayerDisconnected(),MAXWAITTIME);


    }

}

