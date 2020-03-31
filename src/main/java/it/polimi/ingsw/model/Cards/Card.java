package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;
import it.polimi.ingsw.model.Response;

import java.util.ArrayList;

public class Card {

    private String name;
    private String description;
    private boolean isPlayableIn3;
    private CardType type;
    private CardSubType subType;

    public Card(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType)
    {
        this.name = name;
        this.description = description;
        this.isPlayableIn3 = isPlayableIn3;
        this.type = type;
        this.subType = subType;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public boolean isPlayableIn3() {
        return isPlayableIn3;
    }

    public CardType getType() {
        return type;
    }

    public CardSubType getSubType() {
        return subType;
    }

    public Response getFirstOperation(){
        return Response.TOMOVE;

    }

    public ArrayList<Directions> findWorkerMove(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        return gameMap.reachableSquares(worker);
    }

    public Response executeWorkerMove(GameMap gameMap, Directions directions, Player player) {
        if(gameMap == null || player == null || directions == null)
            throw new NullPointerException("null gameMap or player or direction");

        gameMap.moveWorkerTo(player, directions);
        return  Response.MOVED;
    }

    public ArrayList<Directions> findPossibleBuild(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        return gameMap.reachableSquares(worker);
    }

    public Response executeBuild(GameMap gameMap, Building building, Directions directions, Worker worker) {
        if(gameMap == null || worker == null || building == null || directions == null)
            throw new NullPointerException("null gameMap or worker or building or direction");

        if(gameMap.buildInSquare(worker, directions, building))
            return Response.BUILD;
        else
            return Response.NOTBUILD;
    }

    public Response checkVictory(GameMap gameMap, Player player) {
        if(gameMap == null || player == null)
            throw new NullPointerException("null gameMap or player");

        if(player.getCurrentWorker().getBoardPosition().getBuildingLevel() == 3 && player.getCurrentWorker().getPreviousBoardPosition().getBuildingLevel() == 2)
            return Response.WIN;
        return Response.NOTWIN;
    }

    public ArrayList<Directions> eliminateInvalidMove(GameMap gameMap, Worker worker, ArrayList<Directions> directionsArrayList) {
        if(gameMap == null || worker == null || directionsArrayList == null)
            throw new NullPointerException("null gameMap or worker or directionsArrayList");

        return directionsArrayList;
    }

    public boolean canMove(Player player, Worker worker) {
        if(player == null || worker == null)
            throw new NullPointerException("null player or worker");

        return true;
    }

    public boolean isValidVictory(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException("null gameMap or worker");

        return true;
    }

    @Override
    public String toString() {
        String result = "Card Name -> " + name + "\nCard Description -> " + description;
        if(isPlayableIn3)
            result += "\nPlayable with 3 Player -> Yes\n";
        else
            result += "\nPlayable with 3 Player -> No\n";
        return result;
    }
}
