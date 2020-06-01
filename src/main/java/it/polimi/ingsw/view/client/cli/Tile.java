package it.polimi.ingsw.view.client.cli;


public class Tile {

    private int[] coordinates = new int[2];
    private String[] printRawLevel = new String[7];
    private boolean hasPlayer;
    private int buildingLevel = 0;
    private String buildingType = "GROUND";
    private Color playerColor = Color.ANSI_RED;
    private boolean available = false;
    private boolean selected = false;
    //private final String playerSymbol = " 〠 ";

    public Tile() {
        for(int raw=0; raw<7; raw++)
            this.setPrintRawLevel(raw);
    }

    public String getPrintRawLevel (int raw) {
        return setBackgroundColor(printRawLevel[raw]);
    }

    public void setPrintRawLevel (int raw) {
        if(buildingType.equalsIgnoreCase("GROUND")) {
            if(raw==3)
                this.printRawLevel[raw] = "        " + printPlayerColor() + "       ";
            else
                this.printRawLevel[raw] = "                  ";
        } else {
            if (raw == 0 || raw==6)
                this.printRawLevel[raw] = setBuildBackgroundColor("──────────────────");

            switch (buildingType) {
                case "LVL1":
                    if (raw == 3)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│") + "       " + printPlayerColor() + "      " + setBuildBackgroundColor("│");
                    else if (raw != 0 && raw != 6)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│") + "                " + setBuildBackgroundColor("│");

                    buildingLevel++;
                    break;
                case "LVL2":
                    if (raw == 1 || raw == 5)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ ────────────── │");
                    else if (raw == 3)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ │") + "     " + printPlayerColor() + "    " + setBuildBackgroundColor("│ │");
                    else if (raw != 0 && raw != 6)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ │") + "            " + setBuildBackgroundColor("│ │");

                    buildingLevel++;
                    break;
                case "LVL3":
                    if (raw == 1 || raw == 5)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ ────────────── │");
                    else if (raw == 2 || raw == 4)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ │ ────────── │ │");
                    else if (raw == 3)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ │ │") + "   " + printPlayerColor() + "  " + setBuildBackgroundColor("│ │ │");
                    else if (raw != 0 && raw != 6)
                        this.printRawLevel[raw] = setBuildBackgroundColor("│ │ │") + "        " + setBuildBackgroundColor("│ │ │");

                    buildingLevel++;
                    break;
                case "DOME":
                    if (buildingLevel == 0) {
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

                    buildingLevel++;
                    break;
                default:
                    this.printRawLevel[raw] = "      ERROR!      ";
            }
        }
    }

    public void setBuildingType (String buildingType) {
        this.buildingType = buildingType;
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
        return Color.BACKGROUND_YELLOW + string + Color.RESET;
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
}