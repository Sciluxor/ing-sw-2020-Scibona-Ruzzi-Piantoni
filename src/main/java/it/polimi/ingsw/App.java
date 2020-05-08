package it.polimi.ingsw;

import it.polimi.ingsw.view.client.cli.Cli;
import it.polimi.ingsw.view.client.gui.Gui;

import java.util.Scanner;

import static it.polimi.ingsw.view.client.cli.Cursor.*;

public class App {

    private static final String TITLE = "\n\u001B[31m" +
            "             ___       ___  ___          ___    _____   ___      ___               _____  ___   ___                  |_|  |_|\n" +
            " \\   \\/   / |    |    |    |   | |\\  /| |         |    |   |    |       /\\   |\\  |   |   |   | |   | | |\\  | |     ___________\n" +
            "  \\  /\\  /  |--  |    |    |   | | \\/ | |--       |    |   |    |---|  /--\\  | \\ |   |   |   | |___| | | \\ | |     |   _|_   |\n" +
            "   \\/  \\/   |___ |___ |___ |___| |    | |___      |    |___|     ___| /    \\ |  \\|   |   |___| |  \\  | |  \\| |      |_______|\n\n\u001B[0m";

    public static void main(String[] args) {

        //setCursorHome();

        Scanner input = new Scanner(System.in);
        System.out.print("INSERT:\n>>> [1] CLI\n>>> [2] GUI\n");
        //moveCursorUP(2);

        String keyboard = input.nextLine();
        if(keyboard.equals("1")) {
            Cli cli = new Cli();
            cli.start();
        }
        else if(keyboard.equals("2")) {
            Gui gui = new Gui();
            gui.avvio();
        }

    }

}
