package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.client.ClientGameController;
import it.polimi.ingsw.network.message.MessageType;

import java.util.*;

import static it.polimi.ingsw.utils.ConstantsContainer.*;
import static it.polimi.ingsw.view.client.cli.CliUtils.*;

public class Cli extends ClientGameController {

    private int port = 4700;
    //private String address = "100.26.121.189";
    private String address = "127.0.0.1";
    private String nickName;
    private int numberOfPlayers;
    private NewSantoriniMapArrows newSantoriniMapArrows = new NewSantoriniMapArrows();
    private Color myPlayerColor;
    private int[] tileNumber = new int[2];
    private int selectedWorker;
    private Player myPlayerOnServer;
    private String myPower;

    private Map<String, Card> deck = CardLoader.loadCards();
    private List<String> deckOrdered = new ArrayList<>();
    private List<String> selectedCards = new ArrayList<>();
    private static List<Player> opponents = new ArrayList<>();
    private static List<Player> actualPlayers = new ArrayList<>();
    private List<String> availableActions = new ArrayList<>();

    private Response fromServerResponse;

    public void start() {
        clearShell();
        printRed(TITLE);
        login(/*false*/);

        printDebug("HERE");
        printDebug("NICK: " + getNickName() + "\nNUMBER: " + getNumberOfPlayers() + "\nPORT: " + getPort() + "\nIP: " + getAddress());

        try {
            openConnection(getNickName(), getNumberOfPlayers(), getAddress(), getPort());
        }catch (Exception e) {
            printErr("FAILED TO OPENING CONNECTION");
            CliUtils.LOGGER.severe(e.getMessage());
        }
    }

    private synchronized void challengerChooseCards() {
        for(String s: deck.keySet())
            orderCards(s);

        if(getNumberOfPlayers()==2)
            deckOrdered.remove("chronus");

        selectCards();
        String firstPlayer = selectFirstPlayer();

        printDebug("\nFIRSTPLAYER: " + firstPlayer + "\nSELECTED CARDS:\n" + selectedCards);

        challengerResponse(firstPlayer, new ArrayList<>(selectedCards));

        selectedCards.clear();
        printDebug("CHALLENGERRESPONSE");
        controlWaitEnter("endTurn");
        endTurn();
        //mainThread.interrupt();
        printDebug("AFTER ENDTURN");
        printWaitingStartTurn(numberOfPlayers);
    }

    private synchronized void playerChoosePower() {

        printDebug("AVAILABLE CARDS: " + getAvailableCards() + "\nDECK ORDERED: " + deckOrdered);

        clearAndPrintInfo(opponents, myPlayerOnServer, deck);

        printRed("CHOOSE ONE OF THE CARDS BELOW:\n");
        printCards();
        printRed("USE ARROW TO SELECT YOUR POWER & THEN PRESS ENTER TO CONFIRM...");
        //this.power = scrollCards(getArrowUpDown(), 1);
        myPower = scrollCards(getArrowUpDown(), 1);

        cardChoiceResponse(myPower);
        printDebug("CARDCHOICERESPONSE " + myPower);
        controlWaitEnter("endTurn");
        endTurn();
        printDebug("AFTER ENDTURN");
        printWaitingStartTurn(numberOfPlayers);
    }

