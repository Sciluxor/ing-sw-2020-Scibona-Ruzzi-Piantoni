package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.utils.Logger;

import javax.print.DocFlavor;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static void main( String[] args )
    {
        String ip ="127.0.0.1";
        int port = 4700;
        try {
            Socket clientSocket = new Socket(ip, port);
            Logger.info("Connection Established");
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            while(true){
                Message output = (Message) in.readObject();
                Logger.info(output.getContent());

                Scanner scanner = new Scanner(System.in);
                String toSend = scanner.nextLine();
                Message message = new Message(MessageType.START, MessageSubType.ERROR,toSend);
                out.writeObject(message);
                out.flush();
                out.reset();
                output = (Message) in.readObject();
                Logger.info(output.getContent());


            }


        }catch (IOException e){
            Logger.info("problem with connection");
        }
        catch (ClassNotFoundException c){
            Logger.info("problem with class");
        }

    }



}
