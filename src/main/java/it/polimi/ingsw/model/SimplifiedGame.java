package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.WorkerName;

import java.util.*;

public class SimplifiedGame{
    private final Integer numberOfPlayers;
    private List<Player> settedPlayers;
    private Map<String, Card> deck;
    private List<String> availableCards;
    private Player currentPlayer;
    private Player clientPlayer;
    private final GameMap gameMap;
    private boolean isGameStarted;
    private String gameID;
    private Response gameStatus;
    private boolean hasWinner;
    private Player winner;

    public SimplifiedGame(int numberOfPlayers) {

        this.numberOfPlayers = numberOfPlayers;
        gameMap = new GameMap();
        deck = CardLoader.loadCards();
        isGameStarted = false;
        hasWinner = false;
    }

    public Player getClientPlayer() {
        return clientPlayer;
    }

    public void setClientPlayer(Player clientPlayer) {
        this.clientPlayer = clientPlayer;
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
                    System.out.println("----" + oldSquare.getTile() + "---" + newSquare.getTile());
                    System.out.println("----" + player.getNickName() + "---" + newSquare.getPlayer().getNickName());
                    System.out.println("----" + newSquare.getWorker().getName().toString());
                    oldSquare.setWorker(player.getWorkers().get(WorkerName.getNumberWorker(newSquare.getWorker().getName()) -1));
                    oldSquare.getWorker().setPreviousBoardPosition(oldSquare.getWorker().getBoardPosition());
                    oldSquare.getWorker().setBoardPosition(oldSquare);
                    //oldSquare.getWorker().setPreviousBuildPosition(newSquare.getWorker().getPreviousBuildPosition());
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
       // if(currentPlayer == null)
            //throw new NullPointerException("null currentPlayer");

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

    public boolean placeWorkersOnMap(Integer tile1,Integer tile2) {  // da cambiare il tipo
        if(tile1 <= 0 || tile1 > 25 ||  tile2 <= 0 || tile2 > 25 )
            return false;

        Square square1 = gameMap.getMap().get(tile1 -1);
        Square square2 = gameMap.getMap().get(tile2 -1);
        getGameMap().clearModifiedSquare();

        if(square1.hasPlayer() || square2.hasPlayer())
            return false;

        this.getGameMap().placeWorker(square1,currentPlayer,currentPlayer.getWorkers().get(0));
        currentPlayer.getWorkers().get(0).setBoardPosition(square1);
        this.getGameMap().placeWorker(square2,currentPlayer,currentPlayer.getWorkers().get(1));
        currentPlayer.getWorkers().get(1).setBoardPosition(square2);

        currentPlayer.setHasPlacedWorkers(true);

        return true;

    }
/*
    public void removePlayerLose(){
        Player toRemovePlayer = currentPlayer;
        settedPlayers.remove(toRemovePlayer);
        playerQueue.remove(toRemovePlayer);
        gameMap.removeWorkersOfPlayer(toRemovePlayer);
        pickPlayer(); //implementare questa funzione
    }

    public boolean assignCard(String card) {
        if(card == null)
            throw new NullPointerException("null card");

        if(!checkCardIntoDeck(card))
            return false;

        currentPlayer.setPower(deck.get(card));
        return true;
    }
*/
    public void setGameStatus(Response status){
        this.gameStatus = status;
    }

    public Response getGameStatus(){ return this.gameStatus;}

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID){this.gameID = gameID;}
}
