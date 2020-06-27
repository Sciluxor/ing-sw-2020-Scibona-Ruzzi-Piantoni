package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ClientPingTimerTask;
import it.polimi.ingsw.utils.ConfigLoader;
import it.polimi.ingsw.utils.ConstantsContainer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Timer;

/**
 * Class that represents the Connection client side, Exchange Messages(receive and send) with the server, handle also the ping task
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/19
 */

public class ClientConnection implements ConnectionInterface,Runnable {

    private boolean isConnectionActive;
    private final ClientGameController clientController;
    private String userID = ConstantsContainer.USERDIDDEF;
    private String nickName;
    private final String address;
    private final int port;
    private Thread messageListener;
    private Timer pingTimer;

    private Socket clientSocket;
    private final Object outLock = new Object();
    private ObjectInputStream in;
    private ObjectOutputStream out;

    /**
     * Public Constructor for ClientConnection
     * @param name NickName used by the Client in a Game
     * @param address IP of the Server
     * @param port Port used by the Server
     * @param clientController ClientGameController of the Client (could be CLI or GUI)
     */

    public ClientConnection(String name, String address, int port,ClientGameController clientController){
       this.nickName = name;
       this.address = address;
       this.port = port;
       this.clientController = clientController;
       isConnectionActive = true;
    }

    /**
     * Get UserID of the Client
     * @return The UserID of the Client
     */

    public String getUserID() {
        return userID;
    }

    /**
     * Set the UserID of the Client
     * @param userID The new UserID of the Client
     */

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Get the NickName of the Client
     * @return The NickName of the Client
     */

    public String getNickName() {
        return nickName;
    }

    /**
     * Set the NickName of the Client
     * @param nickName The new NickName of the Client
     */

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Function that handle the connection with the Server, and also send The Game configuration Parameters
     * @param numberOfPlayer The number of player selected by the Client
     * @throws ConnectException ConnectException
     */

    public void connectToServer(int numberOfPlayer) throws ConnectException {
        try{
            clientSocket = new Socket(address, port);
            clientSocket.setTcpNoDelay(true);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            sendMessage(new GameConfigMessage(userID,nickName, MessageSubType.ANSWER,numberOfPlayer));
            messageListener = new Thread(this);
            messageListener.start();
            startPingTimer();
        }catch (IOException e){
            throw new ConnectException("wrong parameters");
        }


    }

    @Override
    public boolean isConnectionActive() {
        return isConnectionActive;
    }

    @Override
    public void sendMessage(Message message) {
        try {
            synchronized (outLock) {
                out.writeObject(message);
                out.flush();
                out.reset();
            }
        }catch (IOException e){
            ClientGameController.LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Function that close connection with the Server, close the Socket and the thread of the Message Listener
     */

    public void closeConnection() {
        try {
            clientSocket.close();
            messageListener.interrupt();
        }catch (IOException e){
            ClientGameController.LOGGER.severe(e.getMessage());
        }
    }

    @Override
    public Message receiveMessage() throws IOException, ClassNotFoundException {
        try{
            return (Message) in.readObject();
        }
        catch (IOException e){
            throw new IOException();
        }
        catch (ClassNotFoundException c){
            throw new ClassNotFoundException();
        }
    }

    /**
     * Function that Start the Ping timer
     */

    public void startPingTimer(){
        pingTimer = new Timer();
        ClientPingTimerTask task = new ClientPingTimerTask(clientController);
        pingTimer.schedule(task,(long) ConfigLoader.getPingTimer() * 1000);
    }

    /**
     * Function that Stop the Ping Timer
     */

    public void stopPingTimer(){
        pingTimer.cancel();
    }

    /**
     * Thread that listen for new messages in the input stream, without blocking GUI and Cli, it also handle Ping task
     */

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message received = receiveMessage();

                if (received != null && received.getType() == MessageType.PING) {
                    stopPingTimer();
                    sendMessage(new Message(userID,nickName,MessageType.PING,MessageSubType.UPDATE));
                    startPingTimer();
                } else if (received != null) {
                    clientController.onUpdate(received);
                }
            } catch (IOException e) {
                stopPingTimer();
                closeConnection();
                ClientGameController.LOGGER.info("App Disconnected");
            } catch (ClassNotFoundException e) {
                ClientGameController.LOGGER.severe(e.getMessage());
            }
        }
    }
}
