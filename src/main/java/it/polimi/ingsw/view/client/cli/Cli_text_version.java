package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.client.ClientGameController;
import it.polimi.ingsw.network.message.MessageType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.utils.ConstantsContainer.*;
import static it.polimi.ingsw.view.client.cli.CliUtils.*;

public class Cli_text_version extends ClientGameController {

    private int port = 4700;
    private String address = "127.0.0.1";
    private String nickName;
    private int numberOfPlayers;
    private SantoriniMap map = new SantoriniMap();

    private Thread backThread = new Thread();
    private Thread mainThread = new Thread();

    private Map<String, Card> deck = CardLoader.loadCards();
    private List<String> deckOrdered = new ArrayList<>();
    private List<Player> opponents = new ArrayList<>();
    private List<String> selectedCards = new ArrayList<>();

    private static final String TITLE = "\u001B[31m" +
            "             ___       ___  ___          ___    _____   ___      ___               _____  ___   ___                  |_|  |_|\n" +
            " \\   \\/   / |    |    |    |   | |\\  /| |         |    |   |    |       /\\   |\\  |   |   |   | |   | | |\\  | |     ___________\n" +
            "  \\  /\\  /  |--  |    |    |   | | \\/ | |--       |    |   |    |---|  /--\\  | \\ |   |   |   | |___| | | \\ | |     |   _|_   |\n" +
            "   \\/  \\/   |___ |___ |___ |___| |    | |___      |    |___|     ___| /    \\ |  \\|   |   |___| |  \\  | |  \\| |      |_______|\n\n\n\u001B[0m";


    public void start() {
        //String keyboard;

        clearShell();
        printRed(TITLE);
        login(false);

        printDebug("HERE");
        printDebug("NICK: " + getNickName() + "\nNUMBER: " + getNumberOfPlayers() + "\nPORT: " + getPort() + "\nIP: " + getAddress());

        try {
            openConnection(getNickName(), getNumberOfPlayers(), getAddress(), getPort());
        }catch (Exception e) {
            printErr("FAILED TO OPENING CONNECTION");
            CliUtils.LOGGER.severe(e.getMessage());
        }
    }

    public void login(boolean lobbyCall) {

        setNickName();
        setNumberOfPlayers();
        setPort();
        setAddress();

        if(lobbyCall)
            newGame(getNickName(), getNumberOfPlayers());
    }

    //-------------------------------

    //---------- SETTER & GETTER ----------

    public int getPort() {
        return port;
    }

