package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Square;

import java.io.Serializable;

public class Worker implements Serializable {

    private final WorkerName name;
    private Square boardPosition;
    private Square previousBoardPosition;
    private Square previousBuildPosition;

    public Worker(WorkerName name){
        this.name = name;
    }

    public Square getBoardPosition() { return boardPosition;}

    public void setBoardPosition(Square boardPosition) {
        if (boardPosition == null)
            throw new NullPointerException("boardPosition == null");

        this.boardPosition = boardPosition;
    }

    public Square getPreviousBoardPosition() { return previousBoardPosition;}

    public void setPreviousBoardPosition(Square previousBoardPosition) {
        if (previousBoardPosition == null)
            throw new NullPointerException("previousBoardPosition == null");

        this.previousBoardPosition = previousBoardPosition;
    }

    public Square getPreviousBuildPosition() { return previousBuildPosition;}

    public void setPreviousBuildPosition(Square previousBuildPosition) {
        if (previousBuildPosition == null)
            throw new NullPointerException("boardPosition == null");

        this.previousBuildPosition = previousBuildPosition;
    }

    public WorkerName getName() { return name;}

}
