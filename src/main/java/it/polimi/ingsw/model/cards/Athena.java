package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.Response;

import java.util.ArrayList;

public class Athena extends Card {

    public Athena(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }


    @Override
    public Response executeWorkerMove(GameMap gameMap, Directions directions, Player player) {
        if(gameMap == null || directions == null || player == null)
            throw new NullPointerException("null gameMap or directions or player");

        gameMap.moveWorkerTo(player,directions);

        if(player.getCurrentWorker().getPreviousBoardPosition().getBuildingLevel() + 1 == player.getCurrentWorker().getBoardPosition().getBuildingLevel()){
            return Response.ASSIGNCONSTRAINT;

        }
        return Response.MOVED;

    }

    @Override
    public ArrayList<Directions> eliminateInvalidMove(GameMap gameMap, Worker worker, ArrayList<Directions> directionsArrayList) {
        Square currentSquare = worker.getBoardPosition();
        ArrayList<Directions> toRemoveDirections = new ArrayList<>();
        for (Directions dir : directionsArrayList) {
            Square possibleSquare = gameMap.getGameMap().get(currentSquare.getCanAccess().get(dir) - 1);
            if (possibleSquare.getBuildingLevel() == currentSquare.getBuildingLevel() + 1) {
                toRemoveDirections.add(dir);
            }
        }
        for (Directions dir : toRemoveDirections) {
            directionsArrayList.remove(dir);
        }

        return directionsArrayList;
    }
}
