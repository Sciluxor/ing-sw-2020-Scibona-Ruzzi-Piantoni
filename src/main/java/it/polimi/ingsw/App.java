package it.polimi.ingsw;

import it.polimi.ingsw.view.client.cli.Cli;
import it.polimi.ingsw.view.client.gui.Gui;
import java.util.Scanner;

import static it.polimi.ingsw.utils.CliUtils.TITLE;
import static it.polimi.ingsw.utils.CliUtils.printRed;


public class App {

    public static void main(String[] args) {

        printRed(TITLE);
        Scanner input = new Scanner(System.in);
        System.out.print("INSERT:\n>>> [1] CLI\n>>> [2] GUI\n");

        String keyboard = input.nextLine();
        while (!keyboard.equals("1") && !keyboard.equals("2"))
        {
            printRed(TITLE);
            System.out.print("\nWRONG NUMBER. PLEASE, REINSERT YOUR CHOOSE: ");
            keyboard = input.nextLine();
        }

        if(keyboard.equals("1")) {
            Cli cli = new Cli();
            cli.start();
        }
        else {
            Gui gui = new Gui();
            gui.avvio();
        }

    }

}
