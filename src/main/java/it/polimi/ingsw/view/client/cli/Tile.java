package it.polimi.ingsw.view.client.cli;


public class Tile {

    private int[] coordinate = new int[2];
    private String[] printRawLevel = new String[7];
    private boolean hasPlayer;
    private int buildingLevel = 0;
    private Color playerColor = Color.ANSI_RED;
    //private final String playerSymbol = " 〠 ";

    public Tile() {
        for(int raw=0; raw<7; raw++)
            this.setPrintRawLevel("GROUND", raw);
    }

    public String getPrintRawLevel(int raw) {
        return printRawLevel[raw];
    }

    public void setPrintRawLevel(String buildingType, int raw) {
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

    private String printPlayerColor() {
        return this.playerColor + this.isHasPlayerSymbol() + Color.ANSI_RED;
    }

    public int[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int x, int y) {
        this.coordinate[0] = x;
        this.coordinate[1] = y;
    }

    public String isHasPlayerSymbol() {
        if(hasPlayer)
            return "〠 ";
        else
            return "   ";
    }

    public void setHasPlayer(boolean hasPlayer, Color playerColor) {
        this.hasPlayer = hasPlayer;
        this.playerColor = playerColor;
    }

    public String setBuildBackgroundColor (String string) {
        return Color.BACKGROUND_YELLOW + string + Color.RESET;
    }
}