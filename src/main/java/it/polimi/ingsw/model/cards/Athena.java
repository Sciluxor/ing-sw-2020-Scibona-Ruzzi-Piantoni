package it.polimi.ingsw.model.cards;


import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that extends Card that build the Athena card
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/27
 */

public class Athena extends Card {

    /**
     * Class Builder
     * @param name Name of the card
     * @param description Description of the power of the card
     * @param isPlayableIn3 Boolean saying if the card is playable in 3 Players
     * @param type Type of the card
     * @param subType Subtype of the card
     */

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
    public List<Directions> eliminateInvalidMove(GameMap gameMap, Worker worker, List<Directions> directionsArrayList) {
        Square currentSquare = worker.getBoardPosition();
        List<Directions> toRemoveDirections = new ArrayList<>();
        for (Directions dir : directionsArrayList) {
            Square possibleSquare = gameMap.getMap().get(currentSquare.getCanAccess().get(dir) - 1);
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
