package it.polimi.ingsw.network.message;

import java.io.Serializable;

public class Message implements Serializable {
    private  static final long SERIALVERSION = -1L;

    private String sender;
    private String nickName = "def";

    private final MessageType type;
    private  MessageSubType subType;

    private String info = "standard message";

    public Message(String sender,MessageType type,MessageSubType subType,String message){
        this.sender = sender;
        this.type = type;
        this.subType = subType;
        this.info= message;
    }

    public Message(String sender,MessageType type,MessageSubType subType){
        this.sender = sender;
        this.type = type;
        this.subType = subType;
    }

    public Message(String sender,String nickName,MessageType type,MessageSubType subType){
     this.sender = sender;
     this.type = type;
     this.subType = subType;
     this.nickName = nickName;
    }

    public Message(String sender,String nickName,MessageType type,MessageSubType subType,String message){
        this.sender = sender;
        this.type = type;
        this.nickName = nickName;
        this.subType = subType;
        this.info = message;
    }

    public void setMessageSubType(MessageSubType subType){
        this.subType = subType;
    }
    public String getSender(){
     return sender;
    }

    public MessageType getType() {
        return type;
    }

    public MessageSubType getSubType() {
        return subType;
    }

    public String getNickName(){return nickName;}

    public String getMessage() {
        return info;
    }
}
