package it.polimi.ingsw.model.Player;


import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.MapLoader;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Worker;
import it.polimi.ingsw.model.Player.WorkerName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    Worker worker1,worker2;
    ArrayList<Square> squares;

    @BeforeEach
    void setup(){
        squares = MapLoader.loadMap();
        worker1 = new Worker(WorkerName.WORKER1);
        worker2 = new Worker(WorkerName.WORKER2);


    }



    @Test
    void getBoardPosition() {

        worker1.setBoardPosition(squares.get(0));
        assert (worker1.getBoardPosition() != null);
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

        assert (worker1.getName() == WorkerName.WORKER1);
        assert (worker2.getName() == WorkerName.WORKER2);
    }
}