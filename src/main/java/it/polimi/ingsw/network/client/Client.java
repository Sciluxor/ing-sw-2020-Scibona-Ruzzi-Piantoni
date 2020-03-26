package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.Logger;

import javax.print.DocFlavor;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private boolean isGameStarted = false;
    private String nick = "Default";

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public static void main(String[] args )
    {
        String ip ="127.0.0.1";
        int port = 4700;

        Client client = new Client();
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
                    out.writeObject(new NickNameMessage(client.getNick(),MessageSubType.ANSWER,nickname));
                    out.flush();
                }

                else if(output.getType().equals(MessageType.NICK) && output.getSubType().equals(MessageSubType.ERROR)){
                    Logger.info("NickName already in use or with size problem");
                    Logger.info("Please insert another nickname: ");
                    String nickname = scanner.nextLine();
                    out.writeObject(new NickNameMessage(client.getNick(),MessageSubType.ANSWER,nickname));
                    out.flush();

                }
                else if(output.getType().equals(MessageType.NICK) && output.getSubType().equals(MessageSubType.SETTED)){
                    client.setNick(((NickNameMessage) output) .getNickName());
                    Logger.info("Nickname Selected.\n");

                }
                else if(output.getType().equals(MessageType.NUMBERPLAYER) && output.getSubType().equals(MessageSubType.REQUEST)){
                    Logger.info("Please choose your favourite modality(2/3 players) -> ");
                    int number = scanner.nextInt();
                    out.writeObject(new PlayerNumberMessage(client.getNick(),MessageSubType.ANSWER,number));
                    out.flush();

                }
                else if(output.getType().equals(MessageType.NUMBERPLAYER) && output.getSubType().equals(MessageSubType.ERROR)){
                    Logger.info("You have inserted wrong parameters");
                    Logger.info("Please choose your favourite modality(2/3 players) -> ");
                    int number = scanner.nextInt();
                    out.writeObject(new PlayerNumberMessage(client.getNick(),MessageSubType.ANSWER,number));
                    out.flush();

                }
                else if(output.getType().equals(MessageType.WAITPLAYER) && output.getSubType().equals(MessageSubType.UPDATE)){
                    Logger.info("You have been inserted in lobby,waiting for other players to join,Type \"close\" to stop the server");
                    client.closeClientIfRequestedAsynchronously(out);

                }
                else if(output.getType().equals(MessageType.GAMESTART) && output.getSubType().equals(MessageSubType.UPDATE)) {
                    client.setGameStarted(true);
                    Logger.info("\nMatch created your game is starting now, number of total player -> " + ((gameStartedMessage) output).getPlayersNumber() + "\n");

                }
                else if(output.getType().equals(MessageType.DISCONNECTION) && output.getSubType().equals(MessageSubType.SETTED)) {
                    Logger.info("\nDisconnetted from Server,closing Application...");

                }

                out.reset();

            }


        }catch (IOException e){
            Logger.info("App Disconnected");
        }
        catch (ClassNotFoundException c){
            Logger.info("problem with class");
        }



    }

    private void closeClientIfRequestedAsynchronously(ObjectOutputStream out) {
        new Thread(() -> {
            String input = "";
            while (!isGameStarted && !input.equalsIgnoreCase("close")) {
                input = new Scanner(System.in).nextLine();
            }
            if(input.equalsIgnoreCase("close") && !isGameStarted){
                try {
                    out.writeObject(new Message(nick, MessageType.DISCONNECTION, MessageSubType.REQUEST));
                    out.flush();
                }catch (IOException e){
                    Logger.info("Error in Disconnetting");
                }
            }

        }).start();
    }



}
