package it.polimi.ingsw.model.map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.utils.PathContainer;


import java.io.InputStreamReader;
import java.util.*;

/**
 * Class that build the game map from a json file
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/28
 */

public class MapLoader {

    /**
     * Private constructor, Since it's a loader class it can't be instantiated.
     */

    private MapLoader() {
        throw new IllegalStateException("MapLoader class cannot be instantiated");
    }

    private static class SquareContainer{

        Integer tile;
        int buildingLevel;
        Building building;
        boolean hasPlayer;
        Integer[] canAccess;
        Integer[] xy;
    }

    /**
     * Method that build the map from a json file, create all squares and the maps
     * @return The list of squares created(the entire map)
     */

    public static List<Square> loadMap(){
        Gson gsonMap = new Gson();
        SquareContainer[] containers;

        try{
            String mapPath = PathContainer.MAP;
            InputStreamReader gameMapInput = new InputStreamReader(MapLoader.class.getResourceAsStream(mapPath));
            JsonReader gameMapReader = new JsonReader(gameMapInput);
            containers = gsonMap.fromJson(gameMapReader,SquareContainer[].class);

        }catch (Exception e){

            throw new IllegalStateException("impossible to charge Squares");

        }

        List<Square> squares = new ArrayList<>();

        for(SquareContainer container: containers){

            Map<Directions,Integer> canAccess = createHashMapFromArray(container.canAccess);
            squares.add(new Square(container.tile,container.buildingLevel,container.building,container.hasPlayer,canAccess,container.xy));

        }

        return squares;
    }

    /**
     * Method that create a map using the directions(key) and an array of integer(value)
     * @param canAccess array of all the tile that can be accessed from a specific tile
     * @return The map created with the input parameters
     */

    private static Map<Directions,Integer> createHashMapFromArray(Integer[] canAccess) {

        Map<Directions, Integer> constructorMap = new EnumMap<>(Directions.class);
        int i = 0;
        for (Directions dir : Directions.values()) {

            constructorMap.put(dir,canAccess[i]);
            i++;

        }
        return constructorMap;
    }

}

