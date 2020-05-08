package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.player.Player;

import java.util.List;

public class BuildWorkerMessage extends Message {

    private Directions direction;
    private Building building;
    private List<Square> modifiedSquare;
    private Response winResponse;
    private Player winnerPlayer;

    public BuildWorkerMessage(String sender, String nickName,Directions direction, Building building, Response winResponse, Player winnerPlayer, List<Square> squares) {
        super(sender, nickName, MessageType.BUILDWORKER, MessageSubType.ANSWER);
        this.direction = direction;
        this.building = building;
        this.modifiedSquare = squares;
        this.winResponse = winResponse;
        this.winnerPlayer = winnerPlayer;
    }

    public BuildWorkerMessage(String sender, String nickName,List<Square> squares) {
        super(sender, nickName, MessageType.BUILDWORKER,MessageSubType.UPDATE);
        this.modifiedSquare = squares;
    }

    public Directions getDirection() {
        return direction;
    }

    public Building getBuilding() {
        return building;
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
