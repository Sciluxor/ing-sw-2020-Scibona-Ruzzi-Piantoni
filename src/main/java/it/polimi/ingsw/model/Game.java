package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.Cards.CardLoader;
import it.polimi.ingsw.model.Cards.Response;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.PlayerQueue;
import it.polimi.ingsw.model.Player.Worker;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.view.Server.VirtualView;
import javafx.util.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class Game extends Observable<Game> {
    private Integer numberOfPlayers;
    private ArrayList<Player> players;
    private HashMap<String, Card> deck;
    private Player currentPlayer;
    private GameMap gameMap;
    private boolean isGameStarted;
    private Response gameStatus;

    public Game(ArrayList<VirtualView> actualPlayers,int numberOfPlayers) {

        players = new ArrayList<>();
        for(VirtualView view: actualPlayers){
            addObservers(view);
            players.add(view.getPlayer());
        }
        this.numberOfPlayers = numberOfPlayers;
        deck = CardLoader.loadCards();
        gameMap = new GameMap();
        isGameStarted = true;

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

    public void setGameStatus(Response newStatus){
        this.gameStatus = newStatus;
        notify(this);

    }

    public void placeWorkersOnMap(Player player, int x1, int y1, int x2, int y2) {
        if(player == null)
            throw new NullPointerException("null player");

        Integer[] tile1 = {x1, y1};
        Integer[] tile2 = {x2, y2};

        Square square1 = gameMap.getTileFromCoordinates(tile1);
        Square square2 = gameMap.getTileFromCoordinates(tile2);

        gameMap.getGameMap().get(square1.getTile()).setMovement(player, player.getWorkers().get(0));
        player.getWorkers().get(0).setBoardPosition(square1);

        gameMap.getGameMap().get(square2.getTile()).setMovement(player, player.getWorkers().get(1));
        player.getWorkers().get(1).setBoardPosition(square2);

    }

    public Player pickChallenger() {
        int Challenger = (int) ((Math.random()*(numberOfPlayers))-1);
        return players.get(Challenger);
    }


    public PlayerQueue createQueue(String nickname) {

        ArrayList<Player> queue = new ArrayList<>();

        for(Player player1: players) {
            if (player1.getNickname().equalsIgnoreCase(nickname)) {
                queue.add(player1);
                break;
            }
        }
        for(Player player1: players) {
            if(!player1.getNickname().equalsIgnoreCase(nickname)) {
                queue.add(player1);
            }
        }

        return new PlayerQueue(queue);
    }

    public boolean checkCardIntoDeck(String card) {
        return deck.get(card) != null;
    }

    public boolean assignCard(String card) {
        if(!checkCardIntoDeck(card))
            return false;

        currentPlayer.setPower(deck.get(card));
        return true;
    }

    public Response getGameStatus(){
        return this.gameStatus;
    }


}
