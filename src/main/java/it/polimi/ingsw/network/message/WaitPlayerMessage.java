package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.player.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that extends Message and Represent the Message that notify about the players in the lobby
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/20
 */

public class WaitPlayerMessage extends Message {

    private List<String> nickNames;
    private List<Color> colors;

    /**
     * Public constructor of the message
     * @param sender The Sender of the message
     * @param subType The subtype of the message
     * @param message The String that contains a message
     */

    public WaitPlayerMessage(String sender,MessageSubType subType,String message) {
        super(sender, MessageType.WAITPLAYER, subType,message);
        nickNames = new ArrayList<>();
        colors = new ArrayList<>();
    }

    /**
     * Get The nicknames of the players in the lobby
     * @return A list of nicknames
     */

    public List<String> getNickNames() {
        return nickNames;
    }

    /**
     * Add a Nickname to the list of Nicknames
     * @param nickName The Nickname of the new player in the lobby
     */

    public void addNickName(String nickName) {

        this.nickNames.add(nickName);
    }

    /**
     * Get the colors of the players in the lobby
     * @return A list of colors
     */

    public List<Color> getColors() {
        return colors;
    }

    /**
     * Add color to the list of colors of the players
     * @param color The color of the new player in the lobby
     */

    public void addColor(Color color) {

        this.colors.add(color);
    }
}
