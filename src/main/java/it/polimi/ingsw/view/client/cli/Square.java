package it.polimi.ingsw.view.client.cli;

public class Square {

    private boolean hasPlayer;
    private boolean hasBuilding;
    private int level;
    private BuildingType buildingType;
    private int buildingLevel;
    private String simbolWorker;
    private Color colorPlayer;

    public Square() {
        this.hasPlayer = false;
        this.hasBuilding = false;
        this.simbolWorker = "   ";
        this.colorPlayer = Color.ANSI_CYAN;
        this.buildingType = BuildingType.GROUND;

    }

    public boolean isHasPlayer() {
        return hasPlayer;
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
        this.setSimbol();
    }

    public boolean isHasBuilding() {
        return hasBuilding;
    }

    public void setHasBuilding(boolean hasBuilding) {
        this.hasBuilding = hasBuilding;
    }

    public String getSimbol() {
        return this.simbolWorker;
    }

    public void setSimbol() {
        if(this.hasPlayer && !this.buildingType.equals(BuildingType.DOME))
            this.simbolWorker = "\u3020 ";
        else if(this.buildingType.equals(BuildingType.DOME)) {
            //this.simbolWorker = "------";
        }
        else
            this.simbolWorker = "   ";
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = parseInput(buildingType);
    }

    public int getBuildingLevel() {
        return buildingLevel;
    }

    public void setBuildingLevel(int buildingLevel) {
        this.buildingLevel = buildingLevel;
    }

    public Color getColorPlayer() {
        return colorPlayer;
    }

    public BuildingType parseInput(String buildingType) {
        return BuildingType.valueOf(buildingType.toUpperCase());
    }




}
