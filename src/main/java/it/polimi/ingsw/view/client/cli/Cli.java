package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.client.ClientGameController;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.utils.CliUtils;
import javafx.util.Pair;

import java.util.*;
import java.util.List;

import static it.polimi.ingsw.utils.ConstantsContainer.*;
import static it.polimi.ingsw.utils.CliUtils.*;

/**
 * Class that extends ClientGameController that start the application for the Cli
 * @author edoardopiantoni
 * @version 1.0
 * @since 2020/06/18
 */

public class Cli extends ClientGameController {

    private int port;
    private String address;
    private String nickName;
    private int numberOfPlayers;
    private SantoriniMap santoriniMap = new SantoriniMap();
    private Color myPlayerColor;
    private int[] tileNumber = new int[2];
    private int selectedWorker;
    private Player myPlayerOnServer;
    private String myPower;

    private static final String END_TURN_STRING = "endTurn";
    private static final String UP_AND_DOWN_STRING = "up&down";
    private static final String PLAYER_STRING = "PLAYER ";
    private static final String NO_KEYBOARD_CAUGHT = "NO KEYBOARD CAUGHT";
    private static final String END_TURN_CASE = "END TURN";
    private static final String SELECT_WORKER_CASE = "SELECT WORKER";
    private static final String CHAT_CASE = "CHAT";
    private static final String RAW_STRING = "raw";
    private static final String SANE_STRING = "sane";

    private Map<String, Card> deck = CardLoader.loadCards();
    private List<String> deckOrdered = new ArrayList<>();
    private List<String> selectedCards = new ArrayList<>();
    private static List<Player> opponents = new ArrayList<>();
    private static List<Player> actualPlayers = new ArrayList<>();
    private List<String> availableActions = new ArrayList<>();
    private List<String> constraints = new ArrayList<>();
    private List<Pair<Player, String>> previousChatMessage = new ArrayList<>();

    private Response fromServerResponse;

    /**
     * Method that start the Cli
     * @param args args
     */

    public static void main(String[] args) {
        Cli cli = new Cli();
        mainHandler(cli);
    }

    //----- MAIN FUNCTIONS -----

    /**
     * Method that build the login and open the connection with the server
     * @return openedConnection boolean (true if connection successful)
     */

