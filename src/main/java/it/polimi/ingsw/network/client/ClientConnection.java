package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.message.GameConfigMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection implements ConnectionInterface,Runnable {

    private boolean isConnectionActive;
    private ClientGameController clientController;
    private String userID = ConstantsContainer.USERDIDDEF;
    private String userName;
    private String address;
    private int port;
    private Thread messageListener;

    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientConnection(String name, String address, int port,ClientGameController clientController){
       this.userName = name;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void connectToServer(int numberOfPlayer){
        try{
            clientSocket = new Socket(address, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }catch (IOException e){
            ClientGameController.LOGGER.severe(e.getMessage());
        }
        sendMessage(new GameConfigMessage(userID,userName, MessageSubType.ANSWER,numberOfPlayer,false,false,false));
        messageListener = new Thread(this);
        messageListener.start();
    }

    public void updateUserName(String name){
        this.userName = name;
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

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message received = receiveMessage();

                if (received != null && received.getType() == MessageType.PING) {
                    //fare PING
                } else if (received != null) {
                    new Thread(() -> clientController.onUpdate(received)).start();
                }
            } catch (IOException e) {
                closeConnection();
            } catch (ClassNotFoundException e) {
                Logger.info("App Disconnected");
                ClientGameController.LOGGER.severe(e.getMessage());
            }
        }
    }
}
