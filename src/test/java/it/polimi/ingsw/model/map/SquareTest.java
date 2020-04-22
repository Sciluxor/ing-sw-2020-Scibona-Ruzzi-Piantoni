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
        assertEquals(15,map.getGameMap().get(14).getTile());
    }

    @Test
    void getBuildingLevel() {
        assertEquals(0,map.getGameMap().get(15).getBuildingLevel());
    }

    @Test
    void addBuildingLevel() {
        map.getGameMap().get(9).addBuildingLevel();
        assertEquals(1,map.getGameMap().get(9).getBuildingLevel());
        map.getGameMap().get(9).addBuildingLevel();
        assertEquals(2,map.getGameMap().get(9).getBuildingLevel());
        map.getGameMap().get(9).addBuildingLevel();
        assertEquals(3,map.getGameMap().get(9).getBuildingLevel());
        map.getGameMap().get(9).addBuildingLevel();
        assertEquals(4,map.getGameMap().get(9).getBuildingLevel());

        assertThrows(IllegalStateException.class,() -> map.getGameMap().get(9).addBuildingLevel());
    }

    @Test
    void getBuilding() {
        assertEquals(Building.GROUND,map.getGameMap().get(9).getBuilding());
    }

    @Test
    void setBuilding() {
        map.getGameMap().get(13).setBuilding(Building.DOME);
        assertEquals(Building.DOME,map.getGameMap().get(13).getBuilding());
        map.getGameMap().get(10).setBuilding(Building.LVL1);
        assertEquals(Building.LVL1,map.getGameMap().get(10).getBuilding());
        map.getGameMap().get(11).setBuilding(Building.LVL2);
        assertEquals(Building.LVL2,map.getGameMap().get(11).getBuilding());
        map.getGameMap().get(23).setBuilding(Building.LVL3);
        assertEquals(Building.LVL3,map.getGameMap().get(23).getBuilding());

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
       assertEquals(14,map.getGameMap().get(22).getCanAccess().get(Directions.OVEST));
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
        assertEquals(2,map.getGameMap().get(24).getCoordinates()[0]);
        assertEquals(2,map.getGameMap().get(24).getCoordinates()[1]);
    }
}