package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;

import java.util.ArrayList;

public class Hypnus extends Card {

    public Hypnus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    @Override
    public ArrayList<Directions> canMove(GameMap gameMap, Worker worker) {
        return super.canMove(gameMap, worker);
    }





}
