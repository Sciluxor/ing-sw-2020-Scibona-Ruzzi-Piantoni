package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.MapLoader;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Worker;
import it.polimi.ingsw.model.Player.WorkerName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ApolloTest {
    Card cardAp;
    Worker worker1 = new Worker(WorkerName.WORKER1);
    Worker worker2 = new Worker(WorkerName.WORKER2);
    ArrayList<Square> squares;

    @Test
    void swapWorker() {
        cardAp = CardLoader.loadCards().get("Athena");
        squares = MapLoader.loadMap();
        worker1.setBoardPosition(squares.get(0));
        worker2.setBoardPosition(squares.get(1));
        assertEquals(worker1.getBoardPosition(), squares.get(0));
        assertEquals(worker2.getBoardPosition(), squares.get(1));
        cardAp.swapWorker(worker1, worker2);
        assertEquals(worker1.getBoardPosition(), squares.get(1));
        assertEquals(worker2.getBoardPosition(), squares.get(0));
    }
}