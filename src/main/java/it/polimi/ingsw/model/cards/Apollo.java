package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.utils.ConstantsContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that extends Card that build the Apollo card
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/27
 */

public class Apollo extends Card {

    /**
     * Public constructor
     * @param name Name of the card
     * @param description Description of the power of the card
     * @param isPlayableIn3 Boolean saying if the card is playable in 3 Players
     * @param type Type of the card
     * @param subType Subtype of the card
     */

    public Apollo(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    @Override
    public List<Directions> findWorkerMove(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        return allReachableSquares(gameMap, worker);
    }

    @Override
    public Response executeWorkerMove(GameMap gameMap, Directions directions, Player player) {
        if(gameMap == null || player == null || directions == null)
            throw new NullPointerException("null gameMap or player or direction");

        int wantToAccess = player.getCurrentWorker().getBoardPosition().getCanAccess().get(directions);
        if(gameMap.getMap().get(wantToAccess-1).hasPlayer()){
            gameMap.clearModifiedSquare();
            gameMap.addModifiedSquare(player.getCurrentWorker().getBoardPosition());
            gameMap.addModifiedSquare(gameMap.getMap().get(wantToAccess- 1));
            swapWorker(player.getCurrentWorker().getBoardPosition(), gameMap.getMap().get(wantToAccess- 1));
        }
        else
            gameMap.moveWorkerTo(player, directions);

        return  Response.MOVED;
    }

    /**
     * Method that return the reachable squares for the Worker provided including those with other enemies workers
     * @param gameMap Map of the game
     * @param worker Worker you want to know the reachable squares
     * @return List of directions of the reachable squares
     */

    public List<Directions> allReachableSquares(GameMap gameMap, Worker worker) {
        int levelPosition = worker.getBoardPosition().getBuildingLevel();
        Map<Directions,Integer> canAccess = worker.getBoardPosition().getCanAccess();
        List<Directions> reachableSquares = new ArrayList<>();

        for(Directions dir: Directions.values()){
            int squareTile  =canAccess.get(dir);
            if(squareTile > ConstantsContainer.MINMAPPOSITION && squareTile <= ConstantsContainer.MAXMAPPOSITION) { //rivedere questo if
                Square possibleSquare = gameMap.getMap().get(squareTile- 1);
                if((possibleSquare.getBuildingLevel() >= 0 && possibleSquare.getBuildingLevel() <= levelPosition +1 && !worker.getBoardPosition().equals(possibleSquare) )
                        && possibleSquare.getBuilding() != Building.DOME ){
                    if(possibleSquare.hasPlayer() && !possibleSquare.getPlayer().getNickName().equals(worker.getBoardPosition().getPlayer().getNickName())
                         && !gameMap.buildableSquare(possibleSquare.getWorker()).isEmpty() ){
                        reachableSquares.add(dir);
                    }
                    else if(!possibleSquare.hasPlayer())
                        reachableSquares.add(dir);
                }
            }
        }

        return reachableSquares;
    }

    /**
     * Method that swap the Workers in the 2 squares provided
     * @param square1 First square
     * @param square2 Second square
     */

    public void swapWorker(Square square1, Square square2) {
        Player playerTemp = square1.getPlayer();
        Worker workerTemp = square1.getWorker();

        square1.setHasPlayer(true);
        square1.setWorker(square2.getWorker());
        square1.getWorker().setPreviousBoardPosition(square2);
        square1.getWorker().setBoardPosition(square1);
        square1.setPlayer(square2.getPlayer());

        square2.setHasPlayer(true);
        square2.setWorker(workerTemp);
        square2.getWorker().setPreviousBoardPosition(square1);
        square2.getWorker().setBoardPosition(square2);
        square2.setPlayer(playerTemp);

    }
}
