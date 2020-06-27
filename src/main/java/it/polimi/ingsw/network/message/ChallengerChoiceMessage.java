package it.polimi.ingsw.network.message;

import java.util.List;

/**
 * Class that extends Message and Represent the Message with Choice made by the Challenger
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/20
 */

public class ChallengerChoiceMessage extends Message {

    private String firstPlayer;
    private List<String> cards;

    /**
     * Public constructor for the Message
     * @param sender The Client that send the Message
     * @param nickName Nickname selected by the Client
     * @param subType The Subtype of the Message
     * @param firstPlayer The name of the first player chosen by the Challenger
     * @param cards The name of the cards chosen by the Challenger
     */

    public ChallengerChoiceMessage(String sender, String nickName, MessageSubType subType, String firstPlayer,List<String> cards) {
        super(sender, nickName, MessageType.CHALLENGERCHOICE, subType);
        this.cards = cards;
        this.firstPlayer = firstPlayer;
    }

    /**
     * Get the first player of the Game
     * @return The first player
     */

    public String getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Get the names of the cards chosen by the Challenger
     * @return a list of the cards names
     */

    public List<String> getCards() {
        return cards;
    }
}
