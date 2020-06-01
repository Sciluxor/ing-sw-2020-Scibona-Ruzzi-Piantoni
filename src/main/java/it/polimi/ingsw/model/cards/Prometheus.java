package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.utils.ConstantsContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Prometheus extends Card {

    private boolean hasBuiltBefore;
    private boolean hasMoved;

    public Prometheus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
        hasBuiltBefore = false;
        hasMoved = false;
    }

    @Override
    public Response getFirstAction() {
        return Response.BUILDBEFORE;
    }

    @Override
    public List<Directions> findWorkerMove(GameMap gameMap, Worker worker) {
        if (!hasBuiltBefore)
            return super.findWorkerMove(gameMap, worker);
        else
            return notUpMove(gameMap, worker);
    }

    public List<Directions> notUpMove(GameMap gameMap, Worker worker) {
        if (worker == null)
            throw new NullPointerException("null worker");
        int levelPosition = worker.getBoardPosition().getBuildingLevel();
        Map<Directions, Integer> canAccess = worker.getBoardPosition().getCanAccess();
        List<Directions> reachableSquares = new ArrayList<>();

        for (Directions dir : Directions.values()) {
            int squareTile = canAccess.get(dir);
            if (squareTile > ConstantsContainer.MINMAPPOSITION && squareTile <= ConstantsContainer.MAXMAPPOSITION) {
                Square possibleSquare = gameMap.getMap().get(squareTile - 1);
                if (!possibleSquare.hasPlayer() && (possibleSquare.getBuildingLevel() >= 0 && possibleSquare.getBuildingLevel() <= levelPosition && !worker.getBoardPosition().equals(possibleSquare))
                        && possibleSquare.getBuilding() != Building.DOME) {
                    reachableSquares.add(dir);
                }
            }
        }
        return reachableSquares;
    }

    @Override
    public List<Directions> findPossibleBuild(GameMap gameMap, Worker worker) {
        List<Directions> possibleBuild = gameMap.buildableSquare(worker);
        if(hasBuiltBefore)
            return possibleBuild;
        else{
            possibleBuild.removeIf(dir -> wrongBuild(gameMap,worker,dir));
            return possibleBuild;
        }
    }

    public boolean wrongBuild(GameMap gameMap,Worker worker,Directions directions){
        List<Directions> possibleMove = notUpMove(gameMap,worker);
        return possibleMove.size() <= 1 && (possibleMove.size() != 1 || (possibleMove.get(0).equals(directions) &&
                gameMap.getMap().get(worker.getBoardPosition().getCanAccess().get(directions) -1).getBuildingLevel() < worker.getBoardPosition().getBuildingLevel()));
    }

    @Override
    public Response executeWorkerMove(GameMap gameMap, Directions directions, Player player) {
        if (gameMap == null || player == null || directions == null)
            throw new NullPointerException("null gameMap or player or direction");

        gameMap.moveWorkerTo(player, directions);
        hasMoved = true;
        return Response.MOVED;

    }


    @Override
    public Response executeBuild(GameMap gameMap, Building building, Directions directions, Worker worker) {
        if (!hasBuiltBefore && !hasMoved) {
            if(gameMap.buildInSquare(worker, directions, building)){
                hasBuiltBefore = true;
                return Response.BUILDEDBEFORE;
            }
            else return Response.NOTBUILD;
        }
        else {
            if(gameMap.buildInSquare(worker, directions, building)){
                hasMoved = false;
                hasBuiltBefore = false;
                return Response.BUILD;
            }
            else return Response.NOTBUILD;
        }
    }

    @Override
    public void resetCard() {
        hasBuiltBefore=false;
        hasMoved=false;
    }
}