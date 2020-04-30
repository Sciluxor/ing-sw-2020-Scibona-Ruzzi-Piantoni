package it.polimi.ingsw.model;

import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.model.player.Player;
import java.util.*;

public class SimplifiedGame{
    private Integer numberOfPlayers;
    private List<Player> settedPlayers;
    private Player currentPlayer;
    private Player clientPlayer;
    private GameMap gameMap;
    private boolean isGameStarted;
    private String gameID;
    private Response gameStatus;
    private boolean hasWinner;
    private Player winner;

    public SimplifiedGame(int numberOfPlayers) {

        this.numberOfPlayers = numberOfPlayers;
        gameMap = new GameMap();
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

    public void setHasWinner(boolean hasWinner) {
        this.hasWinner = hasWinner;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void initPlayers(String clientName,List<String> names, List<Color> colors){
        settedPlayers = new ArrayList<>();
        if (names.size() != colors.size())
            throw new IllegalStateException("wrong numbers");
        for (int i = 0; i < names.size(); i++) {
            Player newPlayer = new Player(names.get(i));
            settedPlayers.add(newPlayer);
            newPlayer.setColor(colors.get(i));

            if (newPlayer.getNickname().equals(clientName)) {
                clientPlayer = newPlayer;
            }
        }
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        if(currentPlayer == null)
            throw new NullPointerException("null currentPlayer");

        this.currentPlayer = currentPlayer;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) { isGameStarted = gameStarted;}

    public boolean placeWorkersOnMap(Integer[] tile1,Integer[] tile2) {

        Square square1 = gameMap.getTileFromCoordinates(tile1);
        Square square2 = gameMap.getTileFromCoordinates(tile2);

        if(square1.hasPlayer() || square2.hasPlayer())
            return false;

        this.gameMap.getMap().get(square1.getTile() - 1).setMovement(currentPlayer, currentPlayer.getWorkers().get(0));
        currentPlayer.getWorkers().get(0).setBoardPosition(square1);
        this.gameMap.getMap().get(square2.getTile() - 1).setMovement(currentPlayer, currentPlayer.getWorkers().get(1));
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
