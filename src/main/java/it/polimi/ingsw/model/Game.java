package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.Cards.CardLoader;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {
    private  Integer numberOfPlayers;
    private  ArrayList<Player> players;
    private  HashMap<String, Card> deck;
    private  Player currentPlayer;
    private GameMap gameMap;
    private boolean isGameStarted;


    private static Game gameInstance;

    private Game() {

        players = new ArrayList<>();
        deck = CardLoader.loadCards();
        gameMap = new GameMap();

    }

    public static Game getSingleInstance(){

        if(gameInstance == null)
            gameInstance =  new Game();

        return gameInstance;


    }

    public  Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void  setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player){
        if(isGameStarted)
            throw new IllegalStateException("game already started");  //cambiare questa eccezione
        if(players.size() >= numberOfPlayers)
            throw new IllegalStateException("too much player"); //cambiare questa eccezione
        if(player == null)
            throw new NullPointerException("null player");
        players.add(player);

    }

    public HashMap<String, Card> getDeck() {
        return deck;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }


}
