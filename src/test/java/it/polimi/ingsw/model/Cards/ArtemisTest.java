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

class ArtemisTest {

    Player player1, player2;
    Card cardArte;
    Worker worker1,worker2;
    GameMap gameMap;
    ArrayList<Directions> directions;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer", TurnStatus.PREGAME);
        player2 = new Player("BadPlayer", TurnStatus.PREGAME);
        cardArte = CardLoader.loadCards().get("Artemis");
        worker1 = new Worker(WorkerName.WORKER1);
        worker2 = new Worker(WorkerName.WORKER2);
        gameMap = new GameMap();
        player1.setPower(cardArte);
        player1.setCurrentWorker(player1.getWorkers().get(0));
        gameMap.getGameMap().get(22).setMovement(player1,player1.getWorkers().get(0));
        player1.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(22));
        gameMap.getGameMap().get(4).setMovement(player1,player1.getWorkers().get(1));
        player1.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(4));
        gameMap.getGameMap().get(21).setMovement(player2,player2.getWorkers().get(0));
        player2.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(21));
        gameMap.getGameMap().get(18).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(18));
        directions = player1.findWorkerMove(gameMap, player1.getWorkers().get(0));
    }

    @Test
    void findWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardArte.findWorkerMove(null, worker1));
        assertThrows(NullPointerException.class , () -> cardArte.findWorkerMove(gameMap, null));

        assertEquals(cardArte.executeWorkerMove(gameMap, Directions.OVEST, player1), Response.NEWMOVE);
        //assertEquals(cardArte.findWorkerMove(gameMap, player1.getWorkers().get(0)), cardArte.notPreviousMove(gameMap, player1.getWorkers().get(0)));


    }

    @Test
    void notPreviousMove() {
    }

    @Test
    void executeWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardArte.executeWorkerMove(null, Directions.OVEST, player1));
        assertThrows(NullPointerException.class , () -> cardArte.executeWorkerMove(gameMap, null, player1));
        assertThrows(NullPointerException.class , () -> cardArte.executeWorkerMove(gameMap, Directions.OVEST, null));

        assertEquals(cardArte.executeWorkerMove(gameMap, Directions.OVEST, player1), Response.NEWMOVE);
        assertEquals(cardArte.executeWorkerMove(gameMap, Directions.NORD, player1), Response.MOVED);
    }
}