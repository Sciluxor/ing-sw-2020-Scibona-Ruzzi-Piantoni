package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.WorkerName;
import it.polimi.ingsw.utils.ConstantsContainer;

import java.util.*;

/**
 * Simplified Class that represent a single match (used by the Client)
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/28
 */

public class SimplifiedGame{
    private final Integer numberOfPlayers;
    private List<Player> settedPlayers =new ArrayList<>();
    private boolean hasStopper;
    private final Map<String, Card> deck;
    private List<String> availableCards;
    private Player currentPlayer;
    private Player clientPlayer;
    private final GameMap gameMap;
    private boolean isGameStarted;
    private String gameID;
    private Response gameStatus;
    private boolean hasWinner;
    private Player winner;

    /**
     * Public constructor
     * @param numberOfPlayers Number of player in the game
     */

    public SimplifiedGame(int numberOfPlayers) {

        this.numberOfPlayers = numberOfPlayers;
        gameMap = new GameMap();
        deck = CardLoader.loadCards();
        isGameStarted = false;
        hasWinner = false;
        hasStopper = false;
    }

    /**
     * Function to check if the game has been interrupted
     * @return True if the game has been interrupted, false otherwise
     */

    public boolean hasStopper() {
        return hasStopper;
    }

    /**
     * Setter of hasStopper
     * @param hasStopper Boolean that says if the game has been interrupted
     */

    public void setHasStopper(boolean hasStopper) {
        this.hasStopper = hasStopper;
    }

    /**
     * Function that return the the player of this client
     * @return The player of this client
     */

    public Player getClientPlayer() {
        return clientPlayer;
    }

    /**
     * Getter of the number of Players in game
     * @return Number of the Players in the current game
     */

    public  Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Getter of the List of Players in game
     * @return List of Players in game
     */

    public List<Player> getPlayers() {
        return settedPlayers;
    }

    /**
     * Method that says if the game has a winner
     * @return Boolean that says if the game has a winner
     */

    public boolean hasWinner() {
        return hasWinner;
    }

    /**
     * Getter of the deck of cards
     * @return Deck of cards
     */

    public Map<String, Card> getDeck() {
        return deck;
    }

    /**
     * Setter of hasWinner
     * @param hasWinner Boolean that is true if the game has a winner
     */

    public void setHasWinner(boolean hasWinner) {
        this.hasWinner = hasWinner;
    }

    /**
     * Method that return the winner Player
     * @return Winner Player
     */

    public Player getWinner() {
        return winner;
    }

    /**
     * Setter of the winner Player
     * @param winner Player that win the game
     */

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    /**
     * Function to get the coordinates of the square from the number of the square
     * @param tile The number of the specific square
     * @return The coordinates of the specific square
     */

    public Integer[] getCoordinatesFromTile(int tile){
       return gameMap.getMap().get(tile-1).getCoordinates();
    }

    /**
     * Function that create the players in the game, called when received a lobby update message from the server
     * @param clientName The name of this client
     * @param names The list of players'name
     * @param colors The list of players'colors
     */

    public void initPlayers(String clientName,List<String> names, List<Color> colors){
        settedPlayers = new ArrayList<>();
        if (names.size() != colors.size())
            throw new IllegalStateException("wrong numbers");
        for (int i = 0; i < names.size(); i++) {
            Player newPlayer = new Player(names.get(i));
            settedPlayers.add(newPlayer);
            newPlayer.setColor(colors.get(i));

            if (newPlayer.getNickName().equals(clientName)) {
                clientPlayer = newPlayer;
            }
        }
    }

    /**
     * Method that copy the modified squares received from the server into the local game map of the client
     * @param oldSquare Square to modify
     * @param newSquare Square to copy
     */

    public void copySquare(Square oldSquare, Square newSquare){
        oldSquare.setBuilding(newSquare.getBuilding());
        oldSquare.setBuildingLevel(newSquare.getBuildingLevel());
        oldSquare.setHasPlayer(newSquare.hasPlayer());
        if(newSquare.hasPlayer()) {
            for(Player player: settedPlayers) {
                if (player.getNickName().equals(newSquare.getPlayer().getNickName())) {
                    oldSquare.setPlayer(player);
                    oldSquare.setWorker(player.getWorkers().get(WorkerName.getNumberWorker(newSquare.getWorker().getName()) -1));
                    oldSquare.getWorker().setPreviousBoardPosition(oldSquare.getWorker().getBoardPosition());
                    oldSquare.getWorker().setBoardPosition(oldSquare);
                }
            }
        }
    }

    /**
     * Getter for the availableCards
     * @return List of string of the available cards
     */

    public List<String> getAvailableCards() {
        return availableCards;
    }

    /**
     * Setter of the availableCards
     * @param cardNames List of string of the availableCards
     */

    public void setAvailableCards(List<String> cardNames) {
        availableCards = cardNames;
    }

    /**
     * Method that remove the provided card from the List of availableCards
     * @param toRemoveCard Card to be removed
     */

    public void removeCard(String toRemoveCard){
        availableCards.remove(toRemoveCard);
    }

    /**
     * Getter of the Player is playing in this moment
     * @return Current Player
     */

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Setter of the currentPlayer with the Player provided
     * @param playerName Name of the player to set
     */

    public void setCurrentPlayer(String playerName) {
        if(playerName == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);
        for(Player player : settedPlayers)
            if(player.getNickName().equals(playerName))
                this.currentPlayer = player;
    }

    /**
     * Getter of the map of the game
     * @return Map of the game
     */

    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * Method that says if the game is started
     * @return Boolean that says if the game is started or not
     */

    public boolean isGameStarted() {
        return isGameStarted;
    }

    /**
     * Setter of the isGameStarted
     * @param gameStarted Boolean to set
     */

    public void setGameStarted(boolean gameStarted) { isGameStarted = gameStarted;}

    /**
     * Method that set the Workers of the currentPlayer on the map
     * @param tile1 First square in which to put the Worker
     * @param tile2 Second square in which to put the Worker
     */

    public void placeWorkersOnMap(Integer tile1,Integer tile2) {
        if(tile1 <= 0 || tile1 > 25 ||  tile2 <= 0 || tile2 > 25 )
            throw new IllegalStateException(ConstantsContainer.NULLPARAMETERS);

        Square square1 = gameMap.getMap().get(tile1 -1);
        Square square2 = gameMap.getMap().get(tile2 -1);

        if(square1.hasPlayer() || square2.hasPlayer())
            throw new IllegalStateException("occupied square");

        gameMap.placeWorkerOnMap(square1,square2,currentPlayer);

    }

    /**
     * Method that remove the currentPlayer from the game if he has lost
     */

    public void removePlayerLose(){
        Player toRemovePlayer = currentPlayer;
        settedPlayers.remove(toRemovePlayer);
        gameMap.removeWorkersOfPlayer(toRemovePlayer);
    }

    /**
     * Function that set the new game status
     * @param status The new status of the game
     */

    public void setGameStatus(Response status){
        this.gameStatus = status;
    }

    /**
     * Method that says the actual game status
     * @return The actual game status
     */

    public Response getGameStatus(){ return this.gameStatus;}

    /**
     * Set the GameID of the current game
     * @param gameID The GameID of the current game
     */

    public void setGameID(String gameID){this.gameID = gameID;}

    /**
     * Getter of the GameID
     * @return GameID of the current game
     */

    public String getGameID(){
        return this.gameID;
    }
}
