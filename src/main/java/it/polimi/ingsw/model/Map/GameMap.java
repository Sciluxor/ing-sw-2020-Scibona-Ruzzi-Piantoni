package it.polimi.ingsw.model.Map;

import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMap {



    private ArrayList<Square> gameMap;
    private final Integer perimeterPosition = 16;    //gestire in maniera migliori con un parser di costanti
    private HashMap<Worker, Square> workersPosition;

    public GameMap() {
        this.gameMap = MapLoader.loadMap();
    }

    //
    //function to find all the reachable square moving from a specific square
    //

    public ArrayList<Directions> reachableSquares(Worker worker){
          if(worker == null)
              throw new NullPointerException("null worker");
          int level_position = worker.getBoardPosition().getBuildingLevel();
          HashMap<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();
          ArrayList<Directions> reachableSquares = new ArrayList<>();

          for(Directions dir: Directions.values()){
              int squareTile  =canAccess.get(dir);
              if(squareTile > 0 && squareTile <= 25) { //rivedere questo if
                  Square possibleSquare = gameMap.get(squareTile- 1);
                  if(!possibleSquare.hasPlayer() && (possibleSquare.getBuildingLevel() == level_position || possibleSquare.getBuildingLevel() == level_position +1)
                          && possibleSquare.getBuilding() != Building.DOME ){
                      reachableSquares.add(dir);
                  }
              }
              else throw new IllegalArgumentException("problem with map Square");
          }

return reachableSquares;
    }

    //
    //function that change the position of the worker
    //

    public void moveWorkerTo(Player player, Directions direction){
        if(player == null || direction == null)
            throw new NullPointerException("null player or direction");
        Worker currentWorker = player.getCurrentWorker();
        currentWorker.setPreviousBoardPosition(currentWorker.getBoardPosition());
        currentWorker.getPreviousBoardPosition().setHasPlayer(false);
        currentWorker.setBoardPosition( gameMap.get(currentWorker.getBoardPosition().getCanAccess().get(direction) -1));
        currentWorker.getBoardPosition().setHasPlayer(true);
        currentWorker.getBoardPosition().setPlayer(player);
        currentWorker.getBoardPosition().setWorker(currentWorker);

    }

    //
    //function that change the position of the worker
    //

    public ArrayList<Directions> buildableSquare(Worker worker){
        if(worker == null)
            throw new NullPointerException("null worker");
        ArrayList<Directions> buildableSquare = new ArrayList<>();
        HashMap<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();

        for(Directions dir: Directions.values()){
              int squareTile = canAccess.get(dir);
              if(squareTile > 0 && squareTile <= 25){
                  Square possibleBuild = gameMap.get(squareTile -1);
                  if(!possibleBuild.getBuilding().equals(Building.DOME) && !possibleBuild.hasPlayer()){
                      buildableSquare.add(dir);
                  }
              }
              else throw new IllegalArgumentException("problem with map square");
        }

        return buildableSquare;
    }

    //
    //function that build in the position selected,with the type of building selected
    //

    public boolean buildInSquare(Worker worker, Directions direction,Building building){
        if(worker == null || direction == null || building == null)
            throw new NullPointerException("null worker or building or direction");
            Square buildingSquare = gameMap.get(worker.getBoardPosition().getCanAccess().get(direction) -1);
            if(building.equals(Building.mapNext(buildingSquare.getBuilding()))){
                worker.setPreviousBuildPosition(buildingSquare);
                buildingSquare.setBuilding(building);
                buildingSquare.setBuildingLevel(buildingSquare.getBuildingLevel() +1);

                return true;
            }

return false;

    }

    //
    //function that return the positions of both player's workers
    //

    public ArrayList<Square> workersSquares(Player actualPlayer){
        ArrayList<Square> workerSquare = new ArrayList<>();

        for(Worker worker : actualPlayer.getWorkers()){
            workerSquare.add(worker.getBoardPosition());

        }
        return workerSquare;
    }

    public ArrayList<Square> getGameMap(){ return gameMap;}


    //
    //function that check if a square is in the perimeter
    //

    public  boolean isInPerimeter(Integer tile){
        return tile > perimeterPosition;
    }
}
