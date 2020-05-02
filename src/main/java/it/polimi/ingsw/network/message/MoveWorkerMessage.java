package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class MoveWorkerMessage extends Message{

    private Directions direction;
    private List<Square> modifiedSquare;
    private Response winResponse;
    private Player winnerPlayer;

    public MoveWorkerMessage(String sender, String nickName, MessageSubType subType, Directions direction, Response winResponse, Player winnerPlayer
            , List<Square> squares) {
        super(sender, nickName, MessageType.MOVEWORKER, subType);
        this.direction = direction;
        this.winResponse = winResponse;
        this.modifiedSquare = squares;
        this.winnerPlayer = winnerPlayer;
    }

    public Directions getDirection() {
        return direction;
    }

    public List<Square> getModifiedSquare() {
        return modifiedSquare;
    }

    public Response getWinResponse() {
        return winResponse;
    }

    public Player getWinnerPlayer() {
        return winnerPlayer;
    }
}
