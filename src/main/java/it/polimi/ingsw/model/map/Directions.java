package it.polimi.ingsw.model.map;

/**
 *
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/28
 */

public enum Directions {
    NORD, NORD_EST, EST, SUD_EST, SUD, SUD_OVEST, OVEST, NORD_OVEST, CENTER;

    /**
     *
     * @param input
     * @return
     */

    public static Directions parseInput(String input) { return Enum.valueOf(Directions.class, input.toUpperCase());}
}
