package it.polimi.ingsw.network.message;

public class PlayerNumberMessage extends Message {
    private int playersNumber;
    public PlayerNumberMessage(String sender, MessageSubType subType,int number) {
        super(sender, MessageType.NUMBERPLAYER, subType);
        this.playersNumber = number;

    }
    public int getPlayersNumber(){
        return playersNumber;
    }
}