    public boolean start() {
        clearShell();
        printRed(TITLE);
        login();

        printDebug("HERE");
        printDebug("NICK: " + getNickName() + "\nNUMBER: " + getNumberOfPlayers() + "\nPORT: " + getPort() + "\nIP: " + getAddress());

        try {
            openConnection(getNickName(), getNumberOfPlayers(), getAddress(), getPort());
        }catch (Exception e) {
            printErr("FAILED TO OPENING CONNECTION");
            CliUtils.LOGGER.severe(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Method that handle the choice of cards from the entire deck by the challenger
     */

    private void challengerChooseCards() {
        for(String card: deck.keySet())
            orderCards(card);

        if(getNumberOfPlayers()==3)
            deckOrdered.remove("chronus");

        selectCards();
        String firstPlayer = selectFirstPlayer();

        printDebug("\nFIRSTPLAYER: " + firstPlayer + "\nSELECTED CARDS:\n" + selectedCards);

        challengerResponse(firstPlayer, new ArrayList<>(selectedCards));

        selectedCards.clear();
        printDebug("CHALLENGERRESPONSE");
        controlWaitEnter(END_TURN_STRING);
        endTurn();
        printDebug("AFTER ENDTURN");
    }

    /**
     * Method that handle the choice of the power from availableCards (the cards that challenger has chosen in ChallengerChoiceCards) by the current player
     */

    private void playerChoosePower() {

        printDebug("AVAILABLE CARDS: " + getAvailableCards() + "\nDECK ORDERED: " + deckOrdered);

        clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);

        printRed("CHOOSE ONE OF THE CARDS BELOW:\n");
        printCards();
        printRed("USE ARROW TO SELECT YOUR POWER & THEN PRESS ENTER TO CONFIRM...");
        myPower = scrollCards(1);

        cardChoiceResponse(myPower);
        printDebug("CARDCHOICERESPONSE " + myPower);
        controlWaitEnter(END_TURN_STRING);
        endTurn();
        printDebug("AFTER ENDTURN");
    }

    /**
     * Method that handle the placing of the workers of the current player
     */

    private void playerPlaceWorkers() {
        int selectedTile;
        boolean occupied;

        List<Integer> modifiedTiles = new ArrayList<>();
        List<Square> modifiedSquares = getModifiedsquare();
        for (Square modifiedSquare : modifiedSquares) {
            modifiedTiles.add(modifiedSquare.getTile()-1);
        }

        int i=0;
        while(i<2) {
            santoriniMap.setPlaceWorkerNotAvailableTiles(modifiedTiles);
            clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
            santoriniMap.printAvailableTiles();
            printRed("USE ARROWS TO SELECT TILE...\n");

           do {
                occupied = false;
                selectedTile = selectAvailableTileWithArrows();

                if(!santoriniMap.checkUnoccupiedTile(selectedTile)) {
                    occupied = true;
                    printRed(Color.BACKGROUND_YELLOW + "OCCUPIED TILES + " + selectedTile + Color.RESET + "\n");
                }
            }while (occupied);

            tileNumber[i] = selectedTile;
            santoriniMap.setTileHasPlayer(true, tileNumber[i], myPlayerColor);
            santoriniMap.printMap();

            if(controlWaitEnter("confirm")==186) {
                printDebug("TILE TO REMOVE: " + tileNumber[i]);
                santoriniMap.setTileHasPlayer(false, tileNumber[i], null);

                i--;
            } else {
                modifiedTiles.add(tileNumber[i]);
            }
            i++;
        }

        santoriniMap.setPlaceWorkerNotAvailableTiles(modifiedTiles);

        printDebug("LOCAL TILE NUMBER: " + Arrays.toString(tileNumber) + "\nSENDED TILE NUMBER: " + (tileNumber[0]+1) +  " "  + (tileNumber[1]+1));

        placeWorkersResponse(tileNumber[0]+1, tileNumber[1]+1);
        printDebug("PLACEWORKERSRESPONSE");
        controlWaitEnter(END_TURN_STRING);
        endTurn();
        printDebug(END_TURN_STRING);
        santoriniMap.resetAvailableTiles();
    }

    /**
     * Method that handle the selection of one of the workers of the current player that will be the actual worker for the actual turn
     */

    private void playerSelectWorker() {
        santoriniMap.printMap();

        int[] coordinateWorker1 = santoriniMap.getCoordinatesFromTile(tileNumber[0]);
        int[] coordinateWorker2 = santoriniMap.getCoordinatesFromTile(tileNumber[1]);

        String worker1 = "] WORKER 1\n";
        String worker2 = "] WORKER 2\n";
        String centralSquareBrackets = "] [";

        printRed("\nSELECT WITH ARROWS ONE OF YOUR WORKERS:\n  [" + coordinateWorker1[0] + centralSquareBrackets + coordinateWorker1[1] + worker1 + "  [" + coordinateWorker2[0] + centralSquareBrackets + coordinateWorker2[1] + worker2);

        boolean goOut = false;
        int keyboard = getArrowUpDown();

        do{
            clearShell();
            switch (keyboard) {
                case 183:
                    santoriniMap.setSelectedTile(tileNumber[0], true);
                    santoriniMap.setSelectedTile(tileNumber[1], false);
                    santoriniMap.printMap();
                    printRed("SELECT WITH ARROWS ONE OF YOUR WORKERS:\n");
                    printYellow("> [" + coordinateWorker1[0] + centralSquareBrackets + coordinateWorker1[1] + worker1);
                    printRed("  [" + coordinateWorker2[0] + centralSquareBrackets + coordinateWorker2[1] + worker2);


                    keyboard = controlWaitEnter(UP_AND_DOWN_STRING);
                    if (keyboard == 13)
                        selectedWorker = 1;
                    break;
                case 184:
                    santoriniMap.setSelectedTile(tileNumber[0], false);
                    santoriniMap.setSelectedTile(tileNumber[1], true);
                    santoriniMap.printMap();
                    printRed("SELECT WITH ARROWS ONE OF YOUR WORKERS:\n  [" + coordinateWorker1[0] + centralSquareBrackets + coordinateWorker1[1] + worker1);
                    printYellow("> [" + coordinateWorker2[0] + centralSquareBrackets + coordinateWorker2[1] + worker2);

                    keyboard = controlWaitEnter(UP_AND_DOWN_STRING);
                    if (keyboard == 13)
                        selectedWorker = 2;
                    break;
                case 13:
                    santoriniMap.printMap();
                    santoriniMap.setSelectedTile(tileNumber[0], false);
                    santoriniMap.setSelectedTile(tileNumber[1], false);
                    setWorker(selectedWorker);
                    goOut = true;
                    break;
                default:
                    printErr(NO_KEYBOARD_CAUGHT);
            }
        }while (!goOut);
    }

    /**
     * Method that handle the move action of the selected worker of the current player
     */

    private void playerMoveHisWorker() {
        setAvailableTilesInMap(getAvailableTilesFromServer(availableMoveSquare()));

        clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
        santoriniMap.printAvailableTiles();

        int tile = selectAvailableTileWithArrows();
        santoriniMap.resetAvailableTiles();

        santoriniMap.setTileHasPlayer(false, tileNumber[selectedWorker-1], null);
        santoriniMap.setTileHasPlayer(true, tile, myPlayerColor);

        fromServerResponse = moveWorker(tile+1);
        tileNumber[selectedWorker-1] = tile;

        printDebug("MOVEWORKER: " + (tile+1));

        santoriniMap.printMap();
        new Thread(() -> mapNextAction(fromServerResponse)).start();
    }

    /**
     * Method that handle the build action of the selected workers of the current player
     */

    private void playerBuild() {
        setAvailableTilesInMap(getAvailableTilesFromServer(availableBuildSquare()));

        clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
        santoriniMap.printAvailableTiles();

        int tile = selectAvailableTileWithArrows();
        santoriniMap.resetAvailableTiles();

        List<Building> availableBuildings = new ArrayList<>();
        availableBuildings.add(santoriniMap.getAvailableBuildingFromTile(tile));
        if(myPower.equalsIgnoreCase("ATLAS"))
            availableBuildings.add(Building.DOME);

        clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
        printRed("SELECT THE TYPE OF BUILDING YOU WANT TO CONSTRUCT:\n");

        List<String> availableBuildingsString = new ArrayList<>();
        for(Building availableBuilding: availableBuildings) {
            availableBuildingsString.add(availableBuilding.toString());
        }

        int selectedBuildingType = scrollAvailableOptions(availableBuildingsString);

        santoriniMap.updateStringBoardBuilding(availableBuildings.get(selectedBuildingType), tile);

        fromServerResponse = buildWorker((tile+1), santoriniMap.getTileBuilding(tile));
        printDebug("BUILDWORKER: " + (tile+1) +  " " + availableBuildings.get(selectedBuildingType));

        santoriniMap.printMap();
        new Thread(() -> mapNextAction(fromServerResponse)).start();
    }

    //----- SETTER & GETTER -----

    /**
     * Method that get the port in which open the connection
     * @return the port used by the server
     */

    public int getPort() {
        return port;
    }

    /**
     * Method that set the port in which open the connection
     */

    public void setPort() {
        int defaultPort = 4700;
        printRed("INSERT THE PORT NUMBER (default as " + defaultPort + "): ");
        String portInput = input();
        if(!portInput.equals(""))
            this.port = Integer.parseInt(portInput);
        else
            this.port = defaultPort;
    }

    /**
     * Method that get the IP address in which open the connection
     * @return The IP address of the server
     */

    public String getAddress() {
        return address;
    }

    /**
     * Method that set the IP address in which open the connection
     */

    public void setAddress() {
        String defaultAddress = "54.237.47.88";
        printRed("INSERT THE IP ADDRESS (default as " + defaultAddress + "): ");
        String addressInput = input();
        if(!addressInput.equals(""))
            this.address = addressInput;
        else
            this.address = defaultAddress;
    }

    /**
     * Method that get his own nickName
     * @return The nickname
     */

    public String getNickName() {
        return nickName;
    }

    /**
     * Method that set his own nickName
     */

    public void setNickName() {

        printRed("INSERT YOUR NICKNAME: ");
        String nickNameInput = input();
        while(nickNameInput.length() < MIN_LENGHT_NICK || nickNameInput.length() > MAX_LENGHT_NICK) {
            printRed("INVALID NICKNAME LENGHT. PLEASE, REINSERT YOUR NICKNAME: ");
            nickNameInput = input();
        }

        this.nickName = nickNameInput;
    }

    /**
     * Method that get the number of players of the game someone wants to play
     * @return Number of players in the game
     */

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Method that set (and control if is correct) the number of players of the game someone wants to play
     */

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

    /**
     * Method that set a list of actual players
     * @param actualPlayers List of Player currently in the game
     */

    public static void setActualPlayers(List<Player> actualPlayers) {
        Cli.actualPlayers = actualPlayers;
    }

    //----- USEFUL FUNCTIONS -----

    /**
     * Method used to handle main operation to start new game
     * @param cli cli of the game
     */

    private static void mainHandler(Cli cli) {
        boolean openedConnection;
        do {
            openedConnection = cli.start();
        }while (!openedConnection);
    }

    /**
     * Method that handle the login (setting the nickname, the number of players, the port and the IP address)
     */

    private void login() {
        setNickName();
        setNumberOfPlayers();
        setPort();
        setAddress();
    }

    /**
     * Method used to check if user wants to play another game or not
     */

    private void checkRestart(boolean loser) {
        setSaneTerminalModeIfIsRaw();
        printRed("DO YOU WANT TO START NEW GAME? (use arrows to select one of the option)\n  [YES]\n  [NO]\n");
        int keyboard = getArrowUpDown();
        boolean goOut = false;
        boolean restart = false;

        do {
            clearShell();
            printRed("DO YOU WANT TO START NEW GAME? (use arrows to select one of the option)\n");
            if (keyboard == 183) {
                printYellow("> [YES]\n");
                printRed("  [NO]\n");
                restart = true;
            } else if (keyboard == 184) {
                printRed("  [YES]\n");
                printYellow("> [NO]\n");
                restart = false;
            }

            if (keyboard == 13) {
                goOut = true;
            } else {
                keyboard = controlWaitEnter(UP_AND_DOWN_STRING);
            }
        } while (!goOut);

        if (restart) {
            Cli cli = new Cli();
            mainHandler(cli);
        } else {
            boolean watch = false;
            if(loser && getNumberOfPlayers() == 3)
                watch = checkLoserContinueWatch();

            if(!watch)
                quitFromGame(1);
        }
    }

    /**
     * Method used to check if a loser wants to continue to watch the game
     * @return boolean value (true = watch | false = exit from the game)
     */

    private boolean checkLoserContinueWatch() {
        printRed("DO YOU WANT TO CONTINUE WATCH THE GAME? (use arrows to select one of the option)\n  [YES]\n  [QUIT]\n");
        int keyboard = getArrowUpDown();
        boolean goOut = false;
        boolean watch = false;

        do {
            clearShell();
            printRed("DO YOU WANT TO CONTINUE WATCH THE GAME? (use arrows to select one of the option)\n");
            if (keyboard == 183) {
                printYellow("> [YES]\n");
                printRed("  [QUIT]\n");
                watch = true;
            } else if (keyboard == 184) {
                printRed("  [YES]\n");
                printYellow("> [QUIT]\n");
                watch = false;
            }

            if (keyboard == 13) {
                goOut = true;
            } else {
                keyboard = controlWaitEnter(UP_AND_DOWN_STRING);
            }
        } while (!goOut);

        return watch;
    }

    /**
     * Method that handle the standard up and down arrows construct
     * @param keyboard Keyboard value returned by the key pressed by the user
     * @param counter Old counter value we want to update
     * @param firstPosition First position boolean value (if it is on the beginning of the available options list)
     * @param lastPosition Last position boolean value (if it is on the end of the available options list)
     * @return counter Updated counter value
     */

    private int standardUpDownHandler(int keyboard, int counter, boolean firstPosition, boolean lastPosition) {
        switch (keyboard) {
            case 183:
                if (counter == 0)
                    counter++;
                else if (!firstPosition)
                    counter--;
                break;
            case 184:
                if (!lastPosition)
                    counter++;
                break;

            default:
                if(keyboard != 13)
                    printErr(NO_KEYBOARD_CAUGHT);
        }
        return counter;
    }

    /**
     * Method that handle the selection of one of the available options
     * @param availableOptions Available options (like choose power / place workers / move / build / etc.)
     * @return selectedOption Option that is chosen
     */

    private int scrollAvailableOptions (List<String> availableOptions) {
        printRed("SELECT WITH ARROWS ONE OF THE OPTIONS BELOW, THEN PRESS ENTER TO GO ON...\n");
        for(String action: availableOptions)
            printWhite("[" + action + "]\n");

        int counter = 0;
        boolean firstPosition = false;
        boolean goOut = false;

        int keyboard = getArrowUpDown();
        int size = availableOptions.size();

        boolean lastPosition = counter == size;

        do {
            counter = standardUpDownHandler(keyboard, counter, firstPosition, lastPosition);

            if(keyboard == 13)
                goOut = true;

            if(!goOut) {
                clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);

                firstPosition = counter == 1;

                for (int i = 1; i <= size; i++) {
                    if (i == counter) {
                        printYellow("[" + availableOptions.get(counter - 1) + "]\n");
                    } else {
                        printWhite("[" + availableOptions.get(i - 1) + "]\n");
                    }
                    lastPosition = counter == size;
                }

                keyboard = controlWaitEnter(UP_AND_DOWN_STRING);
            }
        }while(!goOut);

        return counter-1;
    }

    /**
     * Method that handle the actuation of the selected action
     * @param choice Value that correspond in the List of available actions to the action we want to actuate
     */

    private void startSelectedActions(int choice) {
        String choiceString = availableActions.get(choice);
        switch (choiceString) {
            case "CHOOSE CARDS":
                challengerChooseCards();
                break;
            case "CHOOSE POWER":
                playerChoosePower();
                break;
            case "PLACE WORKERS":
                playerPlaceWorkers();
                break;
            case "MOVE":
                playerMoveHisWorker();
                break;
            case "BUILD":
                playerBuild();
                break;
            case SELECT_WORKER_CASE:
                playerSelectWorker();
                break;
            case CHAT_CASE:
                handleChatCli();
                break;
            case END_TURN_CASE:
                endTurn();
                clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
                printWaitForOtherPlayers(numberOfPlayers);
                break;
            default:
                printErr("ERROR IN SELECTED ACTION");
        }
    }

    /**
     * Method that handle the chat sending message
     */

    private void handleChatCli() {
        setVisualized(true);
        clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
        printChat(previousChatMessage);
        printRed("MESSAGE: ");
        String chatMessage = input();

        if (!chatMessage.isEmpty()) {
            sendChatMessage(chatMessage);
            handlePreviousChatMessage(myPlayerOnServer, chatMessage);
        }

        clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
        startSelectedActions(scrollAvailableOptions(availableActions));
    }

    /**
     * Method that handle the set of previous message in chat. It saves only the last 10 message
     * @param player Player that sent the message
     * @param chatMessage Message sent by the player
     */

    private void handlePreviousChatMessage(Player player, String chatMessage) {
        if(previousChatMessage.size()>9)
            previousChatMessage.remove(0);

        Pair<Player, String> playerChatMessage = new Pair<>(player, chatMessage);
        previousChatMessage.add(playerChatMessage);
    }

    /*
     * Method used to set sane terminal mode if the terminal mode was raw
     */

    /*private void setSaneTerminalMode() {
        String previousTerminalMode = getTerminalMode();
        if(previousTerminalMode.equalsIgnoreCase("raw"))
            setTerminalMode("sane");
    }*/

    /**
     * Method used to set sane terminal mode if the terminal mode was raw
     */

    private void setSaneTerminalModeIfIsRaw() {
        if(getTerminalMode().equalsIgnoreCase(RAW_STRING)) {
            setTerminalMode(SANE_STRING);
        }
    }

    //----- MAP & TILES -----

    /**
     * Method that handle the updating of the board when updateBoard is called from the server (an opponent do something)
     * @param modifiedSquares Modified squares sended from the server
     */

    private void updateModification(List<Square> modifiedSquares) {
        for(Square modifiedSquare: modifiedSquares) {
            Color playerColor = null;
            if(modifiedSquare.hasPlayer())
                playerColor = getColorCliFromPlayer(modifiedSquare.getPlayer().getColor());

            santoriniMap.setTileHasPlayer(modifiedSquare.hasPlayer(), modifiedSquare.getTile()-1, playerColor);
            santoriniMap.updateStringBoardBuilding(modifiedSquare);
        }
    }

    /**
     * Method that handle the first player choice by the challenger
     * @return firstPlayer
     */

    private String selectFirstPlayer() {
        clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
        printRed("PLEASE, SELECT THE ONE YOU WANT AS FIRST PLAYER: \n");
        for(Player player: actualPlayers) {
            printRed("  ");
            printPlayer(player);
            printRed("\n");
        }

        int keyboard = getArrowUpDown();
        int counter = 0;
        boolean goOut = false;
        boolean firstPosition = false;
        boolean lastPosition = false;
        boolean twoPlayers = getNumberOfPlayers() == 2;

        do {
            counter = standardUpDownHandler(keyboard, counter, firstPosition, lastPosition);

            if(keyboard == 13)
                goOut = true;

            if(!goOut) {
                clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
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

                keyboard = controlWaitEnter(UP_AND_DOWN_STRING);
            }
        }while (!goOut);

        return actualPlayers.get(counter-1).getNickName();
    }

    /**
     * Method that handle the visualisation on the screen of the selected player
     * @param player Selected Player
     */

    private void printSelectedPlayer(Player player) {
        for(Player p: actualPlayers) {
            if(p == player)
                printYellow("> ");
            else
                printRed("  ");

            printPlayer(p);
            printYellow("\n");
        }
    }

    /**
     * Method that get available squares and get from them the corresponding tiles, adding these tiles to availableTiles
     * @param availableSquares Available Squares sended by the server to
     * @return availableTiles a list of available tiles
     */

    private List<Integer> getAvailableTilesFromServer (List<Integer> availableSquares) {
        printDebug("FROM SERVER: " + availableSquares);

        List<Integer> availableTiles = new ArrayList<>();
        for (Integer square : availableSquares)
            availableTiles.add(square - 1);

        return availableTiles;
    }

    /**
     * Method that get available tiles from getAvailableTilesFromServer and set corresponding tiles in the map as available
     * @param availableTiles Available tiles number
     */

    private void setAvailableTilesInMap (List<Integer> availableTiles) {
        santoriniMap.setAvailableTiles(availableTiles);
    }

    /**
     * Method that handle the choice of an available tile with arrows
     * @return selectedTile Selected tile number
     */

    private int selectAvailableTileWithArrows () {
        List<Integer> availableTiles = santoriniMap.getAvailableTiles();

        int keyboard = getArrowUpDown();

        int counter = 0;
        int size = availableTiles.size();
        int selectedTile = -1;
        boolean goOut = false;
        boolean firstPosition = false;
        boolean lastPosition = counter == size;

        do {
            counter = standardUpDownHandler(keyboard, counter, firstPosition, lastPosition);

            if(keyboard == 13)
                goOut = true;

            if(!goOut) {
                firstPosition = counter == 1;
                selectedTile = selectTile(counter, availableTiles);
                lastPosition = counter == size;

                keyboard = controlWaitEnter(UP_AND_DOWN_STRING);
                if(keyboard != 13) {
                    santoriniMap.setSelectedTile(selectedTile, false);
                    santoriniMap.resetTileBackground(selectedTile);
                }
            }
        }while(!goOut);

        santoriniMap.setSelectedTile(selectedTile, false);
        return selectedTile;
    }

    /**
     * Method used to select a tile in availbleTiles
     * @param counter Int value represents which tile from available tiles
     * @param availableTiles List of available tile
     * @return selectedTile Int tile number of the selected tile
     */

    private int selectTile(int counter, List<Integer> availableTiles) {
        int selectedTile = availableTiles.get(counter-1);

        santoriniMap.setSelectedTile(selectedTile, true);
        clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);

        int[] coordinate;
        for(int availableTile: availableTiles) {
            if(availableTile!=selectedTile) {
                coordinate = santoriniMap.getCoordinatesFromTile(availableTile);
                printRed("  [" + coordinate[0] + "] [" + coordinate[1] + "] Tile number: " + (availableTile+1) + "\n");
            } else {
                coordinate = santoriniMap.getCoordinatesFromTile(selectedTile);
                printYellow("> [" + coordinate[0] + "] [" + coordinate[1] + "] Tile number: " + (selectedTile+1) + "\n");
            }
        }
        return selectedTile;
    }

