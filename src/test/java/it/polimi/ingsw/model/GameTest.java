package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.Cards.CardLoader;
import it.polimi.ingsw.model.Cards.Response;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.PlayerQueue;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.Server.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    ArrayList<VirtualView> players;
    ClientHandler connection1, connection2, connection3;
    VirtualView player1, player2, player3;
    Player player4, player5, player6;
    Card cardApollo, cardAthena, cardAtlas;
    Game game;
    HashMap<String, Card> deck;
    GameMap gameMap;

    @BeforeEach
    void setup(){
        player1 = new VirtualView(connection1, "uno");
        player2 = new VirtualView(connection2, "due");
        player3 = new VirtualView(connection3, "tre");
        player4 = new Player("quattro");
        cardApollo = CardLoader.loadCards().get("Apollo");
        cardAthena = CardLoader.loadCards().get("Athena");
        cardAtlas = CardLoader.loadCards().get("Atlas");
        player1.getPlayer().setPower(cardApollo);
        player2.getPlayer().setPower(cardAthena);
        player3.getPlayer().setPower(cardAtlas);
        players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        deck = CardLoader.loadCards();
        game = new Game(players, 3);
        gameMap = new GameMap();
    }

    @Test
    void getNumberOfPlayers() {
        assertEquals(game.getNumberOfPlayers(), 3);
        game = new Game(players, 2);
        assertEquals(game.getNumberOfPlayers(), 2);
    }

    @Test
    void setNumberOfPlayers() {
        assertThrows(NullPointerException.class , () -> game.setNumberOfPlayers(null));
        assertEquals(game.getNumberOfPlayers(), 3);
        game.setNumberOfPlayers(2);
        assertEquals(game.getNumberOfPlayers(), 2);
    }


    @Test
    void getPlayers() {
        assertEquals(game.getPlayers().size(), 3);
        assertEquals(game.getPlayers().get(0), player1.getPlayer());
        assertEquals(game.getPlayers().get(1), player2.getPlayer());
        assertEquals(game.getPlayers().get(2), player3.getPlayer());
    }

    @Test
    void addPlayer() {
        try{
            game.addPlayer(player4);
            fail("Should throws IllegalStateException");
        }
        catch (IllegalStateException ex){
            assertEquals("game already started", ex.getMessage());
        }
        game.setGameStarted(false);
        try{
            game.addPlayer(player4);
            fail("Should throws IllegalStateException");
        }
        catch (IllegalStateException ex){
            assertEquals("too much player", ex.getMessage());
        }
        game.setNumberOfPlayers(4);
        assertThrows(NullPointerException.class , () -> game.setNumberOfPlayers(null));
        game.addPlayer(player4);
        assertEquals(game.getPlayers().size(), 4);
        assertEquals(game.getPlayers().get(3), player4);
    }

    @Test
    void getDeck() {
        assertNotNull(game.getDeck());
    }

    @Test
    void getCurrentPlayer() {
        assertNull(game.getCurrentPlayer());
        game.setCurrentPlayer(player4);
        assertEquals(game.getCurrentPlayer(), player4);
    }

    @Test
    void setCurrentPlayer() {
        assertThrows(NullPointerException.class , () -> game.setCurrentPlayer(null));
        assertNull(game.getCurrentPlayer());
        game.setCurrentPlayer(player4);
        assertEquals(game.getCurrentPlayer(), player4);
    }

    @Test
    void getGameMap() {
        assertNotNull(game.getGameMap());
    }

    @Test
    void isGameStarted() {
        assertTrue(game.isGameStarted());
        game.setGameStarted(false);
        assertFalse(game.isGameStarted());
    }

    @Test
    void setGameStarted() {
        assertTrue(game.isGameStarted());
        game.setGameStarted(false);
        assertFalse(game.isGameStarted());
    }

    @Test
    void setGameStatus() {
        assertThrows(NullPointerException.class , () -> game.setGameStatus(null));
        assertNull(game.getGameStatus());
        game.setGameStatus(Response.BUILD);
        assertEquals(game.getGameStatus(), Response.BUILD);
    }

    @Test
    void placeWorkersOnMap() {
        assertThrows(NullPointerException.class , () -> game.placeWorkersOnMap(null, 0, 0, 1,1));
        game.placeWorkersOnMap(player4, 0, 0, 1, 1);
        assertEquals(player4.getWorkers().get(0).getBoardPosition(), game.getGameMap().getGameMap().get(0));
        assertEquals(player4.getWorkers().get(1).getBoardPosition(), game.getGameMap().getGameMap().get(16));
        assertEquals(game.getGameMap().getGameMap().get(0).getWorker(), player4.getWorkers().get(0));
        assertEquals(game.getGameMap().getGameMap().get(16).getWorker(), player4.getWorkers().get(1));
        assertTrue(game.getGameMap().getGameMap().get(0).hasPlayer());
        assertTrue(game.getGameMap().getGameMap().get(16).hasPlayer());
        assertEquals(game.getGameMap().getGameMap().get(16).getPlayer(), player4);
        assertEquals(game.getGameMap().getGameMap().get(16).getPlayer(), player4);
    }

    @Test
    void pickChallenger() {
        player5 = game.pickChallenger();
        assertNotNull(player5);
        int value = 0;
        for (Player play : game.getPlayers()){
            if (play == player5){
                player6 = play;
                value++;
            }
        }
        if (value != 1){
            fail("Should be 1");
        }
        assertEquals(player6, player5);
    }

    @Test
    void createQueue() {
        assertThrows(NullPointerException.class , () -> game.createQueue(null));
        PlayerQueue queue = game.createQueue("due");
        assertEquals(queue.size(), 3);
        assertEquals(queue.peekFirst(), game.getPlayers().get(1));
        queue.changeTurn();
        assertEquals(queue.peekFirst(), game.getPlayers().get(0));
        queue.changeTurn();
        assertEquals(queue.peekFirst(), game.getPlayers().get(2));
        queue.changeTurn();
        assertEquals(queue.peekFirst(), game.getPlayers().get(1));
        queue.changeTurn();
        assertEquals(queue.peekFirst(), game.getPlayers().get(0));
        queue.changeTurn();
        assertEquals(queue.peekFirst(), game.getPlayers().get(2));
        queue.changeTurn();
        assertEquals(queue.peekLast(), game.getPlayers().get(2));
        queue.changeTurn();
        assertEquals(queue.peekLast(), game.getPlayers().get(1));
        queue.changeTurn();
        assertEquals(queue.peekLast(), game.getPlayers().get(0));
    }

    @Test
    void checkCardIntoDeck() {
        assertFalse(game.checkCardIntoDeck("sbagliata"));
        assertTrue(game.checkCardIntoDeck("Apollo"));
        assertTrue(game.checkCardIntoDeck("Zeus"));
    }

    @Test
    void assignCard() {
        assertThrows(NullPointerException.class , () -> game.assignCard(null));
        assertFalse(game.assignCard("sbagliata"));
        game.setCurrentPlayer(game.getPlayers().get(0));
        assertFalse(game.assignCard("sbagliata"));
        assertTrue(game.assignCard("Pan"));
    }

    @Test
    void getGameStatus() {
        assertNull(game.getGameStatus());
        game.setGameStatus(Response.BUILD);
        assertEquals(game.getGameStatus(), Response.BUILD);
    }
}