package it.polimi.ingsw.view.client.cli;

import static it.polimi.ingsw.view.client.cli.CliUtils.print;
import static it.polimi.ingsw.view.client.cli.CliUtils.printRed;

public class Tile {

    private int[] coordinate = new int[2];
    private String[] printRawLevel = new String[7];
    private boolean hasPlayer;
    private int buildingLevel = 0;

    public Tile() {
        for(int raw=0; raw<7; raw++)
            this.setPrintRawLevel("GROUND", raw, Color.ANSI_YELLOW);
    }

    public String getPrintRawLevel(int raw) {
        return printRawLevel[raw];
    }

    public void setPrintRawLevel(String buildingType, int raw, Color playerColor) {
        if(buildingType.equalsIgnoreCase("GROUND")) {
            if(raw==3)
                this.printRawLevel[raw] = "        " + printPlayerColor(playerColor) + "       ";
            else
                this.printRawLevel[raw] = "                  ";
        } else {
            if(raw==0)
                this.printRawLevel[raw] ="------------------";
        }

        switch (buildingType) {
            case "LVL1":
                if(raw==3)
                    this.printRawLevel[raw] = "|       " + printPlayerColor(playerColor) + "      |";
                else if(raw!=0 && raw!=6)
                    this.printRawLevel[raw] = "|                |";

                buildingLevel++;
                break;
            case "LVL2":
                if(raw==1 || raw==5)
                    printRed("| -------------- |");
                else if(raw==3)
                    this.printRawLevel[raw] = "| |     " + printPlayerColor(playerColor) + "    | |";
                else if(raw!=0 && raw!=6)
                    this.printRawLevel[raw] = "| |            | |";

                buildingLevel++;
                break;
            case "LVL3":
                if(raw==1 || raw==5)
                    this.printRawLevel[raw] = "| -------------- |";
                else if(raw==2 || raw==4)
                    this.printRawLevel[raw] = "| | ---------- | |";
                else if(raw==3)
                    this.printRawLevel[raw] = "| | |   " + printPlayerColor(playerColor) + "  | | |";
                else if(raw!=0 && raw!=6)
                    this.printRawLevel[raw] = "| | |        | | |";

                buildingLevel++;
                break;
            case "DOME":
                if(buildingLevel == 0) {
                    if(raw==3)
                        this.printRawLevel[raw] = "    | ------ |    ";
                    else
                        this.printRawLevel[raw] = "                  ";
                }
                if(raw==1 || raw==5)
                    this.printRawLevel[raw] = "| -------------- |";
                else if(raw==2 || raw==4)
                    this.printRawLevel[raw] = "| | ---------- | |";
                else if(raw==3)
                    this.printRawLevel[raw] = "| | | ------ | | |";

                buildingLevel++;
                break;
            default:
                this.printRawLevel[raw] ="      ERROR!      ";
        }
    }

    private String printPlayerColor(Color playerColor) {
        return playerColor + this.isHasPlayerSymbol() + Color.ANSI_RED;
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
            return "\u3020 ";
        else
            return "   ";
    }

    public void setHasPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }
}