    private synchronized void playerPlaceWorkers() {
        int keyboard;
        Integer[] tileCoordinates;
        boolean occupied;

        List<Integer> modifiedTiles = new ArrayList<>();
        List<Square> modifiedSquares = getModifiedsquare();
        for (Square modifiedSquare : modifiedSquares) {
            modifiedTiles.add(modifiedSquare.getTile()-1);
        }

        for(int i=0; i<2; i++) {
            newSantoriniMapArrows.setPlaceWorkerNotAvailableTiles(modifiedTiles);
            newSantoriniMapArrows.printMap();
            newSantoriniMapArrows.printAvailableTiles();

            do {
                occupied = false;
                //TILE NUMBER VERSION
                /*do {
                    printRed("INSERT THE NUMBER OF THE TILE YOU WANT TO INSERT YOUR WORKER " + (i+1) + ": ");
                    try {
                        keyboard = Integer.parseInt(input());
                    } catch (NumberFormatException e) {
                        keyboard = 0;
                    }
                } while (keyboard < 1 || keyboard > 25);*/
                //-------------------

                //COORDINATES VERSION
                do {
                    printRed("INSERT COORDINATES (from 0 up to 4) OF THE TILE IN WHICH YOU WANT TO PLACE YOUR WORKER" + (i+1) + ": ");
                    tileCoordinates = getCoordinatesFromString(input());
                }while(tileCoordinates[0] < 0 || tileCoordinates[0] > 4 || tileCoordinates[1] < 0 || tileCoordinates[1] > 4);
                keyboard = newSantoriniMapArrows.getTileFromCoordinate(tileCoordinates[0], tileCoordinates[1]);
                //-------------------

                if(!newSantoriniMapArrows.checkUnoccupiedTile(keyboard)) {
                    occupied = true;
                    printRed(Color.BACKGROUND_YELLOW + "OCCUPIED TILES!!" + Color.RESET + "\n");
                }
            }while (occupied);

            tileNumber[i] = keyboard;
            newSantoriniMapArrows.setTileHasPlayer(true, tileNumber[i], myPlayerColor);
            modifiedTiles.add(tileNumber[i]);
            newSantoriniMapArrows.setPlaceWorkerNotAvailableTiles(modifiedTiles);
            newSantoriniMapArrows.printMap();

            if(controlWaitEnter("confirm")==186) {
                newSantoriniMapArrows.setTileHasPlayer(false, tileNumber[i], null);
                modifiedTiles.remove(tileNumber[i]);
                newSantoriniMapArrows.addAvailableTile(tileNumber[i]);

                i--;
            }
        }
        printDebug("LOCAL TILE NUMBER: " + Arrays.toString(tileNumber) + "\nSENDED TILE NUMBER: " + (tileNumber[0]+1) +  " "  + (tileNumber[1]+1));

        placeWorkersResponse(tileNumber[0]+1, tileNumber[1]+1);
        printDebug("PLACEWORKERSRESPONSE");
        controlWaitEnter("endTurn");
        endTurn();
        printDebug("END TURN");
        printWaitingStartTurn(numberOfPlayers);
        newSantoriniMapArrows.resetAvailableTiles();
    }

    private synchronized void playerSelectWorker() {
        newSantoriniMapArrows.printMap();

        int[] coordinateWorker1 = newSantoriniMapArrows.getCoordinatesFromTile(tileNumber[0]);
        int[] coordinateWorker2 = newSantoriniMapArrows.getCoordinatesFromTile(tileNumber[1]);

        printRed("\nSELECT WITH ARROWS ONE OF YOUR WORKERS:\n  [" + coordinateWorker1[0] + "] [" + coordinateWorker1[1] + "] WORKER 1\n  [" + coordinateWorker2[0] + "] [" + coordinateWorker2[1] + "] WORKER 2\n");

        boolean goOut = false;
        int keyboard = getArrowUpDown();

        do{
            clearShell();
            switch (keyboard) {
                case 183:
                    newSantoriniMapArrows.setSelectedTile(tileNumber[0], true);
                    newSantoriniMapArrows.setSelectedTile(tileNumber[1], false);
                    newSantoriniMapArrows.printMap();
                    printRed("SELECT WITH ARROWS ONE OF YOUR WORKERS:\n");
                    printYellow("> [" + coordinateWorker1[0] + "] [" + coordinateWorker1[1] + "] WORKER 1\n");
                    printRed("  [" + coordinateWorker2[0] + "] [" + coordinateWorker2[1] + "] WORKER 2\n");


                    keyboard = controlWaitEnter("up&down");
                    if (keyboard == 13)
                        selectedWorker = 1;
                    break;
                case 184:
                    newSantoriniMapArrows.setSelectedTile(tileNumber[0], false);
                    newSantoriniMapArrows.setSelectedTile(tileNumber[1], true);
                    newSantoriniMapArrows.printMap();
                    printRed("SELECT WITH ARROWS ONE OF YOUR WORKERS:\n  [" + coordinateWorker1[0] + "] [" + coordinateWorker1[1] + "] WORKER 1\n");
                    printYellow("> [" + coordinateWorker2[0] + "] [" + coordinateWorker2[1] + "] WORKER 2\n");

                    keyboard = controlWaitEnter("up&down");
                    if (keyboard == 13)
                        selectedWorker = 2;
                    break;
                case 13:
                    newSantoriniMapArrows.printMap();
                    newSantoriniMapArrows.setSelectedTile(tileNumber[0], false);
                    newSantoriniMapArrows.setSelectedTile(tileNumber[1], false);
                    setWorker(selectedWorker);
                    goOut = true;
                    break;
                default:
                    printErr("NO KEYBOARD CAUGHT");
            }
        }while (!goOut);
    }

