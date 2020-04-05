package it.polimi.ingsw.network.message;

public class ReconnectionMessage extends Message {
    private String gameID;
    private String nickname;

    public ReconnectionMessage(String sender, MessageSubType subType, String gameID, String nickname) {
        super(sender, MessageType.DISCONNECTION, subType);
        this.gameID = gameID;
        this.nickname = nickname;

    }

    public String getGameID(){
        return gameID;
    }

    public String getNickname() {
        return nickname;
    }
}