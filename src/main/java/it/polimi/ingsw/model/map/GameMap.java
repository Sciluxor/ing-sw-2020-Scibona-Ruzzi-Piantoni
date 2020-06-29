package it.polimi.ingsw.model.map;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.utils.ConstantsContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that represent the Map of the Game
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/28
 */

public class GameMap {

    private final List<Square> map;
    private final Square [][]linkToCoordinates = new Square[ConstantsContainer.MAXMAPPOSITION][ConstantsContainer.MAXMAPPOSITION];
    private final List<Square> modifiedSquare = new ArrayList<>();

    /**
     * Public constructor for the map, initialize all the parameters
     */

    public GameMap() {
        this.map = MapLoader.loadMap();
        for(Square square: map){
            Integer[] coordinates =  square.getCoordinates();
            linkToCoordinates[coordinates[0]][coordinates[1]] = square;
        }
    }

    /**
     * Method to obtain the number of tile from coordinates
     * @param coordinates The coordinates of the square to analise
     * @return The number of tile of the specific square
     */

    public Square getTileFromCoordinates(Integer[] coordinates){
        return linkToCoordinates[coordinates[0]][coordinates[1]];
    }

    /**
     * Method to find all the reachable square moving from a specific square
     * @param worker The worker placed in the specific square to analise
     * @return A list of possible directions in which to move
     */

    public List<Directions> reachableSquares(Worker worker){
          if(worker == null)
              throw new NullPointerException("null worker");
          int levelPosition = worker.getBoardPosition().getBuildingLevel();
          Map<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();
          List<Directions> reachableSquares = new ArrayList<>();

          for(Directions dir: Directions.values()){
              int squareTile  =canAccess.get(dir);
              if(squareTile > ConstantsContainer.MINMAPPOSITION && squareTile <= ConstantsContainer.MAXMAPPOSITION) {
                  Square possibleSquare = map.get(squareTile- 1);
                  if(!possibleSquare.hasPlayer() && (possibleSquare.getBuildingLevel() >= 0 && possibleSquare.getBuildingLevel() <= levelPosition + 1 && !worker.getBoardPosition().equals(possibleSquare) )
                          && possibleSquare.getBuilding() != Building.DOME ){
                      reachableSquares.add(dir);
                  }
              }
          }

           return reachableSquares;
    }

    /**
     * Method that move a specific worker in the map
     * @param player Player that own the worker to move
     * @param direction Direction in which to move the specific worker
     */

    public void moveWorkerTo(Player player, Directions direction){
        if(player == null || direction == null)
            throw new NullPointerException("null player or direction");
        clearModifiedSquare();
        Worker currentWorker = player.getCurrentWorker();
        currentWorker.setPreviousBoardPosition(currentWorker.getBoardPosition());
        modifiedSquare.add(currentWorker.getBoardPosition());
        currentWorker.getPreviousBoardPosition().setHasPlayer(false);
        currentWorker.setBoardPosition( map.get(currentWorker.getBoardPosition().getCanAccess().get(direction) - 1));
        currentWorker.getBoardPosition().setHasPlayer(true);
        currentWorker.getBoardPosition().setPlayer(player);
        currentWorker.getBoardPosition().setWorker(currentWorker);
        modifiedSquare.add(currentWorker.getBoardPosition());
    }

    /**
     * Method to find all the possible squares in which a specific worker can build
     * @param worker The worker placed in the specific square to analise
     * @return A list of possible directions in which to build
     */

    public List<Directions> buildableSquare(Worker worker){
        if(worker == null)
            throw new NullPointerException("null worker");
        List<Directions> buildableSquare = new ArrayList<>();
        Map<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();

        for(Directions dir: Directions.values()){
              int squareTile = canAccess.get(dir);
              if(squareTile > ConstantsContainer.MINMAPPOSITION && squareTile <= ConstantsContainer.MAXMAPPOSITION){
                  Square possibleBuild = map.get(squareTile - 1);
                  if(!possibleBuild.getBuilding().equals(Building.DOME) && !possibleBuild.hasPlayer() && !worker.getBoardPosition().equals(possibleBuild)){
                      buildableSquare.add(dir);
                  }
              }
        }

        return buildableSquare;
    }

