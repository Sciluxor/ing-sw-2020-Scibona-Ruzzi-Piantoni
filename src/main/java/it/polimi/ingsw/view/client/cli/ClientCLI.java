package it.polimi.ingsw.view.client.cli;

import java.util.Scanner;

public class ClientCLI {

    private int numberOfPlayers;
    private String nickname;
    private String gameID;
    private String userID;
    private Color standard = Color.ANSI_RED;
    private SantoriniMap map = new SantoriniMap();
    private ChallengerChoiceCardsCLI challengerChoiceCardsCLI = new ChallengerChoiceCardsCLI();
    private String keyboard;
    private Color clientColor;
    private Lobby lobby;

    Scanner input = new Scanner(System.in);

    public void printCLI() {
        initializationClient();

        this.challengerChoiceCardsCLI.chooseCards(numberOfPlayers);

        System.out.println("Questa è la board vuota: ");
        map.printMap();

        System.out.println("\nQUESTA CHE SEGUE E' UNA PROVA DI ESECUZIONE CON FLUSSO PREDETERMINATO");
        this.provaEsecuzione();
    }

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

    public void provaEsecuzione() {
        System.out.print("Inserire ok per continuare: ");
        this.keyboard = input.nextLine();
        if(this.keyboard.equalsIgnoreCase("ok")) {
            //cella[0][0].setHasPlayer(true);
            this.map.setCellaHasPlayer(0,0,true);
            this.map.printMap();
        }

        System.out.print("Inserire lvl1: ");
        this.keyboard = input.nextLine();
        if(this.keyboard.equalsIgnoreCase("lvl1")) {
            //cella[0][0].setBuildingType("lvl1");
            this.map.setCellaBuildingType(0,0,keyboard);
            this.map.printMap();
        }

        System.out.print("Inserire lvl2: ");
        this.keyboard = input.nextLine();
        if(this.keyboard.equalsIgnoreCase("lvl2")) {
            //cella[0][0].setBuildingType("lvl2");
            this.map.setCellaBuildingType(0,0,keyboard);
            this.map.printMap();
        }

        System.out.print("Inserire lvl3: ");
        this.keyboard = input.nextLine();
        if(this.keyboard.equalsIgnoreCase("lvl3")) {
            //cella[0][0].setBuildingType("lvl3");
            this.map.setCellaBuildingType(0,0,keyboard);
            this.map.printMap();
        }

        System.out.print("Inserire dome: ");
        this.keyboard = input.nextLine();
        if(this.keyboard.equalsIgnoreCase("dome")) {
            //cella[0][0].setBuildingType("Dome");
            this.map.setCellaBuildingType(0,0,keyboard);
            //cella[0][0].setHasPlayer(false);
            this.map.setCellaHasPlayer(0,0,false);
            //cella[1][1].setHasPlayer(true);
            this.map.setCellaHasPlayer(1,1,true);

            this.map.printMap();
        }

        System.out.print("Inserire qualcunque cosa per pulire la schermata e concludere: ");
        this.keyboard = input.nextLine();
        this.map.clearConsole();
    }

}
