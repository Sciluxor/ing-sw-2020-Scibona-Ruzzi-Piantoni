package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;

public class Card {

    private String name;
    private String description;
    private boolean isPlayableIn3;
    private CardType type;
    private CardSubType subType;

    public Card (String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType)
    {
        this.name = name;
        this.description = description;
        this.isPlayableIn3 = isPlayableIn3;
        this.type = type;
        this.subType = subType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPlayableIn3() {
        return isPlayableIn3;
    }

    public void setPlayableIn3(boolean playableIn3) {
        isPlayableIn3 = playableIn3;
    }

    public CardType getType() { return type;}

    public void setType(CardType type) {
        this.type = type;
    }

    public CardSubType getSubType() {
        return subType;
    }

    public void setSubType(CardSubType subType) {
        this.subType = subType;
    }

    public void findWorkerMove(GameMap gameMap){}

    public void executeWorkerMove(GameMap gameMap, Directions directions){}

    public void findPossibleBuild(GameMap gameMap){}

    public void executeBuild(GameMap gameMap, Building building, Directions directions){}

    public void checkVictory(){}

}
