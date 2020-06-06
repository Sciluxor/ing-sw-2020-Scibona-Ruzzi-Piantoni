package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.player.Player;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class CliUtils {

    /*protected static String upLeftAngle = "\u2554";
    protected static String downLeftAngle = "\u255A";
    protected static String upRightAngle = "\u2557";
    protected static String downRightAngle = "\u255D";
    protected static String orizzontalLinear = "\u2550";
    protected static String verticalLinear = "\u2551";
    protected static String middle = "\u256C";
    protected static String middleLeft = "\u2560";
    protected static String middleRight = "\u2563";
    protected static String middleDown = "\u2569";
    protected static String middleUp = "\u2566";*/

    public static final String TITLE = Color.BACKGROUND_WHITE +
            "                                                                                                                                                                                      \n" +
            "   __      __ __      __  _____ __      _____  _____              _____    _____  _____     _____                        _____  _____   _____                      │__│ ╷ │__│        \n" +
            "    ╲       ╲ ╱       ╱  │      │      │      │     │ │╲     ╱ │ │           │   │     │   │           ╱ ╲      │╲     │   │   │     │ │     │ │ │╲    │ │     ___________________    \n" +
            "      ╲     ╱ ╲     ╱    │──    │      │      │     │ │  ╲ ╱   │ │──         │   │     │   ╵─────╷   ╱─────╲    │  ╲   │   │   │     │ │_____│ │ │  ╲  │ │     ╲      __│__      ╱    \n" +
            "        ╲ ╱     ╲ ╱      ╵_____ ╵_____ ╵_____ ╵_____╵ │        │ ╵_____      │   ╵_____╵    _____╵ ╱         ╲  │    ╲ │   │   ╵_____╵ │   ╲   │ │    ╲│ │       ╲ ___________ ╱     \n" +
            "                                                                                                                                                                                      " + Color.RESET + "\n\n";

    public static final String WINNER = Color.BACKGROUND_WHITE +
            "                                                                      _____     \n" +
            "   __      __ _____  __     __  __      __ __       __               │_____│    \n" +
            "    ╲     ╱  │     │  │     │    ╲       ╲ ╱       ╱  │  │╲     │     ╲   ╱     \n" +
            "      ╲ ╱    │     │  │     │      ╲     ╱ ╲     ╱    │  │  ╲   │      │ │      \n" +
            "        │    ╵_____╵  ╵_____╵        ╲ ╱     ╲ ╱      │  │    ╲ │     ╱___╲     \n" +
            "                                                                                " + Color.RESET;

    public static final String LOSER = Color.BACKGROUND_WHITE +
            "                                                                                        \n" +
            "   __      __ _____  __     __   __      _____   _____   _____        │__│ ╷ │__│       \n" +
            "    ╲     ╱  │     │  │     │    │      │     │ │       │             ___________       \n" +
            "      ╲ ╱    │     │  │     │    │      │     │ ╵─────╷ │──          ╱   __│__   ╲      \n" +
            "        │    ╵_____╵  ╵_____╵    ╵_____ ╵_____╵  _____╵ ╵_____     ╱_______________╲    \n" +
            "                                                                                        " + Color.RESET;

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

    public static void printPlayer(Player player) {
        try {
            print(player.getNickName().toUpperCase(), getColorCliFromPlayer(player.getColor()));
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
            printPlayer(player);
            printWhite("] ");
    }

    public static void printWaitForOtherPlayers(int numberOfPlayers) {
        if(numberOfPlayers==2)
            printRed("WAITING FOR OTHER PLAYER DOES HIS ACTIONS\n");
        else
            printRed("WAITING FOR OTHER PLAYERS DO THEIR ACTIONS\n");
    }

    public static void printWaitingStartTurn(int numberOfPlayers) {
        if(numberOfPlayers==2)
            printRed("WAITING FOR OTHER PLAYER START HIS TURN\n");
        else
            printRed("WAITING FOR OTHER PLAYERS START THEM TURN\n");
    }

    //------------ GENERIC -------------------------

    public static String input() {
        String keyboard;
        Scanner input = new Scanner(System.in);
        keyboard = input.nextLine();
        return keyboard;
    }

    public static String[] splitter(String keyboard) {
        while (keyboard.isEmpty()) {
            printRed(setBackground("EMPTY! INSERT CORRECTLY VALUES: ", Color.BACKGROUND_YELLOW));
            keyboard = input();
        }
        return keyboard.split("\\s");
    }

    public static void clearAndPrintInfo(List<Player> opponents, Player currentPlayer, Map<String, Card> deck, List<String> constraints) {
        Color.clearConsole();
        printInfo(opponents, currentPlayer, deck, constraints);
    }

    public static void clearAndPrintInfo(List<Player> opponents, Player currentPlayer, Map<String, Card> deck, List<String> constraints, NewSantoriniMapArrows map){
        Color.clearConsole();
        map.printMap();
        printInfo(opponents, currentPlayer, deck, constraints);
    }

    public static void printInfo(List<Player> opponents, Player currentPlayer, Map<String, Card> deck, List<String> constraints) {
        printRed("[OPPONENTS]:");
        for (Player player : opponents) {
            printOpponents(player);
        }
        printRed("\n[OPPONENTS' POWER]:\n");
        for(Player player: opponents) {
            printOpponents(player);
            try {
                String power = player.getPower().getName();
                printYellow(" " + power.toUpperCase());
                printPower(power, deck);
            } catch (NullPointerException e) {
                printRed("POWER DOESN'T ALREADY CHOOSE\n");
            }
        }
        printRed("[YOUR POWER]:");
        try {
            String power = currentPlayer.getPower().getName();
            printYellow(" " + power.toUpperCase());
            printPower(power, deck);
        } catch (NullPointerException e) {
            printRed(" POWER DOESN'T ALREADY CHOOSE\n");
        }
        if(!constraints.isEmpty()) {
            printRed("[CONSTRAINT]:\n");
            for(String constraint: constraints) {
                printYellow("  " + constraint.toUpperCase() + ":");
                printPower(constraint, deck);
            }
        } else
            printRed("\n");
    }

    public static void clearShell() {
        Color.clearConsole();
    }

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

    public static Player getPlayerFromNickName (List<Player> players, String nickName) {
        for(Player player: players) {
            if(player.getNickName().equalsIgnoreCase(nickName))
                return player;
        }
        return null;
    }

    public static String setBackground (String string, Color background) {
        return background + string + Color.RESET;
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
        if(type.equalsIgnoreCase("endTurn")) {
            printRed("PRESS ENTER TO END YOUR TURN...");
            type = "enter";
        } else if(type.equalsIgnoreCase("confirm")) {
            printRed("PRESS ENTER TO CONFIRM, LEFT ARROW TO REINSERT COORDINATES...");
        } else
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
            case "confirm":
                do {
                    keyboardIn = waitEnter();
                }while(keyboardIn != 13 && keyboardIn != 186);
                break;
            default:
                printErr("CASE ERROR IN CONTROL_WAIT_ENTER");
        }
        return keyboardIn;
    }

    //----------------------------------------------

}
