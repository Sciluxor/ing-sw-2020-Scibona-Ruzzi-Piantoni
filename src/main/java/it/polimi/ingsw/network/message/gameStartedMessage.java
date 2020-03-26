package it.polimi.ingsw.network.message;

public class gameStartedMessage extends Message {
    public gameStartedMessage(String sender, MessageSubType subType) {
        super(sender, MessageType.GAMESTART, subType);
    }

}
