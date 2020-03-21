package it.polimi.ingsw.model.Map;

import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;

import java.util.HashMap;

public class Square {


    private Integer tile;
    private int buildingLevel;
    private Building building;
    private boolean hasPlayer;
    private Player player;
    private Worker worker;
    private HashMap<Directions,Integer> canAccess;

    public Square(Integer tile, int buildingLevel, Building building, boolean hasPlayer,HashMap<Directions,Integer> canAccess) {
        this.tile = tile;
        this.buildingLevel = buildingLevel;
        this.building = building;
        this.hasPlayer = hasPlayer;
        this.canAccess = canAccess;
    }


    public Integer getTile() {
        return tile;
    }

    public int getBuildingLevel() {
        return buildingLevel;
    }

    public void addBuildingLevel() {
        if(buildingLevel<=3)
            this.buildingLevel += 1;
        else throw new IllegalStateException("level 4 already reached");
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        if(building == null)
            throw new NullPointerException("null building");
        this.building = building;
    }

    public boolean hasPlayer() {
        return hasPlayer;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    public Player getPlayer() {
        if(!hasPlayer)
            throw new IllegalStateException("no player present here");
        return player;
    }

    public void setPlayer(Player player) {
        if(player == null)
            throw new NullPointerException("player null");
        this.player = player;
    }

    public Worker getWorker() {
        if(!hasPlayer)
            throw new IllegalStateException("no worker present here");
        return worker;
    }

    public void setWorker(Worker worker) {
        if(worker == null)
            throw new NullPointerException("null worker");
        this.worker = worker;
    }

    public HashMap<Directions, Integer> getCanAccess() {
        return canAccess;
    }


}