    private synchronized void playerMoveHisWorker() {
        setAvailableTilesInMap(getAvailableTilesFromServer(availableMoveSquare()));
        newSantoriniMapArrows.printMap();
        printInfo(opponents, myPlayerOnServer, deck);
        newSantoriniMapArrows.printAvailableTiles();

        int tile = getCoordinateInWhichActFromUser("MOVE");

        newSantoriniMapArrows.setTileHasPlayer(false, tileNumber[selectedWorker-1], null);
        newSantoriniMapArrows.setTileHasPlayer(true, tile, myPlayerColor);

        fromServerResponse = moveWorker(tile+1);
        tileNumber[selectedWorker-1] = tile;

        printDebug("MOVEWORKER: " + (tile+1));

        //VERSIONE FRECCE
        //fromServerResponse = moveWorker(selectTileWithArrows());
        //seleziono con frecce tile e la coloro di giallo con setSelectedTile
        //quando enter per confermare la scelta, allora resetto sia selected che available
        //printDebug("MOVEWORKER " + tile);

        newSantoriniMapArrows.printMap();
        mapNextAction(fromServerResponse);
    }

    private synchronized void playerBuild() {
        setAvailableTilesInMap(getAvailableTilesFromServer(availableBuildSquare()));

        List<Building> availableBuildings = new ArrayList<>();
        int tile = getCoordinateInWhichActFromUser("BUILD");
        availableBuildings.add(newSantoriniMapArrows.getAvailableBuildingFromTile(tile));
        if(myPower.equalsIgnoreCase("ATLAS"))
            availableBuildings.add(Building.DOME);

        clearAndPrintInfo(opponents, myPlayerOnServer, deck);
        printRed("SELECT THE TYPE OF BUILDING YOU WANT TO CONSTRUCT:\n");

        List<String> availableBuildingsString = new ArrayList<>();
        for(Building availableBuilding: availableBuildings) {
            availableBuildingsString.add(availableBuilding.toString());
        }

        int selectedBuildingType = scrollAvailableOptions(availableBuildingsString);

        newSantoriniMapArrows.updateStringBoardBuilding(availableBuildings.get(selectedBuildingType), tile);

        fromServerResponse = buildWorker((tile+1), newSantoriniMapArrows.getTileBuilding(tile));

        printDebug("BUILDWORKER: " + (tile+1) +  " " + availableBuildings.get(selectedBuildingType));

        newSantoriniMapArrows.printMap();
        mapNextAction(fromServerResponse);
    }

