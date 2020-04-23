package it.polimi.ingsw.network.message;

import java.util.List;

public class ChallengerChoiceMessage extends Message {

    private String firstPlayer;
    private List<String> cards;

    public ChallengerChoiceMessage(String sender, String nickName, MessageSubType subType, String firstPlayer,List<String> cards) {
        super(sender, nickName, MessageType.CHALLENGERCHOICE, subType);
        this.cards = cards;
        this.firstPlayer = firstPlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public List<String> getCards() {
        return cards;
    }
}