    /**
     * Method used to print some info when it isn't the current user turn
     * @param challengerNick Turn player's nickname
     * @param s string to print
     */

    private void printInfoNotMyTurn(String challengerNick, String s) {
        clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
        printRed(PLAYER_STRING);
        printPlayer(getPlayerFromNickName(opponents, challengerNick));
        printRed(s);
        printWaitForOtherPlayers(numberOfPlayers);
        printChat(previousChatMessage);


    }

    //----- CARDS -----

    /**
     * Method that create an (alphabetical) ordered List of cards
     * @param card Card received from challengerChooseCards that we want to compare to other cards still in deckOrdered
     */

    private void orderCards(String card) {
        for(int x=0; x < deckOrdered.size(); x++) {
            if (deckOrdered.get(x).compareTo(card) > 0) {
                deckOrdered.add(x, card);
                return;
            }
        }
        deckOrdered.add(card);
    }

    /**
     * Method that handle the choice of cards (and print them corresponding power) that challenger wants to choose
     */

    private void selectCards() {

        int cont = 0;
        int numberOfCardsToChoose = actualPlayers.size();

        while (cont < getNumberOfPlayers() && numberOfCardsToChoose > 0) {
            clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
            printRed("PLEASE, CHOOSE " + numberOfCardsToChoose + " CARDS:\n");
            printCards();
            printRed("USE ARROWS UP&DOWN TO SELECT, THEN PRESS ENTER...");
            selectedCards.add(scrollCards(numberOfCardsToChoose));
            cont++;
            numberOfCardsToChoose--;
        }
    }

