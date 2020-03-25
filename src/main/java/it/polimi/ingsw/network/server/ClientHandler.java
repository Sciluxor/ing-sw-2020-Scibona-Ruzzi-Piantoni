package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.utils.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private Socket socket;
    private Server server;

    public ClientHandler(Server server, Socket socket){
        this.socket = socket;
        this.server = server;

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
                while(true) {
                    Message input = (Message) objectIn.readObject();

                    if (input.getContent().equalsIgnoreCase("end")) {
                        break;

                    }
                    else {
                        Message message = new Message(MessageType.START, MessageSubType.ERROR,"received " + input.getContent());
                        objectOut.writeObject(message);
                        objectOut.flush();
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
