package it.polimi.ingsw.view.client.cli;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class CliUtils {

    public static final java.util.logging.Logger LOGGER = Logger.getLogger("Cli");

    public static void printRed(String string) {
        System.out.print(Color.ANSI_RED + string + Color.RESET);
    }

    public static void printWhite(String string) {
        System.out.print(Color.ANSI_WHITE + string + Color.RESET);
    }

    public static void print(String string, Color color) {
        System.out.print(color + string + Color.RESET);
    }

    public static void printErr(String string) {
        System.err.println(string);
    }

    public static String input() {
        String keyboard;
        Scanner input = new Scanner(System.in);
        keyboard = input.nextLine();
        return keyboard;
    }

    public static String[] splitter(String keyboard) {
        return keyboard.split("\\s");
    }

    public static void clearShell() {
        Color.clearConsole();
    }



    public static int getArrow() {
        int keyboard = 0, keyboard1 = 0, keyboard2 = 0;

        try {
            String[] cmd = {"/bin/sh", "-c", "stty raw </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();

            keyboard = System.in.read();
            if (keyboard == 27) {
                keyboard1 = System.in.read();
                if (keyboard1 == 91)
                    keyboard2 = System.in.read();
            } else if(keyboard == 8) {
                cmd = new String[]{"/bin/sh", "-c", "stty sane </dev/tty"};
                Runtime.getRuntime().exec(cmd).waitFor();

                Scanner input = new Scanner(System.in);
                String s = input.nextLine();
            }

            keyboard = keyboard + keyboard1 + keyboard2;

            cmd = new String[]{"/bin/sh", "-c", "stty sane </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        } catch (IOException | InterruptedException e) {
            LOGGER.severe(e.getMessage());
        }

        return keyboard;
    }

    public static int getArrowUpDown() {
        int keyboard;

        do {
            keyboard = getArrow();
        }while(keyboard != 183 && keyboard != 184);

        return keyboard;
    }

    public static int getArrowLeftRight() {
        int keyboard;

        do {
            keyboard = getArrow();
        }while(keyboard != 185 && keyboard != 186);

        return keyboard;
    }

    private static int waitEnter() {
        int keyboardIn;
        keyboardIn = getArrow();
        return keyboardIn;
    }

    public static int controlWaitEnter(String type) {
        int keyboardIn = 0;
        printRed("PRESS ENTER TO GO ON...");

        switch (type) {
            case "up&down":
                do {
                    keyboardIn = waitEnter();
                }while(keyboardIn != 13 && keyboardIn != 183 && keyboardIn != 184);
                break;
            case "left&right":
                do {
                    keyboardIn = waitEnter();
                }while(keyboardIn != 13 && keyboardIn != 185 && keyboardIn != 186);
                break;
            case "enter":
                do {
                    keyboardIn = waitEnter();
                }while(keyboardIn != 13);
                break;
            default:
                printErr("NO KEYBOARD CATCHED");
        }
        return keyboardIn;
    }

}
