package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Map.Directions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerNameTest {

    @Test
    void parseInput() {
        assertEquals(WorkerName.WORKER1,WorkerName.parseInput("worKer1"));
        assertEquals(WorkerName.WORKER2,WorkerName.parseInput("Worker2"));

        assertThrows(IllegalArgumentException.class,() ->  WorkerName.parseInput("worker"));
    }
}