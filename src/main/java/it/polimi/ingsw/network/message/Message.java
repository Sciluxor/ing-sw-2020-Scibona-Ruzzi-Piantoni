package it.polimi.ingsw.network.message;

import java.io.Serializable;

public class Message implements Serializable {
    private  static final long serialVersion = -1L;

    private String sender;

    private final MessageType type;
    private  final MessageSubType subType;




 public Message(String sender,MessageType type,MessageSubType subType){
     this.sender = sender;
     this.type = type;
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



}
