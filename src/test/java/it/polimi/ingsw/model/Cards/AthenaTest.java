package it.polimi.ingsw.model.Cards;

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

class AthenaTest {

    Player player1, player2;
    Card cardAthe;
    GameMap gameMap;
    ArrayList<Directions> directions;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardAthe = CardLoader.loadCards().get("Athena");
        player1.setPower(cardAthe);
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
        gameMap.getGameMap().get(23).addBuildingLevel();
        directions = player1.findWorkerMove(gameMap, player1.getWorkers().get(0));
    }

    @Test
    void executeWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardAthe.executeWorkerMove(null, Directions.NORD, player1));
        assertThrows(NullPointerException.class , () -> cardAthe.executeWorkerMove(gameMap, null, player1));
        assertThrows(NullPointerException.class , () -> cardAthe.executeWorkerMove(gameMap, Directions.NORD, null));

        assertEquals(gameMap.getGameMap().get(23).getBuildingLevel(), 1);
        assertEquals(cardAthe.executeWorkerMove(gameMap, Directions.OVEST, player1), Response.MOVED);
        assertEquals(cardAthe.executeWorkerMove(gameMap, Directions.NORD_EST, player1), Response.ASSIGNCONSTRAINT);

    }

    @Test
    void eliminateInvalidMove() {
        assertEquals(directions.size(), 7);
        assertEquals(cardAthe.eliminateInvalidMove(gameMap, player1.getCurrentWorker(), directions).size(), 6);
    }
}