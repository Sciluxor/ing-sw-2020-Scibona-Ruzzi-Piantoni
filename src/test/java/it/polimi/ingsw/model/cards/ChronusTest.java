package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.WorkerName;
import it.polimi.ingsw.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChronusTest {

    Player player1, player2;
    Card cardChro;
    Worker worker1,worker2;
    GameMap gameMap;
    ArrayList<Directions> directions;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardChro = CardLoader.loadCards().get("chronus");
        player1.setPower(cardChro);
        worker1 = new Worker(WorkerName.WORKER1);
        worker2 = new Worker(WorkerName.WORKER2);
        gameMap = new GameMap();
        gameMap.getGameMap().get(13).setMovement(player1,player1.getWorkers().get(0));
        player1.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(13));
        gameMap.getGameMap().get(4).setMovement(player1,player1.getWorkers().get(1));
        player1.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(4));
        gameMap.getGameMap().get(21).setMovement(player2,player2.getWorkers().get(0));
        player2.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(21));
        gameMap.getGameMap().get(18).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(18));
        player1.selectCurrentWorker(gameMap, "worker1");

        gameMap.getGameMap().get(23).setBuilding(Building.LVL1);
        gameMap.getGameMap().get(23).addBuildingLevel();
        gameMap.getGameMap().get(23).setBuilding(Building.LVL2);
        gameMap.getGameMap().get(23).addBuildingLevel();
        gameMap.getGameMap().get(23).setBuilding(Building.LVL3);
        gameMap.getGameMap().get(23).addBuildingLevel();
        gameMap.getGameMap().get(14).setBuilding(Building.LVL1);
        gameMap.getGameMap().get(14).addBuildingLevel();
        gameMap.getGameMap().get(14).setBuilding(Building.LVL2);
        gameMap.getGameMap().get(14).addBuildingLevel();


        directions = player1.findWorkerMove(gameMap, player1.getWorkers().get(0));
    }

    @Test
    void checkVictory() {
        assertThrows(NullPointerException.class , () -> cardChro.checkVictory(null, player1));
        assertThrows(NullPointerException.class , () -> cardChro.checkVictory(gameMap, null));

        assertEquals(Response.MOVED,player1.executeWorkerMove(gameMap, Directions.NORD));
        assertEquals(Response.NOTWIN,cardChro.checkVictory(gameMap, player1));
        player1.getCurrentWorker().setBoardPosition(gameMap.getGameMap().get(14));
        assertEquals(Response.MOVED,player1.executeWorkerMove(gameMap, Directions.EST));
        assertEquals(Response.WIN,cardChro.checkVictory(gameMap, player1));
        assertEquals(Response.MOVED,player1.executeWorkerMove(gameMap, Directions.EST));
        assertEquals(Response.NOTWIN,cardChro.checkVictory(gameMap, player1));

        gameMap.getGameMap().get(0).setBuilding(Building.LVL1);
        gameMap.getGameMap().get(0).addBuildingLevel();
        gameMap.getGameMap().get(0).setBuilding(Building.LVL2);
        gameMap.getGameMap().get(0).addBuildingLevel();
        gameMap.getGameMap().get(0).setBuilding(Building.LVL3);
        gameMap.getGameMap().get(0).addBuildingLevel();
        gameMap.getGameMap().get(0).setBuilding(Building.DOME);
        gameMap.getGameMap().get(0).addBuildingLevel();
        gameMap.getGameMap().get(1).setBuilding(Building.LVL1);
        gameMap.getGameMap().get(1).addBuildingLevel();
        gameMap.getGameMap().get(1).setBuilding(Building.LVL2);
        gameMap.getGameMap().get(1).addBuildingLevel();
        gameMap.getGameMap().get(1).setBuilding(Building.LVL3);
        gameMap.getGameMap().get(1).addBuildingLevel();
        gameMap.getGameMap().get(1).setBuilding(Building.DOME);
        gameMap.getGameMap().get(1).addBuildingLevel();
        gameMap.getGameMap().get(2).setBuilding(Building.LVL1);
        gameMap.getGameMap().get(2).addBuildingLevel();
        gameMap.getGameMap().get(2).setBuilding(Building.LVL2);
        gameMap.getGameMap().get(2).addBuildingLevel();
        gameMap.getGameMap().get(2).setBuilding(Building.LVL3);
        gameMap.getGameMap().get(2).addBuildingLevel();
        gameMap.getGameMap().get(2).setBuilding(Building.DOME);
        gameMap.getGameMap().get(2).addBuildingLevel();
        gameMap.getGameMap().get(3).setBuilding(Building.LVL1);
        gameMap.getGameMap().get(3).addBuildingLevel();
        gameMap.getGameMap().get(3).setBuilding(Building.LVL2);
        gameMap.getGameMap().get(3).addBuildingLevel();
        gameMap.getGameMap().get(3).setBuilding(Building.LVL3);
        gameMap.getGameMap().get(3).addBuildingLevel();
        gameMap.getGameMap().get(3).setBuilding(Building.DOME);
        gameMap.getGameMap().get(3).addBuildingLevel();

        assertEquals(Response.NOTWIN,cardChro.checkVictory(gameMap, player1));

        gameMap.getGameMap().get(15).setBuilding(Building.LVL1);
        gameMap.getGameMap().get(15).addBuildingLevel();
        gameMap.getGameMap().get(15).setBuilding(Building.LVL2);
        gameMap.getGameMap().get(15).addBuildingLevel();
        gameMap.getGameMap().get(15).setBuilding(Building.LVL3);
        gameMap.getGameMap().get(15).addBuildingLevel();
        gameMap.getGameMap().get(15).setBuilding(Building.DOME);
        gameMap.getGameMap().get(15).addBuildingLevel();

        assertEquals(Response.BUILDWIN,cardChro.checkVictory(gameMap, player1));

    }
}