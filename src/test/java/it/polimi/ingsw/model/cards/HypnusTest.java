package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HypnusTest {

    Player player1, player2;
    Card cardHypn;
    GameMap gameMap;
    List<Directions> directions;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardHypn = CardLoader.loadCards().get("hypnus");
        player1.setPower(cardHypn);
        gameMap = new GameMap();
        gameMap.getMap().get(22).setMovement(player1,player1.getWorkers().get(0));
        player1.getWorkers().get(0).setBoardPosition(gameMap.getMap().get(22));
        gameMap.getMap().get(4).setMovement(player1,player1.getWorkers().get(1));
        player1.getWorkers().get(1).setBoardPosition(gameMap.getMap().get(4));
        gameMap.getMap().get(21).setMovement(player2,player2.getWorkers().get(0));
        player2.getWorkers().get(0).setBoardPosition(gameMap.getMap().get(21));
        gameMap.getMap().get(18).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(gameMap.getMap().get(18));
        player1.selectCurrentWorker(gameMap, "worker1");
        directions = player1.findWorkerMove(gameMap, player1.getWorkers().get(0));
    }

    @Test
    void canMove() {
        assertThrows(NullPointerException.class , () -> cardHypn.canMove(null, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardHypn.canMove(player1, null));

        assertTrue(cardHypn.canMove(player1, player1.getCurrentWorker()));
        gameMap.getMap().get(22).addBuildingLevel();
        assertEquals(1,gameMap.getMap().get(22).getBuildingLevel());
        assertFalse(cardHypn.canMove(player1, player1.getCurrentWorker()));

    }
}