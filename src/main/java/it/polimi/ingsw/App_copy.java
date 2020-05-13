package it.polimi.ingsw;

import it.polimi.ingsw.view.client.cli.Cli;
import it.polimi.ingsw.view.client.cli.Color;
import it.polimi.ingsw.view.client.gui.Gui;
import java.io.*;
import static it.polimi.ingsw.view.client.cli.CliUtils.*;

public class App_copy {

    public static void main(String[] args) throws IOException, InterruptedException {

        int keyboardIn;
        boolean goOut = false;

        print("USE ARROWS TO SELECT:\n  [1] CLI\n  [2] GUI\n", Color.ANSI_RED);
        keyboardIn = getArrowUpDown();

        do {
            clearShell();
            switch (keyboardIn) {
                case 183:
                    print("SELECTED:\n");
                    print("> [1] CLI\n", Color.ANSI_YELLOW);
                    print("  [2] GUI\n");
                    keyboardIn = waitEnter();
                    if (keyboardIn == 13) {
                        Cli cli = new Cli();
                        cli.start();
                    }
                    break;
                case 184:
                    print("SELECTED:\n  [1] CLI\n");
                    print("> [2] GUI\n", Color.ANSI_YELLOW);
                    keyboardIn = waitEnter();
                    if (keyboardIn == 13) {
                        Gui gui = new Gui();
                        gui.avvio();
                    }
                    break;
                default:
                    goOut = true;
                    if(keyboardIn != 13)
                        printErr("NO KEYBOARD CATCHED");
            }
        }while (!goOut);

    }

}
