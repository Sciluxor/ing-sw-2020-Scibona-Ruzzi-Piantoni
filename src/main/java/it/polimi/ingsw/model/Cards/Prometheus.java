package it.polimi.ingsw.model.Cards;

public class Prometheus extends Card {

    private boolean hasBuiltBefore;
    private boolean hasChooseBefore;

    public Prometheus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
        hasBuiltBefore = false;
        hasChooseBefore = false;
    }

    public boolean isHasBuiltBefore() {
        return hasBuiltBefore;
    }

    public void setHasBuiltBefore(boolean hasBuiltBefore) {
        this.hasBuiltBefore = hasBuiltBefore;
    }

    public boolean isHasChooseBefore() {
        return hasChooseBefore;
    }

    public void setHasChooseBefore(boolean hasChooseBefore) {
        this.hasChooseBefore = hasChooseBefore;
    }

}
