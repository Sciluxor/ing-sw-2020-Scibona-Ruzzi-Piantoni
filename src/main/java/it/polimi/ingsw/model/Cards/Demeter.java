package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Square;

public class Demeter extends Card {

    private boolean hasBuilt;
    private Square position;

    public Demeter(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
        hasBuilt = false;
        position = null;
    }

    public boolean isHasBuilt() {
        return hasBuilt;
    }

    public void setHasBuilt(boolean hasBuilt) {
        this.hasBuilt = hasBuilt;
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

}
