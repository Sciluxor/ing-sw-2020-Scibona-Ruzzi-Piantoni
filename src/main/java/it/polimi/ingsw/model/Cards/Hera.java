package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Worker;

public class Hera extends Card{

    public Hera(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    @Override
    public boolean isValidVictory(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        return !gameMap.isInPerimeter(worker.getBoardPosition().getTile());
    }

}
