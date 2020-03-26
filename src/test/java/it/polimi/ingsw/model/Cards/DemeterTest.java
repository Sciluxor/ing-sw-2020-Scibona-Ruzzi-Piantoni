package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.TurnStatus;
import it.polimi.ingsw.model.Player.Worker;
import it.polimi.ingsw.model.Player.WorkerName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DemeterTest {

    Player player1, player2;
    Card cardDeme;
    Worker worker1,worker2;
    GameMap gameMap;
    ArrayList<Directions> directions;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer", TurnStatus.PREGAME);
        player2 = new Player("BadPlayer", TurnStatus.PREGAME);
        cardDeme = CardLoader.loadCards().get("Demeter");
        player1.setPower(cardDeme);
        worker1 = new Worker(WorkerName.WORKER1);
        worker2 = new Worker(WorkerName.WORKER2);
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
    void findPossibleBuild() {
        assertThrows(NullPointerException.class , () -> cardDeme.findPossibleBuild(null, worker1));
        assertThrows(NullPointerException.class , () -> cardDeme.findPossibleBuild(gameMap, null));

        assertEquals(cardDeme.findPossibleBuild(gameMap, player1.getCurrentWorker()).size(), 7);
        cardDeme.executeBuild(gameMap, Building.LVL1, Directions.NORD, player1.getCurrentWorker());
        assertEquals(cardDeme.findPossibleBuild(gameMap, player1.getCurrentWorker()).size(), 6);

    }

    @Test
    void executeBuild() {
        assertThrows(NullPointerException.class , () -> cardDeme.executeBuild(null, Building.LVL1, Directions.OVEST, worker1));
        assertThrows(NullPointerException.class , () -> cardDeme.executeBuild(gameMap, null, Directions.OVEST, worker1));
        assertThrows(NullPointerException.class , () -> cardDeme.executeBuild(gameMap, Building.LVL1, null, worker1));
        assertThrows(NullPointerException.class , () -> cardDeme.executeBuild(gameMap, Building.LVL1, Directions.OVEST, null));

        assertEquals(cardDeme.executeBuild(gameMap, Building.LVL2, Directions.NORD, player1.getCurrentWorker()), Response.NOTBUILD);
        assertEquals(cardDeme.executeBuild(gameMap, Building.LVL1, Directions.NORD, player1.getCurrentWorker()), Response.NEWBUILD);
        assertEquals(cardDeme.executeBuild(gameMap, Building.LVL2, Directions.NORD_EST, player1.getCurrentWorker()), Response.NOTBUILD);
        assertEquals(cardDeme.executeBuild(gameMap, Building.LVL1, Directions.OVEST, player1.getCurrentWorker()), Response.BUILD);
    }
}