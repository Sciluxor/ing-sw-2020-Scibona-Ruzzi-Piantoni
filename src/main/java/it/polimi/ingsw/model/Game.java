package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.cards.CardSubType;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.PlayerQueue;
import it.polimi.ingsw.model.player.TurnStatus;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.view.server.VirtualView;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * Class that represent a single match (used by the Server)
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/28
 */

public class Game extends Observable<Response> {
    private Integer numberOfPlayers;
    private List<Player> settedPlayers;
    private String lastLosePlayer;
    private String stopper = null;
    private String firstPlayer;
    private String challenger;
    private boolean hasStopper;
    private List<Player> losePlayers = new ArrayList<>();
    private int configPlayer;
    private final Map<String, Card> deck;
    private Player currentPlayer;
    private final GameMap gameMap;
    private boolean isGameStarted;
    private Response gameStatus;
    private final String gameID;
    private List<Color> availableColors;
    private List<String> availableCards;
    private PlayerQueue playerQueue = new PlayerQueue(new ArrayList<>());
    private Random rand;

    private boolean hasWinner;
    private Player winner;

    /**
     * Public constructor for the Game Class
     * @param numberOfPlayers Number of Player in game
     * @param gameID GameId of the current game
     */

    public Game(int numberOfPlayers, String gameID) {

        settedPlayers = new ArrayList<>();
        configPlayer = 0;
        this.numberOfPlayers = numberOfPlayers;
        deck = CardLoader.loadCards();
        gameMap = new GameMap();
        isGameStarted = false;
        hasWinner = false;
        hasStopper = false;
        availableColors = new ArrayList<>();
        this.gameID = gameID;
        availableColors.addAll(Arrays.asList(Color.values()));
    }

    /**
     * Setter of the First Player
     * @param firstPlayer First Player entered in the game
     */

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
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
     * Getter of the number of Players in game
     * @return Number of the Players in the current game
     */

    public  Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Setter of the number of Player in game
     * @param numberOfPlayers Number of the Players
     */

