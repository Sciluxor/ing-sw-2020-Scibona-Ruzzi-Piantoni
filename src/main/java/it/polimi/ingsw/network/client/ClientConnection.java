package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.message.Message;

import java.io.IOException;

public class ClientConnection implements ConnectionInterface {

    private boolean isConnectionActive;

    public ClientConnection(){

    }

    @Override
    public boolean isConnectionActive() {
        return isConnectionActive;
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void closeConnection() {

    }

    @Override
    public Message receiveMessage() throws IOException, ClassNotFoundException {
        return null;
    }
}
