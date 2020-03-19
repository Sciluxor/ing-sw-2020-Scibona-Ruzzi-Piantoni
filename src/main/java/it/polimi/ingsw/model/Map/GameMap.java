package it.polimi.ingsw.model.Map;

import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;

import java.util.ArrayList;
import java.util.HashMap;

public class GameMap {



    private ArrayList<Square> gameMap;
    private final Integer perimeterPosition = 16;
    private HashMap<Worker, Square> workersPosition;
    private ArrayList<Player> playersList;

    public GameMap(ArrayList<Square> gameMap, HashMap<Worker, Square> workersPosition, ArrayList<Player> playersList) {
        this.gameMap = gameMap;
        this.workersPosition = workersPosition;
        this.playersList = playersList;
    }

    //
    //function to find all the reachable square moving from a specific square
    //

    public ArrayList<Directions> reachableSquares(Worker worker){
          int level_position = worker.getBoardPosition().getBuildingLevel();
          HashMap<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();

          ArrayList<Directions> reachableSquares = new ArrayList<Directions>();

          for(Directions dir: Directions.values()){
              int squareTile  =canAccess.get(dir);

              if(squareTile != 0) { //rivedere questo if

                  Square possibleSquare = gameMap.get(squareTile- 1);
                  if(!possibleSquare.isHasPlayer() && (possibleSquare.getBuildingLevel() == level_position || possibleSquare.getBuildingLevel() == level_position +1)
                          && possibleSquare.getBuilding() != Building.DOME ){
                      reachableSquares.add(dir);
                  }



              }
          }

return reachableSquares;
    }

    //
    //function that change the position of the worker
    //

    public void moveWorkerTo(Player player, Directions direction){
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

        ArrayList<Directions> buildableSquare = new ArrayList<Directions>();
        HashMap<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();

        for(Directions dir: Directions.values()){

              int squareTile = canAccess.get(dir);

              if(squareTile != 0){

                  Square possibleBuild = gameMap.get(squareTile -1);
                  if(possibleBuild.getBuilding() != Building.DOME){

                      buildableSquare.add(dir);
                  }
              }
        }

        return buildableSquare;
    }

    //
    //function that build in the position selected,with the building selected
    //

    public void buildInSquare(Worker worker, Directions direction,Building building){







    }

    public ArrayList<Square> workersSquares(Player actualPlayer){
        ArrayList<Square> workerSquare = new ArrayList<>();

        for(Worker worker : actualPlayer.getWorkers()){
            workerSquare.add(worker.getBoardPosition());

        }
        return workerSquare;
    }

    public ArrayList<Square> getGameMap(){ return gameMap;}

    public ArrayList<Player> getPlayersList(){ return playersList;}

    public  boolean isInPerimeter(Integer tile){

        if(tile > perimeterPosition)
                return true;

        return false;

    }
}
