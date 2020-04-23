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

public class Artemis extends Card {

    private boolean hasMoved;

    public Artemis(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
        hasMoved = false;
    }

    @Override
    public List<Directions> findWorkerMove(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        if(hasMoved)
            return notPreviousMove(gameMap, worker);

        return gameMap.reachableSquares(worker);
    }

    public List<Directions> notPreviousMove(GameMap gameMap, Worker worker) {
        int level_position = worker.getBoardPosition().getBuildingLevel();
        Map<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();
        List<Directions> reachableSquares = new ArrayList<>();

        for(Directions dir: Directions.values()){
            int squareTile = canAccess.get(dir);
            if(squareTile > ConstantsContainer.MINMAPPOSITION && squareTile <= ConstantsContainer.MAXMAPPOSITION) { //rivedere questo if
                Square possibleSquare = gameMap.getGameMap().get(squareTile- 1);
                if(!possibleSquare.hasPlayer() && (possibleSquare.getBuildingLevel() >= 0 && possibleSquare.getBuildingLevel() <= level_position +1)
                        && possibleSquare.getBuilding() != Building.DOME && !(possibleSquare.equals(worker.getPreviousBoardPosition()))) {
                    reachableSquares.add(dir);
                }
            }
        }

        return reachableSquares;
    }

    @Override
    public Response executeWorkerMove(GameMap gameMap, Directions directions, Player player) {
        if(gameMap == null || player == null || directions == null)
            throw new NullPointerException("null gameMap or player or direction");

        gameMap.moveWorkerTo(player, directions);

        if(!hasMoved) {
            hasMoved = true;
            return Response.NEWMOVE;
        }

        this.hasMoved = false;
        return Response.MOVED;
    }
}
