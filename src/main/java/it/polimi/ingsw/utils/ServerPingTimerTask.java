package it.polimi.ingsw.utils;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

import java.util.TimerTask;

/**
 * Utility class that contains all the constant of the game
 * @author alessandroruzzi
 * @version 1.0
 * @since 2020/06/27
 */

public class ServerPingTimerTask extends TimerTask {
    private ClientHandler connection;
    private String userID;
    private Server server;
    private String nickName;

    /**
     *
     * @param server
     * @param connection
     * @param userID
     * @param nickName
     */

    public ServerPingTimerTask(Server server,ClientHandler connection,String userID,String nickName){
        this.connection = connection;
        this.userID = userID;
        this.server = server;
        this.nickName = nickName;
    }

    /**
     *
     */

    @Override
    public void run() {
        if(!connection.getUserID().equals(ConstantsContainer.USERDIDDEF)) {
            server.handleDisconnection(userID, connection,
                    new Message(userID, nickName, MessageType.DISCONNECTION, MessageSubType.PINGFAIL));
            connection.setUserID(ConstantsContainer.USERDIDDEF);
        }
        connection.closeAfterDisconnection();
    }
}
