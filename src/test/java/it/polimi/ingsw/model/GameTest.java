

package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.server.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    List<VirtualView> players;
    ClientHandler connection1, connection2, connection3;
    VirtualView viewPlayer1, viewPlayer2, viewPlayer3, viewPlayer4;
    Player player1, player2, player3, player4, player5, player6;
    Card cardApollo, cardAthena, cardAtlas, cardHera, cardHypnus;
    Game game;
    Map<String, Card> deck;
    GameMap gameMap;

    @BeforeEach
    void setup(){
        GameController controller = new GameController(3,"1");
        viewPlayer1 = new VirtualView(connection1, controller);
        viewPlayer2 = new VirtualView(connection2, controller);
        viewPlayer3 = new VirtualView(connection3, controller);
        viewPlayer4 = new VirtualView(connection3, controller);
        player1 = new Player("uno");
        player2 = new Player("due");
        player3 = new Player("tre");
        player4 = new Player("quattro");
        cardApollo = CardLoader.loadCards().get("apollo");
        cardAthena = CardLoader.loadCards().get("athena");
        cardAtlas = CardLoader.loadCards().get("atlas");
        cardHera = CardLoader.loadCards().get("hera");
        cardHypnus = CardLoader.loadCards().get("hypnus");
        player1.setPower(cardApollo);
        player2.setPower(cardAthena);
        player3.setPower(cardAtlas);
        players = new ArrayList<>();
        deck = CardLoader.loadCards();
        game = new Game(3, "G01");
        gameMap = new GameMap();

    }

    @Test
    void getNumberOfPlayers() {
        assertEquals(3,game.getNumberOfPlayers());
        game = new Game(2, "G02");
        assertEquals(2,game.getNumberOfPlayers());
    }

    @Test
    void setNumberOfPlayers() {
        assertThrows(NullPointerException.class , () -> game.setNumberOfPlayers(null));
        assertEquals(3,game.getNumberOfPlayers());
        game.setNumberOfPlayers(2);
        assertEquals(2,game.getNumberOfPlayers());
    }


    @Test
    void getPlayers() {
        assertEquals(0,game.getPlayers().size());
        game.addPlayer(player1, viewPlayer1);
        game.addPlayer(player2, viewPlayer2);
        game.addPlayer(player3, viewPlayer3);
        assertEquals(3,game.getPlayers().size());
        assertEquals(game.getPlayers().get(0), player1);
        assertEquals(game.getPlayers().get(1), player2);
        assertEquals(game.getPlayers().get(2), player3);
    }

    @Test
    void getConfigPlayer() {
        assertEquals(0,game.getConfigPlayer());
        game.addPlayer(player1, viewPlayer1);
        game.addPlayer(player1, viewPlayer1);
        assertEquals(1,game.getConfigPlayer());
        game.addPlayer(player1, viewPlayer1);
        assertEquals(2,game.getConfigPlayer());
    }

    @Test
    void hasWinner() {
        assertFalse(game.hasWinner());
        game.setHasWinner(true);
        assertTrue(game.hasWinner());
    }

    @Test
    void setHasWinner() {
        assertFalse(game.hasWinner());
        game.setHasWinner(true);
        assertTrue(game.hasWinner());
    }

    @Test
    void getWinner() {
        assertNull(game.getWinner());
        game.setWinner(player4);
        assertEquals(game.getWinner(), player4);
    }

    @Test
    void setWinner() {
        assertNull(game.getWinner());
        game.setWinner(player4);
        assertEquals(game.getWinner(), player4);
    }

    @Test
    void addPlayer() {
        assertThrows(NullPointerException.class , () -> game.setNumberOfPlayers(null));
        game.addPlayer(player1, viewPlayer1);
        assertEquals(1,game.getPlayers().size());
        assertEquals(game.getPlayers().get(0), player1);
        assertFalse(game.addPlayer(player1, viewPlayer1));
        game.removeConfigPlayer();
        game.addPlayer(player2, viewPlayer2);
        game.addPlayer(player3, viewPlayer3);
        assertEquals(3,game.getPlayers().size());
        assertEquals(game.getPlayers().get(2), player3);
        assertEquals(Color.PURPLE,game.getPlayers().get(0).getColor());
        assertEquals( Color.WHITE, game.getPlayers().get(1).getColor());
        assertEquals( Color.BLUE, game.getPlayers().get(2).getColor());
        try{
            game.addPlayer(player4, viewPlayer4);
            fail("Should throws IllegalStateException");
        }
        catch (IllegalStateException ex){
            assertEquals("too much player", ex.getMessage());
        }
        game.setGameStarted(true);
        try{
            game.addPlayer(player4, viewPlayer4);
            fail("Should throws IllegalStateException");
        }
        catch (IllegalStateException ex){
            assertEquals("game already started", ex.getMessage());
        }
    }

    @Test
    void removeSettedPlayer() {
        game.addPlayer(player1, viewPlayer1);
        assertEquals(1,game.getPlayers().size());
        assertEquals(game.getPlayers().get(0), player1);
        game.addPlayer(player2, viewPlayer2);
        game.addPlayer(player3, viewPlayer3);
        game.removeSettedPlayer("due");
        assertEquals(2,game.getPlayers().size());
        assertEquals(game.getPlayers().get(0), player1);
        assertEquals(game.getPlayers().get(1), player3);

    }

    @Test
    void removeConfigPlayer() {
        assertEquals(0,game.getConfigPlayer());
        game.addPlayer(player1, viewPlayer1);
        assertFalse(game.addPlayer(player1, viewPlayer1));
        assertEquals(1,game.getConfigPlayer());
        game.removeConfigPlayer();
        assertEquals(0,game.getConfigPlayer());
    }

    @Test
    void newNickName() {
        game.addPlayer(player1, viewPlayer1);
        game.addPlayer(player2, viewPlayer2);
        assertEquals(2,game.getPlayers().size());
        assertEquals(game.getPlayers().get(0), player1);
        assertEquals(game.getPlayers().get(1), player2);
        assertFalse(game.addPlayer(player2, viewPlayer2));
        assertEquals(1,game.getConfigPlayer());
        assertTrue(game.newNickName(player3));
        assertEquals(0,game.getConfigPlayer());
        assertEquals(3,game.getPlayers().size());
        assertEquals(game.getPlayers().get(2), player3);

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
        assertFalse(game.isGameStarted());
        game.setGameStarted(true);
        assertTrue(game.isGameStarted());
    }

    @Test
    void setGameStarted() {
        assertFalse(game.isGameStarted());
        game.setGameStarted(true);
        assertTrue(game.isGameStarted());
    }

    @Test
    void setGameStatus() {
        assertThrows(NullPointerException.class , () -> game.setGameStatus(null));
        assertNull(game.getGameStatus());
        game.setGameStatus(Response.BUILD);
        assertEquals(Response.BUILD,game.getGameStatus());
    }

    @Test
    void placeWorkersOnMap() {
        Integer[] tile1 = {0, 0};
        Integer[] tile2 = {1, 1};
        game.setCurrentPlayer(player4);
        game.placeWorkersOnMap(tile1, tile2);
        assertEquals(player4.getWorkers().get(0).getBoardPosition(), game.getGameMap().getMap().get(0));
        assertEquals(player4.getWorkers().get(1).getBoardPosition(), game.getGameMap().getMap().get(16));
        assertEquals(game.getGameMap().getMap().get(0).getWorker(), player4.getWorkers().get(0));
        assertEquals(game.getGameMap().getMap().get(16).getWorker(), player4.getWorkers().get(1));
        assertTrue(game.getGameMap().getMap().get(0).hasPlayer());
        assertTrue(game.getGameMap().getMap().get(16).hasPlayer());
        assertEquals(game.getGameMap().getMap().get(16).getPlayer(), player4);
        assertEquals(game.getGameMap().getMap().get(16).getPlayer(), player4);
    }

    @Test
    void pickChallenger() {
        game.addPlayer(player1, viewPlayer1);
        game.addPlayer(player2, viewPlayer2);
        game.addPlayer(player3, viewPlayer3);
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
        game.addPlayer(player1, viewPlayer1);
        game.addPlayer(player2, viewPlayer2);
        game.addPlayer(player3, viewPlayer3);
        game.createQueue();
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(1));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(0));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(2));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(1));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(0));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(2));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(1));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(0));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(2));
    }

    @Test
    void checkCardIntoDeck() {
        assertFalse(game.checkCardIntoDeck("sbagliata"));
        assertTrue(game.checkCardIntoDeck("apollo"));
        assertTrue(game.checkCardIntoDeck("zeus"));
    }

    @Test
    void assignCard() {
        assertThrows(NullPointerException.class , () -> game.assignCard(null));
        game.addPlayer(player1, viewPlayer1);
        game.addPlayer(player2, viewPlayer2);
        game.addPlayer(player3, viewPlayer3);
        assertFalse(game.assignCard("sbagliata"));
        game.setCurrentPlayer(game.getPlayers().get(0));
        assertFalse(game.assignCard("sbagliata"));
        assertTrue(game.assignCard("pan"));
    }

    @Test
    void getGameStatus() {
        assertNull(game.getGameStatus());
        game.setGameStatus(Response.BUILD);
        assertEquals(Response.BUILD,game.getGameStatus());
    }

    @Test
    void getGameID() {
        assertEquals("G01",game.getGameID());
    }

    @Test
    void getAvailableCards() {
        ArrayList<String> cardstring = new ArrayList<>();
        cardstring.add("apollo");
        cardstring.add("atlas");
        assertNull(game.getAvailableCards());
        game.setAvailableCards(cardstring);
        assertEquals("apollo",game.getAvailableCards().get(0));
    }

    @Test
    void setAvailableCards() {
        ArrayList<String> cardstring = new ArrayList<>();
        cardstring.add("apollo");
        cardstring.add("atlas");
        game.setAvailableCards(cardstring);
        assertEquals(2,game.getAvailableCards().size());
        assertEquals("apollo",game.getAvailableCards().get(0));
        assertEquals("atlas",game.getAvailableCards().get(1));
    }

    @Test
    void removeCard() {
        ArrayList<String> cardstring = new ArrayList<>();
        cardstring.add("apollo");
        cardstring.add("atlas");
        game.setAvailableCards(cardstring);
        assertEquals(2,game.getAvailableCards().size());
        game.removeCard("apollo");
        assertEquals(1,game.getAvailableCards().size());
        assertEquals("atlas",game.getAvailableCards().get(0));

    }

    @Test
    void getCardFromAvailableCards() {
        ArrayList<String> cardstring = new ArrayList<>();
        cardstring.add("apollo");
        cardstring.add("atlas");
        cardstring.add("pan");
        game.setAvailableCards(cardstring);
        assertEquals("apollo",game.getCardFromAvailableCards("apollo"));
        assertEquals("atlas",game.getCardFromAvailableCards("atlas"));
        assertEquals("pan",game.getCardFromAvailableCards("pan"));
        assertNull(game.getCardFromAvailableCards("chronus"));
        assertNull(game.getCardFromAvailableCards(""));
        assertNull(game.getCardFromAvailableCards("Nonesiste"));
    }

    @Test
    void getCardFromDeck() {
        assertEquals("apollo",game.getCardFromDeck("apollo").getName());
        assertNull(game.getCardFromDeck("Bhu"));
    }

    @Test
    void pickPlayer() {
        game.addPlayer(player1, viewPlayer1);
        game.addPlayer(player2, viewPlayer2);
        game.addPlayer(player3, viewPlayer3);
        game.createQueue();
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(1));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(0));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(2));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(1));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(0));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(2));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(1));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(0));
        game.pickPlayer();
        assertEquals(game.getCurrentPlayer(), game.getPlayers().get(2));
    }

    @Test
    void allWorkersPlaced() {
        Integer[] tile1 = {0, 0};
        Integer[] tile2 = {1, 1};
        Integer[] tile3 = {2, 2};
        Integer[] tile4 = {3, 3};
        Integer[] tile5 = {4, 4};
        Integer[] tile6 = {2, 4};
        game.addPlayer(player1, viewPlayer1);
        game.addPlayer(player2, viewPlayer2);
        game.addPlayer(player3, viewPlayer3);
        game.setCurrentPlayer(player1);
        game.placeWorkersOnMap(tile1, tile2);
        game.setCurrentPlayer(player2);
        game.placeWorkersOnMap(tile3, tile4);
        assertFalse(game.allWorkersPlaced());
        game.setCurrentPlayer(player3);
        game.placeWorkersOnMap(tile5, tile6);
        assertTrue(game.allWorkersPlaced());
    }

    @Test
    void assignPermanentConstraint() {
        player1.setPower(cardHera);
        game.addPlayer(player1, viewPlayer1);
        game.addPlayer(player2, viewPlayer2);
        game.addPlayer(player3, viewPlayer3);
        game.assignPermanentConstraint();
        assertEquals(0,player1.getConstraint().size());
        assertEquals(1,player2.getConstraint().size());
        assertEquals("hera",player2.getConstraint().get(0).getName());
        assertEquals(1,player3.getConstraint().size());
        assertEquals("hera",player3.getConstraint().get(0).getName());
    }
}
