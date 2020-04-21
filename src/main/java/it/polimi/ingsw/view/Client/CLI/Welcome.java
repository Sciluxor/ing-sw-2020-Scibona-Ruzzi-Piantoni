package it.polimi.ingsw.view.Client.CLI;

import java.util.Scanner;

public class Welcome {

    private Color colorServer;
    private Color colorClient;
    private static Color colorPrinter = Color.ANSI_RED;
    private String keyboard;
    private SantoriniMap map = new SantoriniMap();
    static Cella[][] cella = new Cella[5][5];


    public Welcome() {
        this.colorServer = Color.ANSI_BLUE;
        this.colorClient = Color.ANSI_RED;
        this.keyboard = null;
    }

    public void avvio() {

        //Welcome w = new Welcome();
        Scanner input = new Scanner(System.in);

        printTitle();

        //System.out.println("Welcome to Santorini! \uD83D\uDE03\n");
        System.out.println("Please enter 1 to be a server, 2 to be a client: ");
        this.keyboard = input.nextLine();
        while(!this.correctInput(this.keyboard)) {
            System.out.println("Invalid answer. Please enter 1 to be a server, 2 to be a client: ");
            this.keyboard = input.nextLine();
        }

        this.dump();
        this.setRole();

        //this.printMap();
        this.map.printMap();

        this.provaEsecuzione(input);


    }

    @Override
    public String toString() {
        if(keyboard.equals("1"))
            return this.colorServer + "Server: \uD83D\uDF32\n" + Color.RESET;
        else if(keyboard.equals("2"))
            return this.colorClient + "Client: \u3020\n" + Color.RESET;

        return null;
    }

    public void dump() {
        System.out.println(this);
    }

    public void setRole() {
        if(keyboard.equals("1"))
            //set Server role
            System.out.println("Server setted");
        else if(keyboard.equals("2")) {
            Client c = new Client();
            c.initializationClient();
            System.out.println("Client setted");
        }
    }

    /*public void printMap() {
        for(int i=0; i<5; i++) {
            for(int t=0; t<5; t++) {
                System.out.print("-----------");
            }
            System.out.print("-\n");
            for(int j=0; j<2; j++) {
                System.out.println("|          |          |          |          |          |");
            }
            System.out.println("|    " + this.cella[i][0].getColorPlayer() + cella[i][0].getSimbol() + this.cella[i][0].getColorPlayer().RESET + "    |    " +
                    this.cella[i][1].getColorPlayer() + cella[i][1].getSimbol() + this.cella[i][1].getColorPlayer().RESET + "    |    " +
                    this.cella[i][2].getColorPlayer() + cella[i][2].getSimbol() + this.cella[i][2].getColorPlayer().RESET + "    |    " +
                    this.cella[i][3].getColorPlayer() + cella[i][3].getSimbol() + this.cella[i][3].getColorPlayer().RESET + "    |    " +
                    this.cella[i][4].getColorPlayer() + cella[i][4].getSimbol() + this.cella[i][4].getColorPlayer().RESET + "    |");
            for(int j=0; j<2; j++) {
                System.out.println("|          |          |          |          |          |");
            }

        }
        for(int t=0; t<5; t++) {
            System.out.print("-----------");
        }
        System.out.println("-\n");
    }*/

    public boolean correctInput(String keyboard) {
        return keyboard.equals("1") || keyboard.equals("2");
    }

    public void getTile() {
    }

    public void provaEsecuzione(Scanner input) {
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

    public void printTitle() {
        System.out.println(this.colorPrinter +  "             ___       ___  ___          ___    _____   ___      ___               _____  ___   ___                 |_|  |_|\n" +
                " \\   \\/   / |    |    |    |   | |\\  /| |         |    |   |    |       /\\   |\\  |   |   |   | |   | | |\\  | |     ___________\n" +
                "  \\  /\\  /  |--  |    |    |   | | \\/ | |--       |    |   |    |---|  /--\\  | \\ |   |   |   | |___| | | \\ | |     |   _|_   |\n" +
                "   \\/  \\/   |___ |___ |___ |___| |    | |___      |    |___|     ___| /    \\ |  \\|   |   |___| |  \\  | |  \\| |      |_______|\n" + this.colorPrinter.RESET);
    }
}
