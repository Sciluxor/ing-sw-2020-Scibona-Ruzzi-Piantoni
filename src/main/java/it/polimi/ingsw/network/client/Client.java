package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConsoleColor;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.Logger;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private boolean isGameStarted = false; //variabili da spostare
    private String nick = "Default";
    private String userID = "Default";
    private Socket clientSocket;
    private int numberOfPlayers;
    private Thread mainThread;

    private PrintStream printer = new PrintStream(System.out);


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

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

    public static void main(String[] args ){
        String ip ="127.0.0.1";  // mettere in json da caricare come default
        int port = 4700;

        /*
        String startView = "";

        if(args.length == 1 && (args[0].equalsIgnoreCase("gui") || args[0].equalsIgnoreCase("cli"))){
            startView = args[0];
        }
        else{
                //startView = funzione per chiedere se cli o gui
        }

        //la connessione viene chiesta nella gui o nella cli

        if(startView.equalsIgnoreCase("gui")) {
            //funziona per far partire la gui
        }
        else {
            //new Cli.startCli();
        }
*/
        Client client = new Client();

        ConsoleColor.loadColor();
        client.setClientSocket(ip, port);
        Socket clientSocket = client.getClientSocket();



        try {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            client.mainThread = new Thread(()-> client.processNickAndNumber(out,client));

            client.mainThread.start();
            /*
            new Thread(()-> {
                try {
                    client.simultaneousePrint();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();*/

            //client.closeClientForTimeAsynchronously(clientSocket);
            while (!client.isGameStarted){

            }
            while(Thread.currentThread().isAlive()){  //runnare il process del message in parallelo
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

    private void simultaneousePrint(){

        try {
            Thread.sleep(10000);
        }catch (InterruptedException inter){
            Thread.currentThread().interrupt();
        }
        //mainThread.interrupt();
        clearScreen2();
        Logger.info("ciao");

    }


    public void processMessage(Client client,Message output, ObjectOutputStream out){

        try {
            Scanner scanner = new Scanner(System.in);


            if (output.getType().equals(MessageType.CONFIG) && output.getSubType().equals(MessageSubType.NICKUSED)) {
                setUserID(output.getMessage());
                Logger.info("NickName already in use. ");
                Logger.info("Please insert another nickname: ");
                String nickname = scanner.nextLine();
                while(nickname.length() < ConstantsContainer.MIN_LENGHT_NICK || nickname.length() > ConstantsContainer.MAX_LENGHT_NICK){
                    Logger.info("\nNickname must be shorter than 20 characters and longer than 4 characters. Please, reinsert nickname: ");
                    nickname = scanner.nextLine();
                }
                client.setNick(nickname);
                out.writeObject(new GameConfigMessage(client.getUserID(),nickname,MessageSubType.UPDATE,numberOfPlayers ,false,false,false));
                out.flush();

            } else if (output.getType().equals(MessageType.WAITPLAYER) && output.getSubType().equals(MessageSubType.UPDATE)) {
                setUserID(output.getMessage());
                clearScreen2();
                Logger.info("\nYou have been inserted in match, waiting for other players to join. Type \"close\" to stop the server, or \"back\" to go back to modality selection.");
                Logger.info("\nActual Players:");
                for(int i = 0; i < ((WaitPlayerMessage) output).getNickNames().size();i++){
                    String color = ConsoleColor.getColor(((WaitPlayerMessage) output).getColors().get(i));
                    String nickName = ((WaitPlayerMessage) output).getNickNames().get(i);
                    System.out.println(color + nickName + ConsoleColor.RESET);

                }
                client.closeClientIfRequestedAsynchronously(out,client);

            }
            else if (output.getType().equals(MessageType.WAITPLAYER) && output.getSubType().equals(MessageSubType.NEWPLAYER)) {
                setUserID(output.getMessage());
                clearScreen2();
                Logger.info("\nYou have been inserted in match, waiting for other players to join. Type \"close\" to stop the server, or \"back\" to go back to modality selection.");
                Logger.info("\nActual Players:");
                for(int i = 0; i < ((WaitPlayerMessage) output).getNickNames().size();i++){
                    String color = ConsoleColor.getColor(((WaitPlayerMessage) output).getColors().get(i));
                    String nickName = ((WaitPlayerMessage) output).getNickNames().get(i);
                    System.out.println(color + nickName + ConsoleColor.RESET);

                }

            }else if (output.getType().equals(MessageType.WAITPLAYER) && output.getSubType().equals(MessageSubType.REMOVEDPLAYER)) {
                setUserID(output.getMessage());
                clearScreen2();
                Logger.info("\nYou have been inserted in match, waiting for other players to join. Type \"close\" to stop the server, or \"back\" to go back to modality selection.");
                Logger.info("\nActual Players:");
                for(int i = 0; i < ((WaitPlayerMessage) output).getNickNames().size();i++){
                    String color = ConsoleColor.getColor(((WaitPlayerMessage) output).getColors().get(i));
                    String nickName = ((WaitPlayerMessage) output).getNickNames().get(i);
                    System.out.println(color + nickName + ConsoleColor.RESET);

                }

            }else if (output.getType().equals(MessageType.GAMESTART) && output.getSubType().equals(MessageSubType.UPDATE)) {
                client.setGameStarted(true);
                Thread.sleep(200);

                Logger.info("\nYour game is starting now. Number of total player -> "  + "Your gameID is: " + ((GameStartedMessage)output).getGameID() + "\n");

            } else if (output.getType().equals(MessageType.DISCONNECTION) && output.getSubType().equals(MessageSubType.SETTED)) {
                Logger.info("\nDisconnetted from Server, closing Application...");

            }
            else if (output.getType().equals(MessageType.DISCONNECTION) && output.getSubType().equals(MessageSubType.TIMEENDED)) {
                Logger.info("\nYou were disconnected due to inactivity, closing Application...");
                client.clientSocket.close();
                System.exit(1);
            }
            else if (output.getType().equals(MessageType.DISCONNECTION) && output.getSubType().equals(MessageSubType.CONNECTIONLOST)) {
                Logger.info("\nYou were disconnected due to you have lost connection with the server. You are reconnecting...");

                //RICONNESIONE
                Logger.info("\nPlease, reinsert your nickname: ");
                String nickname = scanner.nextLine();
                Logger.info(("\nNow insert your gameID: "));
                String gameID = scanner.nextLine();
                //out.writeObject(new ReconnectionMessage(client.getNick(), MessageSubType.ANSWER, gameID, nickname));
                out.flush();

            }

            out.reset();
        }catch (IOException e){
            Logger.info("\nit.polimi.ingsw.App Disconnected");
        }
        catch (InterruptedException inter){
            Thread.currentThread().interrupt();
        }


    }

    public static void clearScreen() throws IOException {
        Runtime.getRuntime().exec("clear");
    }

    public static void clearScreen2(){
        for(int i = 0;i <30;i++)
            Logger.info("\n");

    }


    public void processNickAndNumber(ObjectOutputStream out,Client client){

        try {
            Logger.info("Please insert your NickName: ");
            String nickname = new Scanner(System.in).nextLine();
            while(nickname.length() < ConstantsContainer.MIN_LENGHT_NICK || nickname.length() > ConstantsContainer.MAX_LENGHT_NICK){
                Logger.info("\nNickname must be shorter than 20 characters and longer than 4 characters. Please, reinsert nickname: ");
                nickname = new Scanner(System.in).nextLine();   //  nick con lo spazio finale li considera uguali
            }
            client.setNick(nickname);

            //Get numberOfPlayers and send it to Server
            Logger.info("\nPlease choose your favourite modality(2/3 players) -> ");
            int number = new Scanner(System.in).nextInt();
            while(number != 3 && number != 2){
                Logger.info(("\nWrong modality selected. Modality must be with 2/3 players. Please, reinsert modality -> "));
                number = new Scanner(System.in).nextInt();
            }
            client.numberOfPlayers = number;
            out.writeObject(new GameConfigMessage(client.getUserID(),nickname,MessageSubType.ANSWER,number,false,false,false));
            out.flush();
            Logger.info("");

            while(!client.isGameStarted()){
                out.writeObject(new Message(client.userID,client.nick,MessageType.PING,MessageSubType.UPDATE));
                out.flush();
            }
        }catch (IOException e){
            Logger.info("Error in Disconnetting");
        }

    }


    private void closeClientIfRequestedAsynchronously(ObjectOutputStream out,Client client) {
        new Thread(() -> {
            String input = "";
            while (!isGameStarted && (!input.equalsIgnoreCase("close") && !input.equalsIgnoreCase("back"))) {
                input = new Scanner(System.in).nextLine();
            }
            if(input.equalsIgnoreCase("close") && !isGameStarted){
                try {
                    out.writeObject(new Message(userID,nick, MessageType.DISCONNECTION, MessageSubType.REQUEST));
                    out.flush();
                    client.clientSocket.close();
                    Logger.info("Closed connection");
                    System.exit(0);
                }catch (IOException e){
                    Logger.info("Error in Disconnetting");
                }
            }
            if(input.equalsIgnoreCase("back") && !isGameStarted){
                try {
                    out.writeObject(new Message(userID,nick, MessageType.DISCONNECTION, MessageSubType.BACK));
                    out.flush();

                    processNickAndNumber(out,client);
                }catch (IOException e){
                    Logger.info("Error in Disconnetting");
                }
            }

        }).start();
    }


}