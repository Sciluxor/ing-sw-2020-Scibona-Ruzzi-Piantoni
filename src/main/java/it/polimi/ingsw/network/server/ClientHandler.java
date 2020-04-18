package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.utils.ConfigLoader;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.LobbyTimerTask;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.view.Server.VirtualView;

import java.io.*;
import java.net.Socket;
import java.util.Timer;

public class ClientHandler implements Runnable, ConnectionInterface {

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private Socket socket;
    private Server server;
    private boolean isViewActive = false;
    private boolean isConnectionActive;
    private VirtualView view;
    private Timer lobbyTimer;
    private int newNickCounter;

    private String userID ="default";
    private String nickName = "def";

    public ClientHandler(Server server, Socket socket){
        this.socket = socket;
        this.server = server;
        this.isConnectionActive = true;


    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isConnectionActive() {
        return isConnectionActive;
    }

    public void setConnectionActive(boolean connectionActive) {
        isConnectionActive = connectionActive;
    }

    public boolean isViewActive() {
        return isViewActive;
    }

    public void setViewActive(boolean active) {
        isViewActive = active;
    }

    public VirtualView getView() {
        return view;
    }

    public void setView(VirtualView view) {
        this.view = view;
    }

    public void sendMessage(Message msg){//fare un'altra funzione per mandare in asincrono i messaggi

        try {
            objectOut.writeObject(msg);
            objectOut.flush();
            objectOut.reset();

        }catch (IOException e){
            Logger.info("error in connection");
        }

    }

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

    public void closeConnection(){
        //chiusura connesione
        sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.DISCONNECTION,MessageSubType.TIMEENDED));
        server.removeFromConnections(this);
        close();
    }

    public void close(){
        try{
            objectIn.close();
            objectOut.close();
            socket.close();
        }catch (IOException e){
            Logger.info("problem in closing connection");
        }
    }

    public void closeAfterDisconnection()
    {
        server.removeFromConnections(this);
        try{
            socket.close();
        }catch (IOException e){
            Logger.info("problem in closing connection");
        }
    }

    public void closeConnectionFromServer(){
        sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.DISCONNECTION,MessageSubType.SERVERSTOPPED));
        close();
    }

    public void dispatchMessageToVirtualView(Message message){
        view.processMessageReceived(message);
    }

    public void startLobbyTimer(){
        lobbyTimer = new Timer();
        LobbyTimerTask task = new LobbyTimerTask(this,userID);
        lobbyTimer.schedule(task, ConfigLoader.getLobbyTimer() * 1000);
    }

    public void stopLobbyTimer(){
        lobbyTimer.cancel();

    }

    @Override
    public void run() {

            try {
                this.objectOut = new ObjectOutputStream(socket.getOutputStream());
                this.objectIn = new ObjectInputStream(socket.getInputStream());
                startLobbyTimer();
                while(isConnectionActive()) {
                    Message input = receiveMessage();

                    if (input.getType() == MessageType.CONFIG && input.getSubType() == MessageSubType.ANSWER) {
                        stopLobbyTimer();
                        this.newNickCounter = 0;
                        server.insertPlayerInGame(input,this,true);
                        server.moveGameStarted();
                    }
                    else if (input.getType() == MessageType.CONFIG && input.getSubType() == MessageSubType.UPDATE) {
                        stopLobbyTimer();
                        newNickCounter++;
                        if(newNickCounter > ConstantsContainer.MAXTRYTOCHANGENICK){
                            input.setMessageSubType(MessageSubType.ANSWER);
                            this.newNickCounter = 0;
                            dispatchMessageToVirtualView(new Message(input.getSender(),MessageType.DISCONNECTION,MessageSubType.NICKMAXTRY));
                            server.insertPlayerInGame(input,this,false);
                        }
                        else{
                            dispatchMessageToVirtualView(input);
                        }
                        server.moveGameStarted();
                    }
                    else if((input.getType() == MessageType.DISCONNECTION && input.getSubType() == MessageSubType.REQUEST)){
                        server.stopGame(userID,this,input);
                        //completare questo
                        //confermare la ricezione del messaggio
                        //terminare la partita
                    }
                    else {
                        dispatchMessageToVirtualView(input); //runnarlo in un altro thread?
                    }

                }
                closeConnection();

            }catch (IOException e){
                isConnectionActive = false;
                //controllo per userID false
                server.stopGame(userID,this,new Message(userID,nickName,MessageType.DISCONNECTION,MessageSubType.ERROR)); //non bisogna chiudere la connesione ai player rimasti, si richiede se vogliono iniziare una nuovs psrtita
                Logger.info("player disconnected");//gestire la disconessione del player
            }
            catch(ClassNotFoundException c){
                Logger.info("problem with class");
            }
        }

        //aggiungere una funziona per pingare il client, se non risponde eliminarlo e far terminare la partita.
}
