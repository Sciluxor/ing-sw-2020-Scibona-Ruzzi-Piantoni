package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Worker;

import java.util.ArrayList;

public class Zeus extends Card {

    public Zeus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }
    @Override
    public ArrayList<Directions> findPossibleBuild(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");
        ArrayList<Directions> direction = gameMap.reachableSquares(worker);
        direction.add(Directions.CENTER);
        return direction;
    }

}
