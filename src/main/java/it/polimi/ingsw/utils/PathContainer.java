package it.polimi.ingsw.utils;

public class PathContainer {

    private PathContainer() {
        throw new IllegalStateException("PathContainer class cannot be instantiated");
    }

    public static final String  MAP = "/MapJson/Map.json";
    public static final String  FLOW = "/PossibleMoves.json" ;
    public static final String  CONFIG = "/ServerConfig.json";
    public static final String  CARD = "/CardsJson/Cards.json";
}
