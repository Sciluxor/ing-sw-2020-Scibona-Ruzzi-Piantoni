package it.polimi.ingsw.model.Player;


import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.MapLoader;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Worker;
import it.polimi.ingsw.model.Player.WorkerName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {




    @Test
    void getBoardPosition() {

        Worker worker;
        ArrayList<Square> squares = MapLoader.loadMap();
        worker = new Worker(WorkerName.WORKER1, squares.get(0), squares.get(1), squares.get(3));
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
        Worker worker;
        ArrayList<Square> squares = MapLoader.loadMap();
       worker = new Worker(WorkerName.WORKER1, squares.get(1), squares.get(2), squares.get(3));
        assert (worker.getName() != null);
    }
}