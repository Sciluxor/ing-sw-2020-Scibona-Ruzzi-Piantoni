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

/**
 * Class that extends Card that build the Hephaestus card
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/27
 */

public class Hephaestus extends Card {

    private boolean hasBuilt;

    /**
     * Class Builder
     * @param name Name of the card
     * @param description Description of the power of the card
     * @param isPlayableIn3 Boolean saying if the card is playable in 3 Players
     * @param type Type of the card
     * @param subType Subtype of the card
     */

    public Hephaestus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
        hasBuilt = false;
    }

    @Override
    public List<Directions> findPossibleBuild(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        if(hasBuilt)
            return buildOnTop(gameMap, worker);

        return gameMap.buildableSquare(worker);
    }

    /**
     * Method that return the buildable squares, in which the provided Worker can build, where he built previously
     * @param gameMap Map of the game
     * @param worker Worker you want to know the buildable squares
     * @return List of directions of the buildable squares
     */

    public List<Directions> buildOnTop(GameMap gameMap, Worker worker) {
        Map<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();
        List<Directions> reachableSquares = new ArrayList<>();

        for(Directions dir: Directions.values()){
            int squareTile = canAccess.get(dir);
            if(squareTile > ConstantsContainer.MINMAPPOSITION && squareTile <= ConstantsContainer.MAXMAPPOSITION) { //rivedere questo if
                Square possibleSquare = gameMap.getMap().get(squareTile- 1);
                if( possibleSquare.equals(worker.getPreviousBuildPosition()) && !possibleSquare.hasPlayer() && possibleSquare.getBuilding() != Building.DOME) {
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
                if(building.equals(Building.DOME) || building.equals(Building.LVL3))
                    return Response.BUILD;
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

    @Override
    public void resetCard() {
        hasBuilt=false;
    }
}
