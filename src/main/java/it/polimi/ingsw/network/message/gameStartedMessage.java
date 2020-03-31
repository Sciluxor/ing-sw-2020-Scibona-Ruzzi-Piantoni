package it.polimi.ingsw.network.message;

public class gameStartedMessage extends Message {
    private int playersNumber;
    private String gameID;

    public gameStartedMessage(String sender, MessageSubType subType, int playersNumber, String gameID) {
        super(sender, MessageType.GAMESTART, subType);
        this.playersNumber = playersNumber;
        this.gameID = gameID;
    }


    public int getPlayersNumber(){
        return playersNumber;
    }

    public String getGameID() {
        return gameID;
    }
}
