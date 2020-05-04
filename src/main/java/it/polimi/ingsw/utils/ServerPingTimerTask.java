package it.polimi.ingsw.utils;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class ServerPingTimerTask implements Runnable {
    private ClientHandler connection;
    private String userID;
    private Server server;
    private String nickName;

    public ServerPingTimerTask(Server server,ClientHandler connection,String userID,String nickName){
        this.connection = connection;
        this.userID = userID;
        this.server = server;
        this.nickName = nickName;
    }

    @Override
    public void run() {
        if(!connection.getUserID().equals(ConstantsContainer.USERDIDDEF))
            server.handleDisconnectionBeforeGame(server.getControllerFromUserID(userID),userID,connection,
                    new Message(userID,nickName, MessageType.DISCONNECTION, MessageSubType.PINGFAIL));

        connection.closeAfterDisconnection();
    }
}
