package it.polimi.ingsw.network.message;

import java.util.ArrayList;

public class ChallengerChoiceMessage extends Message {

    private String firstPlayer;
    private ArrayList<String> cards;

    public ChallengerChoiceMessage(String sender, String nickName, MessageSubType subType, String firstPlayer, ArrayList<String> cards) {
        super(sender, nickName, MessageType.CHALLENGERCHOICE, subType);
        this.cards = cards;
        this.firstPlayer = firstPlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public ArrayList<String> getCards() {
        return cards;
    }
}
