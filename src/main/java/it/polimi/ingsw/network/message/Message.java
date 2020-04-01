package it.polimi.ingsw.network.message;

import java.io.Serializable;

public class Message implements Serializable {
    private  static final long serialVersion = -1L;

    private String sender;
    private String nickName = "default";

    private final MessageType type;
    private  final MessageSubType subType;

    private String message = "standard message";

    public Message(String sender,MessageType type,MessageSubType subType,String message){
        this.sender = sender;
        this.type = type;
        this.subType = subType;
        this.message = message;
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
        return message;
    }
}
