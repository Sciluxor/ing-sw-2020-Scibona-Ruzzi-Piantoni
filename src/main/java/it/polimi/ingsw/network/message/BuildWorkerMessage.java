package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

/**
 * Class that extends Message and Represent the Message with The Build made by the Client
 * @author alessandroruzzi
 * @version 1.0
 * @since 2020/06/20
 */

public class BuildWorkerMessage extends Message {

    private Directions direction;
    private Building building;
    private List<Square> modifiedSquare;
    private Response winResponse;
    private Player winnerPlayer;

    /**
     * Public constructor of the message
     * @param sender The Client that send the Message
     * @param nickName Nickname selected by the Client
     * @param direction The direction in which to build
     * @param building The building used for the build
     * @param winResponse The Response that says if the player has win with this build
     * @param winnerPlayer The winner, selected only if there is a winner
     * @param squares The squares modified by this build
     */

    public BuildWorkerMessage(String sender, String nickName,Directions direction, Building building, Response winResponse, Player winnerPlayer, List<Square> squares) {
        super(sender, nickName, MessageType.BUILDWORKER, MessageSubType.ANSWER);
        this.direction = direction;
        this.building = building;
        this.modifiedSquare = squares;
        this.winResponse = winResponse;
        this.winnerPlayer = winnerPlayer;
    }

    /**
     * Public constructor for the update message forwarded to the other Client
     * @param sender Sender of the message
     * @param nickName NickName of the message
     * @param squares Squares modified by the Build
     */

    public BuildWorkerMessage(String sender, String nickName,List<Square> squares) {
        super(sender, nickName, MessageType.BUILDWORKER,MessageSubType.UPDATE);
        this.modifiedSquare = squares;
    }

    /**
     * Get the direction of the build
     * @return The direction
     */

    public Directions getDirection() {
        return direction;
    }

    /**
     * Get the Building used for the Build
     * @return The Building type
     */

    public Building getBuilding() {
        return building;
    }

    /**
     * Get the Squares modified by the build
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
