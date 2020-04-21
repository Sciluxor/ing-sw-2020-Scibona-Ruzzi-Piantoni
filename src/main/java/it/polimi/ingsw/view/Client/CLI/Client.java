package it.polimi.ingsw.view.Client.CLI;

import java.util.Random;
import java.util.Scanner;

public class Client {

    private int numberOfPlayers;
    private String nickname;
    private String gameID;
    private String userID;
    private Color standard = Color.ANSI_RED;
    private Color clientColor;
    private Lobby lobby;

    Scanner input = new Scanner(System.in);

    public void initializationClient() {

        System.out.println(this.standard + "Inserire il nickname: ");
        setNickname(input.nextLine());

        System.out.println("Inserire in numero (2/3) di giocatori: ");
        setNumberOfPlayers(input.nextInt());

        //lobby = new LobbyGui();
        //lobby.waitingLobby();
        //this.clientColor = lobby.setColor();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        while(nickname.length()<4 || nickname.length()>20) {
            System.out.println("\nLUNGHEZZA NICKNAME NON VALIDA\nReinserire il nickname: ");
            nickname = input.nextLine();
        }
        this.nickname = nickname;
        System.out.println("Nickname setted!\n");
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        while(numberOfPlayers < 2 || numberOfPlayers > 3)
        {
            System.out.println("\nNUMERO INVALIDO DI GIOCATORI\nReinserire il numero di giocatori (2/3): ");
            numberOfPlayers = input.nextInt();
        }
        this.numberOfPlayers = numberOfPlayers;
        System.out.println("NumberOfPlayers setted!\n" + standard.RESET);
    }

    public void setWorkersColor(Color clientColor) {

    }

}
