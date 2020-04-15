package it.polimi.ingsw.network;

import it.polimi.ingsw.network.message.Message;

import java.io.IOException;

public interface ConnectionInterface {

    boolean isConnectionActive();

    void sendMessage(Message message);

    void closeConnection();

    Message receiveMessage() throws IOException, ClassNotFoundException;

}
