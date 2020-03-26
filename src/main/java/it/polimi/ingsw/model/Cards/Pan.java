package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Worker;

public class Pan extends Card {

    public Pan(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    @Override
    public Response checkVictory(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        if((worker.getBoardPosition().getBuildingLevel() == 3 && worker.getPreviousBoardPosition().getBuildingLevel() == 2) ||
                ((worker.getPreviousBoardPosition().getBuildingLevel() - worker.getBoardPosition().getBuildingLevel()) >= 2))
            return Response.WIN;
        return Response.NOTWIN;
    }
}
