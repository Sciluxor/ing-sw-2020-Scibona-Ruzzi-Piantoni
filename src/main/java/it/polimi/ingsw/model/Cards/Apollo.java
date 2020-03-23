package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;

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
        if(gameMap.getGameMap().get(wantToAccess).hasPlayer())
            swapWorker(player.getCurrentWorker(), gameMap.getGameMap().get(wantToAccess).getWorker());
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

    public void swapWorker(Worker worker1, Worker worker2) {
        Square tempswap;
        tempswap = worker1.getBoardPosition();
        worker1.setBoardPosition(worker2.getBoardPosition());
        worker2.setBoardPosition(tempswap);
    }
}
