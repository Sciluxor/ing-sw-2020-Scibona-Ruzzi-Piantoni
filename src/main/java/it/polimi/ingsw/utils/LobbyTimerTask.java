package it.polimi.ingsw.utils;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

import java.util.TimerTask;

public class LobbyTimerTask extends TimerTask {

    private ClientHandler connection;

    public LobbyTimerTask(ClientHandler connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        connection.closeConnection();

    }
}
