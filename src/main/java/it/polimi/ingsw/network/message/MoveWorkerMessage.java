package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Response;
import java.util.ArrayList;

public class MoveWorkerMessage extends Message{

    private Directions direction;
    private ArrayList<Square> modifiedSquare;
    private Response winResponse;

    public MoveWorkerMessage(String sender, String nickName, MessageSubType subType,Directions direction,Response winResponse,ArrayList<Square> squares) {
        super(sender, nickName, MessageType.MOVEWORKER, subType);
        this.direction = direction;
        this.winResponse = winResponse;
        this.modifiedSquare = squares;
    }

    public Directions getDirection() {
        return direction;
    }

    public ArrayList<Square> getModifiedSquare() {
        return modifiedSquare;
    }

    public Response getWinResponse() {
        return winResponse;
    }
}
