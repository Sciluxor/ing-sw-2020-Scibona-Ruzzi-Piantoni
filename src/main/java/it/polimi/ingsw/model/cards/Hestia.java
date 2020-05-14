package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.utils.ConstantsContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hestia extends Card {

    private boolean hasBuilt;

    public Hestia(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
        hasBuilt = false;
    }

    @Override
    public List<Directions> findPossibleBuild(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        if(hasBuilt)
            return notPerimeterBuild(gameMap, worker);

        return gameMap.buildableSquare(worker);
    }

    public List<Directions> notPerimeterBuild(GameMap gameMap, Worker worker) {
        int levelPosition = worker.getBoardPosition().getBuildingLevel();
        Map<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();
        List<Directions> reachableSquares = new ArrayList<>();

        for(Directions dir: Directions.values()){
            int squareTile = canAccess.get(dir);
            if(squareTile > ConstantsContainer.MINMAPPOSITION && squareTile <= ConstantsContainer.MAXMAPPOSITION && !gameMap.isInPerimeter(squareTile)) { //rivedere questo if
                Square possibleSquare = gameMap.getMap().get(squareTile- 1);
                if(!possibleSquare.hasPlayer() && (possibleSquare.getBuildingLevel() >= 0 && possibleSquare.getBuildingLevel() <= levelPosition +1)
                        && possibleSquare.getBuilding() != Building.DOME) {
                    reachableSquares.add(dir);
                }
            }
        }

        return reachableSquares;
    }

    @Override
    public Response executeBuild(GameMap gameMap, Building building, Directions directions, Worker worker) {
        if (gameMap == null || worker == null || building == null || directions == null)
            throw new NullPointerException("null gameMap or worker or building or direction");
        if (!hasBuilt) {
            if (gameMap.buildInSquare(worker, directions, building)) {
                hasBuilt = true;
                System.out.println("hestia :" + hasBuilt);
                return Response.NEWBUILD;
            } else
                return Response.NOTBUILD;
        } else {

            if (gameMap.buildInSquare(worker, directions, building)) {
                hasBuilt = false;
                System.out.println("hestia :" + hasBuilt);
                return Response.BUILD;
            } else
                return Response.NOTBUILD;
        }

    }

    @Override
    public void resetCard() {
       hasBuilt = false;
    }
}
