package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.player.Player;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class CliUtils {

    public static boolean debug = true;

    public static final java.util.logging.Logger LOGGER = Logger.getLogger("Cli");

    public static void printRed(String string) {
        System.out.print(Color.ANSI_RED + string + Color.RESET);
    }

    public static void printWhite(String string) {
        System.out.print(Color.ANSI_WHITE + string + Color.RESET);
    }

    public static void printYellow(String string) {
        System.out.print(Color.ANSI_YELLOW + string + Color.RESET);
    }

    public static void print(String string, Color color) {
        System.out.print(color + string + Color.RESET);
    }

    public static void printErr(String string) {
        System.err.println(string);
    }

    public static void printDebug(String string) {
        if(debug)
            System.out.println(string);
    }

    public static void printPlayer(String nickName, Player player) {
        try {
            print(nickName.toUpperCase(), getColorCliFromPlayer(player.getColor()));
        } catch (NullPointerException e) {
            printErr("NULL POINTER");
            CliUtils.LOGGER.severe(e.getMessage());
        }
    }

    public static void printPower(String cardName, Map<String, Card> deck) {
        Card card = deck.get(cardName.toLowerCase());
        if(card != null) {
            if (cardName.equalsIgnoreCase("ATHENA") || cardName.equalsIgnoreCase("HERA"))
                print("  OPPONENT'S TURN: ", Color.ANSI_BLUE);
            else if (cardName.equalsIgnoreCase("HYPNUS"))
                print("  START OF OPPONENT'S TURN: ", Color.ANSI_BLUE);
            else
                print("  " + deck.get(cardName).getType().toString() + ": ", Color.ANSI_BLUE);
            printRed(deck.get(cardName).getDescription() + "\n");
        }
        else
            printErr("WRONG CARD NAME");
    }

    public static void printOpponents(Player player) {
            printWhite("  [");
            printPlayer(player.getNickName(), player);
            printWhite("] ");
    }

    //------------ GENERIC -------------------------

    public static String input() {
        String keyboard;
        Scanner input = new Scanner(System.in);
        keyboard = input.nextLine();
        return keyboard;
    }

    public static String[] splitter(String keyboard) {
        return keyboard.split("\\s");
    }

    public static void clearShell(List<Player> opponents, Player currentPlayer, Map<String, Card> deck) {
        Color.clearConsole();
        printRed("[OPPONENTS]:");
        for (Player player : opponents) {
            printOpponents(player);
        }
        printRed("\n[OPPONENTS' POWER]:\n");
        for(Player player: opponents) {
            printOpponents(player);
            try {
                printRed(" ");
                printPower(player.getPower().getName(), deck);
            } catch (NullPointerException e) {
                printRed("POWER DOESN'T ALREADY CHOOSE\n");
            }
        }
        printRed("[YOUR POWER]:");
        try {
            printPower(currentPlayer.getPower().getName(), deck);
        } catch (NullPointerException e) {
            printRed(" POWER DOESN'T ALREADY CHOOSE\n");
        }
        printRed("\n");
    }

    public static void clearShell() {
        Color.clearConsole();
    }

    //------------ ARROWS --------------------------

    public static int getArrow() {
        int keyboard = 0, keyboard1 = 0, keyboard2 = 0;

        try {
            setTerminalMode("raw");

            keyboard = System.in.read();
            if (keyboard == 27) {
                keyboard1 = System.in.read();
                if (keyboard1 == 91)
                    keyboard2 = System.in.read();
            }

            keyboard = keyboard + keyboard1 + keyboard2;
            printDebug(Integer.toString(keyboard));

            setTerminalMode("sane");
        } catch (IOException e) {
            LOGGER.severe(e.getMessage() + e.getClass());
        }

        return keyboard;
    }

    public static void setTerminalMode(String mode) {
        try {
            String[] cmd = new String[]{"/bin/sh", "-c", "stty " + mode + " </dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
        }catch (IOException | InterruptedException e) {
            LOGGER.severe(e.getMessage() + e.getClass());
        }
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
            case "all":
                do {
                    keyboardIn = waitEnter();
                }while(keyboardIn != 13 && keyboardIn != 183 && keyboardIn != 184 && keyboardIn != 185 && keyboardIn != 186);
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

    //----------------------------------------------

    public static Color getColorCliFromPlayer(it.polimi.ingsw.model.player.Color color) {
        Color returnedColor = Color.ANSI_BLACK;
        try {
            switch (color) {
                case BLUE:
                    returnedColor = Color.ANSI_BLUE;
                    break;
                case WHITE:
                    returnedColor = Color.ANSI_WHITE;
                    break;
                case PURPLE:
                    returnedColor = Color.ANSI_PURPLE;
                    break;
                default:
                    returnedColor = Color.ANSI_YELLOW;
                    System.err.print("WRONG PLAYER COLOR PASSED");
            }
        } catch (NullPointerException e) {
            printErr("NULL POINTER");
            CliUtils.LOGGER.severe(e.getMessage());
        }

        return returnedColor;
    }

}
