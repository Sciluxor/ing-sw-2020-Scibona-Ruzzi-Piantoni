package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.map.Building;

public class Tile {

    private int[] coordinates = new int[2];
    private String[] printRawLevel = new String[7];
    private boolean hasPlayer;
    private int buildingLevel = 0;
    private Building buildingType = Building.GROUND;
    private Color playerColor = Color.ANSI_RED;
    private boolean available = false;
    private boolean selected = false;
    private String availableBuilding;

    public Tile() {
        for(int raw=0; raw<7; raw++)
            this.setPrintRawLevel(raw);
    }

    public String getPrintRawLevel (int raw) {
        return setBackgroundColor(printRawLevel[raw]);
    }

    public void setPrintRawLevel (int raw) {
        if(buildingType == Building.GROUND) {
            if(raw==3)
                this.printRawLevel[raw] = "        " + printPlayerColor() + "       ";
            else
                this.printRawLevel[raw] = "                  ";

        } else {
            if (raw == 0 || raw==6)
                this.printRawLevel[raw] = setBuildBackgroundColor(" ──────────────── ");

            switch (buildingType) {
                case LVL1:
                    if (raw == 3)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│") + "       " + printPlayerColor() + "      " + setBuildBackgroundColor("│");
                    else if (raw != 0 && raw != 6)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│") + "                " + setBuildBackgroundColor("│");

                    break;
                case LVL2:
                    if (raw == 1 || raw == 5)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ ────────────── │");
                    else if (raw == 3)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ │") + "     " + printPlayerColor() + "    " + setBuildBackgroundColor("│ │");
                    else if (raw != 0 && raw != 6)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ │") + "            " + setBuildBackgroundColor("│ │");

                    break;
                case LVL3:
                    if (raw == 1 || raw == 5)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ ────────────── │");
                    else if (raw == 2 || raw == 4)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ │ ────────── │ │");
                    else if (raw == 3)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ │ │") + "   " + printPlayerColor() + "  " + setBuildBackgroundColor("│ │ │");
                    else if (raw != 0 && raw != 6)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ │ │") + "        " + setBuildBackgroundColor("│ │ │");

                    break;
                case DOME:
                    if (buildingLevel != 4) {
                        if (raw == 3)
                            this.printRawLevel[raw] = "    " + setBuildBackgroundColor("│ ────── │") + "    ";
                        else
                            this.printRawLevel[raw] = "                  ";
                    }
                    if (raw == 1 || raw == 5)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ ────────────── │");
                    else if (raw == 2 || raw == 4)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ │ ────────── │ │");
                    else if (raw == 3)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ │ │ ────── │ │ │");

                    break;
                default:
                    this.printRawLevel[raw] = "      ERROR!      ";
            }
        }
        setAvailableBuilding();
    }

    public void setBuildingType (Building buildingType) {
        this.buildingType = buildingType;
    }

    public int getBuildingLevel() {
        return buildingLevel;
    }

    public void setBuildingLevel (int buildingLevel) {
        this.buildingLevel = buildingLevel;
    }

    private String printPlayerColor() {
        return this.playerColor + this.isHasPlayerSymbol() + Color.ANSI_RED;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinate (int x, int y) {
        this.coordinates[0] = x;
        this.coordinates[1] = y;
    }

    private String isHasPlayerSymbol() {
        if(hasPlayer)
            return "〠 ";
        else
            return "   ";
    }

    public void setPlayerColor (Color playerColor) {
        this.playerColor = playerColor;
    }

    public void setHasPlayer (boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    private String setBuildBackgroundColor (String string) {
        return Color.BACKGROUND_YELLOW + string;
    }

    public void setAvailable (boolean available) {
        this.available = available;
    }

    public void setSelected (boolean selected) {
        this.selected = selected;
    }

    private String setBackgroundColor (String string) {
        if(available && !hasPlayer && !selected)
            return Color.BACKGROUND_GREEN + string + Color.RESET;
        else if(selected)
            return Color.BACKGROUND_YELLOW + string + Color.RESET;

        return string;
    }

    public String getAvailableBuilding () {
        return availableBuilding;
    }

    private void setAvailableBuilding () {
        if (buildingLevel < 4 && buildingType != Building.DOME) {
            switch (buildingType) {
                case GROUND:
                    availableBuilding = Building.LVL1.name();
                    break;
                case LVL1:
                    availableBuilding = Building.LVL2.name();
                    break;
                case LVL2:
                    availableBuilding = Building.LVL3.name();
                    break;
                case LVL3:
                    availableBuilding = Building.DOME.name();
                    break;
            }
        } else
            availableBuilding = "";
    }
}