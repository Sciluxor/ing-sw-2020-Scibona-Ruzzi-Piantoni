package it.polimi.ingsw;


import it.polimi.ingsw.view.client.cli.Cli;
import it.polimi.ingsw.view.client.gui.Gui;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("Insert 1 to run CLI, insert 2 to run GUI: ");
        String keyboard = input.nextLine();
        if(keyboard.equals("1")) {
            Cli cli = new Cli();
            cli.avvio();
        }
        else if(keyboard.equals("2")) {
            Gui gui = new Gui();
            gui.avvio();
        }

    }

}
