package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.TurnStatus;
import it.polimi.ingsw.model.Response;

public class Chronus extends Card {

    public Chronus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }


    @Override
    public Response checkVictory(GameMap gameMap, Player player) {
        if(gameMap == null || player == null)
            throw new NullPointerException("null gameMap or player");

        if(countTower(gameMap))
            return Response.WINTOWERS;
        if (!player.getTurnStatus().equals(TurnStatus.IDLE) && player.getCurrentWorker().getBoardPosition().getBuildingLevel() == 3 && player.getCurrentWorker().getPreviousBoardPosition().getBuildingLevel() == 2)
            return  Response.WIN;
        return Response.NOTWIN;
    }

    public boolean countTower(GameMap gameMap) {
        /*if(gameMap == null)
            throw new NullPointerException("null gameMap");*/

        int counter = 0;
        for(Square x : gameMap.getGameMap()) {
            if (x.getBuilding().equals(Building.DOME) && x.getBuildingLevel() == 4)
                counter++;
            if(counter == 5)
                return true;
        }
        return false;
    }
}
