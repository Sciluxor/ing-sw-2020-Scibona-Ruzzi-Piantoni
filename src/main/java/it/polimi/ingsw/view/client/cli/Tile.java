package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.map.Building;

public class Tile {

    private int[] coordinates = new int[2];
    private String[] printRawLevel = new String[7];
    private boolean hasPlayer = false;
    private int buildingLevel = 0;
    private Building buildingType = Building.GROUND;
    private Color playerColor = Color.ANSI_GREEN;
    private Color backgroundColor = Color.BACKGROUND_BLACK;
    private boolean available = false;
    private boolean selected = false;
    private Building availableBuilding;
    private Color printColor = Color.ANSI_RED;

    public Tile() {
        for(int raw=0; raw<7; raw++)
            this.setPrintRawLevel(raw);
    }

    public String getPrintRawLevel (int raw) {
        setBackgroundColor();
        return backgroundColor + printRawLevel[raw] + printColor;
    }

    public void setPrintRawLevel (int raw) {
        if(buildingType == Building.GROUND) {
            if(raw==3)
                this.printRawLevel[raw] = "        " + printPlayerColor() + "       ";
            else
                this.printRawLevel[raw] = "                  ";

        } else {
            if ((raw == 0 || raw==6) && buildingType != Building.DOME)
                this.printRawLevel[raw] = setBuildBackgroundColor(" ──────────────── ", Color.BACKGROUND_BLUE);

            switch (buildingType) {
                case LVL1:
                    setBackgroundColor();
                    if (raw == 3)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│", Color.BACKGROUND_BLUE) + backgroundColor + "       " + printPlayerColor() + "      " + setBuildBackgroundColor("│", Color.BACKGROUND_BLUE);
                    else if (raw != 0 && raw != 6)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│", Color.BACKGROUND_BLUE) + backgroundColor + "                " + setBuildBackgroundColor("│", Color.BACKGROUND_BLUE);

                    break;
                case LVL2:
                    setBackgroundColor();
                    if (raw == 1 || raw == 5)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│", Color.BACKGROUND_BLUE) + setBuildBackgroundColor(" ────────────── ", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│", Color.BACKGROUND_BLUE);
                    else if (raw == 3)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│", Color.BACKGROUND_BLUE) + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + backgroundColor + "      " + printPlayerColor() + "     " + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│", Color.BACKGROUND_BLUE);
                    else if (raw != 0 && raw != 6)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│", Color.BACKGROUND_BLUE) + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + backgroundColor + "              " + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│", Color.BACKGROUND_BLUE);

