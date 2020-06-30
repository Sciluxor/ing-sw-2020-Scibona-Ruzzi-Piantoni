package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.view.client.cli.Color;
import it.polimi.ingsw.view.client.cli.SantoriniMap;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class that define and implements some useful functions that are used by the Cli
 * @author edoardopiantoni
 * @version 1.0
 * @since 2020/06/18
 */

public class CliUtils {

    public static final String TITLE = Color.BACKGROUND_WHITE +
            "                                                                                                                                                                                      \n" +
            "   __      __ __      __  _____ __      _____  _____              _____    _____  _____     _____                        _____  _____   _____                      │__│ ╷ │__│        \n" +
            "    ╲       ╲ ╱       ╱  │      │      │      │     │ │╲     ╱ │ │           │   │     │   │           ╱ ╲      │╲     │   │   │     │ │     │ │ │╲    │ │     ___________________    \n" +
            "      ╲     ╱ ╲     ╱    │──    │      │      │     │ │  ╲ ╱   │ │──         │   │     │   ╵─────╷   ╱─────╲    │  ╲   │   │   │     │ │_____│ │ │  ╲  │ │     ╲      __│__      ╱    \n" +
            "        ╲ ╱     ╲ ╱      ╵_____ ╵_____ ╵_____ ╵_____╵ │        │ ╵_____      │   ╵_____╵    _____╵ ╱         ╲  │    ╲ │   │   ╵_____╵ │   ╲   │ │    ╲│ │       ╲ ___________ ╱      \n" +
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

    private static final String CLEAR_CONSOLE = "\033[H\033[2J";

    private static boolean debug = true;
    private static boolean newChatMessage = false;
    private static Player lastPlayerOnChat;
    private static String lastChatMessage;
    private static String terminalMode = "sane";

    public static final java.util.logging.Logger LOGGER = Logger.getLogger("Cli");

    private CliUtils() {}

    //--------------PRINTER----------------------

    /**
     * Method used to print on the System.out object a string red colored
     * @param string String to print with the color
     */

    public static void printRed(String string) {
        print(string, Color.ANSI_RED);
    }

    /**
     * Method used to print on the System.out object a string white colored
     * @param string String to print with the color
     */

    public static void printWhite(String string) {
        print(string, Color.ANSI_WHITE);
    }

    /**
     * Method used to print on the System.out object a string yellow colored
     * @param string String to print with the color
     */

    public static void printYellow(String string) {
        print(string, Color.ANSI_YELLOW);
    }

    /**
     * Method used to print on the System.out object a string colored
     * @param string String to print with the color
     * @param color Color used in this print method
     */

    public static void print(String string, Color color) {
        System.out.print(color + string + Color.RESET);
    }

    /**
     * Method used to handle the visualisation of an error represented in a string
     * @param string String that represents the error
     */

    public static void printErr(String string) {
        LOGGER.info(string);
    }

    /**
     * Method used to print some debug info
     * @param string String to print as debug string
     */

    public static void printDebug(String string) {
        if(debug)
            print(Color.BACKGROUND_WHITE + string, Color.ANSI_BLACK);
    }

    /**
     * Method used to print the nickname of a player with his color
     * @param player Player to print nickname with his correct color
     */

