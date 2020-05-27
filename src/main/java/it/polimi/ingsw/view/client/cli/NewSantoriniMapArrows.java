package it.polimi.ingsw.view.client.cli;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.view.client.cli.CliUtils.*;

public class NewSantoriniMapArrows {

    private Tile[] tile = new Tile[25];
    private boolean firstPrint = true;
    List<Integer> availableTiles = new ArrayList<>();
    //private boolean firstPrint = true;

    public NewSantoriniMapArrows() {
        for(int i=0; i<25; i++)
            tile[i] = new Tile();

        int counter = 0;
        for(int y=0; y<5; y++) {
            this.tile[counter].setCoordinate(0, y);
            counter++;
        }
        for(int x=1; x<4; x++) {
            this.tile[counter].setCoordinate(x, 4);
            counter++;
        }
        for(int y=4; y>=0; y--) {
            this.tile[counter].setCoordinate(4, y);
            counter++;
        }
        for(int x=3; x>=1; x--) {
            this.tile[counter].setCoordinate(x, 0);
            counter++;
        }
        for(int y=1; y<4; y++) {
            this.tile[counter].setCoordinate(1, y);
            counter++;
        }
        for(int x=2; x<=3; x++) {
            this.tile[counter].setCoordinate(x, 3);
            counter++;
        }
        for(int y=2; y>=1; y--) {
            this.tile[counter].setCoordinate(3, y);
            counter++;
        }
        for(int y=1; y<3; y++) {
            this.tile[counter].setCoordinate(2, y);
            counter++;
        }

        for(int i=0; i<25; i++)
            availableTiles.add(i);
    }

    public void setTileHasPlayer(boolean hasPlayer, String buildingType, int tileNumber, Color playerColor) {
        this.tile[tileNumber].setHasPlayer(hasPlayer, playerColor);
        this.tile[tileNumber].setPrintRawLevel(buildingType, 3);
    }

    public boolean checkUnoccupiedTile(int tileNumber) {
        return availableTiles.contains(tileNumber);
    }

    public void updateStringBoard(String buildingType, int tileNumber) {
        for(int raw=0; raw<7; raw++)
            this.tile[tileNumber].setPrintRawLevel(buildingType, raw);
    }

    public void printMap() {
        int tileNumber;

        clearShell();

        printYellow("   ");
        for(int i=0; i<5; i++)
            printYellow("         " + i + "         ");
        printYellow("\n");
        for(int x=0; x<=5; x++) {
            printYellow("   ");
            for(int t=0; t<5; t++) {
                printYellow("───────────────────");
                if(t==4)
                    printYellow("─\n");
            }

            if(x==5)
                break;

            for(int raw=0; raw<7; raw++) {
                for (int y=0; y<5; y++) {
                    tileNumber = getTileFromCoordinate(x, y);
                    if(y==0 && raw==3)
                        printYellow(" " + x + " ");
                    else if(y==0)
                        printYellow("   ");
                    printYellow("│");
                    printRed(this.tile[tileNumber].getPrintRawLevel(raw));
                    if(y==4) {
                        printYellow("│");
                        if(raw==3)
                            printYellow(" " + x + "\n");
                        else
                            printYellow("\n");
                    }
                }
            }
        }
        printYellow("   ");
        for(int i=0; i<5; i++)
            printYellow("         " + i + "         ");
        printYellow("\n");
    }

    public void printAvailableTiles() {

        printRed("AVAILABLE SQUARES:\n");

        for(int availableTile: availableTiles) {
            int[] coordinate = getCoordinatesFromTile(availableTile);
            printRed(" Tile number: " + (availableTile+1) + " [" + coordinate[0] + "] [" + coordinate[1] + "]\n");
        }

        //return availableTiles;
    }

    public int getTileFromCoordinate(int x, int y) {
        for(int tileNumber=0; tileNumber<tile.length; tileNumber++) {
            if(tile[tileNumber].getCoordinate()[0] == x && tile[tileNumber].getCoordinate()[1] == y)
                return tileNumber;
        }
        return -1;
    }

    public int[] getCoordinatesFromTile(int tileNumber) {
        return tile[tileNumber].getCoordinate();
    }

    public void setAvailableTiles(List<Integer> availableTilesFromServer) {
        this.availableTiles.clear();
        this.availableTiles.addAll(availableTilesFromServer);
    }

    public void setPlaceWorkerAvailableTiles(List<Integer> modifiedSquares) {
        this.availableTiles.removeAll(modifiedSquares);
    }

    //----- GETTER & SETTER -----
    /*public boolean isFirstPrint() {
        return firstPrint;
    }

    public void setFirstPrint(boolean firstPrint) {
        this.firstPrint = firstPrint;
    }*/



}
