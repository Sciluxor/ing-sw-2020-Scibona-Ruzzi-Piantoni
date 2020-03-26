package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.network.message.NickNameMessage;
import it.polimi.ingsw.utils.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private Socket socket;
    private Server server;
    private boolean isActive;

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

    public void sendMessage(Message msg){

        try {
            objectOut.writeObject(msg);
        }catch (IOException e){
            Logger.info("error in connection");
        }

    }

    @Override
    public void run() {

            try {
                this.objectOut = new ObjectOutputStream(socket.getOutputStream());
                this.objectIn = new ObjectInputStream(socket.getInputStream());
                server.firsLogin(this);
                while(isActive()) {
                    Logger.info("here");
                    Message input = (Message) objectIn.readObject();
                    Logger.info("here");

                    if (input.getType() == MessageType.NICK && input.getSubType() == MessageSubType.ANSWER) {

                        server.setNick(input,this);
                        Logger.info("here");



                    }
                    else if(input.getType() == MessageType.CONFIG){

                    }
                    else {
                        server.onMessage(input);
                    }

                }
                    //chiusura connesione
                    objectIn.close();
                    objectOut.close();
                    socket.close();


            }catch (IOException e){
                Logger.info("problem with input output stream");
            }
            catch(ClassNotFoundException c){
                Logger.info("problem with class");
            }
        }

}
