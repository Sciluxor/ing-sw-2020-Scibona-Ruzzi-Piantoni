package it.polimi.ingsw.network.message;

public class WaitingPlayerMessage extends Message {
    String message;
    public WaitingPlayerMessage(String sender, MessageSubType subType) {
        super(sender, MessageType.WAITPLAYER, subType);
        this.message = "waiting the match to start...";
    }

    public String getMessage() {
        return message;
    }
}
