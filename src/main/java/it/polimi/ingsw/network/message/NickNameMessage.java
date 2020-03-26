package it.polimi.ingsw.network.message;

public class NickNameMessage extends Message {

    private String nickName;


    public NickNameMessage(String sender,MessageSubType subType,String nickName) {
        super(sender, MessageType.NICK, subType);
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }
}
