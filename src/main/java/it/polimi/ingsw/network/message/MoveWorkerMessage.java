package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 * Class that extends Message and Represent the Message with The Move made by the Client
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/20
 */

public class MoveWorkerMessage extends Message{

    private Directions direction;
    private List<Square> modifiedSquare;
    private Response winResponse;
    private Player winnerPlayer;

    /**
     * Public constructor of the message
     * @param sender The Client that send the Message
     * @param nickName Nickname selected by the Client
     * @param direction The direction in which to move the worker
     * @param winResponse The Response that says if the player has win with this move
     * @param winnerPlayer The winner, selected only if there is a winner
     * @param squares The squares modified by this move
     */

    public MoveWorkerMessage(String sender, String nickName, Directions direction, Response winResponse, Player winnerPlayer
            , List<Square> squares) {
        super(sender, nickName, MessageType.MOVEWORKER,MessageSubType.ANSWER);
        this.direction = direction;
        this.winResponse = winResponse;
        this.modifiedSquare = squares;
        this.winnerPlayer = winnerPlayer;
    }

    /**
     * Public constructor for the update message forwarded to the other Client
     * @param sender Sender of the message
     * @param nickName NickName of the message
     * @param squares Squares modified by the Move
     */

    public MoveWorkerMessage(String sender, String nickName,List<Square> squares) {
        super(sender, nickName, MessageType.MOVEWORKER,MessageSubType.UPDATE);
        this.modifiedSquare = squares;
    }

    /**
     * Get the direction of the move
     * @return The direction
     */

    public Directions getDirection() {
        return direction;
    }

    /**
     * Get the Squares modified by the move
     * @return A list of modified Squares
     */

    public List<Square> getModifiedSquare() {
        return modifiedSquare;
    }

    /**
     * Get the Win Response
     * @return The Win Response
     */

    public Response getWinResponse() {
        return winResponse;
    }

    /**
     * Get the Winner
     * @return The Winner
     */

    public Player getWinnerPlayer() {
        return winnerPlayer;
    }
}
