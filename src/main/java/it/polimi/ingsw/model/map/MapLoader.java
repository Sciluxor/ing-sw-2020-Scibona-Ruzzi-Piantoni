package it.polimi.ingsw.model.map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


import java.io.InputStreamReader;
import java.util.*;


public class MapLoader {
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

    public static List<Square> loadMap(){
        Gson gsonMap = new Gson();
        SquareContainer[] containers;

        try{
            String mapPath = "/MapJson/Map.json";
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

