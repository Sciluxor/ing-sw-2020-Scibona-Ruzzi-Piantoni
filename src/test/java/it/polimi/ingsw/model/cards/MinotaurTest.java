package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MinotaurTest {

    Player player1, player2;
    Card cardMino, cardAtla;
    GameMap gameMap;
    ArrayList<Directions> directions;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardMino = CardLoader.loadCards().get("minotaur");
        cardAtla = CardLoader.loadCards().get("atlas");
        player1.setPower(cardMino);
        player2.setPower(cardAtla);
        gameMap = new GameMap();
        gameMap.getGameMap().get(22).setMovement(player1,player1.getWorkers().get(0));
        player1.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(22));
        gameMap.getGameMap().get(5).setMovement(player1,player1.getWorkers().get(1));
        player1.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(5));
        gameMap.getGameMap().get(21).setMovement(player2,player2.getWorkers().get(0));
        player2.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(21));
        gameMap.getGameMap().get(18).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(18));
        player1.selectCurrentWorker(gameMap, "worker1");
        player2.selectCurrentWorker(gameMap, "worker1");
        directions = player1.findWorkerMove(gameMap, player1.getWorkers().get(0));
    }

    @Test
    void findWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardMino.findWorkerMove(null, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardMino.findWorkerMove(gameMap, null));

        player2.selectCurrentWorker(gameMap, "worker2");

        assertEquals(8,cardMino.findWorkerMove(gameMap, player1.getCurrentWorker()).size());
        assertEquals(7,cardMino.findWorkerMove(gameMap, player2.getWorkers().get(1)).size());
    }

    @Test
    void executeWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardMino.executeWorkerMove(null, Directions.OVEST, player1));
        assertThrows(NullPointerException.class , () -> cardMino.executeWorkerMove(gameMap, null, player1));
        assertThrows(NullPointerException.class , () -> cardMino.executeWorkerMove(gameMap, Directions.OVEST, null));

        assertEquals(Response.MOVED,cardMino.executeWorkerMove(gameMap, Directions.NORD, player1));
        assertEquals(player1.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(23));
        assertEquals(gameMap.getModifiedSquare().get(0), gameMap.getGameMap().get(22));
        assertEquals(gameMap.getModifiedSquare().get(1), gameMap.getGameMap().get(23));
        assertEquals(Response.MOVED,cardMino.executeWorkerMove(gameMap, Directions.SUD, player1));
        assertEquals(player1.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(22));
        assertEquals(gameMap.getModifiedSquare().get(0), gameMap.getGameMap().get(23));
        assertEquals(gameMap.getModifiedSquare().get(1), gameMap.getGameMap().get(22));
        assertEquals(Response.MOVED,cardMino.executeWorkerMove(gameMap, Directions.EST, player1));
        assertEquals(player1.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(21));
        assertEquals(player2.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(20));
        assertEquals(gameMap.getModifiedSquare().get(0), gameMap.getGameMap().get(22));
        assertEquals(gameMap.getModifiedSquare().get(1), gameMap.getGameMap().get(21));
        assertEquals(gameMap.getModifiedSquare().get(2), gameMap.getGameMap().get(20));

    }
}