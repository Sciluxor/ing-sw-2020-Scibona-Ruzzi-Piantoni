package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Worker;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.utils.ConstantsContainer;

import java.util.ArrayList;
import java.util.HashMap;

public class Demeter extends Card {

    private boolean hasBuilt;

    public Demeter(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
        hasBuilt = false;
    }

    @Override
    public ArrayList<Directions> findPossibleBuild(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        if(hasBuilt)
            return notPreviousBuild(gameMap, worker);

        return gameMap.reachableSquares(worker);
    }

    public ArrayList<Directions> notPreviousBuild(GameMap gameMap, Worker worker) {
        int level_position = worker.getBoardPosition().getBuildingLevel();
        HashMap<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();
        ArrayList<Directions> reachableSquares = new ArrayList<>();

        for(Directions dir: Directions.values()){
            int squareTile = canAccess.get(dir);
            if(squareTile > ConstantsContainer.MINMAPPOSITION && squareTile <= ConstantsContainer.MAXMAPPOSITION) { //rivedere questo if
                Square possibleSquare = gameMap.getGameMap().get(squareTile- 1);
                if(!possibleSquare.hasPlayer() && (possibleSquare.getBuildingLevel() >= 0 && possibleSquare.getBuildingLevel() <= level_position +1)
                        && possibleSquare.getBuilding() != Building.DOME && !(possibleSquare.equals(worker.getPreviousBuildPosition()))) {
                    reachableSquares.add(dir);
                }
            }
        }

        return reachableSquares;
    }

    @Override
    public Response executeBuild(GameMap gameMap, Building building, Directions directions, Worker worker) {
        if(gameMap == null || worker == null || building == null || directions == null)
            throw new NullPointerException("null gameMap or worker or building or direction");

        if(!hasBuilt) {
            if(gameMap.buildInSquare(worker, directions, building)) {
                hasBuilt = true;
                return Response.NEWBUILD;
            }
            else
                return Response.NOTBUILD;
        }

        if(gameMap.buildInSquare(worker, directions, building)) {
            hasBuilt = false;
            return Response.BUILD;
        }
        else
            return Response.NOTBUILD;
    }

}
