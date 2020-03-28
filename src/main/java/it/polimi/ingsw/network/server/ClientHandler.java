package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.view.Server.VirtualView;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private Socket socket;
    private Server server;
    private boolean isViewActive = false;
    private boolean isConnectionActive;
    private VirtualView view;

    public ClientHandler(Server server, Socket socket){
        this.socket = socket;
        this.server = server;
        this.isConnectionActive = true;

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

    public void sendMessage(Message msg){

        try {
            objectOut.writeObject(msg);
            objectOut.flush();
            objectOut.reset();

        }catch (IOException e){
            Logger.info("error in connection");
        }

    }

    public void closeConnection(){
        //chiusura connesione
        try{
        objectIn.close();
        objectOut.close();
        socket.close();
        }catch (IOException e){
            Logger.info("problem in closing connection");
        }
    }

    @Override
    public void run() {

            try {
                this.objectOut = new ObjectOutputStream(socket.getOutputStream());
                this.objectIn = new ObjectInputStream(socket.getInputStream());
                server.firsLogin(this);
                while(isConnectionActive()) {
                    Message input = (Message) objectIn.readObject();

                    if (input.getType() == MessageType.NICK && input.getSubType() == MessageSubType.ANSWER) {
                        server.setNick(input,this);
                    }
                    else if(input.getType() == MessageType.NUMBERPLAYER && input.getSubType() == MessageSubType.ANSWER){
                        server.handleLobbyNumber(input);
                    }
                    else if(input.getType() == MessageType.DISCONNECTION){
                        server.handleClientDisconnectionBeforeStarting(input);
                    }
                    else {
                        server.onMessage(input); //runnarlo in un altro thread?
                    }

                }
                closeConnection();

            }catch (IOException e){
                Logger.info("player disconnected");
            }
            catch(ClassNotFoundException c){
                Logger.info("problem with class");
            }
        }

}
