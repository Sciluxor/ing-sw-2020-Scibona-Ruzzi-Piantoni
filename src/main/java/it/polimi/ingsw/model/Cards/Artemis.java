package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Square;

public class Artemis extends Card {

    private boolean hasMoved;
    private Square position;

    public Artemis(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
        hasMoved = false;
        position = null;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

}
