package it.polimi.ingsw.model.player;


import it.polimi.ingsw.model.map.MapLoader;
import it.polimi.ingsw.model.map.Square;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    Worker worker1;
    List<Square> squares;

    @BeforeEach
    void setup(){
        squares = MapLoader.loadMap();
        worker1 = new Worker(WorkerName.WORKER1);
    }

    @Test
    void getBoardPosition() {
        assertNull (worker1.getBoardPosition());
        worker1.setBoardPosition(squares.get(0));
        assertEquals (worker1.getBoardPosition(), squares.get(0));
        worker1.setBoardPosition(squares.get(14));
        worker1.setBoardPosition(squares.get(5));
        worker1.setBoardPosition(squares.get(9));
        assertEquals (worker1.getBoardPosition(), squares.get(9));
    }

    @Test
    void setBoardPosition() {
        assertNull (worker1.getBoardPosition());
        worker1.setBoardPosition(squares.get(0));
        assertEquals (worker1.getBoardPosition(), squares.get(0));
        worker1.setBoardPosition(squares.get(14));
        worker1.setBoardPosition(squares.get(5));
        worker1.setBoardPosition(squares.get(9));
        assertEquals (worker1.getBoardPosition(), squares.get(9));

        assertThrows(NullPointerException.class , () -> worker1.setBoardPosition(null));
    }

    @Test
    void getPreviousBoardPosition() {
        assertNull (worker1.getPreviousBoardPosition());
        worker1.setPreviousBoardPosition(squares.get(0));
        assertEquals (worker1.getPreviousBoardPosition(), squares.get(0));
        worker1.setPreviousBoardPosition(squares.get(14));
        worker1.setPreviousBoardPosition(squares.get(5));
        worker1.setPreviousBoardPosition(squares.get(9));
        assertEquals (worker1.getPreviousBoardPosition(), squares.get(9));
    }

    @Test
    void setPreviousBoardPosition() {
        assertNull (worker1.getPreviousBoardPosition());
        worker1.setPreviousBoardPosition(squares.get(0));
        assertEquals (worker1.getPreviousBoardPosition(), squares.get(0));
        worker1.setPreviousBoardPosition(squares.get(14));
        worker1.setPreviousBoardPosition(squares.get(5));
        worker1.setPreviousBoardPosition(squares.get(9));
        assertEquals (worker1.getPreviousBoardPosition(), squares.get(9));

        assertThrows(NullPointerException.class , () -> worker1.setPreviousBoardPosition(null));
    }

    @Test
    void getPreviousBuildPosition() {
        assertNull (worker1.getPreviousBuildPosition());
        worker1.setPreviousBuildPosition(squares.get(0));
        assertEquals (worker1.getPreviousBuildPosition(), squares.get(0));
        worker1.setPreviousBuildPosition(squares.get(14));
        worker1.setPreviousBuildPosition(squares.get(5));
        worker1.setPreviousBuildPosition(squares.get(9));
        assertEquals (worker1.getPreviousBuildPosition(), squares.get(9));
    }

    @Test
    void setPreviousBuildPosition() {
        assertNull (worker1.getPreviousBuildPosition());
        worker1.setPreviousBuildPosition(squares.get(0));
        assertEquals (worker1.getPreviousBuildPosition(), squares.get(0));
        worker1.setPreviousBuildPosition(squares.get(14));
        worker1.setPreviousBuildPosition(squares.get(5));
        worker1.setPreviousBuildPosition(squares.get(9));
        assertEquals (worker1.getPreviousBuildPosition(), squares.get(9));

        assertThrows(NullPointerException.class , () -> worker1.setPreviousBuildPosition(null));
    }

    @Test
    void getName() {
        assertEquals ( WorkerName.WORKER1,worker1.getName());
    }
}