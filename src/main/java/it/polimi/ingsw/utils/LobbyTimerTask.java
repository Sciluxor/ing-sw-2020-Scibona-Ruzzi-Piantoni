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
    private Server server;
    private String nickName;

    public LobbyTimerTask(Server server,ClientHandler connection,String userID,String nickName) {
        this.connection = connection;
        this.userID = userID;
        this.server = server;
        this.nickName = nickName;
    }

    @Override
    public void run() {
        if(connection.isViewActive())
            server.handleDisconnectionBeforeGame(server.getControllerFromUserID(userID),userID,connection,
                    new Message (userID,nickName, MessageType.DISCONNECTION, MessageSubType.TIMEENDED));

            connection.closeConnection();

    }
}