    public void setPort() {
        printRed("INSERT THE PORT NUMBER (default as 4700): ");
        String port = input();
        if(!port.equals(""))
            this.port = Integer.parseInt(port);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress() {
        printRed("INSERT THE IP ADDRESS (default as 127.0.0.1 - localhost): ");
        String address = input();
        if(!address.equals(""))
            this.address = address;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName() {
        String nickName;

        printRed("INSERT YOUR NICKNAME: ");
        nickName = input();
        while(nickName.length() < MIN_LENGHT_NICK || nickName.length() > MAX_LENGHT_NICK) {
            printRed("INVALID NICKNAME LENGHT. PLEASE, REINSERT YOUR NICKNAME: ");
            nickName = input();
        }

        this.nickName = nickName;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers() {
        String keyboard;

        printRed("INSERT THE NUMBER OF PLAYERS: ");
        keyboard = input();

        while (!keyboard.equals("2") && !keyboard.equals("3")) {
            printRed("INVALID NUMBER OF PLAYERS. PLEASE, REINSERT THE NUMBER OF PLAYERS: ");
            keyboard = input();
        }

        this.numberOfPlayers = Integer.parseInt(keyboard);
    }

    //------------------------------

    //---------- USEFUL FUNCTIONS ----------

    public synchronized void checkBackCommand() {
        printRed("INSERT \"BACK\" TO TURN BACK IN THE LOGIN WINDOW: ");
        String keyboard = input();

        if (keyboard.equalsIgnoreCase("BACK")) {
            onBackCommand();
            login(true);
        }

    }

    //------------------------------

    //-----PLAYER-----

    public Color getColorCliFromPlayer(it.polimi.ingsw.model.player.Color color) {
        Color returnedColor;

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
        return returnedColor;
    }

    public Player getPlayerFromNickname(String nickName) {
        for(Player player: getPlayers()) {
            if(player.getNickName().equalsIgnoreCase(nickName))
                return player;
        }
        return null;
    }

    public void printPlayer(String nickName, Player player) {
        print(nickName.toUpperCase(), getColorCliFromPlayer(player.getColor()));
    }

    public void printPlayer(String nickName) {
        print(nickName.toUpperCase(), getColorCliFromPlayer(getPlayerFromNickname(nickName).getColor()));
    }

    public String selectFirstPlayer() {
        printRed("THESE ARE THE ACTUAL PLAYERS:\n");
        for(Player p: getPlayers()) {
            printRed("> ");
            printPlayer(p.getNickName(), p);
            printRed("\n");
        }
        printRed("PLEASE, INSERT THE NICKNAME OF ONE OF THEM YOU WANT AS FIRST PLAYER: ");
        String keyboard = input();

        controlSelectedFirstPlayer(keyboard);
        return keyboard;
    }

    public void controlSelectedFirstPlayer(String nickName) {
        boolean goOut = false;
        do {
            for(Player p: getPlayers()) {
                if (p.getNickName().equalsIgnoreCase(nickName)) {
                    goOut = true;
                    break;
                }
            }
            if(!goOut) {
                printRed("WRONG NICKNAME SELECTED! PLEASE, REINSERT THE CORRECT NICKNAME: ");
                nickName = input();
            }
        }while(!goOut);
    }
    //-----CARDS-----

    public void orderCards(String s) {
        for(int x=0; x < deckOrdered.size(); x++) {
            if (deckOrdered.get(x).compareTo(s) > 0) {
                deckOrdered.add(x, s);
                return;
            }
        }
        deckOrdered.add(s);
    }

    public void challengerChooseCards() {
        for(String s: deck.keySet())
            orderCards(s);

        if(getNumberOfPlayers()==2)
            deckOrdered.remove("chronus");

        selectChoosenCards();

        challengerResponse(selectFirstPlayer(), selectedCards);
    }

    public void selectChoosenCards() {

        String keyboard;

        clearShell();
        printCards();
        printRed("\n");

        keyboard = input().toLowerCase();
        String[] cards = splitter(keyboard);
        while(cards.length == 1) {
            printPower(cards[0]);
            keyboard = input().toLowerCase();
            cards = splitter(keyboard);
        }

        if(cards.length != numberOfPlayers) {
            printRed("WRONG NUMBER OF CARDS. PLEASE REINSERT " + getNumberOfPlayers() + " CARDS: ");
            keyboard = input().toLowerCase();
            cards = splitter(keyboard);
        }

        int i=0;
        while(i<cards.length) {
            Card card = deck.get(cards[i]);
            if(card == null) {
                printRed("WRONG CARD NAME. PLEASE REINSERT CARDS: ");
                keyboard = input().toLowerCase();
                cards = splitter(keyboard);
                i=0;
            } else
                i++;
        }

        Collections.addAll(selectedCards, cards);

        clearShell();
        printRed("THE DECK OF THIS GAME IS COMPOSED BY: ");
        for (String card : selectedCards) {
            printYellow(card.toUpperCase() + " ");
        }
        printRed("\n");
    }

    public void printCards() {
        printRed("PLEASE, CHOOSE " + getPlayers().size() + " CARDS:\n");
        for (String s : deckOrdered) {
            printYellow("  " + s.toUpperCase() + "\n");
        }
    }

    public void printPower(String cardName) {
        Card card = deck.get(cardName.toLowerCase());
            if(card != null) {
                if (cardName.equalsIgnoreCase("ATHENA") || cardName.equalsIgnoreCase("HERA"))
                    print("  OPPONENT'S TURN: ", Color.ANSI_BLUE);
                else if (cardName.equalsIgnoreCase("HYPNUS"))
                    print("  START OF OPPONENT'S TURN: ", Color.ANSI_BLUE);
                else
                    print("  " + deck.get(cardName).getType().toString() + ": ", Color.ANSI_BLUE);
                printRed(deck.get(cardName).getDescription() + "\n\n");
            }
            else
                printRed("WRONG CARD NAME. PLEASE REINSERT CARDS: ");
    }

    public String controlCardChoice(String choice) {
        List<String> availableCards = getAvailableCards();
        if(availableCards.size() == 2) {
            while (!choice.equalsIgnoreCase(availableCards.get(0)) && !choice.equalsIgnoreCase(availableCards.get(1))) {
                printRed("REINSERT ONE OF THE CARDS ABOVE: ");
                choice = input();
            }
        } else if(availableCards.size() == 3) {
            while (!choice.equalsIgnoreCase(availableCards.get(0)) && !choice.equalsIgnoreCase(availableCards.get(1)) && !choice.equalsIgnoreCase(availableCards.get(2))) {
                printRed("REINSERT ONE OF THE CARDS ABOVE: ");
                choice = input();
            }
        } else {
            printErr("WRONG AVAILABLE CARDS LENGHT");
            choice = "";
        }

        return choice;
    }

    //---------------

    public Integer[] getCoordinates() {
        String[] split = splitter(input());

        split = controlCoordinates(split);

        return new Integer[] {Integer.parseInt(split[0]), Integer.parseInt(split[1])};

    }

    public String[] controlCoordinates(String[] split) {
        while(split.length != 2) {
            printRed("WRONG NUMBER OF PARAMETERS!\nPLEASE, REINSERT COORDINATES (from 0 up to 4): ");
            split = splitter(input());
        }

        while(!split[0].equals("0") && !split[0].equals("1") && !split[0].equals("2") && !split[0].equals("3") && !split[0].equals("4") && !split[1].equals("0") && !split[1].equals("1") && !split[1].equals("2") && !split[1].equals("3") && !split[1].equals("4")) {
            printRed("WRONG VALUE!\nPLEASE, REINSERT COORDINATES (from 0 up to 4): ");
            split = splitter(input());
        }

        return split;
    }

    //-----MENU-----
    public void printMenu(String role) {
        clearShell();
        printWhite("[CHAT]  [BOARD]  [ACTIONS]  [OPPONENTS]  [POWER]\n");

        boolean goOut = false;
        printRed("INSERT ONE OF THE OPTION ABOVE: ");
        String keyboard = input();

        do {
            //goOut = true;
            switch (keyboard.toUpperCase()) {
                case "CHAT":
                    printRed("NOTHING TO SHOW. INSERT ONE OF THE OPTION ABOVE: ");
                    keyboard = input();
                    //goOut = false;
                    break;
                case "BOARD":
                    printRed("\n");
                    map.printMap();
                    break;
                case "ACTIONS":
                    if (role.equalsIgnoreCase("CHALLENGER")) {
                        printRed("INSERT ");
                        printYellow("\"CHOOSE CARDS\"");
                        printRed(" TO GO ON: ");
                        String action = input();
                        while (!action.equalsIgnoreCase("CHOOSE CARDS")) {
                            printRed("WRONG! INSERT ");
                            printYellow("\"CHOOSE CARDS\"");
                            printRed(" TO GO ON: ");
                            action = input();
                        }
                        challengerChooseCards();
                        break;
                    } else {
                        printRed("NOTHING TO SHOW. INSERT ONE OF THE OPTION ABOVE: ");
                        keyboard = input();
                        //goOut = false;
                    }
                    break;
                case "OPPONENTS":
                    printRed("YOUR OPPONENTS ARE:\n");
                    for (Player opponent : opponents) {
                        printRed("[");
                        printPlayer(opponent.getNickName(), opponent);
                        printRed("]\n");
                    }

                    keyboard = reprintMenu();
                    break;
                case "POWER":
                    try {
                        printPower(getPlayerFromNickname(getNickName()).getPower().getName());
                    } catch (NullPointerException e) {
                        printRed("YOUR CARD DOESN'T ALREADY CHOOSE\n");
                    }

                    keyboard = reprintMenu();
                    break;
                default:
                    goOut = true;
                    //goOut = false;
                    printRed("WRONG OPTION!");
            }

        }while(!goOut);
    }

    public String reprintMenu() {
        input();
        clearShell();
        printWhite("[CHAT]  [BOARD]  [ACTIONS]  [OPPONENTS]  [POWER]\n");
        printRed("INSERT ONE OF THE OPTION ABOVE: ");
        return input();
    }

    public void printYourTurn() {
        printRed("IT'S YOUR TURN!\nCHOOSE YOUR POWER\n");
    }

    //--------------

    //------------------------------

    //---------- OVERRIDING FUNCTIONS ----------

    @Override
    public void updateLobbyPlayer() {
        synchronized (this) {
            backThread.interrupt();

            clearShell();
            printRed("WAITING LOBBY\n");
            int waitingPlayers;

            waitingPlayers = getNumberOfPlayers() - getPlayers().size();
            printRed("WAITING FOR " + waitingPlayers + " PLAYERS\nPLAYERS ACTUALLY IN THE LOBBY:\n");

            for (Player p : getPlayers()) {
                printRed(">>> ");
                printPlayer(p.getNickName(), p);
                printRed("\n");
            }

            backThread = new Thread(this::checkBackCommand);
            //backThread.start();
        }
    }

    @Override
    public void nickUsed() {
        clearShell();
        printRed("NICKNAME ALREADY USED. PLEASE, REINSERT NEW NICKNAME: ");
        setNickName();

        updateNickName(getNickName());
    }

    @Override
    public synchronized void startGame() {
        backThread.interrupt();

        for (Player player : getPlayers()) {
            if (!player.getNickName().equalsIgnoreCase(getNickName()))
                opponents.add(player);
        }

        clearShell();
        printRed("GAME IS GOING TO START. PLEASE WAIT WHILE IS LOADING\nINSERT SOMETHING TO GO ON: ");
        input();

    }

    @Override
    public synchronized void challengerChoice(String challengerNick, boolean isYourPlayer) {
        String role;
        clearShell();
        if (isYourPlayer) {
            printRed("YOU HAVE BEEN CHOSEN AS CHALLENGER!\n");
            role = "CHALLENGER";
        } else {
            printRed("PLAYER ");
            printPlayer(challengerNick);
            printRed(" IS CHOOSING CARDS\nINSERT SOMETHING TO GO ON: ");
            input();
            role = "";
        }

        mainThread = new Thread(() -> printMenu(role));
        mainThread.start();
    }

    @Override
    public void cardChoice(String challengerNick, boolean isYourPlayer) {
        mainThread.interrupt();
        clearShell();
        printYourTurn();
        mainThread.start();

        if(isYourPlayer) {
            printRed("AVAILABLE CARDS:\n");
            for(String s: getAvailableCards()) {
                printYellow(s + "\n");
                printPower(s);
            }
            printRed("\nINSERT THE NAME OF THE CARD YOU WANT TO CHOOSE: ");
            String choice = input();

            choice = controlCardChoice(choice);

            if(!choice.equalsIgnoreCase(""))
                cardChoiceResponse(choice);
        }
        else {
            printRed("PLAYER ");
            printPlayer(challengerNick);
            printRed("IS CHOOSING HIS POWER\n");
        }

    }

    @Override
    public void placeWorker(String challengerNick, boolean isYourPlayer) {
        clearShell();

        if(isYourPlayer) {
            printRed("INSERT COORDINATES (from 0 up to 4) OF THE TILE IN WHICH YOU WANT TO PLACE FIRST WORKER: ");
            Integer[] tile1 = getCoordinates();

            printRed("INSERT COORDINATES (from 0 up to 4) OF THE TILE IN WHICH YOU WANT TO PLACE SECOND WORKER: ");
            Integer[] tile2 = getCoordinates();

            cliPlaceWorkersResponse(tile1, tile2);
        } else {
            printRed("PLAYER ");
            printPlayer(challengerNick);
            printRed(" IS PLACING HIS WORKERS\n");
        }
    }

    @Override
    public void updatePlacedWorkers(List<Square> squares) {

    }

    @Override
    public void updateBoard(String nick, List<Square> squares, MessageType type) {

    }

    @Override
    public void notifyWin(String nick) {

    }

    @Override
    public void notifyLose(String nick, boolean isYourPlayer) {

    }

    @Override
    public void displayActions(List<MessageType> actions) {
        printRed("YOU CAN CHOOSE ONE OF THIS ACTIONS:\n");
        printYellow(actions.toString());
        printRed("\n");
    }

    @Override
    public void addConstraint(String name) {

    }

    @Override
    public void removeConstraint(String name) {

    }

    @Override
    public void onTurnTimerEnded(String stopper) {

    }

    @Override
    public void onStoppedGame(String stopper) {

    }

    @Override
    public void onLobbyDisconnection() {
        clearShell();
        printRed("YOU ARE GOING TO BE DISCONNECTED FROM THE LOBBY. DO YOU WANT TO BE RECONNECTED OR DO YOU WANT TO CLOSE THE APPLICATION ?\n");
        printRed("  [RECONNECT]\n  [CLOSE]\n");

        boolean goOut = false;
        int keyboardIn = getArrowUpDown();

        do {
            clearShell();
            switch (keyboardIn) {
                case 183:
                    print("> [RECONNECT]\n", Color.ANSI_YELLOW);
                    printRed("  [CLOSE]\n");
                    controlWaitEnter("up&down");
                    break;
                case 184:
                    printRed("  [RECONNECT]\n");
                    print("> [CLOSE]\n", Color.ANSI_YELLOW);
                    controlWaitEnter("up&down");
                    break;
                default:
                    goOut = true;
                    if(keyboardIn != 13)
                        printErr("NO KEYBOARD CATCHED");
            }
        }while (!goOut);

    }

    @Override
    public void onPingDisconnection() {

    }

    @Override
    public void onEndGameDisconnection() {

    }

    @Override
    public void newChatMessage(String nick, String message) {

    }

    @Override
    public void onErrorMessage(String stopper, boolean isYourPlayer) {

    }

    @Override
    public void notYourTurn() {

    }


    @Override
    public void startTurn(String nick, boolean isYourPlayer) {

    }
}
