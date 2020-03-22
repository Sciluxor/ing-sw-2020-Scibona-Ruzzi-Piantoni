package it.polimi.ingsw.model.Map;

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
        assertEquals(Building.mapNext(Building.GROUND),Building.LVL1);
        assertEquals(Building.mapNext(Building.LVL1),Building.LVL2);
        assertEquals(Building.mapNext(Building.LVL2),Building.LVL3);
        assertEquals(Building.mapNext(Building.LVL3),Building.DOME);
        assertThrows(IllegalArgumentException.class,() -> Building.mapNext(Building.DOME));



    }
}