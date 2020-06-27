package it.polimi.ingsw.utils;

/**
 * Utility class that contains all the path for the json files
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/27
 */

public class PathContainer {

    /**
     * Private constructor, Since it's an utility class it can't be instantiated.
     */

    private PathContainer() {
        throw new IllegalStateException("PathContainer class cannot be instantiated");
    }

    public static final String  MAP = "/MapJson/Map.json";
    public static final String  FLOW = "/PossibleMoves.json" ;
    public static final String  CONFIG = "/ServerConfig.json";
    public static final String  CARD = "/CardsJson/Cards.json";
}
