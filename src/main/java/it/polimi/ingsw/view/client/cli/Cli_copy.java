package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.network.client.ClientGameController;
import java.util.Scanner;

public class Cli_copy {

    private Color colorCLIStandardClient;
    private static Color colorPrinter = Color.ANSI_RED;
    private String keyboard;

    private int numberOfPlayers;
    private String nickname;
    private String gameID;
    private String userID;
    private Color clientOut = Color.ANSI_RED;
    private SantoriniMap map = new SantoriniMap();
    private ChoiceCardsCLI choiceCardsCLI = new ChoiceCardsCLI();
    private Color clientColor;


    public Cli_copy() {
        this.colorCLIStandardClient = Color.ANSI_RED;
        this.keyboard = null;
    }

    public void printCLI() {
        initializationClient();

        this.choiceCardsCLI.selectChoosenCards(numberOfPlayers);

        System.out.println("Questa è la board vuota: ");
        map.printMap();

        //System.out.println("\nQUESTA CHE SEGUE E' UNA PROVA DI ESECUZIONE CON FLUSSO PREDETERMINATO");
        //this.provaEsecuzione();

        System.out.println("\nQUESTA CHE SEGUE E' UNA PROVA DI ESECUZIONE NON PREDETERMINATA");
        this.provaEsecuzioneNonPredeterminato();
    }

    public void initializationClient() {

        Scanner input = new Scanner(System.in);

        System.out.println(setOutputColor("Inserire il nickname: "));
        setNickname(input.nextLine());

        System.out.println(setOutputColor("Inserire in numero (2/3) di giocatori: "));
        setNumberOfPlayers(input.nextInt());

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {

        Scanner input = new Scanner(System.in);

        while(nickname.length()<4 || nickname.length()>20) {
            System.out.println(setOutputColor("\nLUNGHEZZA NICKNAME NON VALIDA\nReinserire il nickname: "));
            nickname = input.nextLine();
        }
        this.nickname = nickname;
        System.out.println(setOutputColor("Nickname setted!\n"));
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {

        Scanner input = new Scanner(System.in);

        while(numberOfPlayers < 2 || numberOfPlayers > 3)
        {
            System.out.println(setOutputColor("\nNUMERO INVALIDO DI GIOCATORI\nReinserire il numero di giocatori (2/3): "));
            numberOfPlayers = Integer.parseInt(input.nextLine());
        }
        this.numberOfPlayers = numberOfPlayers;
        System.out.println(setOutputColor("NumberOfPlayers setted!\n"));
    }

    public void provaEsecuzione() {
        String keyboard;
        Scanner input = new Scanner(System.in);

        System.out.print("Inserire ok per continuare: ");
        keyboard = input.nextLine();
        if(keyboard.equalsIgnoreCase("ok")) {
            this.map.setCellaHasPlayer(0,0);
            this.map.printMap();
        }

        System.out.print("Inserire lvl1: ");
        keyboard = input.nextLine();
        if(keyboard.equalsIgnoreCase("lvl1")) {
            this.map.setCellaBuildingType(0,0,keyboard);
            this.map.printMap();
        }

        System.out.print("Inserire lvl2: ");
        keyboard = input.nextLine();
        if(keyboard.equalsIgnoreCase("lvl2")) {
            this.map.setCellaBuildingType(0,0,keyboard);
            this.map.printMap();
        }

        System.out.print("Inserire lvl3: ");
        keyboard = input.nextLine();
        if(keyboard.equalsIgnoreCase("lvl3")) {
            this.map.setCellaBuildingType(0,0,keyboard);
            this.map.printMap();
        }

        System.out.print("Inserire dome: ");
        keyboard = input.nextLine();
        if(keyboard.equalsIgnoreCase("dome")) {
            this.map.setCellaBuildingType(0,0,keyboard);
            this.map.setCellaHasPlayer(1,1);

            this.map.printMap();
        }

        System.out.print("Inserire qualcunque cosa per pulire la schermata e concludere: ");
        input.nextLine();
        Color.clearConsole();
    }

    public String setOutputColor(String string) {
        return this.clientOut + string + this.clientOut.RESET;
    }

    public void provaEsecuzioneNonPredeterminato() {
        String keyboard;
        Scanner input = new Scanner(System.in);

        System.out.print("Inserire le coordinate in cui mettere il worker: ");
        int[] coordinate = getCoordinatesFromString();
        this.map.setCellaHasPlayer(coordinate[0], coordinate[1]);

        this.map.printMap();

        do {
            System.out.print("Inserire MOVE o BUILD: (qualsiasi altra cosa per terminare l'esecuzione");
            keyboard = input.nextLine().toUpperCase();
            if(!keyboard.equals("MOVE") && !keyboard.equals("BUILD"))
                break;

            System.out.print("Inserire le coordinate in cui eseguire l'azione: ");
            coordinate = getCoordinatesFromString();

            selectCorrectExec(keyboard, coordinate);

            this.map.printMap();
        }while(keyboard.equals("MOVE") || keyboard.equals("BUILD"));

        System.out.println(Color.ANSI_CYAN + "FINE ESECUZIONE!" + Color.RESET);
    }

    public void selectCorrectExec(String choice, int[] coordinate) {
        if(choice.equals("MOVE"))
            this.map.setCellaHasPlayer(coordinate[0], coordinate[1]);
        if(choice.equals("BUILD")) {
            Scanner input = new Scanner(System.in);
            System.out.print("Inserire il tipo di edificio da costruire: ");
            this.map.setCellaBuildingType(coordinate[0], coordinate[1], input.nextLine().toUpperCase());
        }

    }

    public int[] getCoordinatesFromString() {
        Scanner input = new Scanner(System.in);

        keyboard = input.nextLine();
        int[] coordinate = new int[2];
        String[] split = keyboard.split("\\s");
        for(int i=0; i<2; i++)
            coordinate[i] = Integer.parseInt(split[i]);

        return coordinate;
    }

}
