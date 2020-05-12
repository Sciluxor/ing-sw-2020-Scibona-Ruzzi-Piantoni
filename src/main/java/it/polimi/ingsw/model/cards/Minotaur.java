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

public class Minotaur extends Card {

    public Minotaur(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    @Override
    public List<Directions> findWorkerMove(GameMap gameMap, Worker worker) {
        if (gameMap == null ||worker == null)
            throw new NullPointerException("null gameMap or worker");

        int levelPosition = worker.getBoardPosition().getBuildingLevel();
        Map<Directions, Integer> canAccess = worker.getBoardPosition().getCanAccess();
        List<Directions> reachableSquares = new ArrayList<>();

        for (Directions dir : Directions.values()) {
            int squareTile = canAccess.get(dir);
            if (squareTile > ConstantsContainer.MINMAPPOSITION && squareTile <= ConstantsContainer.MAXMAPPOSITION) { //rivedere questo if
                Square possibleSquare = gameMap.getMap().get(squareTile - 1);
                if ((possibleSquare.getBuildingLevel() >= 0 && possibleSquare.getBuildingLevel() <= levelPosition + 1)
                        && possibleSquare.getBuilding() != Building.DOME) {
                    if (possibleSquare.hasPlayer() && !possibleSquare.getPlayer().getNickName().equals(worker.getBoardPosition().getPlayer().getNickName())) {
                        if(canPush(gameMap, possibleSquare, dir))
                            reachableSquares.add(dir);
                    }
                    else
                    {
                        reachableSquares.add(dir);
                    }
                }
            }
        }
        return reachableSquares;
    }

    @Override
    public Response executeWorkerMove(GameMap gameMap, Directions directions, Player player) {
        if(gameMap == null || player == null || directions == null)
            throw new NullPointerException("null gameMap or player or direction");

        Worker currentWorker = player.getCurrentWorker();
        Square nextSquare = gameMap.getMap().get(currentWorker.getBoardPosition().getCanAccess().get(directions) - 1);
        gameMap.clearModifiedSquare();

        if(nextSquare.hasPlayer()){
            push(gameMap, nextSquare, directions);
        }
        gameMap.getModifiedSquare().add(0, currentWorker.getBoardPosition());
        currentWorker.setPreviousBoardPosition(currentWorker.getBoardPosition());
        currentWorker.getPreviousBoardPosition().setHasPlayer(false);
        currentWorker.setBoardPosition(nextSquare);
        currentWorker.getBoardPosition().setHasPlayer(true);
        currentWorker.getBoardPosition().setPlayer(player);
        currentWorker.getBoardPosition().setWorker(currentWorker);
        gameMap.getModifiedSquare().add(1, nextSquare);

        return Response.MOVED;
    }

    private boolean canPush(GameMap gameMap, Square possibleSquare, Directions directions) {
        if(gameMap == null || possibleSquare == null || directions == null)
            throw new NullPointerException("null gameMap or square or direction");

        int pushingTile = possibleSquare.getCanAccess().get(directions);
        if (pushingTile != 0){
            Square pushingSquare = gameMap.getMap().get(pushingTile - 1);
            return !pushingSquare.hasPlayer() && pushingSquare.getBuildingLevel() != 4;
        }
        return  false;
    }

    public void push(GameMap gameMap, Square actualSquare, Directions directions) {
        if(gameMap == null || actualSquare == null || directions == null)
            throw new NullPointerException("null gameMap or square or direction");

        Worker pushedWorker = actualSquare.getWorker();
        Player pushedPlayer = pushedWorker.getBoardPosition().getPlayer();


        pushedWorker.setPreviousBoardPosition(actualSquare);
        pushedWorker.getPreviousBoardPosition().setHasPlayer(false);
        pushedWorker.setBoardPosition(gameMap.getMap().get(pushedWorker.getBoardPosition().getCanAccess().get(directions) - 1));
        pushedWorker.getBoardPosition().setHasPlayer(true);
        pushedWorker.getBoardPosition().setPlayer(pushedPlayer);
        pushedWorker.getBoardPosition().setWorker(pushedWorker);
        gameMap.addModifiedSquare(pushedWorker.getBoardPosition());
    }
}


