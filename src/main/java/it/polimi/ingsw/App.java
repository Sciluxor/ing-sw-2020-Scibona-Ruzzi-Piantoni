package it.polimi.ingsw;

import it.polimi.ingsw.view.client.cli.Cli;
import it.polimi.ingsw.view.client.gui.Gui;

import java.util.Scanner;

public class App {

    private static final String TITLE = "\n\u001B[31m" +
            "             ___       ___  ___          ___    _____   ___      ___               _____  ___   ___                  |_|  |_|\n" +
            " \\   \\/   / |    |    |    |   | |\\  /| |         |    |   |    |       /\\   |\\  |   |   |   | |   | | |\\  | |     ___________\n" +
            "  \\  /\\  /  |--  |    |    |   | | \\/ | |--       |    |   |    |---|  /--\\  | \\ |   |   |   | |___| | | \\ | |     |   _|_   |\n" +
            "   \\/  \\/   |___ |___ |___ |___| |    | |___      |    |___|     ___| /    \\ |  \\|   |   |___| |  \\  | |  \\| |      |_______|\n\n\u001B[0m";

    public static void main(String[] args) {

        System.out.print(TITLE);

        Scanner input = new Scanner(System.in);
        System.out.print("Insert 1 to run CLI, insert 2 to run GUI: ");
        String keyboard = input.nextLine();
        if(keyboard.equals("1")) {
            Cli cli = new Cli();
            cli.printCLI();
        }
        else if(keyboard.equals("2")) {
            Gui gui = new Gui();
            gui.avvio();
        }

    }

}
