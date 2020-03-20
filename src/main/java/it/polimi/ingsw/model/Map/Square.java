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

    public void setBuildingLevel(int buildingLevel) {
        this.buildingLevel = buildingLevel;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public boolean hasPlayer() {
        return hasPlayer;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public HashMap<Directions, Integer> getCanAccess() {
        return canAccess;
    }


}
