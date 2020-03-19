package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Player;

import java.util.ArrayList;

public class Atena extends Card {

    public Atena(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }



    @Override
    public ArrayList<Directions> eliminateInvalidMove(GameMap gameMap, ArrayList<Directions> directionsArrayList) {
        return super.eliminateInvalidMove(gameMap, directionsArrayList);
    }
}
