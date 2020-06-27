package it.polimi.ingsw.utils;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

import java.util.TimerTask;

/**
 * Utility class that represent the timer task to execute when the server ping timer ends
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/27
 */

public class ServerPingTimerTask extends TimerTask {
    private ClientHandler connection;
    private String userID;
    private Server server;
    private String nickName;

    /**
     * Public constructor
     * @param server The server of the game
     * @param connection Client Handler of the client
     * @param userID UserID of the client
     * @param nickName NickName of the client
     */

    public ServerPingTimerTask(Server server,ClientHandler connection,String userID,String nickName){
        this.connection = connection;
        this.userID = userID;
        this.server = server;
        this.nickName = nickName;
    }

    /**
     * Function that notify the server when the timer ends, with disconnection information
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
