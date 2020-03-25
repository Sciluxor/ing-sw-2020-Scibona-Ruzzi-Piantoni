package it.polimi.ingsw.network.message;

import java.io.Serializable;

public class Message implements Serializable {
    private  static final long serialVersion = -1L;

    private final MessageType type;
    private  final MessageSubType subType;

    private String content;



 public Message(MessageType type,MessageSubType subType,String content){
     this.type = type;
     this.subType = subType;
     this.content = content;
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
