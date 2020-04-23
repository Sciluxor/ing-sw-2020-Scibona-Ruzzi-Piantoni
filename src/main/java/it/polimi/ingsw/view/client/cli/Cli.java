package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.controller.ClientGameController;
import java.util.Scanner;

public class Cli extends ClientGameController {

    private Color colorServer;
    private Color colorClient;
    private static Color colorPrinter = Color.ANSI_RED;
    private String keyboard;
    private String role;

    private ClientCLI clientCLI = new ClientCLI();
    private ServerCLI serverCLI = new ServerCLI();

    public Cli() {
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

        this.jumpToRoleCode();

    }

    @Override
    public String toString() {
        if(keyboard.equals("1"))
            return this.colorServer + "Server: \uD83D\uDF32\n" + Color.RESET;
        else if(keyboard.equals("2"))
            return this.colorClient + "Client: \u3020\n" + Color.RESET;

        return "";
    }

    public void dump() {
        System.out.println(this);
    }

    public void setRole() {
        if(keyboard.equals("1")) {
            this.role = "SERVER";
            System.out.println("Server setted");
        }
        else {
            this.role = "CLIENT";
            System.out.println("Client setted");
        }
    }

    public void jumpToRoleCode() {
        if(this.role.equals("SERVER")) {
            serverCLI.printServer();
        }
        else if(this.role.equals("CLIENT")) {
            clientCLI.printCLI();
        }

    }

    public boolean correctInput(String keyboard) {
        return keyboard.equals("1") || keyboard.equals("2");
    }

    public void getTile() {
    }

    public void printTitle() {
        System.out.println(this.colorPrinter +  "             ___       ___  ___          ___    _____   ___      ___               _____  ___   ___                 |_|  |_|\n" +
                " \\   \\/   / |    |    |    |   | |\\  /| |         |    |   |    |       /\\   |\\  |   |   |   | |   | | |\\  | |     ___________\n" +
                "  \\  /\\  /  |--  |    |    |   | | \\/ | |--       |    |   |    |---|  /--\\  | \\ |   |   |   | |___| | | \\ | |     |   _|_   |\n" +
                "   \\/  \\/   |___ |___ |___ |___| |    | |___      |    |___|     ___| /    \\ |  \\|   |   |___| |  \\  | |  \\| |      |_______|\n" + this.colorPrinter.RESET);
    }

}
