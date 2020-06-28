package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.map.Square;

import java.io.Serializable;

/**
 * Class that implements Serializable. It is used to implements all methods and declare all attributes of the worker object
 * @author edoardopiantoni, alessandroruzzi, luigiscibona
 * @version 1.0
 * @since 2020/06/28
 */

public class Worker implements Serializable {

    private final WorkerName name;
    private transient Square boardPosition;
    private transient Square previousBoardPosition;
    private transient Square previousBuildPosition;

    /**
     * Constructor method of the worker's object
     * @param name Name of the new worker
     */

    public Worker(WorkerName name){
        this.name = name;
    }

    /**
     * Method used to get tue board position of the current worker
     * @return boardPosition Square object corresponding to the worker's board position
     */

    public Square getBoardPosition() { return boardPosition;}

    /**
     * Method used to set the worker's board position
     * @param boardPosition Square object to set as board position
     */

    public void setBoardPosition(Square boardPosition) {
        if (boardPosition == null)
            throw new NullPointerException("boardPosition == null");

        this.boardPosition = boardPosition;
    }

    /**
     * Method used to get the previous board position of the current worker
     * @return previousBoardPosition Square object corresponding to the previous board position
     */

    public Square getPreviousBoardPosition() { return previousBoardPosition;}

    /**
     * Method used to set worker's previous board position
     * @param previousBoardPosition Square object to set as previous board position
     */

    public void setPreviousBoardPosition(Square previousBoardPosition) {
        if (previousBoardPosition == null)
            throw new NullPointerException("previousBoardPosition == null");

        this.previousBoardPosition = previousBoardPosition;
    }

    /**
     * Method used to get the previous build position
     * @return previousBuildPosition Square object corresponding to the previous square in which current player has built
     */

    public Square getPreviousBuildPosition() { return previousBuildPosition;}

    /**
     * Method used to set worker's previous build position
     * @param previousBuildPosition Square object to set as previous square in which worker has built
     */

    public void setPreviousBuildPosition(Square previousBuildPosition) {
        if (previousBuildPosition == null)
            throw new NullPointerException("boardPosition == null");

        this.previousBuildPosition = previousBuildPosition;
    }

    /**
     * Method used to get worker's name
     * @return name WorkerName of the current worker
     */

    public WorkerName getName() { return name;}

}
