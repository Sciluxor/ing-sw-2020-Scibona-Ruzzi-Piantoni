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

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public boolean hasStopper() {
        return hasStopper;
    }

    public void setHasStopper(boolean hasStopper) {
        this.hasStopper = hasStopper;
    }

    public List<String> getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(List<String> cardNames) {
        availableCards = cardNames;
    }

    public void removeCard(String toRemoveCard){
        availableCards.remove(toRemoveCard);
    }

    public  Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void  setNumberOfPlayers(Integer numberOfPlayers) {
        if(numberOfPlayers == null)
            throw new NullPointerException("null numberOfPlayers");

        this.numberOfPlayers = numberOfPlayers;
    }

    public String getStopper() {
        return stopper;
    }

    public void setStopper(String stopper) {
        this.stopper = stopper;
    }

    public String getLastLosePlayer() {
        return lastLosePlayer;
    }

    public List<Player> getLosePlayers() {
        return losePlayers;
    }

    public String getCardFromAvailableCards(String card) {
        for(String possibileCard : availableCards){
            if(possibileCard.equals(card))
                return possibileCard;
        }

        return null;

    }


    public List<Player> getPlayers() {
        return settedPlayers;
    }

    public int getConfigPlayer() {
        return configPlayer;
    }

    public boolean hasWinner() {
        return hasWinner;
    }

    public void setHasWinner(boolean hasWinner) {
        this.hasWinner = hasWinner;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Card getCardFromDeck(String cardName){
        return deck.get(cardName);
    }

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

    public void removeSettedPlayer(String nick){
        for(Player player:settedPlayers){
            if(player.getNickName().equals(nick)){
                settedPlayers.remove(player);
                availableColors.add(player.getColor());
                break;
            }
        }

    }
    public void removeConfigPlayer(){
        configPlayer--;
    }

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

    public Map<String, Card> getDeck() {
        return deck;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        if(currentPlayer == null)
            throw new NullPointerException("null currentPlayer");

        this.currentPlayer = currentPlayer;
    }

    public void pickPlayer(){
        setCurrentPlayer(playerQueue.peekFirst());
        playerQueue.changeTurn();
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) { isGameStarted = gameStarted;}

    public void setGameStatus(Response newStatus){
        if(newStatus == null)
            throw new NullPointerException("null newStatus");

        this.gameStatus = newStatus;
        String stringStatus = newStatus.toString();
        Server.LOGGER.info(stringStatus);
        notify(gameStatus);
    }

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

    public void removePlayerLose(){
        Player toRemovePlayer = currentPlayer;
        lastLosePlayer = toRemovePlayer.getNickName();
        losePlayers.add(toRemovePlayer);
        settedPlayers.remove(toRemovePlayer);
        playerQueue.remove(toRemovePlayer);
        gameMap.removeWorkersOfPlayer(toRemovePlayer);
        pickPlayer();
    }

    public boolean allWorkersPlaced(){
        for(Player player : settedPlayers){
            if(!player.hasPlacedWorkers())
                return false;
        }
        return true;
    }

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

    public boolean checkCardIntoDeck(String card) { return deck.get(card) != null;}

    public boolean assignCard(String card) {
        if(card == null)
            throw new NullPointerException("null card");

        if(!checkCardIntoDeck(card))
            return false;

        currentPlayer.setPower(deck.get(card));
        return true;
    }

    public Response getGameStatus(){ return this.gameStatus;}

    public String getGameID() {
        return gameID;
    }

    public void assignPermanentConstraint(){
        for(Player player : settedPlayers){
            if(player.getPower().getSubType().equals(CardSubType.PERMANENTCONSTRAINT)){
                player.assignConstraint(getPlayers());
            }
        }

    }


}
