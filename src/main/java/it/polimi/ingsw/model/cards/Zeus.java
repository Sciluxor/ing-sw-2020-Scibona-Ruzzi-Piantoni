package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Worker;

import java.util.List;

public class Zeus extends Card {

    public Zeus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }
    @Override
    public List<Directions> findPossibleBuild(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");
        List<Directions> direction = gameMap.reachableSquares(worker);
        direction.add(Directions.CENTER);
        return direction;
    }

}
