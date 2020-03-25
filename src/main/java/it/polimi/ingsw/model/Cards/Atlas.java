package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Worker;

import java.util.ArrayList;
import java.util.HashMap;

public class Atlas extends Card {
    public Atlas(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }
    @Override
    public Response executeBuild(GameMap gameMap, Building building, Directions directions, Worker worker) {
        if(gameMap == null || worker == null || building == null || directions == null)
            throw new NullPointerException("null gameMap or worker or building or direction");

        Square buildingSquare = gameMap.getGameMap().get(worker.getBoardPosition().getCanAccess().get(directions) - 1);
        if(building.equals(Building.mapNext(buildingSquare.getBuilding())) || building.equals(Building.DOME)){
            worker.setPreviousBuildPosition(buildingSquare);
            buildingSquare.setBuilding(building);
            buildingSquare.addBuildingLevel();
            return Response.BUILD;
        }
        return Response.NOTBUILD;
    }



}
