package it.polimi.ingsw.model.Map;

import it.polimi.ingsw.model.Player.WorkerName;

public enum Directions {
    NORD, NORD_EST, EST, SUD_EST, SUD, SUD_OVEST, OVEST, NORD_OVEST;

    public static Directions parseInput(String input) { return Enum.valueOf(Directions.class, input.toUpperCase());}
}
