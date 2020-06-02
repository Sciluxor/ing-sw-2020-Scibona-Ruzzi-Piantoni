package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.map.MapLoader;
import it.polimi.ingsw.model.map.Square;
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

class SimplifiedGameTest {
    List<VirtualView> players;
    ClientHandler connection1, connection2, connection3;
    VirtualView viewPlayer1, viewPlayer2, viewPlayer3, viewPlayer4;
    Player player1, player2, player3;
    Card cardApollo, cardAthena, cardAtlas, cardHera, cardHypnus;
    SimplifiedGame simplifiedGame;
    Map<String, Card> deck;
    GameMap gameMap;
    ArrayList<String> playersName = new ArrayList<>();
    ArrayList<Color> colors = new ArrayList<>();

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
        simplifiedGame = new SimplifiedGame(3);
        gameMap = new GameMap();

        playersName= new ArrayList<>();
        playersName.add(player1.getNickName());
        playersName.add(player2.getNickName());
        playersName.add(player3.getNickName());
        colors= new ArrayList<>();
        colors.add(Color.PURPLE);
        colors.add(Color.WHITE);
        colors.add(Color.BLUE);

    }

    @Test
    void hasStopper() {
        assertFalse(simplifiedGame.hasStopper());
        simplifiedGame.setHasStopper(true);
        assertTrue(simplifiedGame.hasStopper());
    }


    @Test
    void getNumberOfPlayers() {
        assertEquals(3,simplifiedGame.getNumberOfPlayers());
        simplifiedGame= new SimplifiedGame(2);
        assertEquals(2,simplifiedGame.getNumberOfPlayers());
    }

    @Test
    void getPlayers() {
        assertEquals(0,simplifiedGame.getPlayers().size());

        simplifiedGame.initPlayers(player1.getNickName(),playersName,colors);
        assertEquals(3,simplifiedGame.getPlayers().size());
        assertEquals(simplifiedGame.getPlayers().get(0).getNickName(), player1.getNickName());
        assertEquals(simplifiedGame.getPlayers().get(1).getNickName(), player2.getNickName());
        assertEquals(simplifiedGame.getPlayers().get(2).getNickName(), player3.getNickName());

        colors.clear();

        assertThrows(IllegalStateException.class, () -> simplifiedGame.initPlayers(player1.getNickName(),playersName,colors));
    }

    @Test
    void hasWinner() {
        assertFalse(simplifiedGame.hasWinner());
        simplifiedGame.setHasWinner(true);
        assertTrue(simplifiedGame.hasWinner());
    }

    @Test
    void getDeck() {
        assertEquals(14,simplifiedGame.getDeck().size());
        assertEquals("apollo",simplifiedGame.getDeck().get("apollo").getName());
    }


    @Test
    void setWinner() {
        simplifiedGame.setWinner(player1);
        assertEquals(player1.getNickName(),simplifiedGame.getWinner().getNickName());
    }

    @Test
    void getCoordinatesFromTile() {

        assertEquals(0,simplifiedGame.getCoordinatesFromTile(2)[0]);
        assertEquals(1,simplifiedGame.getCoordinatesFromTile(2)[1]);

        assertEquals(2,simplifiedGame.getCoordinatesFromTile(20)[0]);
        assertEquals(3,simplifiedGame.getCoordinatesFromTile(20)[1]);
    }

    @Test
    void initPlayers() {
        simplifiedGame.initPlayers(player1.getNickName(),playersName,colors);

        assertEquals(player1.getNickName(),simplifiedGame.getClientPlayer().getNickName());
    }

    @Test
    void copySquare() {
        simplifiedGame.initPlayers(player1.getNickName(),playersName,colors);


        Square oldSquare = simplifiedGame.getGameMap().getMap().get(0);
        Square newSquare = MapLoader.loadMap().get(0);
        newSquare.setBuildingLevel(3);
        newSquare.setBuilding(Building.LVL3);
        newSquare.setHasPlayer(true);
        newSquare.setPlayer(player1);
        newSquare.setWorker(player1.getWorkers().get(0));
        newSquare.getWorker().setBoardPosition(simplifiedGame.getGameMap().getMap().get(1));
        player1.getWorkers().get(0).setBoardPosition(simplifiedGame.getGameMap().getMap().get(1));

        simplifiedGame.getClientPlayer().getWorkers().get(0).setBoardPosition(simplifiedGame.getGameMap().getMap().get(1));
        assertEquals(0,oldSquare.getBuildingLevel());
        assertEquals(Building.GROUND,oldSquare.getBuilding());
        assertFalse(oldSquare.hasPlayer());
        assertEquals(2,player1.getWorkers().get(0).getBoardPosition().getTile());

        simplifiedGame.copySquare(oldSquare,newSquare);

        assertEquals(3,oldSquare.getBuildingLevel());
        assertEquals(Building.LVL3,oldSquare.getBuilding());
        assertTrue(oldSquare.hasPlayer());
        assertEquals(player1.getNickName(),oldSquare.getPlayer().getNickName());
        assertEquals(player1.getWorkers().get(0).getName(),oldSquare.getWorker().getName());
        assertEquals(1,oldSquare.getWorker().getBoardPosition().getTile());
    }

    @Test
    void getAvailableCards() {
        ArrayList<String> cardString = new ArrayList<>();
        cardString.add("apollo");
        cardString.add("atlas");
        cardString.add("athena");
        simplifiedGame.setAvailableCards(cardString);
        assertEquals(3,simplifiedGame.getAvailableCards().size());
        assertEquals("apollo",simplifiedGame.getAvailableCards().get(0));
        assertEquals("atlas",simplifiedGame.getAvailableCards().get(1));
        assertEquals("athena",simplifiedGame.getAvailableCards().get(2));
    }

    @Test
    void removeCard() {
        ArrayList<String> cardstring = new ArrayList<>();
        cardstring.add("apollo");
        cardstring.add("atlas");
        simplifiedGame.setAvailableCards(cardstring);
        assertEquals(2,simplifiedGame.getAvailableCards().size());
        simplifiedGame.removeCard("apollo");
        assertEquals(1,simplifiedGame.getAvailableCards().size());
        assertEquals("atlas",simplifiedGame.getAvailableCards().get(0));
    }

    @Test
    void getCurrentPlayer() {
        simplifiedGame.initPlayers(player1.getNickName(),playersName,colors);

        assertThrows(NullPointerException.class , () -> simplifiedGame.setCurrentPlayer(null));
        assertNull(simplifiedGame.getCurrentPlayer());
        simplifiedGame.setCurrentPlayer(player3.getNickName());
        assertEquals(simplifiedGame.getCurrentPlayer().getNickName(), player3.getNickName());
    }

    @Test
    void getGameMap() {
        assertNotNull(simplifiedGame.getGameMap());
    }

    @Test
    void isGameStarted() {
        assertFalse(simplifiedGame.isGameStarted());
        simplifiedGame.setGameStarted(true);
        assertTrue(simplifiedGame.isGameStarted());
    }

    @Test
    void placeWorkersOnMap() {
        simplifiedGame.initPlayers(player1.getNickName(),playersName,colors);
        simplifiedGame.setCurrentPlayer(player3.getNickName());
        simplifiedGame.placeWorkersOnMap(1,17);
        assertEquals(simplifiedGame.getGameMap().getMap().get(0).getWorker().getBoardPosition(), simplifiedGame.getGameMap().getMap().get(0));
        assertEquals(simplifiedGame.getGameMap().getMap().get(16).getWorker().getBoardPosition(), simplifiedGame.getGameMap().getMap().get(16));
        assertEquals(simplifiedGame.getGameMap().getMap().get(0).getWorker().getName(), player3.getWorkers().get(0).getName());
        assertEquals(simplifiedGame.getGameMap().getMap().get(16).getWorker().getName(), player3.getWorkers().get(1).getName());
        assertTrue(simplifiedGame.getGameMap().getMap().get(0).hasPlayer());
        assertTrue(simplifiedGame.getGameMap().getMap().get(16).hasPlayer());
        assertEquals(simplifiedGame.getGameMap().getMap().get(16).getPlayer().getNickName(), player3.getNickName());
        assertEquals(simplifiedGame.getGameMap().getMap().get(16).getPlayer().getNickName(), player3.getNickName());

        assertThrows(IllegalStateException.class, () -> simplifiedGame.placeWorkersOnMap(0,26));
        assertThrows(IllegalStateException.class, () -> simplifiedGame.placeWorkersOnMap(2,17));
    }

    @Test
    void removePlayerLose() {
        simplifiedGame.initPlayers(player1.getNickName(),playersName,colors);
        simplifiedGame.setCurrentPlayer(player3.getNickName());
        simplifiedGame.placeWorkersOnMap(1,17);

        assertTrue(simplifiedGame.getGameMap().getMap().get(0).hasPlayer());
        assertTrue(simplifiedGame.getGameMap().getMap().get(16).hasPlayer());
        simplifiedGame.removePlayerLose();

        assertFalse(simplifiedGame.getGameMap().getMap().get(0).hasPlayer());
        assertFalse(simplifiedGame.getGameMap().getMap().get(16).hasPlayer());
    }

    @Test
    void getGameStatus() {
        assertNull(simplifiedGame.getGameStatus());
        simplifiedGame.setGameStatus(Response.BUILD);
        assertEquals(Response.BUILD,simplifiedGame.getGameStatus());
    }

    @Test
    void setGameID() {
        simplifiedGame.setGameID("GID00");
        assertEquals("GID00",simplifiedGame.getGameID());
    }
}