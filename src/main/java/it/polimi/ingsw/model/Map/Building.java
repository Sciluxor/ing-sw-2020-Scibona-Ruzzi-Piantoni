package it.polimi.ingsw.model.Map;

public enum Building {
    GROUND, LVL1, LVL2, LVL3, DOME;

    public static Building parseInput(String input){ return Enum.valueOf(Building.class, input.toUpperCase());}

}