    private void updateModification(List<Square> modifiedSquares) {
        for(Square modifiedSquare: modifiedSquares) {
            Color playerColor = null;
            if(modifiedSquare.hasPlayer())
                playerColor = getColorCliFromPlayer(modifiedSquare.getPlayer().getColor());

            newSantoriniMapArrows.setTileHasPlayer(modifiedSquare.hasPlayer(), modifiedSquare.getTile()-1, playerColor);
            newSantoriniMapArrows.updateStringBoardBuilding(modifiedSquare);
        }
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
        printRed("INSERT THE IP ADDRESS (default as 127.0.0.1 - localhost || " + address + " - AmazonServer): ");
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

    private void login(/*boolean lobbyCall*/) {

        setNickName();
        setNumberOfPlayers();
        //if(!lobbyCall) {
            setPort();
            setAddress();
        //}

        /*if(lobbyCall)
            newGame(getNickName(), getNumberOfPlayers());*/
    }

    private String selectFirstPlayer() {
        clearAndPrintInfo(opponents, myPlayerOnServer, deck);
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
                clearAndPrintInfo(opponents, myPlayerOnServer, deck);
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

    private void printSelectedPlayer(Player player) {
        for(Player p: actualPlayers) {
            if(p == player)
                printYellow("> ");
            else
                printRed("  ");

            printPlayer(p.getNickName(), p);
            printYellow("\n");
        }
    }

    private int scrollAvailableOptions (List<String> availableOptions) {
        for(String s: availableOptions)
            printWhite("[" + s + "]\n");

        int keyboard = getArrowUpDown();

        int counter = 0, size = availableOptions.size();
        boolean goOut = false, firstPosition = false, lastPosition = counter == size;
        do {
            switch (keyboard) {
                case 183:
                    if(counter == 0)
                        counter++;
                    else if (!firstPosition)
                        counter--;
                    break;
                case 184:
                    if(!lastPosition)
                        counter++;
                    break;
                case 13:
                    goOut = true;
                    break;

                default:
                    printErr("NO KEYBOARD CAUGHT");
            }

            if(!goOut) {
                clearAndPrintInfo(opponents, myPlayerOnServer, deck);

                firstPosition = counter == 1;

                for (int i = 1; i <= size; i++) {
                    if (i == counter) {
                        printYellow("[" + availableOptions.get(counter - 1) + "]\n");
                    } else {
                        printWhite("[" + availableOptions.get(i - 1) + "]\n");
                    }
                    lastPosition = counter == size;
                }

                keyboard = controlWaitEnter("up&down");
            }
        }while(!goOut);

        return counter-1;
    }

    private List<Integer> getAvailableTilesFromServer (List<Integer> availableSquares) {
        printDebug("FROM SERVER: " + availableSquares);

        List<Integer> availableTiles = new ArrayList<>();
        for (Integer square : availableSquares)
            availableTiles.add(square - 1);

        return availableTiles;
    }

    private void setAvailableTilesInMap (List<Integer> availableTilesInMap) {
        newSantoriniMapArrows.setAvailableTiles(availableTilesInMap);
    }

    private int getCoordinateInWhichActFromUser(String typeOfAction) {
        int keyboard, tile;
        do {
            clearAndPrintInfo(opponents, myPlayerOnServer, deck, newSantoriniMapArrows);

            newSantoriniMapArrows.printAvailableTiles();
            printRed("INSERT COORDINATES IN WHICH YOU WANT TO " + typeOfAction + ": ");
            Integer[] coordinates = getCoordinatesFromString(input());
            tile = newSantoriniMapArrows.getTileFromCoordinate(coordinates[0], coordinates[1]);
            //CONTROLLARE DISPONIBILITA' TILE
            printDebug("TILE " + (tile+1));
            //newSantoriniMapArrows.removeTileFromAvailableTiles(tile);
            newSantoriniMapArrows.setSelectedTile(tile, true);
            clearShell();
            newSantoriniMapArrows.printMap();
            keyboard = controlWaitEnter("confirm");
            if(keyboard != 13)
                newSantoriniMapArrows.setSelectedTile(tile, false);
        }while(keyboard != 13);

        newSantoriniMapArrows.setSelectedTile(tile, false);
        newSantoriniMapArrows.resetAvailableTiles();

        return tile;
    }

    //-----CARDS-----

    private void orderCards(String s) {
        for(int x=0; x < deckOrdered.size(); x++) {
            if (deckOrdered.get(x).compareTo(s) > 0) {
                deckOrdered.add(x, s);
                return;
            }
        }
        deckOrdered.add(s);
    }

    private void selectCards() {

        int cont = 0, numberOfCardsToChoose = actualPlayers.size();

        while (cont < getNumberOfPlayers() && numberOfCardsToChoose > 0) {
            clearAndPrintInfo(opponents, myPlayerOnServer, deck);
            printRed("PLEASE, CHOOSE " + numberOfCardsToChoose + " CARDS:\n");
            printCards();
            printRed("USE ARROWS UP&DOWN TO SELECT, THEN PRESS ENTER...");
            selectedCards.add(scrollCards(getArrowUpDown(), numberOfCardsToChoose));
            cont++;
            numberOfCardsToChoose--;
        }
    }

    private String scrollCards(int keyboardIn, int numberOfCardsToChoose) {
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
                clearAndPrintInfo(opponents, myPlayerOnServer, deck);

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

    private void printCards() {
        for (String s : deckOrdered) {
            print("  " + s.toUpperCase() + "\n", Color.ANSI_YELLOW);
        }
    }

    //-----MAP&WORKERS-----

    private Integer[] getCoordinatesFromString(String input) {
        String[] split = splitter(input);

        split = controlCoordinates(split);

        return new Integer[] {Integer.parseInt(split[0]), Integer.parseInt(split[1])};

    }

    private String[] controlCoordinates(String[] split) {
        boolean wrongSplit;

        while(split.length != 2) {
            printRed(setBackground("WRONG NUMBER OF PARAMETERS!", Color.BACKGROUND_YELLOW));
            printRed("\nPLEASE, REINSERT COORDINATES (from 0 up to 4): ");
            split = splitter(input());
        }

        while(!split[0].equals("0") && !split[0].equals("1") && !split[0].equals("2") && !split[0].equals("3") && !split[0].equals("4") && !split[1].equals("0") && !split[1].equals("1") && !split[1].equals("2") && !split[1].equals("3") && !split[1].equals("4")) {
            printRed("ERROR!\nPLEASE, REINSERT COORDINATES (from 0 up to 4): ");
            do {
                wrongSplit = false;
                split = splitter(input());
                if(split.length != 2) {
                    wrongSplit = true;
                    printRed("WRONG NUMBER OF PARAMETERS!\nPLEASE, REINSERT COORDINATES from 0 up to 4): ");
                }
            }while (wrongSplit);
        }

        return split;
    }

    private int selectTileWithArrows() {
        printRed("USE ARROWS TO SELECT THE TILE IN WHICH YOU WANT TO MOVE...");
        int keyboard = getArrow();

        int[] coordinates = newSantoriniMapArrows.getCoordinatesFromTile(tileNumber[selectedWorker-1]);

        int currentSelectedTile = -1, nextSelection;
        int coordinateX = coordinates[0], coordinateY = coordinates[1];
        int tempCounter = -1;
        boolean goOut = false, stillAvailableTile = true, error, firstExec = true;
        do {
            error = false;
            clearShell();
            switch (keyboard) {
                case 183:
                    tempCounter = coordinateX;
                    if(tempCounter!=0 && stillAvailableTile)
                        tempCounter--;
                    break;
                case 184:
                    tempCounter = coordinateX;
                    if(tempCounter!=4 && stillAvailableTile)
                        tempCounter++;
                    break;
                case 185:
                    tempCounter = coordinateY;
                    if(tempCounter!=0 && stillAvailableTile)
                        tempCounter++;
                    break;
                case 186:
                    tempCounter = coordinateY;
                    if(tempCounter!=4 && stillAvailableTile)
                        tempCounter--;
                    break;
                case 13:
                    goOut = true;
                    break;
                default:
                    error = true;
                    printErr("NO KEYBOARD CATCHED");
            }

            if(!goOut && !error) {

                if(keyboard == 183 || keyboard == 184)
                    nextSelection = newSantoriniMapArrows.getTileFromCoordinate(tempCounter, coordinateY);
                else
                    nextSelection = newSantoriniMapArrows.getTileFromCoordinate(coordinateX, tempCounter);

                for(Integer availableTile: newSantoriniMapArrows.availableTiles) {
                    if((nextSelection+1) == availableTile) {
                        if(keyboard == 183 || keyboard == 184)
                            coordinateX = tempCounter;
                        else
                            coordinateY = tempCounter;

                        printDebug(coordinateX + " " + coordinateY + " " + nextSelection);
                        if(firstExec)
                            firstExec = false;
                        else
                            newSantoriniMapArrows.setSelectedTile(currentSelectedTile, false);

                        currentSelectedTile = nextSelection;
                        newSantoriniMapArrows.setSelectedTile(currentSelectedTile, true);

                        stillAvailableTile = true;
                        break;
                    } else {
                        stillAvailableTile = false;
                    }
                }

                newSantoriniMapArrows.printMap();
                keyboard = controlWaitEnter("all");
            }
        }while (!goOut);

        newSantoriniMapArrows.setSelectedTile(currentSelectedTile, false);
        newSantoriniMapArrows.resetAvailableTiles();

        newSantoriniMapArrows.setTileHasPlayer(false, tileNumber[selectedWorker-1], null);
        newSantoriniMapArrows.setTileHasPlayer(true, currentSelectedTile, myPlayerColor);

        return currentSelectedTile+1;
    }

    //-----MENU-----
    private void printMenu(boolean isFirstPlayer, boolean constraint) {
        clearShell();
        if(constraint) {
            printWhite("[CHAT]  [BOARD]  [ACTIONS]  [OPPONENTS]  ");
            print("[POWER]\n\n", Color.ANSI_CYAN);
        } else
            printWhite("[CHAT]  [BOARD]  [ACTIONS]  [OPPONENTS]  [POWER]\n\n");

        int counter = 0;
        boolean goOut = false, firstPosition = false, lastPosition = false;

        int keyboardIn = getArrowLeftRight();

        do {
            clearShell();
            switch (keyboardIn) {
                case 185:
                    printDebug("HERE");
                    if (!lastPosition)
                        counter++;
                    break;
                case 186:
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

                } else if (counter == 2) {
                    firstPosition = false;
                    printWhite("[CHAT]  ");
                    printYellow("[BOARD]");
                    printWhite("  [ACTIONS]  [OPPONENTS]  [POWER]\n\n");

                } else if (counter == 3) {
                    printWhite("[CHAT]  [BOARD]  ");
                    /*if(availableActions.size()>0)
                        print("[ACTIONS]", Color.ANSI_CYAN);
                    else*/
                        printYellow("[ACTIONS]");
                    printWhite("  [OPPONENTS]  [POWER]\n");
                    if (isFirstPlayer)
                        printActions();
                    else
                        printRed("\n");

                } else if (counter == 4) {
                    lastPosition = false;
                    printWhite("[CHAT]  [BOARD]  [ACTIONS]  ");
                    printYellow("[OPPONENTS]");
                    printWhite("  [POWER]\n");
                    for (Player player : opponents) {
                        printWhite("                            [");
                        printPlayer(player.getNickName(), player);
                        printWhite("]\n");
                    }

                } else if (counter == 5) {
                    lastPosition = true;
                    printWhite("[CHAT]  [BOARD]  [ACTIONS]  [OPPONENTS]  ");
                    printYellow("[POWER]\n");
                    try {
                        printYellow(myPlayerOnServer.getPower().getName().toUpperCase() + ":");
                        printPower(myPlayerOnServer.getPower().getName(), deck);
                    } catch (NullPointerException e) {
                        printRed("YOUR CARD DOESN'T ALREADY CHOOSE\n");
                    }
                    //printConstraint

                }

                keyboardIn = controlWaitEnter("left&right");
            }
        }while(!goOut);

        selectMenu(counter, isFirstPlayer);
    }

    private void selectMenu(int counter, boolean isFirstPlayer) {
        switch (counter) {
            case 1:
                //printChat
                break;
            case 2:
                printWhite("\n");
                newSantoriniMapArrows.printMap();
                break;
            case 3:
                if(isFirstPlayer) {
                    clearAndPrintInfo(opponents, myPlayerOnServer, deck);
                    printRed("SELECT WITH ARROWS ONE OF THE OPTIONS BELOW, THEN PRESS ENTER TO GO ON...\n");
                    startSelectedActions(scrollAvailableOptions(availableActions));
                }
                break;
            default:
                printErr("ERROR IN CHOICE");
        }
    }

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
            case "SELECT WORKER":
                playerSelectWorker();
                break;
            case "END TURN":
                endTurn();
                clearAndPrintInfo(opponents, myPlayerOnServer, deck, newSantoriniMapArrows);
                printWaitForOtherPlayers(numberOfPlayers);
                break;
            default:
                printErr("ERROR IN SELECTED ACTION");
        }
    }

