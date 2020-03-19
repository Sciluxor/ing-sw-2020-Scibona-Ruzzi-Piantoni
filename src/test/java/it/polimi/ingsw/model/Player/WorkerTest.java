package it.polimi.ingsw.model.Player;


import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Worker;
import it.polimi.ingsw.model.Player.WorkerName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {
    Worker worker;
    Square square, square1, square2;

    @Test
    void getBoardPosition() {
         worker = new Worker(WorkerName.WORKER1, square, square1, square2);
        assert (worker.getBoardPosition() != null);
    }

    @Test
    void setBoardPosition() {
    }

    @Test
    void getPreviousBoardPosition() {
    }

    @Test
    void setPreviousBoardPosition() {
    }

    @Test
    void getPreviousBuildPosition() {
    }

    @Test
    void setPreviousBuildPosition() {
    }

    @Test
    void getName() {
        worker = new Worker(WorkerName.WORKER1, square, square1, square2);
        assert (worker.getName() != null);
    }
}