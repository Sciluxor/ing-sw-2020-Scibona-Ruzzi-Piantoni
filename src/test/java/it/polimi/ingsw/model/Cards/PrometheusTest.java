package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PrometheusTest {

    Player player1, player2;
    Card cardPro;
    GameMap gameMap;
    ArrayList<Directions> directions;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardPro = CardLoader.loadCards().get("Prometheus");
        player1.setPower(cardPro);
        gameMap = new GameMap();
        gameMap.getGameMap().get(22).setMovement(player1,player1.getWorkers().get(0));
        player1.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(22));
        gameMap.getGameMap().get(4).setMovement(player1,player1.getWorkers().get(1));
        player1.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(4));
        gameMap.getGameMap().get(21).setMovement(player2,player2.getWorkers().get(0));
        player2.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(21));
        gameMap.getGameMap().get(18).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(18));
        player1.selectCurrentWorker(gameMap, "worker1");

        directions = player1.findWorkerMove(gameMap, player1.getWorkers().get(0));
    }

    @Test
    void getFirstAction() {
        assertEquals(cardPro.getFirstAction(), Response.BUILDBEFORE);

    }

    @Test
    void findWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardPro.findWorkerMove(null, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardPro.findWorkerMove(gameMap, null));

        assertEquals(player1.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(22));
        assertEquals(cardPro.findWorkerMove(gameMap, player1.getWorkers().get(0)).size(), 7);
        cardPro.executeBuild(gameMap,Building.LVL1, Directions.NORD_EST, player1.getCurrentWorker());
        assertEquals(cardPro.findWorkerMove(gameMap, player1.getWorkers().get(0)).size(), 6);
    }

    @Test
    void executeWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardPro.executeWorkerMove(null, Directions.OVEST, player1));
        assertThrows(NullPointerException.class , () -> cardPro.executeWorkerMove(gameMap, null, player1));
        assertThrows(NullPointerException.class , () -> cardPro.executeWorkerMove(gameMap, Directions.OVEST, null));

        assertEquals(cardPro.executeWorkerMove(gameMap, Directions.OVEST, player1), Response.MOVED);
    }

    @Test
    void executeBuild() {
        assertThrows(NullPointerException.class , () -> cardPro.executeBuild(null, Building.LVL1, Directions.NORD, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardPro.executeBuild(gameMap, null, Directions.NORD, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardPro.executeBuild(gameMap, Building.LVL1, null, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardPro.executeBuild(gameMap, Building.LVL1, Directions.NORD, null));

        assertEquals(cardPro.executeBuild(gameMap,Building.LVL2, Directions.NORD_EST, player1.getCurrentWorker()), Response.NOTBUILD);
        assertEquals(cardPro.executeBuild(gameMap,Building.LVL1, Directions.NORD_EST, player1.getCurrentWorker()), Response.BUILDEDBEFORE);
        assertEquals(cardPro.executeBuild(gameMap,Building.LVL2, Directions.NORD, player1.getCurrentWorker()), Response.NOTBUILD);
        assertEquals(cardPro.executeBuild(gameMap,Building.LVL1, Directions.NORD, player1.getCurrentWorker()), Response.BUILD);


    }
}