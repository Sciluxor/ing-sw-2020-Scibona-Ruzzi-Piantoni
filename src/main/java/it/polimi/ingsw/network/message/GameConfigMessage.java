package it.polimi.ingsw.network.message;

public class GameConfigMessage extends Message {

    private String nickName;
    private int numberOfPlayer;
    private boolean isNickValid;
    private boolean isNumberPlayerValid;
    private boolean isNickUsed;


    public GameConfigMessage(String sender, MessageSubType subType, String nickName,int numberOfPlayer,boolean isNickValid,boolean isNickUsed,boolean isNumberPlayerValid) {
        super(sender, MessageType.NICK, subType);
        this.nickName = nickName;
        this.numberOfPlayer=numberOfPlayer;
        this.isNumberPlayerValid = isNumberPlayerValid;
        this.isNickUsed = isNickUsed;
        this.isNickValid = isNickValid;
    }

    public String getNickName() {
        return nickName;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }
}
