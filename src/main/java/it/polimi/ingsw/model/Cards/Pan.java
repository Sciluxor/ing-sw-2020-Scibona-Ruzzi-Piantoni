package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Response;

public class Pan extends Card {

    public Pan(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    @Override
    public Response checkVictory(GameMap gameMap, Player player) {
        if(gameMap == null || player == null)
            throw new NullPointerException("null gameMap or player");

        if((player.getCurrentWorker().getBoardPosition().getBuildingLevel() == 3 && player.getCurrentWorker().getPreviousBoardPosition().getBuildingLevel() == 2) ||
                ((player.getCurrentWorker().getPreviousBoardPosition().getBuildingLevel() - player.getCurrentWorker().getBoardPosition().getBuildingLevel()) >= 2))
            return Response.WIN;
        return Response.NOTWIN;
    }
}