    /**
     * Method that handle the player to use arrows to move through cards and then select one of them
     * @param numberOfCardsToChoose Number of cards that player has to choose from available cards
     * @return selectedCard the name of the card that player has choose
     */

    private String scrollCards(int numberOfCardsToChoose) {
        int keyboard = getArrowUpDown();
        int counter = 0;
        boolean goOut = false;
        boolean firstPosition = false;
        boolean lastPosition = false;

        do {
            counter = standardUpDownHandler(keyboard, counter, firstPosition, lastPosition);

            if(keyboard == 13)
                goOut = true;

            if (!goOut) {
                clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);

                boolean[] position = getFirstOrLastPosition(counter);
                firstPosition = position[0];
                lastPosition = position[1];

                printRed("PLEASE, CHOOSE " + numberOfCardsToChoose + " CARDS:\n");
                if (counter != 0)
                    printCards(counter - 1);

                keyboard = controlWaitEnter(UP_AND_DOWN_STRING);
            }
        }while(!goOut);

        String selectedCard = deckOrdered.get(counter-1);
        deckOrdered.remove(counter-1);
        return selectedCard;
    }

    private boolean[] getFirstOrLastPosition(int counter) {
        boolean firstPosition = false;
        boolean lastPosition = false;

        if (counter == 1)
            firstPosition = true;
        else if (getNumberOfPlayers() == 3 && counter == 13) {
            lastPosition = true;
        } else if (getNumberOfPlayers() != 3 && counter == 14) {
            lastPosition = true;
        }

        boolean[] position = new boolean[2];
        position[0] = firstPosition;
        position[1] = lastPosition;

        return position;
    }

    /**
     * Method that handle the visualisation of the list of cards
     * @param counter Counter that represents the selected card
     */

    private void printCards(int counter) {

        for(int i=0; i < deckOrdered.size(); i++) {
            if(counter == i) {
                print("> " + deckOrdered.get(i).toUpperCase() + ":\n", Color.ANSI_PURPLE);
                printPower(deckOrdered.get(i), deck);
            }
            else
                print("  " + deckOrdered.get(i).toUpperCase() + "\n", Color.ANSI_YELLOW);
        }
    }

    /**
     * Method that handle the visualisation of the list of cards
     */

    private void printCards() {
        for (String s : deckOrdered) {
            print("  " + s.toUpperCase() + "\n", Color.ANSI_YELLOW);
        }
    }

    //----- OVERRIDING FUNCTIONS -----

    @Override
    public void updateLobbyPlayer() {
        clearShell();
        printRed("WAITING LOBBY\n");
        int waitingPlayers;

        waitingPlayers = getNumberOfPlayers() - getPlayers().size();
        printRed("WAITING FOR " + waitingPlayers + " PLAYERS\nPLAYERS ACTUALLY IN THE LOBBY:\n");

        setActualPlayers(getPlayers());
        for (Player player: actualPlayers) {
            printRed(">>> ");
            printPlayer(player);
            printRed("\n");
        }
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

        printDebug("START GAME");

        for (Player player : getPlayers()) {
            if (!player.getNickName().equalsIgnoreCase(getNickName()))
                opponents.add(player);
            else {
                this.myPlayerColor = getColorCliFromPlayer(player.getColor());
                this.myPlayerOnServer = player;
            }
        }
    }

    @Override
    public void challengerChoice(String challengerNick, boolean isYourPlayer) {

        if (isYourPlayer) {

            availableActions.clear();
            availableActions.add(CHAT_CASE);
            availableActions.add("CHOOSE CARDS");

            clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
            printRed("YOU HAVE BEEN CHOSEN AS CHALLENGER!\n");

            startSelectedActions(scrollAvailableOptions(availableActions));

        } else {

            printInfoNotMyTurn(challengerNick, " IS CHOOSING CARDS\n");

        }
    }

    @Override
    public void cardChoice(String challengerNick, boolean isYourPlayer) {

        if (isYourPlayer) {

            deckOrdered = new ArrayList<>(getAvailableCards());
            printDebug("CARDCHOICE AVAILABLE: " + getAvailableCards());

            availableActions.clear();
            availableActions.add(CHAT_CASE);
            availableActions.add("CHOOSE POWER");

            clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
            printRed("IT'S YOUR TURN TO CHOOSE YOUR POWER!\n");

            startSelectedActions(scrollAvailableOptions(availableActions));

        } else {

            printInfoNotMyTurn(challengerNick, " IS CHOOSING HIS POWER\n");

        }
    }

    @Override
    public synchronized void placeWorker(String challengerNick, boolean isYourPlayer) {

        if (isYourPlayer) {

            availableActions = new ArrayList<>();
            availableActions.add(CHAT_CASE);
            availableActions.add("PLACE WORKERS");

            clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
            printRed("PLACE YOUR WORKERS!\n");

            startSelectedActions(scrollAvailableOptions(availableActions));

        } else {

            printInfoNotMyTurn(challengerNick, " IS PLACING HIS WORKERS\n");

        }
    }

    @Override
    public void updatePlacedWorkers(List<Square> squares) {
        printDebug("HERE UPDATE");
        for (Square square : squares) {
            Color playerColor = null;
            if (square.hasPlayer())
                playerColor = getColorCliFromPlayer(square.getPlayer().getColor());

            santoriniMap.setTileHasPlayer(square.hasPlayer(), square.getTile() - 1, playerColor);
        }
    }

    @Override
    public void updateBoard(String nick, List<Square> squares, MessageType type) {
        updateModification(squares);
        clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
        printWaitForOtherPlayers(numberOfPlayers);
    }

    @Override
    public void notifyWin(String nick) {
        clearShell();
        if(nick.equalsIgnoreCase(getNickName()))
            printRed(WINNER);
        else {
            printRed("LOSERS:\n");
            for (Player opponent : opponents) {
                printRed("> [");
                printPlayer(opponent);
                printRed("]\n");
            }
        }

        checkRestart(false);
    }

    @Override
    public void notifyLose(String nick, boolean isYourPlayer) {
        if(isYourPlayer) {
            printRed(LOSER);
            printRed("THE WINNER IS: ");
            printPlayer(getPlayerFromNickName(opponents, nick));
        }

        checkRestart(true);
    }

    @Override
    public void displayActions(List<MessageType> actions) {
        printDebug("DISPLAYACTIONS " + actions);
        try {
            availableActions = new ArrayList<>();
            availableActions.add((CHAT_CASE));
            for (MessageType m : actions) {
                switch (m) {
                    case BUILDWORKER:
                        availableActions.add("BUILD");
                        break;
                    case MOVEWORKER:
                        availableActions.add("MOVE");
                        break;
                    case WORKERCHOICE:
                        availableActions.add(SELECT_WORKER_CASE);
                        break;
                    case ENDTURN:
                        availableActions.add(END_TURN_CASE);
                        break;
                    default:
                        printErr("NO ACTION TO DISPLAY");
                }
            }
        } catch (NullPointerException e) {
            printErr("NULL POINTER");
            CliUtils.LOGGER.severe(e.getMessage());
        }

        clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
        startSelectedActions(scrollAvailableOptions(availableActions));
    }

    @Override
    public void addConstraint(String name) {
        this.constraints.add(name);
    }

    @Override
    public void removeConstraint(String name) {
        this.constraints.remove(name);
    }

    @Override
    public void onTurnTimerEnded(String stopper) {
        setSaneTerminalModeIfIsRaw();
        printRed("\nTIMER IS ENDED...");
        System.exit(-1);
    }

    @Override
    public void onStoppedGame(String stopper) {
        setSaneTerminalModeIfIsRaw();
        printRed("\nGAME IS STOPPED...\n");
        checkRestart(false);
    }

    @Override
    public void onLobbyDisconnection() {
        setSaneTerminalModeIfIsRaw();
        printRed("\nYOU ARE DISCONNECTED FROM THE LOBBY...");
        quitFromGame(-1);
    }

    @Override
    public void onPingDisconnection() {
        setSaneTerminalModeIfIsRaw();
        printRed("\nPING DISCONNECTION...");
        quitFromGame(-1);
    }

    @Override
    public void onEndGameDisconnection() {
        setSaneTerminalModeIfIsRaw();
        printRed("\nDISCONNECTED FROM THE GAME...");
        quitFromGame(-1);
    }

    @Override
    public void newChatMessage(String nick, String message) {
        setSaneTerminalModeIfIsRaw();

        setNewChatMessage(true);
        Player playerOnChat = getPlayerFromNickName(opponents, nick);
        setLastChatMessage(playerOnChat, message);
        handlePreviousChatMessage(playerOnChat, message);

        clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
        printChat(previousChatMessage);
    }

    @Override
    public void onErrorMessage(String stopper, boolean isYourPlayer) {
        setSaneTerminalModeIfIsRaw();
        printRed("\nERROR MESSAGE...");
    }

    @Override
    public void notYourTurn() {
        setSaneTerminalModeIfIsRaw();
        printRed("\nIT'S NOT YOUR TURN...");
    }

    @Override
    public void startTurn(String nick, boolean isYourPlayer) {
        if (isYourPlayer) {

            availableActions = new ArrayList<>();
            availableActions.add(CHAT_CASE);
            availableActions.add(SELECT_WORKER_CASE);

            clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
            printRed("IT'S YOUR TURN!\n");
            startSelectedActions(scrollAvailableOptions(availableActions));

        } else {

            clearAndPrintInfo(opponents, myPlayerOnServer, deck, constraints, santoriniMap);
            printRed("IT'S NOT YOUR TURN! " + nick.toUpperCase() + " IS STARTING HIS TURN!\n");
            printWaitingStartTurn(numberOfPlayers);
            printChat(previousChatMessage);

        }
    }
}
