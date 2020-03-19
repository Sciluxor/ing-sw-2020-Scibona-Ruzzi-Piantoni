package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Player.Player;

import java.util.ArrayList;

public class Hypnus extends Card {

    public Hypnus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    public ArrayList<Directions> checkIfCanMove() {
        return null;
    }

    public void assignPermanentConstraint(ArrayList<Player> playerArrayList) {

    }

}
