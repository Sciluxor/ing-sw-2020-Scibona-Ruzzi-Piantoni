package it.polimi.ingsw.network.message;

public class GameStartedMessage extends Message {
    private String gameID;



    public GameStartedMessage(String sender, MessageSubType subType, String gameID) {
        super(sender, MessageType.GAMESTART, subType);
        this.gameID = gameID;
    }

    public String getGameID() {
        return gameID;
    }


}
