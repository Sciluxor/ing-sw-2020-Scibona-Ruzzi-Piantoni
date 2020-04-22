package it.polimi.ingsw.model.map;

import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

GameMap map;

    @BeforeEach
    void setup(){
        map = new GameMap();
    }

    @Test
    void getTile() {
        assertEquals(map.getGameMap().get(14).getTile(),15);
    }

    @Test
    void getBuildingLevel() {
        assertEquals(map.getGameMap().get(15).getBuildingLevel(),0);
    }

    @Test
    void addBuildingLevel() {
        map.getGameMap().get(9).addBuildingLevel();
        assertEquals(map.getGameMap().get(9).getBuildingLevel(),1);
        map.getGameMap().get(9).addBuildingLevel();
        assertEquals(map.getGameMap().get(9).getBuildingLevel(),2);
        map.getGameMap().get(9).addBuildingLevel();
        assertEquals(map.getGameMap().get(9).getBuildingLevel(),3);
        map.getGameMap().get(9).addBuildingLevel();
        assertEquals(map.getGameMap().get(9).getBuildingLevel(),4);

        assertThrows(IllegalStateException.class,() -> map.getGameMap().get(9).addBuildingLevel());
    }

    @Test
    void getBuilding() {
        assertEquals(map.getGameMap().get(9).getBuilding(),Building.GROUND);
    }

    @Test
    void setBuilding() {
        map.getGameMap().get(13).setBuilding(Building.DOME);
        assertEquals(map.getGameMap().get(13).getBuilding(),Building.DOME);
        map.getGameMap().get(10).setBuilding(Building.LVL1);
        assertEquals(map.getGameMap().get(10).getBuilding(),Building.LVL1);
        map.getGameMap().get(11).setBuilding(Building.LVL2);
        assertEquals(map.getGameMap().get(11).getBuilding(),Building.LVL2);
        map.getGameMap().get(23).setBuilding(Building.LVL3);
        assertEquals(map.getGameMap().get(23).getBuilding(),Building.LVL3);

        assertThrows(NullPointerException.class,() -> map.getGameMap().get(17).setBuilding(null));
    }

    @Test
    void hasPlayer() {
        assertFalse(map.getGameMap().get(13).hasPlayer());
    }

    @Test
    void setHasPlayer() {
        map.getGameMap().get(17).setHasPlayer(true);
        assertTrue(map.getGameMap().get(17).hasPlayer());
    }

    @Test
    void getPlayer() {
        assertThrows(IllegalStateException.class,() -> map.getGameMap().get(17).getPlayer());
    }

    @Test
    void setPlayer() {
        Player player1 = new Player("nome");
        map.getGameMap().get(19).setPlayer(player1);
        map.getGameMap().get(19).setHasPlayer(true);
        assertEquals(map.getGameMap().get(19).getPlayer(),player1);

        assertThrows(NullPointerException.class,() -> map.getGameMap().get(17).setPlayer(null));
    }

    @Test
    void getWorker() {
        assertThrows(IllegalStateException.class,() -> map.getGameMap().get(21).getWorker());
    }

    @Test
    void setWorker() {
        Player player1 = new Player("nome");
        map.getGameMap().get(22).setWorker(player1.getWorkers().get(0));
        map.getGameMap().get(22).setHasPlayer(true);
        assertEquals(map.getGameMap().get(22).getWorker(),player1.getWorkers().get(0));

        assertThrows(NullPointerException.class,() -> map.getGameMap().get(22).setWorker(null));
    }

    @Test
    void getCanAccess() {
       assertEquals(map.getGameMap().get(22).getCanAccess().get(Directions.OVEST),14);
    }
    @Test
    void setMovement(){
        Player player1 = new Player("nome");
        map.getGameMap().get(21).setMovement(player1,player1.getWorkers().get(1));
        assertTrue(map.getGameMap().get(21).hasPlayer());
        assertEquals(map.getGameMap().get(21).getPlayer(),player1);
        assertEquals(map.getGameMap().get(21).getWorker(),player1.getWorkers().get(1));

    }

    @Test
    void getCoordinates() {
        assertEquals(map.getGameMap().get(24).getCoordinates()[0], 2);
        assertEquals(map.getGameMap().get(24).getCoordinates()[1], 2);
    }
}