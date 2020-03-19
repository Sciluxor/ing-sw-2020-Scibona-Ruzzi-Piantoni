package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Map.Square;

public class Worker {

    private WorkerName name;
    private Square boardPosition;
    private  Square previousBoardPosition;
    private  Square previousBuilPosition;

    public Worker(WorkerName name, Square boardPosition, Square previousBoardPosition, Square previousBuilPosition){

        this.name = name;
        this.boardPosition = boardPosition;
        this.previousBoardPosition = previousBoardPosition;
        this.previousBuilPosition = previousBuilPosition;
    }

    public Square getBoardPosition() { return boardPosition;}

    public void setBoardPosition(Square boardPosition) { this.boardPosition = boardPosition;}

    public Square getPreviousBoardPosition() { return previousBoardPosition;}

    public void setPreviousBoardPosition(Square previousBoardPosition) { this.previousBoardPosition = previousBoardPosition;}

    public Square getPreviousBuilPosition() { return previousBuilPosition;}

    public void setPreviousBuilPosition(Square previousBuilPosition) { this.previousBuilPosition = previousBuilPosition;}

}
