package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.utils.ConstantsContainer;

public class Pan extends Card {

    public Pan(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    @Override
    public Response checkVictory(GameMap gameMap, Player player) {
        if(gameMap == null || player == null)
            throw new NullPointerException("null gameMap or player");

        if((player.getCurrentWorker().getBoardPosition().getBuildingLevel() == ConstantsContainer.WINNINGLEVEL
                 && player.getCurrentWorker().getPreviousBoardPosition().getBuildingLevel() == (ConstantsContainer.WINNINGLEVEL -1) ) ||
                ((player.getCurrentWorker().getPreviousBoardPosition().getBuildingLevel() - player.getCurrentWorker().getBoardPosition().getBuildingLevel()) >=
                        ConstantsContainer.PANLEVELWIN))
            return Response.WIN;
        return Response.NOTWIN;
    }
}
