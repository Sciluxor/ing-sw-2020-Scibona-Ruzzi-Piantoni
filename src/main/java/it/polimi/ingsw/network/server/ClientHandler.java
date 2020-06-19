package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.message.GameConfigMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.utils.ConfigLoader;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.LobbyTimerTask;
import it.polimi.ingsw.utils.ServerPingTimerTask;
import it.polimi.ingsw.view.server.VirtualView;

import java.io.*;
import java.net.Socket;
import java.util.Timer;

/**
 * Class that Handle a specific Client, listening for new messages through input stream and sending messages through output stream
 * @author alessandroruzzi
 * @version 1.0
 * @since 2020/06/19
 */

public class ClientHandler implements Runnable, ConnectionInterface {

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    private final Object inputLock = new Object();
    private final Object outputLock = new Object();

    private final Socket socket;
    private final Server server;
    private boolean isConnectionActive;
    private boolean isErrorStopper;
    private VirtualView view;
    private Timer lobbyTimer;
    private Timer pingTimer;
    private int newNickCounter;

    private String userID = ConstantsContainer.USERDIDDEF;
    private String nickName = ConstantsContainer.NICKDEF;

    /**
     * Public constructor for the Socket Handler, initialize parameters
     * @param socket Socket of the connection
     * @param server The Server to which refer
     */

    public ClientHandler(Server server, Socket socket){
        this.socket = socket;
        this.server = server;
        this.isConnectionActive = true;
        this.isErrorStopper = false;

    }

    /**
     * Function that check if The client of this ClientHandler is the one that stopped the game
     * @return True if it's the stopper, false otherwise
     */

    public boolean isErrorStopper() {
        return isErrorStopper;
    }

    /**
     * Get the Nickname of the Client
     * @return The Nickname of the Client
     */

    public String getNickName() {
        return nickName;
    }

    /**
     * Set The Nickname of the Client
     * @param nickName New Nickname of the Client
     */

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Get UserID of the Client
     * @return The UserID of the Client
     */

    public String getUserID() {
        return userID;
    }

    /**
     * Set UserID of the Client
     * @param userID New UserID of the Client
     */

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Function that check if the connection with the client is active
     * @return True if the connection is active, false otherwise
     */

    public boolean isConnectionActive() {
        return isConnectionActive;
    }

    /**
     * Set the connection to active or not active
     * @param connectionActive New state of connection, could be true or false
     */

    public void setConnectionActive(boolean connectionActive) {
        isConnectionActive = connectionActive;
    }

    /**
     * Get the VirtualView of the Client
     * @return The VirtualView of the Client
     */

    public VirtualView getView() {
        return view;
    }

    /**
     * Set the VirtualView of the Client
     * @param view New VirtualView of the Client
     */

    public void setView(VirtualView view) {
        this.view = view;
    }

    /**
     * Function that send a message to the Client, through output stream
     * @param msg The message to send
     */

    public synchronized void sendMessage(Message msg){
      synchronized (outputLock) {
          try {
              objectOut.writeObject(msg);
              objectOut.flush();
              objectOut.reset();

          } catch (IOException e) {
              Server.LOGGER.severe(e.getMessage());
          }
      }

    }

    /**
     * Function that receive a message through inputStream
     * @return The message received
     * @throws IOException IOException
     * @throws ClassNotFoundException ClassNotFoundException
     */

    public Message receiveMessage() throws IOException, ClassNotFoundException {
        try{
            return (Message) objectIn.readObject();
        }
        catch (IOException e){
            throw new IOException();
        }
        catch (ClassNotFoundException c){
            throw new ClassNotFoundException();
        }
    }

    /**
     * Function that close connection with the client sending a message
     * @param message The message to send to the client for the disconnection
     */

    public void closeConnection(Message message){
        sendMessage(message);
        server.removeFromConnections(this);
        close();
    }

    /**
     * Function that close the socket and input and output stream
     */

