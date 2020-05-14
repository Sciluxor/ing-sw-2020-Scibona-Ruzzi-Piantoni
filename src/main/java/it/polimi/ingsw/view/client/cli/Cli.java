package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.client.ClientGameController;
import it.polimi.ingsw.network.message.MessageType;

import java.util.*;

import static it.polimi.ingsw.utils.ConstantsContainer.*;
import static it.polimi.ingsw.view.client.cli.CliUtils.*;

public class Cli extends ClientGameController {

    private int port = 4700;
    private String address = "127.0.0.1";
    private String nickName;
    private int numberOfPlayers;

    private Thread backThread = new Thread();
    private Thread mainThread = new Thread();

    private Map<String, Card> deck = CardLoader.loadCards();
    private List<String> deckOrdered = new ArrayList<>();

    private static final String TITLE = "\u001B[31m" +
            "             ___       ___  ___          ___    _____   ___      ___               _____  ___   ___                  |_|  |_|\n" +
            " \\   \\/   / |    |    |    |   | |\\  /| |         |    |   |    |       /\\   |\\  |   |   |   | |   | | |\\  | |     ___________\n" +
            "  \\  /\\  /  |--  |    |    |   | | \\/ | |--       |    |   |    |---|  /--\\  | \\ |   |   |   | |___| | | \\ | |     |   _|_   |\n" +
            "   \\/  \\/   |___ |___ |___ |___| |    | |___      |    |___|     ___| /    \\ |  \\|   |   |___| |  \\  | |  \\| |      |_______|\n\n\n\u001B[0m";


    public void start() {
        String keyboard;

        clearShell();
        printRed(TITLE);
        login(false);

        printRed("INSERT THE PORT NUMBER (default as 4700): ");
        keyboard = input();
        if(!keyboard.equals(""))
            setPort(Integer.parseInt(keyboard));

        printRed("INSERT THE IP ADDRESS (default as 127.0.0.1 - localhost): ");
        keyboard = input();
        if(!keyboard.equals(""))
            setAddress(keyboard);

        try {
            openConnection(getNickName(), getNumberOfPlayers(), getAddress(), getPort());
        }catch (Exception e) {
            printErr("FAILED TO OPENING CONNECTION");
            CliUtils.LOGGER.severe(e.getMessage());
            controlWaitEnter("enter");
        }
    }

    /*public void lobby() {
        clearShell();
        print("WAITING LOBBY\n");
        int waitingPlayers;

        waitingPlayers = getNumberOfPlayers() - 1;
        print("WAITING FOR " + waitingPlayers + " PLAYERS\nPLAYERS ACTUALLY IN THE LOBBY:\n");

        print(">>> " + getNickName() + "\n");

        backThread = new Thread(this::checkBackCommand);
        backThread.start();

    }*/

    public void login(boolean lobbyCall) {

        setNickName();
        setNumberOfPlayers();

        if(lobbyCall)
            newGame(getNickName(), getNumberOfPlayers());
    }

    public List<String> challengerChooseCards() {
        List<String> chosenCards = new ArrayList<>();
        String keyboard;

        for(String s: deck.keySet())
            orderCards(s);

        if(getNumberOfPlayers()==2)
            deckOrdered.remove("chronus");

        selectCards();

        /*keyboard = input().toLowerCase();
        String[] cards = splitter(keyboard);

        cards = printPower(cards, keyboard);

        for(int i=0; i<cards.length; i++) {
            Card card = deck.get(cards[i]);
            if(card == null) {
                printRed("WRONG CARD NAME. PLEASE, REINSERT NEW CARD NAME: ");
                keyboard = input().toLowerCase();
                cards = splitter(keyboard);
            }
        }

        clearShell();
        printRed("THE DECK OF THIS GAME IS COMPOSED BY: ");
        for (String card : cards) {
            print(card.toUpperCase() + " ", Color.ANSI_YELLOW);
        }
        printRed("\n");

        Collections.addAll(chosenCards, cards);
        return chosenCards;*/
        return null;
    }

    //-------------------------------

    //---------- SETTER & GETTER ----------

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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

