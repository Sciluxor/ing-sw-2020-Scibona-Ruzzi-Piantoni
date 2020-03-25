package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.Cards.CardLoader;
import it.polimi.ingsw.model.Cards.Response;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.view.Server.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;

public class Game extends Observable<Game> {
    private  Integer numberOfPlayers;
    private  ArrayList<Player> players;
    private  HashMap<String, Card> deck;
    private  Player currentPlayer;
    private GameMap gameMap;
    private boolean isGameStarted;
    private Response gameStatus;


    private static Game gameInstance;

    public Game(ArrayList<VirtualView> actualPlayers,int numberOfPlayers) {

        players = new ArrayList<>();
        for(VirtualView view: actualPlayers){
            players.add(view.getPlayer());
        }
        this.numberOfPlayers = numberOfPlayers;
        deck = CardLoader.loadCards();
        gameMap = new GameMap();
        isGameStarted = true;

    }

   /* public static Game getSingleInstance(){

        if(gameInstance == null)
            gameInstance =  new Game();

        return gameInstance;


    }*/

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

    public void setGameStatus(Response newStatus){
        this.gameStatus = newStatus;
        notify(this);

    }

    public Response getGameStatus(){
        return this.gameStatus;
    }


}
