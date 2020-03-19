package it.polimi.ingsw.model.Map;

import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMap {

    private ArrayList<Square> gameMap;
    private HashMap<Worker, Square> workersPosition;
    private ArrayList<Player> playersList;

    public GameMap(ArrayList<Square> gameMap){
        this.gameMap = gameMap;
    }

    public ArrayList<Directions> reachableSquares(Worker worker){ } //da implementare

    public void moveWorkerTo(Worker worker, Directions direction){
        worker.setPreviousBoardPosition(worker.getBoardPosition());
        //worker.setBoardPosition();
    } //da implementare

    public ArrayList<Directions> buildableSquare(Worker worker){ } //da implementare

    public void buildInSquare(Worker worker, Directions direction){ } //da implementare

    public ArrayList<Square> workersSquares(Player actualPlayer){
        return actualPlayer.getCurrentWorker().getBoardPosition().getCanAccess(); // da implementare
    }

    public ArrayList<Square> getGameMap(){ return gameMap;}

    public ArrayList<Player> getPlayersList(){ return playersList;}

    public  boolean checkPerimeter(){ return false;} //da implementare
}
