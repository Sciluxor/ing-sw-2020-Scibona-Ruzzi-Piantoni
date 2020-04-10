package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.Square;

import java.sql.Array;
import java.util.ArrayList;

public class MoveWorkerMessage extends Message{

    private Directions direction;
    private ArrayList<Square> modifiedSquare;
    private boolean hasWin;

    public MoveWorkerMessage(String sender, String nickName, MessageSubType subType,Directions direction,boolean hasWin,ArrayList<Square> squares) {
        super(sender, nickName, MessageType.MOVEWORKER, subType);
        this.direction = direction;
        this.hasWin = hasWin;
        this.modifiedSquare = squares;
    }

    public Directions getDirection() {
        return direction;
    }

    public ArrayList<Square> getModifiedSquare() {
        return modifiedSquare;
    }

    public boolean hasWin() {
        return hasWin;
    }
}
