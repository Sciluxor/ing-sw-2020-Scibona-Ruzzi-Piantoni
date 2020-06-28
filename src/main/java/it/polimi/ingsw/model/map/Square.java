package it.polimi.ingsw.model.map;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;

import java.io.Serializable;
import java.util.Map;

/**
 *
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
     *
     * @param tile
     * @param buildingLevel
     * @param building
     * @param hasPlayer
     * @param canAccess
     * @param coordinates
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
     *
     * @return
     */

    public Integer getTile() {
        return tile;
    }

    /**
     *
     * @return
     */

    public int getBuildingLevel() {
        return buildingLevel;
    }

    /**
     *
     * @param level
     */

    public void setBuildingLevel(int level){
        this.buildingLevel = level;
    }

    /**
     *
     */

    public void addBuildingLevel() {
        if(buildingLevel<=3)
            this.buildingLevel += 1;
        else throw new IllegalStateException("level 4 already reached");
    }

    /**
     *
     * @return
     */

    public Building getBuilding() {
        return building;
    }

    /**
     *
     * @param building
     */

    public void setBuilding(Building building) {
        if(building == null)
            throw new NullPointerException("null building");
        this.building = building;
    }

    /**
     *
     * @return
     */

    public Integer[] getCoordinates() {
        return coordinates;
    }

    /**
     *
     * @return
     */

    public boolean hasPlayer() {
        return hasPlayer;
    }

    /**
     *
     * @param hasPlayer
     */

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    /**
     *
     * @return
     */

    public Player getPlayer() {
        if(!hasPlayer)
            throw new IllegalStateException("no player present here");
        return player;
    }

    /**
     *
     * @param player
     */

    public void setPlayer(Player player) {
        if(player == null)
            throw new NullPointerException("player null");
        this.player = player;
    }

    /**
     *
     * @return
     */

    public Worker getWorker() {
        if(!hasPlayer)
            throw new IllegalStateException("no worker present here");
        return worker;
    }

    /**
     *
     * @param worker
     */

    public void setWorker(Worker worker) {
        if(worker == null)
            throw new NullPointerException("null worker");
        this.worker = worker;
    }

    /**
     *
     * @return
     */

    public Map<Directions, Integer> getCanAccess() {
        return canAccess;
    }

    /**
     *
     * @param player
     * @param worker
     */

    public void setMovement(Player player,Worker worker){
        setHasPlayer(true);
        setPlayer(player);
        setWorker(worker);

    }

}
