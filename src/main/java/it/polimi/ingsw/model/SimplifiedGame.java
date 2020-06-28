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

    public boolean hasStopper() {
        return hasStopper;
    }

    public void setHasStopper(boolean hasStopper) {
        this.hasStopper = hasStopper;
    }

    public Player getClientPlayer() {
        return clientPlayer;
    }

    public  Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public List<Player> getPlayers() {
        return settedPlayers;
    }

    public boolean hasWinner() {
        return hasWinner;
    }

    public Map<String, Card> getDeck() {
        return deck;
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

    public Integer[] getCoordinatesFromTile(int tile){
       return gameMap.getMap().get(tile-1).getCoordinates();
    }

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

    public List<String> getAvailableCards() {
        return availableCards;
    }

    public void setAvailableCards(List<String> cardNames) {
        availableCards = cardNames;
    }

    public void removeCard(String toRemoveCard){
        availableCards.remove(toRemoveCard);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String playerName) {
        if(playerName == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);
        for(Player player : settedPlayers)
            if(player.getNickName().equals(playerName))
                this.currentPlayer = player;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) { isGameStarted = gameStarted;}

    public void placeWorkersOnMap(Integer tile1,Integer tile2) {
        if(tile1 <= 0 || tile1 > 25 ||  tile2 <= 0 || tile2 > 25 )
            throw new IllegalStateException(ConstantsContainer.NULLPARAMETERS);

        Square square1 = gameMap.getMap().get(tile1 -1);
        Square square2 = gameMap.getMap().get(tile2 -1);

        if(square1.hasPlayer() || square2.hasPlayer())
            throw new IllegalStateException("occupied square");

        gameMap.placeWorkerOnMap(square1,square2,currentPlayer);

    }

    public void removePlayerLose(){
        Player toRemovePlayer = currentPlayer;
        settedPlayers.remove(toRemovePlayer);
        gameMap.removeWorkersOfPlayer(toRemovePlayer);
    }

    public void setGameStatus(Response status){
        this.gameStatus = status;
    }

    public Response getGameStatus(){ return this.gameStatus;}

    public void setGameID(String gameID){this.gameID = gameID;}

    public String getGameID(){
        return this.gameID;
    }
}
