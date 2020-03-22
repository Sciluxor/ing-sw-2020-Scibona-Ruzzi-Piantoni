package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;

import java.util.ArrayList;

public class Athena extends Card {

    public Athena(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }


    @Override
    public Response executeWorkerMove(GameMap gameMap, Directions directions, Player player) {
        gameMap.moveWorkerTo(player,directions);

        if(player.getCurrentWorker().getPreviousBoardPosition().getBuildingLevel() == player.getCurrentWorker().getBoardPosition().getBuildingLevel()+1){
            return Response.ASSIGNCONSTRAINT;

        }
        return Response.MOVED;

    }

    @Override
    public ArrayList<Directions> eliminateInvalidMove(GameMap gameMap,Worker worker, ArrayList<Directions> directionsArrayList) {

         Square currentSquare = worker.getBoardPosition();

         for(Directions dir: directionsArrayList){
             Square possibleSquare = gameMap.getGameMap().get(currentSquare.getCanAccess().get(dir)-1);
             if(possibleSquare.getBuildingLevel() == currentSquare.getBuildingLevel() +1){

                 directionsArrayList.remove(dir);
             }

         }
return directionsArrayList;

    }
}
