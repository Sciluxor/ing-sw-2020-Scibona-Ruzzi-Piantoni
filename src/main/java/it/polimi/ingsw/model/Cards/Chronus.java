package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.TurnStatus;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.utils.ConstantsContainer;

public class Chronus extends Card {

    public Chronus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }


    @Override
    public Response checkVictory(GameMap gameMap, Player player) {
        if(gameMap == null || player == null)
            throw new NullPointerException("null gameMap or player");

        if(countTower(gameMap))
            return Response.BUILDWIN;
        if (!player.getTurnStatus().equals(TurnStatus.IDLE) && player.getCurrentWorker().getBoardPosition().getBuildingLevel() == ConstantsContainer.WINNINGLEVEL
                && player.getCurrentWorker().getPreviousBoardPosition().getBuildingLevel() == (ConstantsContainer.WINNINGLEVEL -1) )
            return  Response.WIN;
        return Response.NOTWIN;
    }

    public boolean countTower(GameMap gameMap) {
        /*if(gameMap == null)
            throw new NullPointerException("null gameMap");*/

        int counter = 0;
        for(Square x : gameMap.getGameMap()) {
            if (x.getBuilding().equals(Building.DOME) && x.getBuildingLevel() == ConstantsContainer.MAXBUILDINGLEVEL)
                counter++;
            if(counter == ConstantsContainer.CHRONUSTOWERWIN)
                return true;
        }
        return false;
    }
}
