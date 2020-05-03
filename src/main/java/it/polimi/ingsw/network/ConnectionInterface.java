package it.polimi.ingsw.network;

import it.polimi.ingsw.network.message.Message;

import java.io.IOException;

public interface ConnectionInterface {

    boolean isConnectionActive();

    void sendMessage(Message message);

    Message receiveMessage() throws IOException, ClassNotFoundException;

}
