package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.map.Building;

/**
 * Class that define and implement the single tile's object
 * @author _theonlyonepiantu
 * @version 1.0
 * @since 2020/06/18
 */

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

    /**
     * Method used to initialize the initial raw' string value
     */

    public Tile() {
        for(int raw=0; raw<7; raw++)
            this.setPrintRawLevel(raw);
    }

    /**
     * Method used to get the raw value necessary to print the board in SantoriniMap
     * @param raw Int value of the corresponding raw that contains the string asked
     * @return stringRaw String value contained in the raw
     */

    public String getPrintRawLevel (int raw) {
        setBackgroundColor();
        return backgroundColor + printRawLevel[raw] + printColor;
    }

    /**
     * Method used to set (update) a correct value in the raw (using attributes' value contained in the tile's object)
     * @param raw Int value of the raw that is going to be updated
     */

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

    /**
     * Method used to set a building in the current tile
     * @param buildingType Building type this method set on the current object
     */

    public void setBuildingType (Building buildingType) {
        this.buildingType = buildingType;
    }

    /**
     * Method used to get the building type of the current tile
     * @return buildingType Building contained in the current object
     */

    public Building getBuildingType() {
        return buildingType;
    }

    /**
     * Method used to get the building level
     * @return buildingLevel Int value corresponding to the level of the building
     */

    public int getBuildingLevel() {
        return buildingLevel;
    }

    /**
     * Method used to set the building level
     * @param buildingLevel Int value corresponting to the level value of the building
     */

    public void setBuildingLevel (int buildingLevel) {
        this.buildingLevel = buildingLevel;
    }

    /**
     * Method used to print the worker symbol (if tile has player) with its correct color
     * @return coloredString String with worker' symbol (if tile has player) colored
     */

    private String printPlayerColor() {
        return getPlayerColor() + this.isHasPlayerSymbol() + Color.ANSI_RED;
    }

    /**
     * Method used to get corresponding coordinates of the current tile
     * @return coordinates[] An array of int that contain the coordinates of the current tile
     * (coordinate[0] = coordinate X | coordinate[1] = coordiante Y)
     * */

    public int[] getCoordinates() {
        return coordinates;
    }

    /**
     * Method used to set coordinates to the current tile
     * @param x Coordiante X int value
     * @param y Coordiante Y int value
     */

    public void setCoordinate (int x, int y) {
        this.coordinates[0] = x;
        this.coordinates[1] = y;
    }

    /**
     * Method used to get the current symbol in case of presence or not of a player
     * @return workerSymbol String that represents the correct value to print in case of presence of a player
     */

    private String isHasPlayerSymbol() {
        if(hasPlayer)
            return "〠 ";
        else
            return "   ";
    }

    /**
     * Method used to get self player color
     * @return playerColor Color of his proper player
     */

    private Color getPlayerColor() {
        return this.playerColor;
    }

    /**
     * Method used to set some info: if tile has player and in case of positive response, it set also the local player color
     * @param hasPlayer Boolean value that represents the presence of a player (true = has player | false = hasn't player)
     * @param color Color of possible player
     */

    public void setPlayerInfo (boolean hasPlayer, Color color) {
        this.hasPlayer = hasPlayer;
        if(hasPlayer)
            this.playerColor = color;
    }

    /**
     * Method used to set if current tile is available or not
     * @param available Boolean value that represents availability (true = available | false = not available)
     */

    public void setAvailable (boolean available) {
        this.available = available;
    }

    /**
     * Method used to set if current tile is selected or not
     * @param selected Boolean value that represents if current tile is selected (true = selected | false = not selected)
     */

    public void setSelected (boolean selected) {
        this.selected = selected;
    }

    /**
     * Method used to set the correct background color in relation of the type of the building
     * @param string Parameter that contains the string on which applies the background color
     * @param backgroundBuildColor Color that corresponds to the building type
     * @return stringWithBackground String with the background color
     */

    private String setBuildBackgroundColor (String string, Color backgroundBuildColor) {
        return backgroundBuildColor + string + printColor;
    }

    /**
     * Method used to set the background color in case of availability, selection and presence of player
     */

    private void setBackgroundColor () {
        if (available && !selected && !hasPlayer)
            backgroundColor = Color.BACKGROUND_GREEN;
        else if (selected)
            backgroundColor = Color.BACKGROUND_YELLOW;
        else if(hasPlayer)
            backgroundColor = Color.BACKGROUND_BLACK;
    }

    /**
     * Method used to reset the background to a default value
     */

    public void resetBackground() {
        this.backgroundColor = Color.BACKGROUND_BLACK;
    }

    /**
     * Method used to get available building of the current tile
     * @return availableBuilding Building that is available
     */

    public Building getAvailableBuilding () {
        return availableBuilding;
    }

    /**
     * Method used to set the correct available building (the successor of the current tile building type.
     * Atlas constraint is handled in the main class)
     */

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