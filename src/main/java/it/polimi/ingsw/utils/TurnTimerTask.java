package it.polimi.ingsw.utils;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.TimerTask;

/**
 * Utility class that represent the timer task to execute when the turn timer ends
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/27
 */

public class TurnTimerTask extends TimerTask {

    private ClientHandler connection;

    /**
     * Public constructor
     * @param connection Client Handler of the client
     */

    public TurnTimerTask(ClientHandler connection) {
        this.connection = connection;
    }

    /**
     * Function that notify the server when the timer ends, with disconnection information
     */

    @Override
    public void run() {
        connection.turnTimerEnded(new Message(connection.getUserID(),connection.getNickName(), MessageType.DISCONNECTION, MessageSubType.TIMEENDED));
    }
}
