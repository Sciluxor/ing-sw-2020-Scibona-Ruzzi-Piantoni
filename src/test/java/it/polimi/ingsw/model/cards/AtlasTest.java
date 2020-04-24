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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AtlasTest {

    Player player1, player2;
    Card cardAtlas;
    Worker worker1,worker2;
    GameMap gameMap;
    List<Directions> directions;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardAtlas = CardLoader.loadCards().get("atlas");
        player1.setPower(cardAtlas);
        worker1 = new Worker(WorkerName.WORKER1);
        worker2 = new Worker(WorkerName.WORKER2);
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
    void executeBuild() {
        assertThrows(NullPointerException.class , () -> cardAtlas.executeBuild(null, Building.LVL1, Directions.NORD, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardAtlas.executeBuild(gameMap, null, Directions.NORD, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardAtlas.executeBuild(gameMap, Building.LVL1, null, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardAtlas.executeBuild(gameMap, Building.LVL1, Directions.NORD, null));

        assertEquals(Response.BUILD,cardAtlas.executeBuild(gameMap, Building.LVL1, Directions.NORD, player1.getCurrentWorker()));
        assertEquals(gameMap.getModifiedSquare().get(0), gameMap.getMap().get(23));
        assertEquals(Response.NOTBUILD,cardAtlas.executeBuild(gameMap, Building.LVL2, Directions.OVEST, player1.getCurrentWorker()));
        assertEquals(Response.NOTBUILD,cardAtlas.executeBuild(gameMap, Building.LVL3, Directions.SUD_OVEST, player1.getCurrentWorker()));
        assertEquals(Response.BUILD,cardAtlas.executeBuild(gameMap, Building.DOME, Directions.SUD, player1.getCurrentWorker()));
        assertEquals(gameMap.getModifiedSquare().get(0), gameMap.getMap().get(11));
        assertEquals(Response.BUILD,cardAtlas.executeBuild(gameMap, Building.LVL1, Directions.OVEST, player1.getCurrentWorker()));
        assertEquals(gameMap.getModifiedSquare().get(0), gameMap.getMap().get(13));
        assertEquals(Response.BUILD,cardAtlas.executeBuild(gameMap, Building.LVL1, Directions.SUD_OVEST, player1.getCurrentWorker()));
        assertEquals(gameMap.getModifiedSquare().get(0), gameMap.getMap().get(12));
        assertEquals(Response.BUILD,cardAtlas.executeBuild(gameMap, Building.LVL2, Directions.OVEST, player1.getCurrentWorker()));
        assertEquals(gameMap.getModifiedSquare().get(0), gameMap.getMap().get(13));
        assertEquals(Response.BUILD,cardAtlas.executeBuild(gameMap, Building.LVL2, Directions.SUD_OVEST, player1.getCurrentWorker()));
        assertEquals(gameMap.getModifiedSquare().get(0), gameMap.getMap().get(12));
        assertEquals(Response.BUILD,cardAtlas.executeBuild(gameMap, Building.LVL3, Directions.OVEST, player1.getCurrentWorker()));
        assertEquals(gameMap.getModifiedSquare().get(0), gameMap.getMap().get(13));
        assertEquals(Response.BUILD,cardAtlas.executeBuild(gameMap, Building.DOME, Directions.OVEST, player1.getCurrentWorker()));
        assertEquals(gameMap.getModifiedSquare().get(0), gameMap.getMap().get(13));
        assertEquals(Response.BUILD,cardAtlas.executeBuild(gameMap, Building.DOME, Directions.SUD_OVEST, player1.getCurrentWorker()));
        assertEquals(gameMap.getModifiedSquare().get(0), gameMap.getMap().get(12));

    }
}