package it.polimi.ingsw;

import it.polimi.ingsw.view.client.cli.Cli;
import it.polimi.ingsw.view.client.cli.Color;
import it.polimi.ingsw.view.client.gui.Gui;
import java.io.*;

public class App_copy {

    public static void main(String[] args) throws IOException, InterruptedException {

        int keyboardIn;
        boolean goOut = false;

        keyboardIn = getArrowUpDown();

        do {
            Color.clearConsole();
            switch (keyboardIn) {
                case 183:
                    System.out.print(Color.ANSI_RED + "SELECTED:\n" + Color.ANSI_YELLOW + "> [1] CLI\n" + Color.ANSI_RED + "  [2] GUI\n" + Color.RESET);
                    keyboardIn = waitEnter();
                    if (keyboardIn == 13) {
                        Cli cli = new Cli();
                        cli.start();
                    }
                    break;
                case 184:
                    System.out.print(Color.ANSI_RED + "SELECTED:\n  [1] CLI\n" + Color.ANSI_YELLOW + "> [2] GUI\n" + Color.RESET);
                    keyboardIn = waitEnter();
                    if (keyboardIn == 13) {
                        Gui gui = new Gui();
                        gui.avvio();
                    }
                    break;
                default:
                    goOut = true;
                    if(keyboardIn != 13)
                        System.err.println("\nNO KEYBOARD CATCHED");
            }
        }while (!goOut);

    }

    public static int getArrowUpDown() throws IOException, InterruptedException {
        int keyboard;

        do {
            System.out.print(Color.ANSI_RED + "USE ARROWS TO SELECT:\n  [1] CLI\n  [2] GUI\n" + Color.RESET);
            keyboard = getArrow();
        }while(keyboard != 183 && keyboard != 184);
        return keyboard;
    }

    public static int getArrow() throws IOException, InterruptedException {
        int keyboard, keyboard1 = 0, keyboard2 = 0;

        String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
        Runtime.getRuntime().exec(cmd).waitFor();

        keyboard = System.in.read();
        if (keyboard == 27) {
            keyboard1 = System.in.read();
            if (keyboard1 == 91)
                keyboard2 = System.in.read();
        }

        keyboard = keyboard + keyboard1 + keyboard2;

        cmd = new String[] {"/bin/sh", "-c", "stty sane </dev/tty"};
        Runtime.getRuntime().exec(cmd).waitFor();

        return keyboard;
    }

    public static int waitEnter() throws IOException, InterruptedException {
        int keyboardIn;

        System.out.print("\nPRESS ENTER TO GO ON WITH THE SELECTED MODE...");
        keyboardIn = getArrow();
        return keyboardIn;
    }

}
