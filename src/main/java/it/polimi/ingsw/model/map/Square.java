package it.polimi.ingsw.model.map;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;

import java.io.Serializable;
import java.util.Map;

/**
 * Class that represent a single square of the game map
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/28
 */

public class Square implements Serializable{


    private final Integer tile;
    private Integer[] coordinates;
    private int buildingLevel;
    private Building building;
    private boolean hasPlayer;
    private Player player;
    private Worker worker;
    private  Map<Directions,Integer> canAccess;

    /**
     * Public constructor for the square
     * @param tile Number of the square
     * @param buildingLevel Level of the building
     * @param building Type of the building
     * @param hasPlayer Boolean to check if there is a player
     * @param canAccess Map with the reachable squares from this square
     * @param coordinates Coordinates of the square
     */

    public Square(Integer tile, int buildingLevel, Building building, boolean hasPlayer,Map<Directions,Integer> canAccess,Integer[] coordinates) {
        this.tile = tile;
        this.buildingLevel = buildingLevel;
        this.building = building;
        this.hasPlayer = hasPlayer;
        this.canAccess = canAccess;
        this.coordinates = coordinates;
    }

    /**
     * Get the tile of the square
     * @return The tile of the square
     */

    public Integer getTile() {
        return tile;
    }

    /**
     * Get the building level in the square
     * @return The building level in the square
     */

    public int getBuildingLevel() {
        return buildingLevel;
    }

    /**
     * Set the building level in the square
     * @param level The new building level in the square
     */

    public void setBuildingLevel(int level){
        this.buildingLevel = level;
    }

    /**
     * Add one level to the building level in the square
     */

    public void addBuildingLevel() {
        if(buildingLevel<=3)
            this.buildingLevel += 1;
        else throw new IllegalStateException("level 4 already reached");
    }

    /**
     * Get the building type in the square
     * @return The building type in the square
     */

    public Building getBuilding() {
        return building;
    }

    /**
     * Set the building type in the square
     * @param building The new building type to put in the square
     */

    public void setBuilding(Building building) {
        if(building == null)
            throw new NullPointerException("null building");
        this.building = building;
    }

    /**
     * Get the coordinates of the squares
     * @return The coordinates(x,y) in the map
     */

    public Integer[] getCoordinates() {
        return coordinates;
    }

    /**
     * Function to check if there is a player in the square
     * @return True if there is a player in the square, false otherwise
     */

    public boolean hasPlayer() {
        return hasPlayer;
    }

    /**
     * Function to set the boolean hasPlayer
     * @param hasPlayer New value of the boolean hasPlayer
     */

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    /**
     * Get the player placed into the square
     * @return The player placed into the square
     */

    public Player getPlayer() {
        if(!hasPlayer)
            throw new IllegalStateException("no player present here");
        return player;
    }

    /**
     * Set a player into the square
     * @param player The new player that moved into the square
     */

    public void setPlayer(Player player) {
        if(player == null)
            throw new NullPointerException("player null");
        this.player = player;
    }

    /**
     * Get the specific player's worker placed in the square
     * @return The specific player's worker placed in the square
     */

    public Worker getWorker() {
        if(!hasPlayer)
            throw new IllegalStateException("no worker present here");
        return worker;
    }

    /**
     * Set a specific worker of a player into the square
     * @param worker The new worker that moved into the square
     */

    public void setWorker(Worker worker) {
        if(worker == null)
            throw new NullPointerException("null worker");
        this.worker = worker;
    }

    /**
     * Get the list of reachable squares from this square
     * @return A Map with the reachable square
     */

    public Map<Directions, Integer> getCanAccess() {
        return canAccess;
    }

    /**
     * Function to execute a player move into the square
     * @param player The player that moved into the square
     * @param worker The specific worker that the player has moved
     */

    public void setMovement(Player player,Worker worker){
        setHasPlayer(true);
        setPlayer(player);
        setWorker(worker);

    }

}
