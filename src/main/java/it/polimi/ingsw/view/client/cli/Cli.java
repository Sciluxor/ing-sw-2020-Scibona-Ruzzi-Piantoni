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
    private SantoriniMap map = new SantoriniMap();

    private Thread backThread = new Thread();
    private Thread mainThread = new Thread();

    private Map<String, Card> deck = CardLoader.loadCards();
    private List<String> deckOrdered = new ArrayList<>();
    private List<String> selectedCards = new ArrayList<>();
    private List<Player> opponents = new ArrayList<>();
    private List<Player> actualPlayers = new ArrayList<>();
    List<String> availableActions;

    private static final String TITLE = "\u001B[31m" +
            "             ___       ___  ___          ___    _____   ___      ___               _____  ___   ___                  |_|  |_|\n" +
            " \\   \\/   / |    |    |    |   | |\\  /| |         |    |   |    |       /\\   |\\  |   |   |   | |   | | |\\  | |     ___________\n" +
            "  \\  /\\  /  |--  |    |    |   | | \\/ | |--       |    |   |    |---|  /--\\  | \\ |   |   |   | |___| | | \\ | |     |   _|_   |\n" +
            "   \\/  \\/   |___ |___ |___ |___| |    | |___      |    |___|     ___| /    \\ |  \\|   |   |___| |  \\  | |  \\| |      |_______|\n\n\n\u001B[0m";


    public void start() {

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

    public void challengerChooseCards() {
        for(String s: deck.keySet())
            orderCards(s);

        if(getNumberOfPlayers()==2)
            deckOrdered.remove("chronus");

        selectCards();
        String firstPlayer = selectFirstPlayer();

        printDebug("\nFIRSTPLAYER: " + firstPlayer + "\nSELECTED CARDS:\n" + selectedCards);

        challengerResponse(firstPlayer, selectedCards);
        printDebug("CHALLENGERRESPONSE");
        endTurn();
        printDebug("AFTER INTERRAPT");
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
        for(Player player: actualPlayers) {
            if(player.getNickName().equalsIgnoreCase(nickName))
                return player;
        }
        return null;
    }

    public void checkBackCommand() {
        printRed("INSERT \"BACK\" TO TURN BACK IN THE LOGIN WINDOW: ");
        String keyboard = input();

        if (keyboard.equalsIgnoreCase("BACK")) {
            onBackCommand();
            login(true);
        }

    }

    public String selectFirstPlayer() {
        clearShell();
        printRed("PLEASE, SELECT THE ONE YOU WANT AS FIRST PLAYER: \n");
        for(Player p: actualPlayers) {
            printRed("  ");
            printPlayer(p.getNickName(), p);
            printRed("\n");
        }

        int keyboard = getArrowUpDown(), counter = 0;
        boolean goOut = false, firstPosition = false, lastPosition = false, twoPlayers = getNumberOfPlayers() == 2;

        do {
            switch (keyboard) {
                case 183:
                    if(counter == 0)
                        counter++;
                    else if(!firstPosition)
                        counter--;
                    break;
                case 184:
                    if(!lastPosition)
                        counter++;
                    break;
                default:
                    goOut = true;
                    if(keyboard != 13)
                        printErr("NO KEYBOARD CAUGHT");
            }

            if(!goOut) {
                clearShell();
                printRed("PLEASE, SELECT THE ONE YOU WANT AS FIRST PLAYER: \n");
                if(counter == 1) {
                    firstPosition = true;
                    lastPosition = false;

                    printSelectedPlayer(actualPlayers.get(0));
                } else if(counter == 2) {
                    firstPosition = false;
                    lastPosition = twoPlayers;

                    printSelectedPlayer(actualPlayers.get(1));
                } else if(counter == 3) {
                    lastPosition = true;

                    printSelectedPlayer(actualPlayers.get(2));
                }

                keyboard = controlWaitEnter("up&down");
            }
        }while (!goOut);

        return actualPlayers.get(counter-1).getNickName();
    }

    public void printSelectedPlayer(Player player) {
        for(Player p: actualPlayers) {
            if(p == player)
                printYellow("> ");
            else
                printRed("  ");

            printPlayer(p.getNickName(), p);
            printYellow("\n");
        }
    }

    public void printPlayer(String nickName, Player player) {
        print(nickName.toUpperCase(), getColorCliFromPlayer(player.getColor()));
    }

    public void printPlayer(String nickName) {
        print(nickName.toUpperCase(), getColorCliFromPlayer(getPlayerFromNickname(nickName).getColor()));
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

    public void selectCards() {

        int cont = 0, numberOfCardsToChoose = actualPlayers.size();

        while (cont < getNumberOfPlayers() && numberOfCardsToChoose > 0) {
            clearShell();
            printRed("PLEASE, CHOOSE " + numberOfCardsToChoose + " CARDS:\n");
            printCards();
            printRed("USE ARROWS UP&DOWN TO SELECT, THEN PRESS ENTER...");
            selectedCards.add(scrollCards(getArrowUpDown(), numberOfCardsToChoose));
            cont++;
            numberOfCardsToChoose--;
        }
    }

    public String scrollCards(int keyboardIn, int numberOfCardsToChoose) {
        int counter = 0;
        boolean goOut = false, firstPosition = false, lastPosition = false;

        do {
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
                        printErr("NO KEYBOARD CAUGHT");
            }

            if (!goOut) {
                clearShell();
                printRed("PLEASE, CHOOSE " + numberOfCardsToChoose + " CARDS:\n");
                if (counter == 1)
                    firstPosition = true;
                else if (counter == 2)
                    firstPosition = false;
                else if (getNumberOfPlayers() == 2) {
                    if (counter == 12)
                        lastPosition = false;
                    else if (counter == 13)
                        lastPosition = true;
                } else if (getNumberOfPlayers() != 2) {
                    if (counter == 13)
                        lastPosition = false;
                    else if (counter == 14)
                        lastPosition = true;
                }

                if (counter != 0)
                    printCards(counter - 1);

                keyboardIn = controlWaitEnter("up&down");
            }
        }while(!goOut);

        String selectedCard = deckOrdered.get(counter-1);
        deckOrdered.remove(counter-1);
        return selectedCard;
    }

    public void printCards(int counter) {

        for(int i=0; i < deckOrdered.size(); i++) {
            if(counter == i) {
                print("> " + deckOrdered.get(i).toUpperCase() + ":\n", Color.ANSI_PURPLE);
                printPower(deckOrdered.get(i));
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
                printErr("WRONG CARD NAME");
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
    public void printMenu(boolean isFirstPlayer, boolean constraint) {
        clearShell();
        if(constraint) {
            printWhite("[CHAT]  [BOARD]  [ACTIONS]  [OPPONENTS]  ");
            print("[POWER]\n\n", Color.ANSI_CYAN);
        } else
            printWhite("[CHAT]  [BOARD]  [ACTIONS]  [OPPONENTS]  [POWER]\n\n");

        int counter = 0;
        boolean goOut = false, firstPosition = false, lastPosition = false, canGoOut = false;

        int keyboardIn = getArrowLeftRight();

        do {
            clearShell();
            switch (keyboardIn) {
                case 185:
                    canGoOut = false;
                    printDebug("HERE");
                    if (!lastPosition)
                        counter++;
                    break;
                case 186:
                    canGoOut = false;
                    printDebug("HERE");
                    if (counter == 0)
                        counter++;
                    else if (!firstPosition)
                        counter--;
                    break;

                default:
                    goOut = true;
                    if (keyboardIn != 13) {
                        printErr("NO KEYBOARD CAUGHT");
                        counter = 0;
                    }
            }

            if(!goOut) {
                if (counter == 1) {
                    firstPosition = true;
                    printYellow("[CHAT]");
                    printWhite("  [BOARD]  [ACTIONS]  [OPPONENTS]  [POWER]\n\n");

                    //canGoOut = true;
                } else if (counter == 2) {
                    firstPosition = false;
                    printWhite("[CHAT]  ");
                    printYellow("[BOARD]");
                    printWhite("  [ACTIONS]  [OPPONENTS]  [POWER]\n\n");

                    //canGoOut = true;
                } else if (counter == 3) {
                    printWhite("[CHAT]  [BOARD]  ");
                    printYellow("[ACTIONS]");
                    printWhite("  [OPPONENTS]  [POWER]\n");
                    if (isFirstPlayer)
                        printActions();
                    else
                        printRed("\n");

                    //canGoOut = isFirstPlayer;
                } else if (counter == 4) {
                    lastPosition = false;
                    printWhite("[CHAT]  [BOARD]  [ACTIONS]  ");
                    printYellow("[OPPONENTS]");
                    printWhite("  [POWER]\n");
                    for (Player p : opponents) {
                        printWhite("                            [");
                        printPlayer(p.getNickName());
                        printWhite("]\n");
                    }

                    //canGoOut = false;
                } else if (counter == 5) {
                    lastPosition = true;
                    printWhite("[CHAT]  [BOARD]  [ACTIONS]  [OPPONENTS]  ");
                    printYellow("[POWER]\n");
                    try {
                        printPower(getPlayerFromNickname(getNickName()).getPower().getName());
                    } catch (NullPointerException e) {
                        printRed("YOUR CARD DOESN'T ALREADY CHOOSE\n");
                    }
                    //printConstraint

                    //canGoOut = false;
                }

                keyboardIn = controlWaitEnter("left&right");
            }
        }while(!goOut /*&& !canGoOut*/);

        selectMenu(counter, isFirstPlayer);
    }

    public void selectMenu(int counter, boolean isFirstPlayer) {
        switch (counter) {
            case 1:
                //printChat
                break;
            case 2:
                printWhite("\n");
                map.printMap();
                break;
            case 3:
                if(isFirstPlayer)
                    startSelectedActions(selectActions());
                break;
            default:
                printErr("ERROR IN CHOICE");
        }
    }

    public int selectActions() {
        int counter = 0, size = availableActions.size();
        boolean goOut = false, firstPosition = true, lastPosition = false;

        clearShell();
        printWhite("[CHAT]  [BOARD]  ");
        printYellow("[ACTIONS]");
        printWhite("  [OPPONENTS]  [POWER]\n");
        for(String s: availableActions)
            printWhite("                 [" + s + "]\n");

        int keyboard = getArrowUpDown();

        do {
            switch (keyboard) {
                case 184:
                    //printDebug("HERE");
                    if (!lastPosition)
                        counter++;
                    break;
                case 183:
                    //printDebug("HERE");
                    if (!firstPosition)
                        counter--;
                    break;

                default:
                    goOut = true;
                    if (keyboard != 13) {
                        printErr("NO KEYBOARD CAUGHT");
                    }
            }

            if(!goOut) {
                clearShell();
                printWhite("[CHAT]  [BOARD]  ");
                printYellow("[ACTIONS]");
                printWhite("  [OPPONENTS]  [POWER]\n");

                firstPosition = counter == 0;

                for (int i = 1; i <= size; i++) {
                    if (i == counter) {
                        printYellow("                 [" + availableActions.get(counter - 1) + "]\n");
                    } else
                        printWhite("                 [" + availableActions.get(i - 1) + "]\n");
                    lastPosition = counter == size;
                }

                keyboard = controlWaitEnter("up&down");
            }
        }while (!goOut);

        return counter;
    }

    public void startSelectedActions(int choice) {
        String choiceString = availableActions.get(choice-1);
        switch (choiceString) {
            case "CHOOSE CARDS":
                challengerChooseCards();
                break;
            case "MOVE":
                //move
                break;
            case "BUILD":
                //build;
                break;
            case "SELECT WORKER":
                //setWorker();
                break;
            case "CHALLENGER CHOICE":
                //challengerchoice
                break;
            case "CHOOSE POWER":
                //choosecard
                break;
            default:
                printErr("ERRORE IN SELECTED ACTION");
        }
    }

    public void printActions() {
        for(String s: availableActions) {
            printWhite("                 [" + s + "]\n");
        }
    }

    public void printYourTurn() {
        printRed("IT'S YOUR TURN!\nCHOOSE YOUR POWER\n");
    }

    //--------------

    //------------------------------

    //---------- OVERRIDING FUNCTIONS ----------

    @Override
    public void updateLobbyPlayer() {
        backThread.interrupt();

        clearShell();
        printRed("WAITING LOBBY\n");
        int waitingPlayers;

        waitingPlayers = getNumberOfPlayers() - getPlayers().size();
        printRed("WAITING FOR " + waitingPlayers + " PLAYERS\nPLAYERS ACTUALLY IN THE LOBBY:\n");

        actualPlayers = getPlayers();
        for (Player p : actualPlayers) {
            printRed(">>> ");
            printPlayer(p.getNickName(), p);
            printRed("\n");
        }

        backThread = new Thread(this::checkBackCommand);
        //backThread.start();
    }

    @Override
    public void nickUsed() {
        clearShell();
        printRed("NICKNAME ALREADY USED. PLEASE, REINSERT A NEW NICKNAME: ");
        setNickName();
        updateNickName(getNickName());
    }

    @Override
    public void startGame() {

        backThread.interrupt();

        for (Player player : getPlayers()) {
            if (!player.getNickName().equalsIgnoreCase(getNickName()))
                opponents.add(player);
        }

        clearShell();
        printRed("GAME IS GOING TO START. PLEASE WAIT WHILE IS LOADING\n");
        controlWaitEnter("enter");

    }

    @Override
    public void challengerChoice(String challengerNick, boolean isYourPlayer) {
        clearShell();
        if (isYourPlayer) {
            printRed("YOU HAVE BEEN CHOSEN AS CHALLENGER!\n");
            controlWaitEnter("enter");
            availableActions = new ArrayList<>();
            availableActions.add("CHOOSE CARDS");

        } else {
            printRed("PLAYER ");
            printPlayer(challengerNick);
            printRed(" IS CHOOSING CARDS\n");
            controlWaitEnter("enter");

        }

        mainThread = new Thread(() -> printMenu(isYourPlayer, false));
        mainThread.start();
    }

    @Override
    public void cardChoice(String challengerNick, boolean isYourPlayer) {
        printDebug("CARDCHOICE");

        //mainThread.interrupt();
        clearShell();
        //printYourTurn();
        //mainThread.start();

        if (isYourPlayer) {
            printRed("YOU HAVE BEEN CHOSEN AS FIRST PLAYER!\n");
            controlWaitEnter("enter");
            availableActions = new ArrayList<>();
            availableActions.add("CHOOSE POWER");

        } else {
            printRed("PLAYER ");
            printPlayer(challengerNick);
            printRed(" IS CHOOSING HIS POWER\n");
            controlWaitEnter("enter");
        }

        printMenu(isYourPlayer, false);


        /*if(isYourPlayer) {
            printRed("AVAILABLE CARDS:\n");
            for(String s: getAvailableCards())
                printYellow(s + "\n");
            printRed("\nINSERT THE NAME OF THE CARD YOU WANT TO CHOOSE: ");
            String choose = input();

            //FARE CONTROLLO SCELTA CORRETTA

            cardChoiceResponse(choose);
        }
        else {
            printRed("PLAYER " + challengerNick + "IS CHOOSING HIS POWER");
        }*/

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
        availableActions = new ArrayList<>();
        for(MessageType m: actions) {
            switch (m) {
                case BUILDWORKER:
                    availableActions.add("BUILD");
                    break;
                case MOVEWORKER:
                    availableActions.add("MOVE");
                    break;
                case CHALLENGERCHOICE:
                    availableActions.add("CHALLENGER CHOICE");
                    break;
                case CHOOSECARD:
                    availableActions.add("CHOOSE CARD");
                    break;
                case WORKERCHOICE:
                    availableActions.add("SELECT WORKER");
                    break;
            }
        }
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
                        printErr("NO KEYBOARD CAUGHT");
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