    public void checkBackCommand() {
        printRed("INSERT \"BACK\" TO TURN BACK IN THE LOGIN WINDOW: ");
        String keyboard = input();

        if (keyboard.equalsIgnoreCase("BACK")) {
            onBackCommand();
            login(true);
        }

    }

    public void orderCards(String s) {
        for(int x=0; x < deckOrdered.size(); x++) {
            if (deckOrdered.get(x).compareTo(s) > 0) {
                deckOrdered.add(x, s);
                return;
            }
        }
        deckOrdered.add(s);
    }

    public void selectCards() {
        clearShell();
        printCards();

        int counter = 0;
        boolean goOut = false, firstPosition = false, lastPosition = false;
        int keyboardIn = getArrowUpDown();

        do {
            clearShell();
            switch (keyboardIn) {
                case 184:
                    if (!lastPosition)
                        counter++;
                    break;
                case 183:
                    if (counter == 0)
                        counter++;
                    else if (!firstPosition)
                        counter--;
                    break;
                default:
                    goOut = true;
                    if (keyboardIn != 13)
                        printErr("NO KEYBOARD CATCHED");
            }

            if(counter == 1)
                firstPosition = true;
            else if(counter == 2)
                firstPosition = false;
            else if(getNumberOfPlayers()==2) {
                if(counter == 12)
                    lastPosition = false;
                else if(counter == 13)
                    lastPosition = true;
            }
            else if(getNumberOfPlayers() != 2) {
                if(counter == 13)
                    lastPosition = false;
                else if(counter == 14)
                    lastPosition = true;
            }

            if(counter!=0)
                printCards(counter-1);


            keyboardIn = controlWaitEnter("up&down");
        }while(!goOut);
    }

    public void printCards(int counter) {
        for(int i=0; i < deckOrdered.size(); i++) {
            if(counter == i) {
                print("> " + deckOrdered.get(i).toUpperCase() + ":\n", Color.ANSI_PURPLE);
            }
            else
                print("  " + deckOrdered.get(i).toUpperCase() + "\n", Color.ANSI_YELLOW);
        }
    }

    public void printCards() {
        for (String s : deckOrdered) {
            print("  " + s.toUpperCase() + "\n", Color.ANSI_YELLOW);
        }
    }

    public String[] printPower(String[] cards, String keyboard) {
        while(cards.length == 1)
        {
            Card card = deck.get(cards[0]);
            if(card != null) {
                printRed("THIS IS THE POWER OF ");
                print(keyboard.toUpperCase(), Color.ANSI_YELLOW);
                printRed(":\n");
                if (keyboard.equalsIgnoreCase("ATHENA") || keyboard.equalsIgnoreCase("HERA"))
                    print("OPPONENT'S TURN:", Color.ANSI_BLUE);
                else if (keyboard.equalsIgnoreCase("HYPNUS"))
                    print("START OF OPPONENT'S TURN:", Color.ANSI_BLUE);
                else
                    print(deck.get(keyboard).getType().toString() + ":", Color.ANSI_BLUE);
                printRed(deck.get(keyboard).getDescription() + "\n\n");
            }
            else
                printRed("WRONG CARD NAME. PLEASE, REINSERT NEW CARD NAME: ");

            keyboard = input().toLowerCase();
            cards = splitter(keyboard);
        }
        if(cards.length != getPlayers().size()) {
            printRed("WRONG NUMBER OF CARDS. PLEASE, REINSERT " + getPlayers().size() + " CARDS: ");
            keyboard = input().toLowerCase();
            cards = splitter(keyboard);
        }

        return cards;
    }

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

