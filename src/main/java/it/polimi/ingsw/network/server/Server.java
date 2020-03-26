package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server {

    private final int MAXWAITTIME = 1;

    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private final Object clientsLock = new Object();
    private HashMap<ClientHandler,Object> locker = new HashMap<>();
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
            if (!lobby.setNickName(nick, connection))
                connection.sendMessage(new Message("God",MessageType.NICK,MessageSubType.ERROR));
            else if(lobby.isFirst()){
                lobby.setFirst(false);
                lobby.startNewWaitLobby(connection);
                connection.sendMessage(new NickNameMessage("God", MessageSubType.SETTED, nick));
                connection.sendMessage(new Message("God",MessageType.NUMBERPLAYER,MessageSubType.REQUEST));
                //startLobbyTimer();
            }
           else {
                handleNonFirstPlayerConnection(connection);
                connection.sendMessage(new NickNameMessage("God", MessageSubType.SETTED, nick));

            }
        }
    }

    public void handleNonFirstPlayerConnection(ClientHandler connection){
        lobby.insertPlayerInWaitLobby(connection);


    }

    public void handleLobbyNumber(Message message){

        WaitLobby waitLobby = lobby.getWaitLobbyFromString(message.getSender());
        waitLobby.setNumberOfPlayer(((PlayerNumberMessage) message).getPlayersNumber());
        waitLobby.setNumberset(true);

        lobby.handleSettedNumber(waitLobby);
        lobby.handleFreeSpace(waitLobby);



    }

    public ClientHandler getConnectionFromString(String nick){
        return clientsFromString.get(nick);

    }

    public void startLobbyTimer(ClientHandler connection){
       //timer.start(this::firstPlayerDisconnected(),MAXWAITTIME);


    }

    public void eliminateWaitLobby(){

    }

    public void reassignPlayers(){


    }


}

