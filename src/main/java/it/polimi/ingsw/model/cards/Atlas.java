package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.Response;

public class Atlas extends Card {
    public Atlas(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }
    @Override
    public Response executeBuild(GameMap gameMap, Building building, Directions directions, Worker worker) {
        if(gameMap == null || worker == null || building == null || directions == null)
            throw new NullPointerException("null gameMap or worker or building or direction");

        Square buildingSquare = gameMap.getMap().get(worker.getBoardPosition().getCanAccess().get(directions) - 1);
        if(building.equals(Building.DOME) || building.equals(Building.mapNext(buildingSquare.getBuilding())) ){
            worker.setPreviousBuildPosition(buildingSquare);
            buildingSquare.setBuilding(building);
            buildingSquare.addBuildingLevel();
            gameMap.clearModifiedSquare();
            gameMap.addModifiedSquare(buildingSquare);
            return Response.BUILD;
        }
        return Response.NOTBUILD;
    }



}
