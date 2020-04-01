package it.polimi.ingsw.utils;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

import java.util.TimerTask;

public class LobbyTimerTask extends TimerTask {

    private ClientHandler connection;
    private String userID;

    public LobbyTimerTask(ClientHandler connection,String userID) {
        this.connection = connection;
        this.userID = userID;
    }

    @Override
    public void run() {
        if(connection.isViewActive())
        connection.dispatchMessageToVirtualView(new Message(userID, MessageType.DISCONNECTION, MessageSubType.TIMEENDED));

        connection.closeConnection();

    }
}