    private void printActions() {
        for(String s: availableActions) {
            printWhite("                 [" + s + "]\n");
        }
    }

    //--------------

    //------------------------------

    //---------- OVERRIDING FUNCTIONS ----------

    @Override
    public synchronized void updateLobbyPlayer() {
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
    }

    @Override
    public void nickUsed() {
        clearShell();
        printRed("NICKNAME ALREADY USED. PLEASE, REINSERT A NEW NICKNAME: ");
        setNickName();
        updateNickName(getNickName());
    }

    @Override
    public synchronized void startGame() {

        printDebug("START GAME");

        for (Player player : getPlayers()) {
            if (!player.getNickName().equalsIgnoreCase(getNickName()))
                opponents.add(player);
            else {
                this.myPlayerColor = getColorCliFromPlayer(player.getColor());
                this.myPlayerOnServer = player;
            }
        }
        /*clearShell();
        printRed("GAME IS GOING TO START. PLEASE WAIT WHILE IS LOADING\n");
        controlWaitEnter("enter");*/

    }

    @Override
    public synchronized void challengerChoice(String challengerNick, boolean isYourPlayer) {

    printDebug("CHALLENGER CHOICE");
        clearAndPrintInfo(opponents, myPlayerOnServer, deck);
        if (isYourPlayer) {
            printRed("YOU HAVE BEEN CHOSEN AS CHALLENGER!\n");
            controlWaitEnter("enter");
            availableActions.clear();
            availableActions.add("CHOOSE CARDS");

            clearAndPrintInfo(opponents, myPlayerOnServer, deck);
            printRed("SELECT WITH ARROWS ONE OF THE OPTIONS BELOW, THEN PRESS ENTER TO GO ON...\n");
            startSelectedActions(scrollAvailableOptions(availableActions));

        } else {
            printRed("PLAYER ");
            printPlayer(challengerNick, getPlayerFromNickName(opponents, challengerNick));
            printRed(" IS CHOOSING CARDS\n");
            printWaitForOtherPlayers(numberOfPlayers);
        }

        //mainThread = new Thread(() -> printMenu(isYourPlayer, false));
        //mainThread.start();
    }

