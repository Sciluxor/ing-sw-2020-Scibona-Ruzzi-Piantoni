package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Map.Square;

public class Worker {

    private WorkerName name;
    private Square boardPosition;
    private  Square previousBoardPosition;
    private  Square previousBuildPosition;

    public Worker(WorkerName name, Square boardPosition, Square previousBoardPosition, Square previousBuildPosition){

        this.name = name;
        this.boardPosition = boardPosition;
        this.previousBoardPosition = previousBoardPosition;
        this.previousBuildPosition = previousBuildPosition;
    }

    public Square getBoardPosition() { return boardPosition;}

    public void setBoardPosition(Square boardPosition) { this.boardPosition = boardPosition;}

    public Square getPreviousBoardPosition() { return previousBoardPosition;}

    public void setPreviousBoardPosition(Square previousBoardPosition) { this.previousBoardPosition = previousBoardPosition;}

    public Square getPreviousBuildPosition() { return previousBuildPosition;}

    public void setPreviousBuildPosition(Square previousBuildPosition) { this.previousBuildPosition = previousBuildPosition;}

    public WorkerName getName() { return name;}
}
