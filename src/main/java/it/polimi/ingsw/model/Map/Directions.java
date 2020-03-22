package it.polimi.ingsw.model.Map;

public enum Directions {
    NORD, NORD_EST, EST, SUD_EST, SUD, SUD_OVEST, OVEST, NORD_OVEST, CENTER;

    public static Directions parseInput(String input) { return Enum.valueOf(Directions.class, input.toUpperCase());}
}
