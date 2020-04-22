package it.polimi.ingsw.model.map;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionsTest {

    @Test
    void parseInput() {
        assertEquals(Directions.OVEST,Directions.parseInput("oVest"));
        assertEquals(Directions.NORD,Directions.parseInput("NorD"));
        assertEquals(Directions.NORD_EST,Directions.parseInput("Nord_EST"));
        assertEquals(Directions.NORD_OVEST,Directions.parseInput("nORD_OveSt"));
        assertEquals(Directions.EST,Directions.parseInput("Est"));
        assertEquals(Directions.SUD,Directions.parseInput("SUD"));
        assertEquals(Directions.SUD_EST,Directions.parseInput("Sud_esT"));
        assertEquals(Directions.SUD_OVEST,Directions.parseInput("sUD_oVest"));

        assertThrows(IllegalArgumentException.class,() ->  Directions.parseInput("test"));
    }
}