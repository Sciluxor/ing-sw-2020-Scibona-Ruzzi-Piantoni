package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;
import it.polimi.ingsw.model.Response;

import java.util.ArrayList;
import java.util.HashMap;

public class Artemis extends Card {

    private boolean hasMoved;

    public Artemis(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
        hasMoved = false;
    }

    @Override
    public ArrayList<Directions> findWorkerMove(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        if(hasMoved)
            return notPreviousMove(gameMap, worker);

        return gameMap.reachableSquares(worker);
    }

    public ArrayList<Directions> notPreviousMove(GameMap gameMap, Worker worker) {
        int level_position = worker.getBoardPosition().getBuildingLevel();
        HashMap<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();
        ArrayList<Directions> reachableSquares = new ArrayList<>();

        for(Directions dir: Directions.values()){
            int squareTile = canAccess.get(dir);
            if(squareTile > 0 && squareTile <= 25) { //rivedere questo if
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
