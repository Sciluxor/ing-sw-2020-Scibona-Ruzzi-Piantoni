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

class MinotaurTest {

    Player player1, player2;
    Card cardMino, cardAtla;
    Worker worker1,worker2;
    GameMap gameMap;
    ArrayList<Directions> directions;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer", TurnStatus.PREGAME);
        player2 = new Player("BadPlayer", TurnStatus.PREGAME);
        cardMino = CardLoader.loadCards().get("Minotaur");
        cardAtla = CardLoader.loadCards().get("Atlas");
        player1.setPower(cardMino);
        player2.setPower(cardAtla);
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
        player2.selectCurrentWorker(gameMap, "worker1");
        directions = player1.findWorkerMove(gameMap, player1.getWorkers().get(0));
    }

    @Test
    void findWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardMino.findWorkerMove(null, worker1));
        assertThrows(NullPointerException.class , () -> cardMino.findWorkerMove(gameMap, null));

        assertEquals(cardMino.findWorkerMove(gameMap, player1.getCurrentWorker()).size(), 8);
    }

    @Test
    void executeWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardMino.executeWorkerMove(null, Directions.OVEST, player1));
        assertThrows(NullPointerException.class , () -> cardMino.executeWorkerMove(gameMap, null, player1));
        assertThrows(NullPointerException.class , () -> cardMino.executeWorkerMove(gameMap, Directions.OVEST, null));

        assertEquals(cardMino.executeWorkerMove(gameMap, Directions.NORD, player1), Response.MOVED);
        assertEquals(cardMino.executeWorkerMove(gameMap, Directions.SUD, player1), Response.MOVED);
        assertEquals(player1.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(22));
        //assertEquals(cardMino.executeWorkerMove(gameMap, Directions.EST, player1), Response.MOVED);
        //assertEquals(player1.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(21));
        //assertEquals(player2.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(22));

    }
}