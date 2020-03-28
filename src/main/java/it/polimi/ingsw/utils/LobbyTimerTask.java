package it.polimi.ingsw.utils;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.TimerTask;

public class LobbyTimerTask extends TimerTask {

    private ClientHandler connection;

    public LobbyTimerTask(ClientHandler connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        connection.sendMessage(new Message("God",MessageType.DISCONNECTION,MessageSubType.TIMEENDED));
        connection.closeConnection();
    }
}
