package it.polimi.ingsw.model.map;

/**
 * Enumeration used to represent the directions in which to move/build
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/28
 */

public enum Directions {
    NORD, NORD_EST, EST, SUD_EST, SUD, SUD_OVEST, OVEST, NORD_OVEST, CENTER;

    /**
     * Method used to parse an input string to one of the possible enumeration's value
     * @param input The string to parse
     * @return The value of the enumeration corresponding to the string
     */

    public static Directions parseInput(String input) { return Enum.valueOf(Directions.class, input.toUpperCase());}
}
