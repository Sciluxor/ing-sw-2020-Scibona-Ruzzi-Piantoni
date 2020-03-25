package it.polimi.ingsw.network.message;

import java.io.Serializable;

public class Message implements Serializable {
    private  static final long serialVersion = -1L;

    private String sender;

    private final MessageType type;
    private  final MessageSubType subType;

    private String content;



 public Message(String sender,MessageType type,MessageSubType subType,String content){
     this.sender = sender;
     this.type = type;
     this.subType = subType;
     this.content = content;
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


    public String getContent() {
        return content;
    }


}