    public static void printPlayer(Player player) {
        try {
            print(player.getNickName().toUpperCase(), getColorCliFromPlayer(player.getColor()));
        } catch (NullPointerException e) {
            printErr("NULL POINTER");
            CliUtils.LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Method used to print the power of a card
     * @param cardName String that represents the name of the card to print the power
     * @param deck The set of all cards with them info (name, power, etc.)
     */

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

    /**
     * Method used to print the opponent of a player with his color
     * @param player Opponent player to print with his correct color
     */

    public static void printOpponent(Player player) {
            printWhite("  [");
            printPlayer(player);
            printWhite("] ");
    }

    /**
     * Method used to print a waiting string (WaitOtherPlayer).
     * @param numberOfPlayers Int used to distinguish the string to print
     */

    public static void printWaitForOtherPlayers(int numberOfPlayers) {
        if(numberOfPlayers==2)
            printRed("WAITING FOR OTHER PLAYER DOES HIS ACTIONS\n");
        else
            printRed("WAITING FOR OTHER PLAYERS DO THEIR ACTIONS\n");
    }

    /**
     * Method used to print a waiting string (WaitStartTurn).
     * @param numberOfPlayers Int used to distinguish the string to print
     */

    public static void printWaitingStartTurn(int numberOfPlayers) {
        if(numberOfPlayers==2)
            printRed("WAITING FOR OTHER PLAYER START HIS TURN\n");
        else
            printRed("WAITING FOR OTHER PLAYERS START THEM TURN\n");
    }

    //------------ GENERIC -------------------------

    /**
     * Standard method used to get something from System.in
     * @return keyboard String input from the System.in
     */

    public static String input() {
        String keyboard;
        Scanner input = new Scanner(System.in);
        keyboard = input.nextLine();
        return keyboard;
    }

    /**
     * Method used to get an integer from System.in
     * @return keyboard int value as input from System.in
     */

    public static int inputRead() {
        int keyboard = -1;
        try {
            keyboard = System.in.read();
        } catch (IOException e) {
            LOGGER.severe(e.getMessage() + e.getClass());
        }
        return keyboard;
    }

    /**
     * Method used to quit from the game
     * @param status status of the game (1 = correct, -1 = error)
     */

    public static void quitFromGame(int status) {
        System.exit(status);
    }

    /**
     * Method used to clear the shell and print some info regards the match
     * @param opponents List of the opponents of the current player
     * @param currentPlayer Player that invoke this method
     * @param deck Set of all cards
     * @param constraints List of string of possible constraints
     * @param map The map object of the current game
     */

    public static void clearAndPrintInfo(List<Player> opponents, Player currentPlayer, Map<String, Card> deck, List<String> constraints, SantoriniMap map){
        clearShell();
        map.printMap();
        printInfo(opponents, currentPlayer, deck, constraints);
    }

    /**
     * Method used to print all the info required (not the map) by the clearAndPrintInfo methods
     * @param opponents List of the opponents of the current player
     * @param currentPlayer Player that invoke this method
     * @param deck Set of all cards
     * @param constraints List of string of possible constraints
     */

    public static void printInfo(List<Player> opponents, Player currentPlayer, Map<String, Card> deck, List<String> constraints) {
        printRed("[OPPONENTS]:");
        for (Player player : opponents) {
            printOpponent(player);
        }
        printRed("\n[OPPONENTS' POWER]:\n");
        for(Player player: opponents) {
            printOpponent(player);
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
        }
        if(newChatMessage) {
            printYellow("[CHAT]: ");
        }
        else
            printRed("[CHAT]: ");

        try {
            print("[" + lastPlayerOnChat.getNickName().toUpperCase() + "]: ", getColorCliFromPlayer(lastPlayerOnChat.getColor()));
            printRed(lastChatMessage + "\n");
        }catch (NullPointerException e) {
            printRed("\n");
        }
        printRed("\n");
    }

    /**
     * Method used to initialize chat attributes when new game is started, without losing connection
     */

    public static void resetChat() {
        newChatMessage = false;
        lastPlayerOnChat = null;
        lastChatMessage = null;
    }

    /**
     * Method used to set if there is a new chat message
     * @param newChatMessage Boolean value (true = new chat message | false = no new chat message)
     */

    public static void setNewChatMessage(boolean newChatMessage) {
        CliUtils.newChatMessage = newChatMessage;
    }

    /**
     * Method used to set if a new chat message has been visualized
     * @param visualized True if is visualized (entered in chat)
     */

    public static void setVisualized(boolean visualized) {
        if(visualized)
            newChatMessage = false;
    }

    /**
     * Method used to set the last chat message
     * @param player Last player that has sent a message in chat
     * @param chatMessage Last chat message received in chat
     */

    public static void setLastChatMessage(Player player, String chatMessage) {
        lastPlayerOnChat = player;
        lastChatMessage = chatMessage;
    }

    /**
     * Method used to handle the visualisation of the chat
     * @param previousChatMessage Set of last 10 message of the chat
     */

    public static void printChat(List<Pair<Player, String>> previousChatMessage) {
        printRed("[----- CHAT -----]\n");
        for(Pair<Player, String> previousChat: previousChatMessage) {
            Player player = previousChat.getKey();
            print("[" + player.getNickName().toUpperCase() + "]: ", getColorCliFromPlayer(player.getColor()));
            printRed(previousChat.getValue() + "\n");
        }
    }

    /**
     * Method used to clear the shell
     */

    public static void clearShell() {
        print(CLEAR_CONSOLE, Color.ANSI_BLACK);
    }

    /**
     * Method used to convert the color of a player (an enum of string contained in the model) in the corresponding color
     * value useful in the cli to print colors
     * @param color The color of the player
     * @return playerColor Color converted in the correct cli color value (contained in the Color enum of the Cli)
     */

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
                    printErr("WRONG PLAYER COLOR PASSED");
            }
        } catch (NullPointerException e) {
            printErr("NULL POINTER");
            CliUtils.LOGGER.severe(e.getMessage());
        }

