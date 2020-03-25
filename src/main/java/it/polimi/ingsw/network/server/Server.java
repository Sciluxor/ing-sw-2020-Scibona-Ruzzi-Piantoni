package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.utils.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private final Object clientsLock = new Object();
    private HashMap<ClientHandler,Object> locker = new HashMap<>();
    private HashMap<String,ClientHandler> clientsFromString = new HashMap<>();
    private Lobby lobby = new Lobby();
    private Integer socketPort;

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

    public void firsLogin(ClientHandler clientHandler){
        clients.add(clientHandler);
        clientHandler.sendMessage(new Message(MessageType.NICK, MessageSubType.REQUEST,"richiesta di nick"));


    }


}