    /**
     * Method that build in the position selected,with the type of building selected
     * @param worker The worker that will build
     * @param direction The direction in which to build
     * @param building The type of building to build
     * @return True if the type of building is correct, false otherwise
     */

    public boolean buildInSquare(Worker worker, Directions direction, Building building){
        if(worker == null || direction == null || building == null){
            throw new NullPointerException("null worker or building or direction");
        }
        clearModifiedSquare();
        Square buildingSquare = map.get(worker.getBoardPosition().getCanAccess().get(direction) - 1);
        if(building.equals(Building.mapNext(buildingSquare.getBuilding()))){
            worker.setPreviousBuildPosition(buildingSquare);
            buildingSquare.setBuilding(building);
            buildingSquare.addBuildingLevel();
            clearModifiedSquare();
            modifiedSquare.add(buildingSquare);

            return true;
        }
        return false;

    }

    /**
     * Method that place the workers of a specific player in the game map
     * @param square1 First square in which to place the first worker
     * @param square2 Second square in which to place the second worker
     * @param currentPlayer The player that is placing the two workers
     */

    public void placeWorkerOnMap(Square square1,Square square2, Player currentPlayer){
        clearModifiedSquare();


        placeWorker(square1,currentPlayer,currentPlayer.getWorkers().get(0));
        currentPlayer.getWorkers().get(0).setBoardPosition(square1);
        placeWorker(square2,currentPlayer,currentPlayer.getWorkers().get(1));
        currentPlayer.getWorkers().get(1).setBoardPosition(square2);

        currentPlayer.setHasPlacedWorkers(true);

    }

    /**
     * Place a specific worker in the square
     * @param square Square in which to place the worker
     * @param player Player placing the worker
     * @param worker Worker to place
     */

    public void placeWorker(Square square,Player player,Worker worker){
        square.setMovement(player,worker);
        modifiedSquare.add(square);
    }

    /**
     * Method that return the positions of both player's workers
     * @param actualPlayer Player to analise
     * @return A list with the position of the two workers
     */

    public List<Square> getWorkersSquares(Player actualPlayer){
        if(actualPlayer == null)
            throw new NullPointerException("player null");
        List<Square> workerSquare = new ArrayList<>();

        for(Worker worker : actualPlayer.getWorkers()){
            workerSquare.add(worker.getBoardPosition());

        }
        return workerSquare;
    }

    /**
     * Method that return the map
     * @return A list of squares(the entire map)
     */

    public List<Square> getMap(){ return map;}

    /**
     * Method that check if a square is in the perimeter
     * @param tile Number of the square to analise
     * @return True if the square is in the perimeter, false otherwise
     */

    public  boolean isInPerimeter(Integer tile){
        if(tile == null)
            throw new NullPointerException("tile null");

        return tile <= ConstantsContainer.PERIMETERPOSITION;
    }

    /**
     * Method that add a square to the list of modified square, can be modified by a move,build or during the place workers phase
     * @param square The square to add to the list
     */

    public void addModifiedSquare(Square square){
        this.modifiedSquare.add(square);
    }

    /**
     * Get the list of squares modified by a specific action
     * @return A list of modified squares
     */

    public List<Square> getModifiedSquare() {
        return modifiedSquare;
    }

    /**
     * Method that clear the list of modified squares
     */

    public void clearModifiedSquare(){
        this.modifiedSquare.clear();
    }

    /**
     * Method that remove the workers of a specific player from the map (after he has lost)
     * @param player The player that has lost
     */

    public void removeWorkersOfPlayer(Player player){

        List<Square> workerSquares = getWorkersSquares(player);

        clearModifiedSquare();

        for(Square square: workerSquares){
            addModifiedSquare(square);
            remove(square);
        }
    }

    /**
     * Method that remove a specific worker from the map
     * @param square The square in which is located the worker to remove
     */

    public void remove(Square square){
        square.setHasPlayer(false);

    }
}
