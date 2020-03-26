package it.polimi.ingsw.network.message;

public class gameStartedMessage extends Message {
    private int playersNumber;
    public gameStartedMessage(String sender, MessageSubType subType, int playersNumber) {
        super(sender, MessageType.GAMESTART, subType);
        this.playersNumber = playersNumber;
    }


    public int getPlayersNumber(){
        return playersNumber;
    }
}
