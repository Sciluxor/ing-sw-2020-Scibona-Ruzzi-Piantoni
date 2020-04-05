package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;
import it.polimi.ingsw.model.Response;

import java.util.ArrayList;
import java.util.HashMap;

public class Apollo extends Card {

    public Apollo(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    @Override
    public ArrayList<Directions> findWorkerMove(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        return allReachableSquares(gameMap, worker);
    }

    @Override
    public Response executeWorkerMove(GameMap gameMap, Directions directions, Player player) {
        if(gameMap == null || player == null || directions == null)
            throw new NullPointerException("null gameMap or player or direction");

        int wantToAccess = player.getCurrentWorker().getBoardPosition().getCanAccess().get(directions);
        if(gameMap.getGameMap().get(wantToAccess).hasPlayer()){
            gameMap.addModifiedSquare(player.getCurrentWorker().getBoardPosition());
            gameMap.addModifiedSquare(gameMap.getGameMap().get(wantToAccess- 1).getWorker().getBoardPosition());
            swapWorker(player.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(wantToAccess- 1).getWorker().getBoardPosition());
        }
        else
            gameMap.moveWorkerTo(player, directions);

        return  Response.MOVED;
    }

    public ArrayList<Directions> allReachableSquares(GameMap gameMap, Worker worker) {
        int level_position = worker.getBoardPosition().getBuildingLevel();
        HashMap<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();
        ArrayList<Directions> reachableSquares = new ArrayList<>();

        for(Directions dir: Directions.values()){
            int squareTile  =canAccess.get(dir);
            if(squareTile > 0 && squareTile <= 25) { //rivedere questo if
                Square possibleSquare = gameMap.getGameMap().get(squareTile- 1);
                if((possibleSquare.getBuildingLevel() >= 0 && possibleSquare.getBuildingLevel() <= level_position +1 && !worker.getBoardPosition().equals(possibleSquare) )
                        && possibleSquare.getBuilding() != Building.DOME ){
                    reachableSquares.add(dir);
                }
            }
        }

        return reachableSquares;
    }

    public void swapWorker(Square square1, Square square2) {
        Player playerTemp = square1.getPlayer();
        Worker workerTemp = square1.getWorker();

        square1.setWorker(square2.getWorker());
        square1.getWorker().setPreviousBoardPosition(square2);
        square1.getWorker().setBoardPosition(square1);
        square1.setPlayer(square2.getPlayer());

        square2.setWorker(workerTemp);
        square2.getWorker().setPreviousBoardPosition(square1);
        square2.getWorker().setBoardPosition(square2);
        square2.setPlayer(playerTemp);

    }
}
