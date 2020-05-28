package it.polimi.ingsw;

import it.polimi.ingsw.view.client.cli.Cli;
import it.polimi.ingsw.view.client.cli.Color;
import it.polimi.ingsw.view.client.gui.Gui;

import static it.polimi.ingsw.view.client.cli.CliUtils.*;

public class App_copy {

    public static void main(String[] args) {

        int keyboardIn;
        boolean goOut = false;

        clearShell();
        printRed(TITLE);
        printRed("USE ARROWS TO SELECT:\n  [1] CLI\n  [2] GUI\n");
        keyboardIn = getArrowUpDown();

        do {
            clearShell();
            printRed(TITLE);
            switch (keyboardIn) {
                case 183:
                    printRed("SELECTED:\n");
                    print("> [1] CLI\n", Color.ANSI_YELLOW);
                    printRed("  [2] GUI\n");

                    keyboardIn = controlWaitEnter("up&down");
                    if(keyboardIn == 0) {
                        goOut = true;
                        printErr("NO KEYBOARD CAUGHT");
                        break;
                    }

                    if (keyboardIn == 13) {
                        Cli cli = new Cli();
                        cli.start();
                    }
                    break;
                case 184:
                    printRed("SELECTED:\n  [1] CLI\n");
                    print("> [2] GUI\n", Color.ANSI_YELLOW);

                    keyboardIn = controlWaitEnter("up&down");
                    if(keyboardIn == 0) {
                        goOut = true;
                        printErr("NO KEYBOARD CAUGHT");
                        break;
                    }

                    if (keyboardIn == 13) {
                        Gui gui = new Gui();
                        gui.avvio();
                    }
                    break;
                default:
                    goOut = true;
                    if (keyboardIn != 13)
                        printErr("NO KEYBOARD CAUGHT");
            }
        } while (!goOut);

    }
}
