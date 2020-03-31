package it.polimi.ingsw.network.client;

import com.sun.jdi.VMDisconnectedException;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.Logger;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.LinkOption;
import java.util.Scanner;

//classe di prova solo per testare il server



public class Client {
    private boolean isGameStarted = false;
    private String nick = "Default";
    private Socket clientSocket;

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

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(String ip, int port) {
        try {
            this.clientSocket = new Socket(ip, port);
            Logger.info("CONNECTION ESTABLISHED.\n\n");
        } catch (IOException e) {
            return;
        }
    }

    public static void main(String[] args ) throws IOException {
        String ip ="127.0.0.1";
        int port = 4700;

        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        client.setClientSocket(ip, port);
        Socket clientSocket = client.getClientSocket();

        try {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            //Get nickname and send it to Server
            Logger.info("Please insert your NickName: ");
            String nickname = scanner.nextLine();
            while(nickname.length() < ConstantsContainer.MIN_LENGHT_NICK || nickname.length() > ConstantsContainer.MAX_LENGHT_NICK){
                Logger.info("\nNickname must be shorter than 20 characters and longer than 4 characters. Please, reinsert nickname: ");
                nickname = scanner.nextLine();
            }
            out.writeObject(new NickNameMessage(client.getNick(), MessageSubType.ANSWER, nickname));
            out.flush();

            //Get numberOfPlayers and send it to Server
            Logger.info("\nPlease choose your favourite modality(2/3 players) -> ");
            int number = scanner.nextInt();
            while(number != 3 && number != 2){
                Logger.info(("\nWrong modality selected. Modality must be with 2/3 players. Please, reinsert modality -> "));
                number = scanner.nextInt();
            }
            out.writeObject(new PlayerNumberMessage(client.getNick(), MessageSubType.ANSWER, number));
            out.flush();
            
            //client.closeClientForTimeAsynchronously(clientSocket);
            while(true){  //runnare il process del message in parallelo
                Message output = (Message) in.readObject();
                new Thread(()-> client.processMessage(client,output,out)).start();
            }
        }catch (IOException e) {
            //clientSocket.close();
            return;
        }catch (ClassNotFoundException e){
            Logger.info("problem with class");
        }

        //clientSocket.close();
        System.exit(1);

    }

    public void processMessage(Client client,Message output, ObjectOutputStream out){

        try {
            Scanner scanner = new Scanner(System.in);

            if (output.getType().equals(MessageType.NICK) && output.getSubType().equals(MessageSubType.ERROR)) {
                Logger.info("NickName already in use. ");
                Logger.info("Please insert another nickname: ");
                String nickname = scanner.nextLine();
                out.writeObject(new NickNameMessage(client.getNick(), MessageSubType.ANSWER, nickname));
                out.flush();

            } else if (output.getType().equals(MessageType.NICK) && output.getSubType().equals(MessageSubType.SETTED)) {
                client.setNick(((NickNameMessage) output).getNickName());
                Logger.info("Nickname Selected.\n");

            } else if (output.getType().equals(MessageType.WAITPLAYER) && output.getSubType().equals(MessageSubType.UPDATE)) {
                Logger.info("\nYou have been inserted in match, waiting for other players to join. Type \"close\" to stop the server, or \"back\" to go back to modality selection.");
                client.closeClientIfRequestedAsynchronously(out);

            } else if (output.getType().equals(MessageType.GAMESTART) && output.getSubType().equals(MessageSubType.UPDATE)) {
                client.setGameStarted(true);
                Logger.info("\nYour game is starting now. Number of total player -> " + ((gameStartedMessage) output).getPlayersNumber() + "\n" + "Your gameID is: " + ((gameStartedMessage)output).getGameID() + "\n");

            } else if (output.getType().equals(MessageType.DISCONNECTION) && output.getSubType().equals(MessageSubType.SETTED)) {
                Logger.info("\nDisconnetted from Server, closing Application...");

            }
            else if (output.getType().equals(MessageType.DISCONNECTION) && output.getSubType().equals(MessageSubType.TIMEENDED)) {
                Logger.info("\nYou were disconnected due to inactivity, closing Application...");
                System.exit(1);
            }
            else if (output.getType().equals(MessageType.DISCONNECTION) && output.getSubType().equals(MessageSubType.CONNECTIONLOST)) {
                Logger.info("\nYou were disconnected due to you have lost connection with the server. You are reconnecting...");

                //RICONNESIONE
                Logger.info("\nPlease, reinsert your nickname: ");
                String nickname = scanner.nextLine();
                Logger.info(("\nNow insert your gameID: "));
                String gameID = scanner.nextLine();
                out.writeObject(new ReconnectionMessage(client.getNick(), MessageSubType.ANSWER, gameID, nickname));
                out.flush();                                                                              

            }

            out.reset();
        }catch (IOException e){
            Logger.info("\nApp Disconnected");
        }


    }



    private void closeClientIfRequestedAsynchronously(ObjectOutputStream out) {
        new Thread(() -> {
            String input = "";
            while (!isGameStarted && (!input.equalsIgnoreCase("close") && !input.equalsIgnoreCase("back"))) {
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
            if(input.equalsIgnoreCase("back") && !isGameStarted){
                try {
                    out.writeObject(new Message(nick, MessageType.DISCONNECTION, MessageSubType.BACK));
                    out.flush();
                }catch (IOException e){
                    Logger.info("Error in Disconnetting");
                }
            }

        }).start();
    }

}