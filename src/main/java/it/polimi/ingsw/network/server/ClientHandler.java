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
    private boolean isActive;
    private VirtualView view;

    public ClientHandler(Server server, Socket socket){
        this.socket = socket;
        this.server = server;
        this.isActive = true;

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
                while(isActive()) {
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
                        server.onMessage(input);
                    }

                }
                closeConnection();

            }catch (IOException e){
                Logger.info("problem with input output stream");
            }
            catch(ClassNotFoundException c){
                Logger.info("problem with class");
            }
        }

}
