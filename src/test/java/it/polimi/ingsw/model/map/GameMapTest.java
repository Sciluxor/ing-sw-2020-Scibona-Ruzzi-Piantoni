package it.polimi.ingsw.model.map;

import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameMapTest {
    GameMap map;
    Player player1,player2;

    @BeforeEach
    void setup(){

        map = new GameMap();
        player1 = new Player("uno");
        player2 = new Player("due");
        map.getGameMap().get(22).setMovement(player1,player1.getWorkers().get(0));
        player1.getWorkers().get(0).setBoardPosition(map.getGameMap().get(22));
        map.getGameMap().get(4).setMovement(player1,player1.getWorkers().get(1));
        player1.getWorkers().get(1).setBoardPosition(map.getGameMap().get(4));
        map.getGameMap().get(21).setMovement(player2,player2.getWorkers().get(0));
        player2.getWorkers().get(0).setBoardPosition(map.getGameMap().get(21));
        map.getGameMap().get(18).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(map.getGameMap().get(18));


    }
    @Test
    void reachableSquares() {
        map.getGameMap().get(5).setBuilding(Building.LVL3);
        map.getGameMap().get(5).addBuildingLevel();
        map.getGameMap().get(5).addBuildingLevel();
        map.getGameMap().get(5).addBuildingLevel();

        List<Directions> directions = map.reachableSquares(player1.getWorkers().get(1));
        assertEquals(1, directions.size());
        assertEquals(Directions.OVEST,directions.get(0));

        assertThrows(NullPointerException.class,() -> map.reachableSquares(null));

        map.getGameMap().get(21).addBuildingLevel();
        map.getGameMap().get(21).addBuildingLevel();
        map.getGameMap().get(21).setBuilding(Building.LVL2);

        map.getGameMap().get(24).setBuilding(Building.DOME);
        map.getGameMap().get(19).setBuilding(Building.DOME);
        map.getGameMap().get(20).setBuilding(Building.DOME);

        map.getGameMap().get(9).addBuildingLevel();
        map.getGameMap().get(9).addBuildingLevel();
        map.getGameMap().get(9).addBuildingLevel();
        map.getGameMap().get(9).addBuildingLevel();
        map.getGameMap().get(9).setBuilding(Building.DOME);

        map.getGameMap().get(10).addBuildingLevel();
        map.getGameMap().get(10).addBuildingLevel();
        map.getGameMap().get(10).addBuildingLevel();
        map.getGameMap().get(10).setBuilding(Building.LVL3);

        directions = map.reachableSquares(player2.getWorkers().get(0));
        assertEquals(3, directions.size());

        assertEquals(Directions.SUD,directions.get(0));
        assertEquals(Directions.SUD_OVEST,directions.get(1));
        assertEquals(Directions.NORD_OVEST,directions.get(2));

    }


    @Test
    void moveWorkerTo() {
        assertThrows(NullPointerException.class,() -> map.moveWorkerTo(null,Directions.NORD_OVEST));
        assertThrows(NullPointerException.class,() -> map.moveWorkerTo(player1,null));
        player2.setCurrentWorker(player2.getWorkers().get(0));
        map.moveWorkerTo(player2,Directions.NORD_OVEST);

        assertEquals(player2.getCurrentWorker().getPreviousBoardPosition(),map.getGameMap().get(21));
        assertEquals(player2.getCurrentWorker().getBoardPosition(),map.getGameMap().get(23));
        assertTrue(player2.getCurrentWorker().getBoardPosition().hasPlayer());
        assertEquals(player2.getCurrentWorker().getBoardPosition().getPlayer(),player2);
        assertEquals(player2.getCurrentWorker().getBoardPosition().getWorker(),player2.getCurrentWorker());
        assertEquals(map.getModifiedSquare().get(0),map.getGameMap().get(21));
        assertEquals(map.getModifiedSquare().get(1),map.getGameMap().get(23));
    }


    @Test
    void buildableSquare() {
        assertThrows(NullPointerException.class,() -> map.buildableSquare(null));

        map.getGameMap().get(5).setBuilding(Building.LVL3);
        map.getGameMap().get(5).addBuildingLevel();
        map.getGameMap().get(5).addBuildingLevel();
        map.getGameMap().get(5).addBuildingLevel();

        List<Directions> directions = map.buildableSquare(player1.getWorkers().get(1));
        assertEquals(2, directions.size());
        assertEquals(Directions.SUD,directions.get(0));
        assertEquals(Directions.OVEST,directions.get(1));

        map.getGameMap().get(21).addBuildingLevel();
        map.getGameMap().get(21).addBuildingLevel();
        map.getGameMap().get(21).setBuilding(Building.LVL2);

        map.getGameMap().get(24).setBuilding(Building.DOME);
        map.getGameMap().get(19).setBuilding(Building.DOME);
        map.getGameMap().get(20).setBuilding(Building.DOME);

        map.getGameMap().get(9).addBuildingLevel();
        map.getGameMap().get(9).addBuildingLevel();
        map.getGameMap().get(9).addBuildingLevel();
        map.getGameMap().get(9).addBuildingLevel();
        map.getGameMap().get(9).setBuilding(Building.DOME);

        map.getGameMap().get(10).addBuildingLevel();
        map.getGameMap().get(10).addBuildingLevel();
        map.getGameMap().get(10).addBuildingLevel();
        map.getGameMap().get(10).setBuilding(Building.LVL3);

        directions = map.buildableSquare(player2.getWorkers().get(0));
        assertEquals(3, directions.size());

        assertEquals(Directions.SUD,directions.get(0));
        assertEquals(Directions.SUD_OVEST,directions.get(1));
        assertEquals(Directions.NORD_OVEST,directions.get(2));



    }


    @Test
    void buildInSquare() {
        player2.setCurrentWorker(player2.getWorkers().get(0));

        assertThrows(NullPointerException.class,() -> map.buildInSquare(null,Directions.NORD_OVEST,Building.LVL2));
        assertThrows(NullPointerException.class,() -> map.buildInSquare(player2.getCurrentWorker(),null,Building.LVL2));
        assertThrows(NullPointerException.class,() -> map.buildInSquare(player2.getCurrentWorker(),Directions.NORD_OVEST,null));


        map.getGameMap().get(21).addBuildingLevel();
        map.getGameMap().get(21).addBuildingLevel();
        map.getGameMap().get(21).setBuilding(Building.LVL2);

        map.getGameMap().get(24).setBuilding(Building.DOME);
        map.getGameMap().get(19).setBuilding(Building.DOME);
        map.getGameMap().get(20).setBuilding(Building.DOME);

        map.getGameMap().get(9).addBuildingLevel();
        map.getGameMap().get(9).addBuildingLevel();
        map.getGameMap().get(9).addBuildingLevel();
        map.getGameMap().get(9).addBuildingLevel();
        map.getGameMap().get(9).setBuilding(Building.DOME);

        map.getGameMap().get(10).addBuildingLevel();
        map.getGameMap().get(10).addBuildingLevel();
        map.getGameMap().get(10).addBuildingLevel();
        map.getGameMap().get(10).setBuilding(Building.LVL3);


        assertTrue(map.buildInSquare(player2.getCurrentWorker(),Directions.SUD,Building.DOME));
        assertEquals(player2.getCurrentWorker().getPreviousBuildPosition().getTile(),player2.getCurrentWorker().getBoardPosition().getCanAccess().get(Directions.SUD));
        assertEquals(4,player2.getCurrentWorker().getPreviousBuildPosition().getBuildingLevel());
        assertEquals(Building.DOME,player2.getCurrentWorker().getPreviousBuildPosition().getBuilding());
        assertEquals(map.getModifiedSquare().get(0),map.getGameMap().get(10));

        assertFalse(map.buildInSquare(player2.getCurrentWorker(),Directions.SUD_OVEST,Building.LVL3));
        assertEquals(0,map.getModifiedSquare().size());
        assertTrue(map.buildInSquare(player2.getCurrentWorker(),Directions.SUD_OVEST,Building.LVL1));
        assertEquals(map.getModifiedSquare().get(0),map.getGameMap().get(11));

        map.getGameMap().get(23).addBuildingLevel();
        map.getGameMap().get(23).setBuilding(Building.LVL1);

        assertTrue(map.buildInSquare(player2.getCurrentWorker(),Directions.NORD_OVEST,Building.LVL2));
        assertEquals(map.getModifiedSquare().get(0),map.getGameMap().get(23));
        assertTrue(map.buildInSquare(player2.getCurrentWorker(),Directions.NORD_OVEST,Building.LVL3));
        assertEquals(map.getModifiedSquare().get(0),map.getGameMap().get(23));

        assertThrows(IllegalArgumentException.class,() -> map.buildInSquare(player2.getCurrentWorker(),Directions.EST,Building.LVL2));

    }


    @Test
    void workersSquares() {
        assertThrows(NullPointerException.class,() -> map.getWorkersSquares(null));

        assertEquals(map.getWorkersSquares(player1).get(0),player1.getWorkers().get(0).getBoardPosition());
        assertEquals(map.getWorkersSquares(player1).get(1),player1.getWorkers().get(1).getBoardPosition());

    }


    @Test
    void isInPerimeter() {
        assertThrows(NullPointerException.class,() -> map.isInPerimeter(null));
        assertTrue(map.isInPerimeter(map.getGameMap().get(1).getTile()));
        assertTrue(map.isInPerimeter(map.getGameMap().get(15).getTile()));
        assertFalse(map.isInPerimeter(map.getGameMap().get(16).getTile()));

    }

    @Test
    void coordinates(){
        Integer[] coordinates = {3,4};
        assertEquals(8,map.getTileFromCoordinates(coordinates).getTile());


    }

    @Test
    void addModifiedSquare() {
        assertEquals(0,map.getModifiedSquare().size());
        map.addModifiedSquare(map.getGameMap().get(23));
        assertEquals(map.getModifiedSquare().get(0), map.getGameMap().get(23));
    }

    @Test
    void getModifiedSquare() {
        assertEquals(0,map.getModifiedSquare().size());
        map.addModifiedSquare(map.getGameMap().get(23));
        assertEquals(map.getModifiedSquare().get(0), map.getGameMap().get(23));
    }

    @Test
    void clearModifiedSquare() {
        assertEquals(0,map.getModifiedSquare().size());
        map.addModifiedSquare(map.getGameMap().get(23));
        assertEquals(map.getModifiedSquare().get(0), map.getGameMap().get(23));
        map.addModifiedSquare(map.getGameMap().get(17));
        assertEquals(map.getModifiedSquare().get(1), map.getGameMap().get(17));
        map.clearModifiedSquare();
        assertEquals(0,map.getModifiedSquare().size());
    }
}