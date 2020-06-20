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
 * @author alessandroruzzi
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
     *
     * @param name
     * @param address
     * @param port
     * @param clientController
     */

    public ClientConnection(String name, String address, int port,ClientGameController clientController){
       this.nickName = name;
       this.address = address;
       this.port = port;
       this.clientController = clientController;
       isConnectionActive = true;
    }

    /**
     *
     * @return
     */

    public String getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     */

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     *
     * @return
     */

    public String getNickName() {
        return nickName;
    }

    /**
     *
     * @param nickName
     */

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     *
     * @param numberOfPlayer
     * @throws ConnectException
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
     *
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
     *
     */

    public void startPingTimer(){
        pingTimer = new Timer();
        ClientPingTimerTask task = new ClientPingTimerTask(clientController);
        pingTimer.schedule(task,(long) ConfigLoader.getPingTimer() * 1000);
    }

    /**
     *
     */

    public void stopPingTimer(){
        pingTimer.cancel();
    }

    /**
     *
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
