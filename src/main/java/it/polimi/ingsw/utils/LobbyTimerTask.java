package it.polimi.ingsw.utils;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

import java.util.TimerTask;

public class LobbyTimerTask extends TimerTask {

    private ClientHandler connection;
    private Server server;

    public LobbyTimerTask(ClientHandler connection, Server server) {
        this.connection = connection;
        this.server = server;
    }

    @Override
    public void run() {
        server.handleTimeLobbyEnded(connection);

    }
}
