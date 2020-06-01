package it.polimi.ingsw.model.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerNameTest {

    @Test
    void parseInput() {
        assertEquals(WorkerName.WORKER1,WorkerName.parseInput("worKer1"));
        assertEquals(WorkerName.WORKER2,WorkerName.parseInput("Worker2"));

        assertThrows(IllegalArgumentException.class,() ->  WorkerName.parseInput("worker"));
    }

    @Test
    void getNumberWorker(){
        assertEquals(1,WorkerName.getNumberWorker(WorkerName.WORKER1));
        assertEquals(2,WorkerName.getNumberWorker(WorkerName.WORKER2));
    }
}