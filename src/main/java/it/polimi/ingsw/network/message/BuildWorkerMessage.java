package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.Response;

import java.util.List;

public class BuildWorkerMessage extends Message {

    private Directions direction;
    private Building building;
    private List<Square> modifiedSquare;
    private Response winResponse;

    public BuildWorkerMessage(String sender, String nickName, MessageSubType subType, Directions direction, Building building,Response winResponse,List<Square> squares) {
        super(sender, nickName, MessageType.BUILDWORKER, subType);
        this.direction = direction;
        this.building = building;
        this.modifiedSquare = squares;
        this.winResponse = winResponse;
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

}
