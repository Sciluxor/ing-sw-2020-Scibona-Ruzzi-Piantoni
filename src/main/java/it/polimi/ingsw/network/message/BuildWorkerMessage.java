package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;

public class BuildWorkerMessage extends Message {

    private Directions direction;
    private Building building;

    public BuildWorkerMessage(String sender, String nickName, MessageType type, MessageSubType subType, Directions direction, Building building) {
        super(sender, nickName, MessageType.BUILDWORKER, subType);
    }

    public Directions getDirection() {
        return direction;
    }

    public Building getBuilding() {
        return building;
    }

}