    public void  setNumberOfPlayers(Integer numberOfPlayers) {
        if(numberOfPlayers == null)
            throw new NullPointerException("null numberOfPlayers");

        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Getter of the stopper, player that left the game
     * @return Name of the Stopper
     */

    public String getStopper() {
        return stopper;
    }

    /**
     * Setter of the stopper
     * @param stopper Name of the player who left the game
     */

    public void setStopper(String stopper) {
        this.stopper = stopper;
    }

    /**
     * Getter of the last Player who lost
     * @return Last Player that lost
     */

    public String getLastLosePlayer() {
        return lastLosePlayer;
    }

    /**
     * Getter of the List of Players that have lost
     * @return List of Players that have lost
     */

    public List<Player> getLosePlayers() {
        return losePlayers;
    }

    /**
     * Getter of the provided card from the List of available cards
     * @param card Card to be selected
     * @return Card
     */

    public String getCardFromAvailableCards(String card) {
        for(String possibileCard : availableCards){
            if(possibileCard.equals(card))
                return possibileCard;
        }
        return null;
    }

    /**
     * Getter of the List of Players in game
     * @return List of Players in game
     */

    public List<Player> getPlayers() {
        return settedPlayers;
    }

    /**
     * Getter of the number of player in game that are still choosing nickname ( used only if the nickname selected is already in use)
     * @return Number of config player
     */

    public int getConfigPlayer() {
        return configPlayer;
    }

    /**
     * Method that says if the game has a winner
     * @return Boolean that says if the game has a winner
     */

    public boolean hasWinner() {
        return hasWinner;
    }

    /**
     * Setter of hasWinner
     * @param hasWinner Boolean to set
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
     * Method that return the card provided from the deck of cards
     * @param cardName Card wanted from the deck
     * @return Card
     */

    public Card getCardFromDeck(String cardName){
        return deck.get(cardName);
    }

    /**
     * Method that add the Player provided to the list of Players in game
     * @param player Player to be added
     * @param actualView VirtualView of the game
     * @return Boolean that says if the Player is added or not
     */

    public boolean addPlayer(Player player, VirtualView actualView){
        if(isGameStarted)
            throw new IllegalStateException("game already started");
        if(settedPlayers.size() + configPlayer >= numberOfPlayers)
            throw new IllegalStateException("too much player");
        if(player == null)
            throw new NullPointerException("null player");

        addObservers(actualView);

        for(Player player1: settedPlayers){
            if(player1.getNickName().equalsIgnoreCase(player.getNickName())){
                configPlayer++;
                return false;
            }
        }
        player.setColor(availableColors.get(0));
        availableColors.remove(0);
        settedPlayers.add(player);
        return true;
    }

    /**
     * Method that remove the provided Player form the list of settedPlayers
     * @param nick Player to be removed
     */

    public void removeSettedPlayer(String nick){
        for(Player player:settedPlayers){
            if(player.getNickName().equals(nick)){
                settedPlayers.remove(player);
                availableColors.add(player.getColor());
                break;
            }
        }
    }

    /**
     * Method that remove a configPlayer
     */

    public void removeConfigPlayer(){
        configPlayer--;
    }

    /**
     * Method that move a player from the list of configPlayer to SettedPlayer, only if the the nickname is free
     * @param player Player to be moved
     * @return Boolean that says if the Player is moved or not
     */

    public boolean newNickName(Player player){
        for(Player player1: settedPlayers){
            if(player1.getNickName().equals(player.getNickName())){
                return false;
            }
        }
        configPlayer--;
        player.setColor(availableColors.get(0));
        availableColors.remove(0);
        settedPlayers.add(player);
        return true;
    }

    /**
     * Getter of the deck of cards
     * @return Deck of cards
     */

    public Map<String, Card> getDeck() {
        return deck;
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
     * @param currentPlayer Player to be set
     */

    public void setCurrentPlayer(Player currentPlayer) {
        if(currentPlayer == null)
            throw new NullPointerException("null currentPlayer");

        this.currentPlayer = currentPlayer;
    }

    /**
     * Method that pick the first Player from the playerQueue
     */

    public void pickPlayer(){
        setCurrentPlayer(playerQueue.peekFirst());
        playerQueue.changeTurn();
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
     * Function that set the new game status, and notify all the observers of the game (the virtual views of the clients)
     * @param newStatus The new status of the game
     */

    public void setGameStatus(Response newStatus){
        if(newStatus == null)
            throw new NullPointerException("null newStatus");

        this.gameStatus = newStatus;
        String stringStatus = newStatus.toString();
        Server.LOGGER.info(stringStatus);
        notify(gameStatus);
    }

    /**
     * Method that set the Workers of the currentPlayer on the map
     * @param tile1 First square to set the Worker
     * @param tile2 Second square to set the Worker
     * @return Boolean that says if the Workers are placed or not
     */

    public boolean placeWorkersOnMap(Integer[] tile1,Integer[] tile2) {
         if(tile1[0]> ConstantsContainer.MAXMAPCOORD || tile1[0]<ConstantsContainer.MINMAPPOSITION
                 || tile1[1]>ConstantsContainer.MAXMAPCOORD || tile1[1] <ConstantsContainer.MINMAPPOSITION
                 || tile2[0]>ConstantsContainer.MAXMAPCOORD || tile2[0]<ConstantsContainer.MINMAPPOSITION
                 || tile2[1]>ConstantsContainer.MAXMAPCOORD || tile2[1] <ConstantsContainer.MINMAPPOSITION)
             return false;

        Square square1 = gameMap.getTileFromCoordinates(tile1);
        Square square2 = gameMap.getTileFromCoordinates(tile2);

        if(square1.hasPlayer() || square2.hasPlayer())
            return false;

        gameMap.placeWorkerOnMap(square1,square2,currentPlayer);

        return true;
    }

    /**
     * Method that remove the currentPlayer from the game if he has lost
     */

    public void removePlayerLose(){
        Player toRemovePlayer = currentPlayer;
        lastLosePlayer = toRemovePlayer.getNickName();
        losePlayers.add(toRemovePlayer);
        settedPlayers.remove(toRemovePlayer);
        playerQueue.remove(toRemovePlayer);
        gameMap.removeWorkersOfPlayer(toRemovePlayer);
        pickPlayer();
    }

    /**
     * Method that check if all the Workers of all the Players in game are placed on the map
     * @return True if all the players have placed their workers, false otherwise
     */

    public boolean allWorkersPlaced(){
        for(Player player : settedPlayers){
            if(!player.hasPlacedWorkers())
                return false;
        }
        return true;
    }

    /**
     * Method that choose randomly the Challenger from the Players in game
     * @return Player chosen as Challenger
     */

    public Player pickChallenger() {
        try {
            rand = SecureRandom.getInstanceStrong();
        }
        catch (NoSuchAlgorithmException nsa){
            Server.LOGGER.severe(nsa.getMessage());
        }

        int numChallenger = rand.nextInt(numberOfPlayers-1);
        Player newChallenger = settedPlayers.get(numChallenger);
        newChallenger.setTurnStatus(TurnStatus.PLAYTURN);
        currentPlayer = newChallenger;
        this.challenger = newChallenger.getNickName();
        return newChallenger;
    }

    /**
     * Method that create the Players Queue from the List of Players and using the choice of the challenger
     */

    public void createQueue() {
        playerQueue.clear();
        List<Player> queue = new ArrayList<>();

        for(Player player1: settedPlayers) {
            if (player1.getNickName().equalsIgnoreCase(firstPlayer)) {
                queue.add(player1);
                break;
            }
        }
        for(Player player1: settedPlayers) {
            if(!player1.getNickName().equalsIgnoreCase(firstPlayer)) {
                queue.add(player1);
            }
        }
        this.playerQueue = new PlayerQueue(queue);
    }

    /**
     * Method that create the Players Queue from the List of Players placing the challenger in the last position(queue used for the card choice phase)
     */

    public void createCardQueue(){
        List<Player> queue = new ArrayList<>();

        for(Player player1: settedPlayers) {
            if (!player1.getNickName().equalsIgnoreCase(challenger)) {
                queue.add(player1);
            }
        }
        for(Player player1: settedPlayers) {
            if(player1.getNickName().equalsIgnoreCase(challenger)) {
                queue.add(player1);
                break;
            }
        }

        this.playerQueue = new PlayerQueue(queue);
    }

    /**
     * Method that check if the Card provided is in the deck of cards
     * @param card Card to be checked
     * @return Boolean that says if the Card is in the deck or not
     */

    public boolean checkCardIntoDeck(String card) { return deck.get(card) != null;}

    /**
     * Method that assign a specific card to the current player
     * @param card The card to assign
     * @return True if the card is in the deck, false otherwise
     */

    public boolean assignCard(String card) {
        if(card == null)
            throw new NullPointerException("null card");

        if(!checkCardIntoDeck(card))
            return false;

        currentPlayer.setPower(deck.get(card));
        return true;
    }

    /**
     * Method that says the actual game status
     * @return The actual game status
     */

    public Response getGameStatus(){ return this.gameStatus;}

    /**
     * Getter of the GameId
     * @return GameId of the current game
     */

    public String getGameID() {
        return gameID;
    }

    /**
     * Method that assign the permanent constraints to the players
     */

    public void assignPermanentConstraint(){
        for(Player player : settedPlayers){
            if(player.getPower().getSubType().equals(CardSubType.PERMANENTCONSTRAINT)){
                player.assignConstraint(getPlayers());
            }
        }
    }

}
