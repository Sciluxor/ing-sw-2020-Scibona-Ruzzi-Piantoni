package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.TimerTask;

public class TurnTimerTask extends TimerTask {

    private ClientHandler connection;

    public TurnTimerTask(ClientHandler connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        connection.TurnTimerEnded(new Message(connection.getUserID(),connection.getNickName(), MessageType.DISCONNECTION, MessageSubType.TIMEENDED));
    }
}