    public void printMenu() {
        clearShell();
        printWhite("[CHAT]  [BOARD]  [ACTIONS]  [OPPONENTS]  [POWER]\n");

        int counter = 0;
        boolean goOut = false, firstPosition = false, lastPosition = false;
        int keyboardIn = getArrowLeftRight();

        do {
            clearShell();
            switch (keyboardIn) {
                case 185:
                    if (!lastPosition)
                        counter++;
                    break;
                case 186:
                    if (counter == 0)
                        counter++;
                    else if (!firstPosition)
                        counter--;
                    break;

                default:
                    goOut = true;
                    if (keyboardIn != 13)
                        printErr("NO KEYBOARD CATCHED");
            }

            if(counter == 1) {
                firstPosition = true;
                print("[CHAT]", Color.ANSI_PURPLE);
                printWhite("  [BOARD]  [ACTIONS]  [OPPONENTS]  [POWER]\n");
                //printCHAT

            } else if(counter == 2) {
                firstPosition = false;
                printWhite("[CHAT]  ");
                print("[BOARD]", Color.ANSI_PURPLE);
                printWhite("  [ACTIONS]  [OPPONENTS]  [POWER]\n");
                //printBOARD

            } else if(counter == 3) {
                printWhite("[CHAT]  [BOARD]  ");
                print("[ACTIONS]", Color.ANSI_PURPLE);
                printWhite("  [OPPONENTS]  [POWER]\n");
                //printACTIONS

            } else if(counter == 4) {
                lastPosition = false;
                printWhite("[CHAT]  [BOARD]  [ACTIONS]  ");
                print("[OPPONENTS]", Color.ANSI_PURPLE);
                printWhite("  [POWER]\n");
                //printOPPONENTS

            } else if(counter == 5) {
                lastPosition = true;
                printWhite("[CHAT]  [BOARD]  [ACTIONS]  [OPPONENTS]  ");
                print("[POWER]\n", Color.ANSI_PURPLE);
                //printPOWER

            }

            keyboardIn = controlWaitEnter("left&right");
        }while(!goOut);

    }

    public void printYourTurn() {
        printRed("IT'S YOUR TURN!\nCHOOSE YOUR POWER\n");
    }

    public void printActions(String role) {

    }

    //------------------------------

    //---------- OVERRIDING FUNCTIONS ----------

    @Override
    public synchronized void updateLobbyPlayer() {

        backThread.interrupt();

        clearShell();
        printRed("WAITING LOBBY\n");
        int waitingPlayers;

        waitingPlayers = getNumberOfPlayers() - getPlayers().size();
        printRed("WAITING FOR " + waitingPlayers + " PLAYERS\nPLAYERS ACTUALLY IN THE LOBBY:\n");

        for (Player p : getPlayers())
            print(">>> " + p.getNickName() + "\n", getColorCliFromPlayer(p.getColor()));

        backThread = new Thread(this::checkBackCommand);
        backThread.start();
    }

    @Override
    public void nickUsed() {
        clearShell();
        printRed("NICKNAME ALREADY USED. PLEASE, REINSERT A NEW NICKNAME: ");
        setNickName();
    }

    @Override
    public synchronized void startGame() {

        backThread.interrupt();
        clearShell();
        printRed("GAME IS GOING TO START. PLEASE WAIT WHILE IS LOADING\n");
        controlWaitEnter("enter");

    }

    @Override
    public void challengerChoice(String challengerNick, boolean isYourPlayer) {
        clearShell();
        if(isYourPlayer) {
            printRed("YOU HAVE BEEN CHOSEN AS CHALLENGER!\n");
            controlWaitEnter("enter");

            clearShell();
            //STAMPE DA METTERE IN ACTION
            printRed("PLEASE, CHOOSE " + getPlayers().size() + " CARDS ");
            print("(insert ONLY ONE card to see its power)", Color.ANSI_BLUE);
            printRed(":\n");

            List<String> cards = challengerChooseCards();

            challengerResponse(challengerNick, cards);
        }
        else {
            printRed("PLAYER ");
            print(challengerNick.toUpperCase(), Color.ANSI_YELLOW);
            printRed(" IS CHOOSING CARDS\n");
            controlWaitEnter("enter");
        }

        mainThread = new Thread(this::printMenu);
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
            for(String s: getAvailableCards())
                print(s + "\n", Color.ANSI_YELLOW);
            printRed("\nINSERT THE NAME OF THE CARD YOU WANT TO CHOOSE: ");
            String choose = input();

            //FARE CONTROLLO SCELTA CORRETTA

            cardChoiceResponse(choose);
        }
        else {
            printRed("PLAYER " + challengerNick + "IS CHOOSING HIS POWER");
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
        }
        else
            printRed("PLAYER " + challengerNick + " IS PLACING HIS WORKERS\n");
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
        printRed("CHOOSE YOUR POWER\n");
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
    public void errorMessage() {

    }

    @Override
    public void startTurn(String nick, boolean isYourPlayer) {

    }
}
