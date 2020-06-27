package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.utils.ConstantsContainer;

import java.util.List;

/**
 * Class that extends Card that build the Prometheus card
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/27
 */

public class Zeus extends Card {

    /**
     * Class Builder
     * @param name Name of the card
     * @param description Description of the power of the card
     * @param isPlayableIn3 Boolean saying if the card is playable in 3 Players
     * @param type Type of the card
     * @param subType Subtype of the card
     */

    public Zeus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    @Override
    public List<Directions> findPossibleBuild(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");
        List<Directions> direction = gameMap.buildableSquare(worker);
        if(worker.getBoardPosition().getBuildingLevel() < ConstantsContainer.MAXBUILDINGLEVEL -1)
             direction.add(Directions.CENTER);
        return direction;
    }

}