    @Override
    public synchronized void cardChoice(String challengerNick, boolean isYourPlayer) {
        /*synchronized (this) {
            setTerminalMode("sane");
        }*/

        //mainThread.interrupt();
        opponents = new ArrayList<>();
        for(Player player: getPlayers()) {
            if(!getNickName().equalsIgnoreCase(player.getNickName()))
                opponents.add(player);
        }

        clearAndPrintInfo(opponents, myPlayerOnServer, deck);

        if (isYourPlayer) {
            deckOrdered = new ArrayList<>(getAvailableCards());
            printDebug("CARDCHOICE AVAILABLE: " + getAvailableCards());

            printRed("IT'S YOUR TURN TO CHOOSE YOUR POWER!\n");
            controlWaitEnter("enter");
            availableActions.clear();
            availableActions.add("CHOOSE POWER");

            clearAndPrintInfo(opponents, myPlayerOnServer, deck);
            printRed("SELECT WITH ARROWS ONE OF THE OPTIONS BELOW, THEN PRESS ENTER TO GO ON...\n");
            startSelectedActions(scrollAvailableOptions(availableActions));

        } else {
            printRed("PLAYER ");
            printPlayer(challengerNick, getPlayerFromNickName(opponents, challengerNick));
            printRed(" IS CHOOSING HIS POWER\n");
            printWaitForOtherPlayers(numberOfPlayers);
        }

        //mainThread.start();
    }

