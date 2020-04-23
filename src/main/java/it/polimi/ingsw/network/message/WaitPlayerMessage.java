package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.player.Color;

import java.util.ArrayList;
import java.util.List;

public class WaitPlayerMessage extends Message {

    private List<String> nickNames;
    private List<Color> colors;


    public WaitPlayerMessage(String sender,MessageSubType subType,String message) {
        super(sender, MessageType.WAITPLAYER, subType,message);
        nickNames = new ArrayList<>();
        colors = new ArrayList<>();
    }

    public List<String> getNickNames() {
        return nickNames;
    }

    public void addNickName(String nickName) {

        this.nickNames.add(nickName);
    }

    public List<Color> getColors() {
        return colors;
    }

    public void addColor(Color color) {

        this.colors.add(color);
    }
}
