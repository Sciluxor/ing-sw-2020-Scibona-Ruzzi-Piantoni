package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.message.Message;

import java.io.IOException;

public class ClientConnection implements ConnectionInterface {

    private boolean isConnectionActive;
    private String userName;
    private String address;
    private int port;

    public ClientConnection(String name, String address, int port){
       //da implementare
    }

    @Override
    public boolean isConnectionActive() {
        return isConnectionActive;
    }

    @Override
    public void sendMessage(Message message) {
           //da implementare
    }

    @Override
    public void closeConnection() {
         //da implementare
    }

    @Override
    public Message receiveMessage() throws IOException, ClassNotFoundException {
        //da implementare
        return null;
    }
}