    @Override
    public synchronized void placeWorker(String challengerNick, boolean isYourPlayer) {
        /*synchronized (this) {
            setTerminalMode("sane");
        }*/
        //mainThread.interrupt();

        opponents = new ArrayList<>();
        for(Player player: getPlayers()) {
            if(!getNickName().equalsIgnoreCase(player.getNickName()))
                opponents.add(player);
        }

        clearAndPrintInfo(opponents, myPlayerOnServer, deck, newSantoriniMapArrows);
        if (isYourPlayer) {
            printRed("PLACE YOUR WORKERS!\n");
            controlWaitEnter("enter");
            availableActions = new ArrayList<>();
            availableActions.add("PLACE WORKERS");

            clearAndPrintInfo(opponents, myPlayerOnServer, deck);
            printRed("SELECT WITH ARROWS ONE OF THE OPTIONS BELOW, THEN PRESS ENTER TO GO ON...\n");
            startSelectedActions(scrollAvailableOptions(availableActions));

        } else {
            printRed("PLAYER ");
            printPlayer(challengerNick, myPlayerOnServer);
            printRed(" IS PLACING HIS WORKERS\n");
            printWaitForOtherPlayers(numberOfPlayers);
        }

        //mainThread.start();
    }

    @Override
    public synchronized void updatePlacedWorkers(List<Square> squares) {
        printDebug("HERE UPDATE");
        for(Square square: squares) {
            Color playerColor = null;
            if(square.hasPlayer())
                playerColor = getColorCliFromPlayer(square.getPlayer().getColor());

            newSantoriniMapArrows.setTileHasPlayer(square.hasPlayer(), square.getTile()-1, playerColor);
        }
    }

