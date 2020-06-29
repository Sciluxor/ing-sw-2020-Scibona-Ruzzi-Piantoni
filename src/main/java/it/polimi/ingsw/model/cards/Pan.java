package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.utils.ConstantsContainer;

/**
 * Class that extends Card that build the Pan card
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/27
 */

public class Pan extends Card {

    /**
     * Public constructor
     * @param name Name of the card
     * @param description Description of the power of the card
     * @param isPlayableIn3 Boolean saying if the card is playable in 3 Players
     * @param type Type of the card
     * @param subType Subtype of the card
     */

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
