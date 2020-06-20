package it.polimi.ingsw.network.message;

import java.io.Serializable;

/**
 * Class that represents General Messages of the Game
 * @author alessandroruzzi
 * @version 1.0
 * @since 2020/06/20
 */

public class Message implements Serializable {
    private  static final long SERIALVERSION = -1L;

    private String sender;
    private String nickName = "def";

    private final MessageType type;
    private  MessageSubType subType;

    private String info = "standard message";

    /**
     * Public constructor for messages used by the Server(Without Nickname)
     * @param sender The Sender of the message
     * @param type The type of the message
     * @param subType The subtype of the message
     * @param message The String that contains a message
     */

    public Message(String sender,MessageType type,MessageSubType subType,String message){
        this.sender = sender;
        this.type = type;
        this.subType = subType;
        this.info= message;
    }

    /**
     * Public constructor for messages used by the Server(Without Nickname), used for messages that doesn't need a String Message
     * @param sender The Sender of the message
     * @param type The type of the message
     * @param subType The subtype of the message
     */

    public Message(String sender,MessageType type,MessageSubType subType){
        this.sender = sender;
        this.type = type;
        this.subType = subType;
    }

    /**
     * Public constructor for messages used by the Client(With Nickname), used for messages that doesn't nedd a String Message
     * @param sender The Sender of the message
     * @param nickName Nickname used by the Client in a specific Game
     * @param type The type of the message
     * @param subType The subtype of the message
     */

    public Message(String sender,String nickName,MessageType type,MessageSubType subType){
     this.sender = sender;
     this.type = type;
     this.subType = subType;
     this.nickName = nickName;
    }

    /**
     * Public constructor for messages used by the Client(With Nickname), used for messages that doesn't nedd a String Message
     * @param sender The Sender of the message
     * @param nickName Nickname used by the Client in a specific Game
     * @param type The type of the message
     * @param subType The subtype of the message
     * @param message The String that contains a message
     */

    public Message(String sender,String nickName,MessageType type,MessageSubType subType,String message){
        this.sender = sender;
        this.type = type;
        this.nickName = nickName;
        this.subType = subType;
        this.info = message;
    }

    /**
     * Get the Sender of the Message
     * @return The Sender of the message
     */

    public String getSender(){
     return sender;
    }

    /**
     * Get the Nickname that the Client that send the Message is using during the match
     * @return The Nickname of the Client
     */

    public String getNickName(){return nickName;}

    /**
     * Get the Type of the Message
     * @return The Type of the Message
     */

    public MessageType getType() {
        return type;
    }

    /**
     * Set the Message SubType
     * @param subType The new Message SubType
     */

    public void setMessageSubType(MessageSubType subType){
        this.subType = subType;
    }

    /**
     * Get the SubType of the Message
     * @return The SubType of the Message
     */

    public MessageSubType getSubType() {
        return subType;
    }

    /**
     * Get The String that contain the Message
     * @return The String that contain the Message
     */

    public String getMessage() {
        return info;
    }
}
