package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.message.Message;

import java.io.IOException;

public class ClientConnection implements ConnectionInterface {

    private boolean isConnectionActive;

    public ClientConnection(){
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
