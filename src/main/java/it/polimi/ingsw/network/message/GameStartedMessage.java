package it.polimi.ingsw.network.message;

/**
 * Class that extends Message and Represent the Message That Notify that the Game is started
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/20
 */

public class GameStartedMessage extends Message {
    private String gameID;

    /**
     * Public constructor of the message
     * @param sender The sender of the Message
     * @param subType The subtype of the Message
     * @param gameID The GameID of the Game
     */

    public GameStartedMessage(String sender, MessageSubType subType, String gameID) {
        super(sender, MessageType.GAMESTART, subType);
        this.gameID = gameID;
    }

    /**
     * Get the GameID of the Game in which the server has inserted the Client
     * @return The GameID of the Game
     */

    public String getGameID() {
        return gameID;
    }


}
