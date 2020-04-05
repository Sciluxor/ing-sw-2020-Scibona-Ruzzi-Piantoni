package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.MapLoader;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.TurnStatus;
import it.polimi.ingsw.model.Player.Worker;
import it.polimi.ingsw.model.Player.WorkerName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ApolloTest {

    Player player1, player2;
    Card cardApo, cardAthe;
    GameMap gameMap;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardApo = CardLoader.loadCards().get("Apollo");
        cardAthe = CardLoader.loadCards().get("Athena");
        player1.setPower(cardApo);
        player2.setPower(cardAthe);
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
    }

    @Test
    void findWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardApo.findWorkerMove(null, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardApo.findWorkerMove(gameMap, null));

        assertEquals(cardApo.findWorkerMove(gameMap, player1.getCurrentWorker()).size(), 8);
    }

    @Test
    void executeWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardApo.executeWorkerMove(null, Directions.NORD, player1));
        assertThrows(NullPointerException.class , () -> cardApo.executeWorkerMove(gameMap, null, player1));
        assertThrows(NullPointerException.class , () -> cardApo.executeWorkerMove(gameMap, Directions.NORD, null));

        assertEquals(player1.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(22));
        assertEquals(player2.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(21));
        player1.executeWorkerMove(gameMap, Directions.EST);
        assertEquals(player1.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(21));
        assertEquals(player2.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(22));
        assertEquals(gameMap.getModifiedSquare().get(0),gameMap.getGameMap().get(22));
        assertEquals(gameMap.getModifiedSquare().get(1),gameMap.getGameMap().get(21));
    }
}