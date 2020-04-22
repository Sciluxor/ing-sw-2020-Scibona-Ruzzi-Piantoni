package it.polimi.ingsw.model.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

    @Test
    void parseInput() {
        assertEquals(Building.GROUND,Building.parseInput("GrOunD"));
        assertEquals(Building.LVL1,Building.parseInput("Lvl1"));
        assertEquals(Building.LVL2,Building.parseInput("LvL2"));
        assertEquals(Building.LVL3,Building.parseInput("lvL3"));
        assertEquals(Building.DOME,Building.parseInput("DomE"));
        assertThrows(IllegalArgumentException.class,() ->  Building.parseInput("test"));

    }

    @Test
    void mapNext() {
        assertEquals(Building.LVL1,Building.mapNext(Building.GROUND));
        assertEquals(Building.LVL2,Building.mapNext(Building.LVL1));
        assertEquals(Building.LVL3,Building.mapNext(Building.LVL2));
        assertEquals(Building.DOME,Building.mapNext(Building.LVL3));
        assertThrows(IllegalArgumentException.class,() -> Building.mapNext(Building.DOME));



    }
}