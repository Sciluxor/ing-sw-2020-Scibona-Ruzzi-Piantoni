package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Map.Directions;

public class MoveWorkerMessage extends Message{

    private Directions direction;

    public MoveWorkerMessage(String sender, String nickName, MessageSubType subType,Directions direction) {
        super(sender, nickName, MessageType.MOVEWORKER, subType);
        this.direction = direction;

    }

    public Directions getDirection() {
        return direction;
    }
}
