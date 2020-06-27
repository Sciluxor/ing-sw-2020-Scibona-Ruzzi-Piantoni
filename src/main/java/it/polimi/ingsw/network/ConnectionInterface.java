package it.polimi.ingsw.network;

import it.polimi.ingsw.network.message.Message;

import java.io.IOException;

/**
 * Interface that represent the basic function of the connection, both server and client side
 */

public interface ConnectionInterface {

    /**
     * Check if the connection is active
     * @return true if the connection is active, false otherwise
     */

    boolean isConnectionActive();

    /**
     * Send a message through the output stream
     * @param message The Message to send to the Client or Server
     */

    void sendMessage(Message message);

    /**
     * Receive a message through the input stream
     * @return The Message received
     * @throws IOException IOException
     * @throws ClassNotFoundException ClassNotFoundException
     */

    Message receiveMessage() throws IOException, ClassNotFoundException;

}
