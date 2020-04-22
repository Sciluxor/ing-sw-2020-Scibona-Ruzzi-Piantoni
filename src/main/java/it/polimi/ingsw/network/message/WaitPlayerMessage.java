package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.player.Color;

import java.util.ArrayList;

public class WaitPlayerMessage extends Message {

    private ArrayList<String> nickNames;
    private ArrayList<Color> colors;


    public WaitPlayerMessage(String sender,MessageSubType subType,String message) {
        super(sender, MessageType.WAITPLAYER, subType,message);
        nickNames = new ArrayList<>();
        colors = new ArrayList<>();
    }

    public ArrayList<String> getNickNames() {
        return nickNames;
    }

    public void addNickName(String nickName) {

        this.nickNames.add(nickName);
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void addColor(Color color) {

        this.colors.add(color);
    }
}
