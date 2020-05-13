package it.polimi.ingsw.view.client.cli;

import java.io.IOException;
import java.util.Scanner;

public class CliUtils {

    public static void print(String string) {
        System.out.print(Color.ANSI_RED + string + Color.RESET);
    }

    public static void print(String string, Color color) {
        System.out.print(color + string + Color.RESET);
    }

    public static void printErr(String string) {
        System.err.println(Color.ANSI_RED + string + Color.RESET);
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

    public static int getArrowUpDown() throws IOException, InterruptedException {
        int keyboard;

        do {
            keyboard = getArrow();
        }while(keyboard != 183 && keyboard != 184);

        return keyboard;
    }

    public static int getArrowLeftRight() throws IOException, InterruptedException {
        int keyboard;

        do {
            keyboard = getArrow();
        }while(keyboard != 185 && keyboard != 186);

        return keyboard;
    }

    public static int waitEnter() throws IOException, InterruptedException {
        int keyboardIn;

        print("PRESS ENTER TO GO ON WITH THE SELECTED MODE...");
        keyboardIn = getArrow();
        return keyboardIn;
    }

}
