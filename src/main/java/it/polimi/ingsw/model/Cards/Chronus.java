package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Worker;

public class Chronus extends Card {

    public Chronus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    @Override
    public Response checkVictory(GameMap gameMap, Worker worker) {
        if(worker == null)
            throw new NullPointerException("null worker");

        if((worker.getBoardPosition().getBuildingLevel() == 3 && worker.getPreviousBoardPosition().getBuildingLevel() == 2) || countTower(gameMap))
            return Response.WIN;
        return Response.NOTWIN;
    }

    public boolean countTower(GameMap gameMap) {
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