    public void close(){
        try{
            objectIn.close();
            objectOut.close();
            socket.close();
        }catch (IOException e){
            Server.LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Function that close the connection after a disconnection without sending a message to the Client
     */

    public void closeAfterDisconnection()
    {
        server.removeFromConnections(this);
        try{
            socket.close();
        }catch (IOException e){
            Server.LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Function that close connection with the client when the server is stopped by the administrator
     */

    public void closeConnectionFromServer(){
        sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.DISCONNECTION,MessageSubType.SERVERSTOPPED));
        close();
    }

    /**
     * Function that forward the message to the VirtualView of the Client
     * @param message Message received from the Client to send to the VirtualView
     */

    public void dispatchMessageToVirtualView(Message message){
        view.processMessageReceived(message);
    }

    /**
     * Function that start the timer for the Client in the lobby
     */

    public void startLobbyTimer(){
        lobbyTimer = new Timer();
        LobbyTimerTask task = new LobbyTimerTask(server,this,userID,nickName);
        lobbyTimer.schedule(task, (long) ConfigLoader.getLobbyTimer() * 1000);
    }

    /**
     * Function that Stop the Lobby timer for the Client
     */

    public void stopLobbyTimer(){
        lobbyTimer.cancel();

    }

    /**
     * Function that start the timer for the ping
     */

    public void startPingTimer(){
        pingTimer = new Timer();
        ServerPingTimerTask task = new ServerPingTimerTask(server,this,userID,nickName);
        pingTimer.schedule(task, (long) ConfigLoader.getPingTimer() * 1000);
    }

    /**
     * Function that Stop the Ping timer for the Client
     */

    public void stopPingTimer(){
        pingTimer.cancel();
    }

    /**
     * Function that send a ping message to the client
     */

    public void ping(){
        sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.PING,MessageSubType.UPDATE));
    }

    /**
     * Function that advise the Server if the Timer for the Turn has ended
     * @param message The disconnection message to send to the Server
     */

    public void turnTimerEnded(Message message){
        server.handleDisconnection(userID,this,message);
    }

    /**
     * Function called when the Client of this ClientHandler disconnect during the game
     * @param message The disconnection message to send to the Server
     */

    public void clientError(Message message){
        isErrorStopper = true;
        server.handleDisconnection(userID,this,message);
    }

    /**
     * Specific Thread that receive messages from the Client and forward them to the Server or to the VirtualView(depends on the type of message)
     * It also handle the Ping task
     */

    @Override
    public void run() {

            try{
                this.objectOut = new ObjectOutputStream(socket.getOutputStream());
                this.objectIn = new ObjectInputStream(socket.getInputStream());
                startLobbyTimer();
                startPingTimer();
                while(isConnectionActive()) {
                    synchronized (inputLock) {
                        Message input = receiveMessage();
                        if (input.getType() == MessageType.CONFIG && input.getSubType() == MessageSubType.ANSWER) {
                            stopLobbyTimer();
                            this.newNickCounter = 0;
                            server.insertPlayerInGame(input, this, true);
                            server.moveGameStarted();
                        } else if (input.getType() == MessageType.PING) {
                            stopPingTimer();
                            startPingTimer();
                        } else if (input.getType() == MessageType.CONFIG && input.getSubType() == MessageSubType.UPDATE) {
                            nickMaxTry(input);

                        } else if ((input.getType() == MessageType.DISCONNECTION)) {
                            server.handleDisconnection(userID, this, input);

                            if (!input.getSubType().equals(MessageSubType.BACK))
                                break;

                        } else {
                            dispatchMessageToVirtualView(input);
                        }

                    }
                }

            }catch (IOException e){
                stopLobbyTimer();
                stopPingTimer();
                isConnectionActive = false;
                server.handleDisconnection(userID,this,new Message(userID,nickName,MessageType.DISCONNECTION,MessageSubType.ERROR));
            }
            catch(ClassNotFoundException c){
                Server.LOGGER.severe(c.getMessage());
            }
            finally {
                onFinalDisconnection();
            }
        }

    /**
     * Function called to move the Client in another game,called when the Client keep sending the same nickname(already in use in the match)
     * @param input The Configuration message received from the Client
     */

    public void nickMaxTry(Message input){
        synchronized (inputLock) {
            stopLobbyTimer();
            newNickCounter++;
            if (newNickCounter > ConstantsContainer.MAXTRYTOCHANGENICK) {
                input.setMessageSubType(MessageSubType.ANSWER);
                this.newNickCounter = 0;
                server.handleDisconnection(userID, this, new Message(input.getSender(), input.getNickName(), MessageType.DISCONNECTION, MessageSubType.NICKMAXTRY));
                server.insertPlayerInGame(input, this, false);
            } else {
                if(!server.checkValidConfig(input.getNickName(),((GameConfigMessage) input).getNumberOfPlayer())) {
                    server.nickError(this, input);
                    return;
                }
                dispatchMessageToVirtualView(input);
            }
            server.moveGameStarted();
         }
        }

    /**
     * Function Called when the Client Disconnect, handle disconnection phase, stop the timers and call the functions to close the socket
     */

    public void onFinalDisconnection(){
            stopLobbyTimer();
            stopPingTimer();
            if(isConnectionActive)
                closeConnection(new Message(ConstantsContainer.SERVERNAME, MessageType.DISCONNECTION, MessageSubType.UPDATE));
            else
                closeAfterDisconnection();
            Server.LOGGER.info("player disconnected");
        }

}
