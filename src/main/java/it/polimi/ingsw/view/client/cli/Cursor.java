package it.polimi.ingsw.view.client.cli;

import static it.polimi.ingsw.view.client.cli.Cli.print;

public class Cursor {
    private static final String ESC = (char) 27 + "[";
    private static final String CLEAN = ESC + "J";
    private static final String HOME = ESC + "H";

    public static void cleanConsole() {
        print(CLEAN);
        System.out.flush();
    }

    public static void setCursorHome() {
        print(HOME);
    }

    public static void moveCursorTo(int row, int column) {
        print(ESC + row + ";" + column + "H");
    }

    public static void moveCursorUP(int numOfLines) {
        print(ESC + numOfLines + "A");
    }

    public static void moveCursorDOWN(int numOfLines) {
        print(ESC + numOfLines + "C");
    }

    public static void moveCursorRIGHT(int numOfLines) {
        print(ESC + numOfLines + "C");
    }

    public static void moveCursorLEFT(int numOfLines) {
        print(ESC + numOfLines + "D");
    }

}
