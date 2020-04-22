package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.Cards.CardLoader;
import it.polimi.ingsw.model.Cards.CardSubType;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Color;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.PlayerQueue;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.view.server.VirtualView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Game extends Observable<Response> {
    private Integer numberOfPlayers;
    private ArrayList<Player> settedPlayers;
    private int configPlayer;
    private HashMap<String, Card> deck;
    private Player currentPlayer;
    private GameMap gameMap;
    private boolean isGameStarted;
    private Response gameStatus;
    private String gameID;
    private ArrayList<Color> availableColors;
    private ArrayList<String> availableCards;
    private PlayerQueue playerQueue;

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
        availableColors = new ArrayList<>();
        this.gameID = gameID;
        availableColors.addAll(Arrays.asList(Color.values()));
    }

    public ArrayList<String> getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(ArrayList<String> cardNames) {
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

    public String getCardFromAvailableCards(String card) {
        for(String possibileCard : availableCards){
            if(possibileCard.equals(card))
                return possibileCard;
        }

        return null;

    }


    public ArrayList<Player> getPlayers() {
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
            if(player1.getNickname().equals(player.getNickname())){
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
            if(player.getNickname().equals(nick)){
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
            if(player1.getNickname().equals(player.getNickname())){
                return false;
            }
        }
        configPlayer--;
        player.setColor(availableColors.get(0));
        availableColors.remove(0);
        settedPlayers.add(player);
        return true;
    }

    public HashMap<String, Card> getDeck() {
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
        notify(gameStatus);
    }

    public boolean placeWorkersOnMap(Integer[] tile1,Integer[] tile2) {

        Square square1 = gameMap.getTileFromCoordinates(tile1);
        Square square2 = gameMap.getTileFromCoordinates(tile2);

        if(square1.hasPlayer() || square2.hasPlayer())
            return false;

        this.gameMap.getGameMap().get(square1.getTile() - 1).setMovement(currentPlayer, currentPlayer.getWorkers().get(0));
        currentPlayer.getWorkers().get(0).setBoardPosition(square1);
        this.gameMap.getGameMap().get(square2.getTile() - 1).setMovement(currentPlayer, currentPlayer.getWorkers().get(1));
        currentPlayer.getWorkers().get(1).setBoardPosition(square2);

        currentPlayer.setHasPlacedWorkers(true);

        return true;

    }

    public void removePlayerLose(){
        Player toRemovePlayer = currentPlayer;
        settedPlayers.remove(toRemovePlayer);
        playerQueue.remove(toRemovePlayer);
        gameMap.removeWorkersOfPlayer(toRemovePlayer);
        pickPlayer(); //implementare questa funzione
    }

    public boolean allWorkersPlaced(){
        for(Player player : settedPlayers){
            if(!player.hasPlacedWorkers())
                return false;
        }
        return true;
    }

    public Player pickChallenger() {
        int Challenger = (int) ((Math.random()*(numberOfPlayers)) - 1);
        return settedPlayers.get(Challenger);
    }


    public void createQueue(String nickname) {
        if(nickname == null)
            throw new NullPointerException("null nickname");

        ArrayList<Player> queue = new ArrayList<>();

        for(Player player1: settedPlayers) {
            if (player1.getNickname().equalsIgnoreCase(nickname)) {
                queue.add(player1);
                break;
            }
        }
        for(Player player1: settedPlayers) {
            if(!player1.getNickname().equalsIgnoreCase(nickname)) {
                queue.add(player1);
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