                    break;
                case LVL3:
                    setBackgroundColor();
                    if (raw == 1 || raw == 5)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│", Color.BACKGROUND_BLUE) + setBuildBackgroundColor(" ────────────── ", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│", Color.BACKGROUND_BLUE);
                    else if (raw == 2 || raw == 4)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│", Color.BACKGROUND_BLUE) + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor(" ──────────── ", Color.BACKGROUND_BLACK) + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│", Color.BACKGROUND_BLUE);
                    else if (raw == 3)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│", Color.BACKGROUND_BLUE) + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│", Color.BACKGROUND_BLACK) + backgroundColor + "     " + printPlayerColor() + "    " + setBuildBackgroundColor("│", Color.BACKGROUND_BLACK) + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│", Color.BACKGROUND_BLUE);
                    else if (raw != 0 && raw != 6)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│", Color.BACKGROUND_BLUE) + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│", Color.BACKGROUND_BLACK) + backgroundColor + "            " + setBuildBackgroundColor("│", Color.BACKGROUND_BLACK) + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│", Color.BACKGROUND_BLUE);

                    break;
                case DOME:
                    if (buildingLevel != 4) {
                        if (raw == 3)
                            this.printRawLevel[raw] = "    " + setBuildBackgroundColor("  ──────  ", Color.BACKGROUND_PURPLE) + backgroundColor + Color.ANSI_RED + "    ";
                        else if(raw == 2 || raw == 4)
                            this.printRawLevel[raw] = "    " + setBuildBackgroundColor("          ", Color.BACKGROUND_PURPLE) + backgroundColor + Color.ANSI_RED + "    ";
                        else if(raw != 0 && raw != 6)
                            this.printRawLevel[raw] = "                  ";
                    } else {
                        if (raw == 1 || raw == 5)
                            this.printRawLevel[raw] = setBuildBackgroundColor("│", Color.BACKGROUND_BLUE) + setBuildBackgroundColor(" ────────────── ", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│", Color.BACKGROUND_BLUE);
                        else if (raw == 2 || raw == 4)
                            this.printRawLevel[raw] = setBuildBackgroundColor("│", Color.BACKGROUND_BLUE) + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor(" ──────────── ", Color.BACKGROUND_BLACK) + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│", Color.BACKGROUND_BLUE);
                        else if (raw == 3)
                            this.printRawLevel[raw] = setBuildBackgroundColor("│", Color.BACKGROUND_BLUE) + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│ ", Color.BACKGROUND_BLACK) + setBuildBackgroundColor("│ ────── │", Color.BACKGROUND_PURPLE) + setBuildBackgroundColor(" │", Color.BACKGROUND_BLACK) + setBuildBackgroundColor("│", Color.BACKGROUND_YELLOW) + setBuildBackgroundColor("│", Color.BACKGROUND_BLUE);
                    }
                    break;
                default:
                    this.printRawLevel[raw] = Color.BACKGROUND_BLUE + "      ERROR!      " + printColor;
            }
        }
        setAvailableBuilding();
    }

    public void setBuildingType (Building buildingType) {
        this.buildingType = buildingType;
    }

    public Building getBuildingType() {
        return buildingType;
    }

    public int getBuildingLevel() {
        return buildingLevel;
    }

    public void setBuildingLevel (int buildingLevel) {
        this.buildingLevel = buildingLevel;
    }

    private String printPlayerColor() {
        return getPlayerColor() + this.isHasPlayerSymbol() + Color.ANSI_RED;
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

    /*public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }*/

    private Color getPlayerColor() {
        return this.playerColor;
    }

    public void setPlayerInfo (boolean hasPlayer, Color color) {
        this.hasPlayer = hasPlayer;
        if(hasPlayer)
            this.playerColor = color;
    }

    public void setAvailable (boolean available) {
        this.available = available;
    }

    public void setSelected (boolean selected) {
        this.selected = selected;
    }

    private String setBuildBackgroundColor (String string, Color backgroundBuildColor) {
        return backgroundBuildColor + string + printColor;
    }

    private void setBackgroundColor (/*String string*/) {
        if (available && !selected && !hasPlayer)
            backgroundColor = Color.BACKGROUND_GREEN;
            //return Color.BACKGROUND_GREEN + string + Color.ANSI_RED;
        else if (selected)
            backgroundColor = Color.BACKGROUND_YELLOW;
            //return Color.BACKGROUND_YELLOW + string + Color.ANSI_RED;
        else if(hasPlayer)
            backgroundColor = Color.BACKGROUND_BLACK;

        //return Color.RESET + string + Color.ANSI_RED;
    }

    public void resetBackground() {
        this.backgroundColor = Color.BACKGROUND_BLACK;
    }

    public Building getAvailableBuilding () {
        return availableBuilding;
    }

    private void setAvailableBuilding () {
        if (buildingLevel < 4 && buildingType != Building.DOME) {
            switch (buildingType) {
                case GROUND:
                    availableBuilding = Building.LVL1;
                    break;
                case LVL1:
                    availableBuilding = Building.LVL2;
                    break;
                case LVL2:
                    availableBuilding = Building.LVL3;
                    break;
                case LVL3:
                    availableBuilding = Building.DOME;
                    break;
            }
        } else
            availableBuilding = null;
    }
}