package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HephaestusTest {

    Player player1, player2;
    Card cardHeph;
    GameMap gameMap;
    List<Directions> directions;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardHeph = CardLoader.loadCards().get("hephaestus");
        player1.setPower(cardHeph);
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
    void findPossibleBuild() {
        assertThrows(NullPointerException.class , () -> cardHeph.findPossibleBuild(null, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardHeph.findPossibleBuild(gameMap, null));

        assertEquals(7,cardHeph.findPossibleBuild(gameMap, player1.getCurrentWorker()).size());
        cardHeph.executeBuild(gameMap, Building.LVL1, Directions.NORD, player1.getCurrentWorker());
        assertEquals(1,cardHeph.findPossibleBuild(gameMap, player1.getCurrentWorker()).size());

    }

    @Test
    void executeBuild() {
        assertThrows(NullPointerException.class , () -> cardHeph.executeBuild(null, Building.LVL1, Directions.OVEST, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardHeph.executeBuild(gameMap, null, Directions.OVEST, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardHeph.executeBuild(gameMap, Building.LVL1, null, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardHeph.executeBuild(gameMap, Building.LVL1, Directions.OVEST, null));

        assertEquals(Response.NOTBUILD,cardHeph.executeBuild(gameMap, Building.LVL2, Directions.NORD, player1.getCurrentWorker()));
        gameMap.getMap().get(13).setBuilding(Building.LVL2);
        assertEquals(Response.BUILD,cardHeph.executeBuild(gameMap, Building.LVL3, Directions.OVEST, player1.getCurrentWorker()));
        assertEquals(Response.NEWBUILD,cardHeph.executeBuild(gameMap, Building.LVL1, Directions.SUD_OVEST, player1.getCurrentWorker()));
        assertEquals(Response.NOTBUILD,cardHeph.executeBuild(gameMap, Building.LVL2, Directions.NORD_EST, player1.getCurrentWorker()));
        assertEquals(Response.BUILD,cardHeph.executeBuild(gameMap, Building.LVL1, Directions.SUD, player1.getCurrentWorker()));

    }
}