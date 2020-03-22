package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Worker;

import java.util.ArrayList;
import java.util.HashMap;

public class Hephaestus extends Card {

    private boolean hasBuilt;

    public Hephaestus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
        hasBuilt = false;
    }

    @Override
    public ArrayList<Directions> findPossibleBuild(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        return gameMap.reachableSquares(worker);
    }

    @Override
    public Response executeBuild(GameMap gameMap, Building building, Directions directions, Worker worker) {
        if(gameMap == null || worker == null || building == null || directions == null)
            throw new NullPointerException("null gameMap or worker or building or direction");

        if(!hasBuilt) {
            if(gameMap.buildInSquare(worker, directions, building)) {
                if(building.equals(Building.DOME) || building.equals(Building.LVL3))
                    return Response.BUILD;
                hasBuilt = true;
                return Response.NEWBUILDSAMEPLACE;
            }
            else
                return Response.NOTBUILD;
        }

        gameMap.buildInSquare(worker, directions, building);
        hasBuilt = false;
        return Response.BUILD;
    }

}
