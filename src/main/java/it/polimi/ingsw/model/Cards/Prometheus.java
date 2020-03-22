package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;

import java.util.ArrayList;

public class Prometheus extends Card {

    private boolean hasBuiltBefore;
    private boolean hasChooseBefore;

    public Prometheus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
        hasBuiltBefore = false;
        hasChooseBefore = false;
    }

    @Override
    public Response getFirstOperation() {
        return Response.BUILDBEFORE;
    }

    @Override
    public ArrayList<Directions> findWorkerMove(GameMap gameMap, Worker worker) {
        if(!hasBuiltBefore)
        return super.findWorkerMove(gameMap, worker);
        else{
            return null;
            //ArrayList<Directions> directions =
        }
    }

    @Override
    public Response executeWorkerMove(GameMap gameMap, Directions directions, Player player) {
        return super.executeWorkerMove(gameMap, directions, player);
    }

    @Override
    public Response executeBuild(GameMap gameMap, Building building, Directions directions, Worker worker) {
        return super.executeBuild(gameMap, building, directions, worker);
    }

    public boolean isHasBuiltBefore() {
        return hasBuiltBefore;
    }

    public void setHasBuiltBefore(boolean hasBuiltBefore) {
        this.hasBuiltBefore = hasBuiltBefore;
    }

    public boolean isHasChooseBefore() {
        return hasChooseBefore;
    }

    public void setHasChooseBefore(boolean hasChooseBefore) {
        this.hasChooseBefore = hasChooseBefore;
    }

}