        return returnedColor;
    }

    /**
     * Method used to get player object using his nickname
     * @param players List of players actually in the current game
     * @param nickName String nickname of the asked player
     * @return aksedPlayer Player whose nickname is passed as a parameter
     */

    public static Player getPlayerFromNickName (List<Player> players, String nickName) {
        for(Player player: players) {
            if(player.getNickName().equalsIgnoreCase(nickName))
                return player;
        }
        return null;
    }

    /**
     * Method used to set the background of a generic string
     * @param string String on which applies the background color
     * @param background Background color
     * @return stringWithBackground String on which this method applied the background color
     */

    public static String setBackground (String string, Color background) {
        return background + string + Color.RESET;
    }

    //------------ ARROWS --------------------------

    /**
     * Method used to get arrow values as user's input. It also convert the terminal mode in the raw mode and then it reset its
     * @return arrow Int value that represent the arrow value
     */

    public static int getArrow() {
        int keyboard;
        int keyboard1 = 0;
        int keyboard2 = 0;

        setTerminalMode("raw");

        keyboard = inputRead();

        if(keyboard == 3)
            quitFromGame(0);
        else if (keyboard == 27) {
            keyboard1 = inputRead();
            if (keyboard1 == 91)
                keyboard2 = inputRead();
        }

        keyboard = keyboard + keyboard1 + keyboard2;
        printDebug(Integer.toString(keyboard));

        setTerminalMode("sane");

        return keyboard;
    }

    /**
     * Method used to set the terminal mode (raw or sane)
     * @param mode String that represent the terminal mode to set
     */

    public static void setTerminalMode(String mode) {
        if(!terminalMode.equalsIgnoreCase(mode)) {
            terminalMode = mode.toLowerCase();
            try {
                String[] cmd = new String[]{"/bin/sh", "-c", "stty " + mode + " </dev/tty"};
                Runtime.getRuntime().exec(cmd).waitFor();
            } catch (IOException | InterruptedException e) {
                LOGGER.severe(e.getMessage() + e.getClass());
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Method used to get current terminal mode
     * @return terminalMode Current value of terminal mode
     */

    public static String getTerminalMode() {
        return terminalMode;
    }

    /**
     * Method use to get (through getArrow) only up and down arrows
     * @return upOrDown Int value that represents the user's choice (up or down)
     */

    public static int getArrowUpDown() {
        int keyboard;

        do {
            keyboard = getArrow();
        }while(keyboard != 183 && keyboard != 184);

        return keyboard;
    }

    /**
     * Method used to wait the user press "ENTER" (or something else represented by the type parameter)
     * @param type String that represents which values should be accepted from the user
     * @return userChoice Int value that represents the user's choice (one of the options allowed by the type or "ENTER")
     */

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
                    keyboardIn = getArrow();
                }while(keyboardIn != 13 && keyboardIn != 183 && keyboardIn != 184);
                break;
            case "left&right":
                do {
                    keyboardIn = getArrow();
                }while(keyboardIn != 13 && keyboardIn != 185 && keyboardIn != 186);
                break;
            case "enter":
                do {
                    keyboardIn = getArrow();
                }while(keyboardIn != 13);
                break;
            case "confirm":
                do {
                    keyboardIn = getArrow();
                }while(keyboardIn != 13 && keyboardIn != 186);
                break;
            default:
                printErr("CASE ERROR IN CONTROL_WAIT_ENTER");
        }
        return keyboardIn;
    }

    //----------------------------------------------

}
