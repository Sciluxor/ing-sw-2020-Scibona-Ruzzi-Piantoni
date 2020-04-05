package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.Cards.CardLoader;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Color;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.PlayerQueue;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.view.Server.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

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

    private boolean hasWinner;
    private Player winner;
    //private colors =

    public Game(int numberOfPlayers, String gameID) {

        settedPlayers = new ArrayList<>();
        configPlayer = 0;
        this.numberOfPlayers = numberOfPlayers;
        deck = CardLoader.loadCards();
        gameMap = new GameMap();
        isGameStarted = false;
        hasWinner = false;
        this.gameID = gameID;
    }

    public  Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void  setNumberOfPlayers(Integer numberOfPlayers) {
        if(numberOfPlayers == null)
            throw new NullPointerException("null numberOfPlayers");

        this.numberOfPlayers = numberOfPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return settedPlayers;
    }

    public ArrayList<Player> getSettedPlayers() {
        return settedPlayers;
    }

    public int getConfigPlayer() {
        return configPlayer;
    }

    public boolean isHasWinner() {
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

    public boolean addPlayer(Player player, VirtualView actualView){
        if(isGameStarted)
            throw new IllegalStateException("game already started");  //cambiare questa eccezione
        if(settedPlayers.size() >= numberOfPlayers)
            throw new IllegalStateException("too much player"); //cambiare questa eccezione
        if(player == null)
            throw new NullPointerException("null player");

        addObservers(actualView);

        for(Player player1: settedPlayers){
            if(player1.getNickname().equals(player.getNickname())){
                configPlayer++;
                return false;
            }
        }
        player.setColor(Color.values()[settedPlayers.size()]);
        settedPlayers.add(player);
        return true;
    }

    public void removeSettedPlayer(String nick){
        for(Player player:settedPlayers){
            if(player.getNickname().equals(nick)){
                settedPlayers.remove(player);
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

    public void placeWorkersOnMap(Player player, int x1, int y1, int x2, int y2) {
        if(player == null)
            throw new NullPointerException("null player");

        Integer[] tile1 = {x1, y1};
        Integer[] tile2 = {x2, y2};

        Square square1 = gameMap.getTileFromCoordinates(tile1);
        Square square2 = gameMap.getTileFromCoordinates(tile2);

        this.gameMap.getGameMap().get(square1.getTile() - 1).setMovement(player, player.getWorkers().get(0));
        player.getWorkers().get(0).setBoardPosition(square1);

        this.gameMap.getGameMap().get(square2.getTile() - 1).setMovement(player, player.getWorkers().get(1));
        player.getWorkers().get(1).setBoardPosition(square2);

    }

    public Player pickChallenger() {
        int Challenger = (int) ((Math.random()*(numberOfPlayers)) - 1);
        return settedPlayers.get(Challenger);
    }


    public PlayerQueue createQueue(String nickname) {
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

        return new PlayerQueue(queue);
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


}
