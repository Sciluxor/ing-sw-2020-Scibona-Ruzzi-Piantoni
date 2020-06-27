package it.polimi.ingsw.utils;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.TimerTask;

/**
 * Utility class that contains all the constant of the game
 * @author alessandroruzzi
 * @version 1.0
 * @since 2020/06/27
 */

public class TurnTimerTask extends TimerTask {

    private ClientHandler connection;

    /**
     *
     * @param connection
     */

    public TurnTimerTask(ClientHandler connection) {
        this.connection = connection;
    }

    /**
     *
     */

    @Override
    public void run() {
        connection.turnTimerEnded(new Message(connection.getUserID(),connection.getNickName(), MessageType.DISCONNECTION, MessageSubType.TIMEENDED));
    }
}
