package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConfigLoader;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.PingTimerTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;

public class ClientConnection implements ConnectionInterface,Runnable {

    private boolean isConnectionActive;
    private ClientGameController clientController;
    private String userID = ConstantsContainer.USERDIDDEF;
    private String nickName;
    private String address;
    private int port;
    private Thread messageListener;
    private Timer pingTimer;

    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientConnection(String name, String address, int port,ClientGameController clientController){
       this.nickName = name;
       this.address = address;
       this.port = port;
       this.clientController = clientController;
       isConnectionActive = true;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void connectToServer(int numberOfPlayer){
        try{
            clientSocket = new Socket(address, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }catch (IOException e){
            ClientGameController.LOGGER.severe(e.getMessage());
        }
        sendMessage(new GameConfigMessage(userID,nickName, MessageSubType.ANSWER,numberOfPlayer,false,false,false));
        messageListener = new Thread(this);
        messageListener.start();
        startPingTimer();

    }

    public void updateNickName(String name){
        this.nickName = name;
    }

    @Override
    public boolean isConnectionActive() {
        return isConnectionActive;
    }

    @Override
    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
            out.reset();
        }catch (IOException e){
            ClientGameController.LOGGER.severe(e.getMessage());
        }
    }

    @Override
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

    public void startPingTimer(){
        pingTimer = new Timer();
        PingTimerTask task = new PingTimerTask(clientController);
        pingTimer.schedule(task,(long) ConfigLoader.getPingTimer() * 1000);
    }

    public void stopPingTimer(){
        pingTimer.cancel();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message received = receiveMessage();

                if (received != null && received.getType() == MessageType.PING) { // si deve controllare il ping anche lato server?
                    stopPingTimer();
                    startPingTimer();
                                                                   //fare due config sepratati per client e server?
                } else if (received != null) {
                    new Thread(() -> clientController.onUpdate(received)).start();
                }
            } catch (IOException e) {
                closeConnection();
            } catch (ClassNotFoundException e) {
                Logger.info("App Disconnected");
                ClientGameController.LOGGER.severe(e.getMessage());  //mettere una finally?
            }
        }
    }
}