    @Override
    public synchronized void updateBoard(String nick, List<Square> squares, MessageType type) {
        updateModification(squares);

        clearAndPrintInfo(opponents, myPlayerOnServer, deck, newSantoriniMapArrows);
        printWaitForOtherPlayers(numberOfPlayers);
    }

    @Override
    public synchronized void notifyWin(String nick) {

    }

    @Override
    public synchronized void notifyLose(String nick, boolean isYourPlayer) {

    }

    @Override
    public synchronized void displayActions(List<MessageType> actions) {
        printDebug("DISPLAYACTIONS " + actions);
        try {
            availableActions = new ArrayList<>();
            for (MessageType m : actions) {
                switch (m) {
                    case BUILDWORKER:
                        availableActions.add("BUILD");
                        break;
                    case MOVEWORKER:
                        availableActions.add("MOVE");
                        break;
                    case WORKERCHOICE:
                        availableActions.add("SELECT WORKER");
                        break;
                    case ENDTURN:
                        availableActions.add("END TURN");
                        break;
                }
            }
        } catch (NullPointerException e) {
            printErr("NULL POINTER");
            CliUtils.LOGGER.severe(e.getMessage());
        }

        clearAndPrintInfo(opponents, myPlayerOnServer, deck);
        printRed("SELECT WITH ARROWS ONE OF THE OPTIONS BELOW, THEN PRESS ENTER TO GO ON...\n");
        startSelectedActions(scrollAvailableOptions(availableActions));
    }

    @Override
    public synchronized void addConstraint(String name) {

    }

    @Override
    public synchronized void removeConstraint(String name) {

    }

    @Override
    public synchronized void onTurnTimerEnded(String stopper) {

    }

    @Override
    public synchronized void onStoppedGame(String stopper) {

    }

    @Override
    public synchronized void onLobbyDisconnection() {
        /*clearShell();
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
        }while (!goOut);*/

    }

    @Override
    public synchronized void onPingDisconnection() {

    }

    @Override
    public synchronized void onEndGameDisconnection() {

    }

    @Override
    public synchronized void newChatMessage(String nick, String message) {

    }

    @Override
    public synchronized void onErrorMessage(String stopper, boolean isYourPlayer) {

    }

    @Override
    public synchronized void notYourTurn() {

    }

    @Override
    public synchronized void startTurn(String nick, boolean isYourPlayer) {
        clearAndPrintInfo(opponents, myPlayerOnServer, deck, newSantoriniMapArrows);

        if(isYourPlayer) {
            printRed("IT'S YOUR TURN!\n");
            controlWaitEnter("enter");
            availableActions = new ArrayList<>();
            availableActions.add("SELECT WORKER");

            clearAndPrintInfo(opponents, myPlayerOnServer, deck, newSantoriniMapArrows);
            printRed("SELECT WITH ARROWS ONE OF THE OPTIONS BELOW, THEN PRESS ENTER TO GO ON...\n");
            startSelectedActions(scrollAvailableOptions(availableActions));
            //selectAction --> move / build
        } else {
            printRed("IT'S NOT YOUR TURN! " + nick.toUpperCase() + " IS STARTING HIS TURN!\n");
            printWaitingStartTurn(numberOfPlayers);
        }
    }
}
