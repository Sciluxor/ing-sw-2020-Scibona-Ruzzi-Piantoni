package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.*;
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
        String nick = "Default";
        try {
            Socket clientSocket = new Socket(ip, port);
            Logger.info("Connection Established,waiting for server...");
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            while(true){
                Message output = (Message) in.readObject();
                Scanner scanner = new Scanner(System.in);

                if(output.getType().equals(MessageType.NICK) && output.getSubType().equals(MessageSubType.REQUEST)){
                    Logger.info("Please insert your NickName: ");
                    String nickname = scanner.nextLine();
                    out.writeObject(new NickNameMessage(nick,MessageSubType.ANSWER,nickname));
                    out.flush();
                }

                else if(output.getType().equals(MessageType.NICK) && output.getSubType().equals(MessageSubType.ERROR)){
                    Logger.info("NickName already in use or with size problem");
                    Logger.info("Please insert another nickname: ");
                    String nickname = scanner.nextLine();
                    out.writeObject(new NickNameMessage(nick,MessageSubType.ANSWER,nickname));
                    out.flush();

                }
                else if(output.getType().equals(MessageType.NICK) && output.getSubType().equals(MessageSubType.SETTED)){
                    nick = ((NickNameMessage) output) .getNickName();
                    Logger.info("Nickname Selected, waiting for the other players and for the game to start\n");

                }
                else if(output.getType().equals(MessageType.NUMBERPLAYER) && output.getSubType().equals(MessageSubType.REQUEST)){
                    Logger.info("You are the first player,please insert the number of player for this match: ");
                    int number = scanner.nextInt();
                    out.writeObject(new PlayerNumberMessage(nick,MessageSubType.ANSWER,number));
                    out.flush();

                }
                else if(output.getType().equals(MessageType.GAMESTART) && output.getSubType().equals(MessageSubType.UPDATE)){
                    Logger.info("Match created your game is starting now, number of total player -> " + ((gameStartedMessage) output).getPlayersNumber()+"\n");

                }

                out.reset();


            }


        }catch (IOException e){
            Logger.info("problem with connection");
        }
        catch (ClassNotFoundException c){
            Logger.info("problem with class");
        }

    }



}
