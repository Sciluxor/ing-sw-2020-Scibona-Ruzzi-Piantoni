package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.utils.ConstantsContainer;

import java.util.List;

public class Zeus extends Card {

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
