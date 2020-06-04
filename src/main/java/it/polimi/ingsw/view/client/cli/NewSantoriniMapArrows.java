package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Square;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.view.client.cli.CliUtils.*;

public class NewSantoriniMapArrows {

    private Tile[] tile = new Tile[25];
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
                printWhite(setBlueBackgroundColor("───────────────────"));
                if(t==4)
                    printWhite(setBlueBackgroundColor("─") + "\n");
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
                    printWhite(setBlueBackgroundColor("│"));
                    printRed(this.tile[tileNumber].getPrintRawLevel(raw));
                    if(y==4) {
                        printWhite(setBlueBackgroundColor("│"));
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
            tile[availableTile].setAvailable(true);
            printRed(" [" + coordinate[0] + "] [" + coordinate[1] + "] Tile number: " + (availableTile+1) + "\n");
        }
    }

    public String setBlueBackgroundColor(String string) {
        return setBackground(string, Color.BACKGROUND_BLUE);
    }

    public void setSelectedTile (int tileNumber, boolean selected) {
        this.tile[tileNumber].setSelected(selected);
    }

    public void setTileHasPlayer(boolean hasPlayer, int tileNumber, Color playerColor) {
        this.tile[tileNumber].setPlayerInfo(hasPlayer, playerColor);
        this.tile[tileNumber].setPrintRawLevel(3);
        /*if(hasPlayer) {
            this.tile[tileNumber].setPlayerColor(playerColor);
        }*/
    }

    public boolean checkUnoccupiedTile(int tileNumber) {
        return availableTiles.contains(tileNumber);
    }

    public void updateStringBoardBuilding(Building buildingType, int tileNumber) {
        this.tile[tileNumber].setBuildingType(buildingType);
        this.tile[tileNumber].setBuildingLevel(tile[tileNumber].getBuildingLevel()+1);
        for(int raw=0; raw<7; raw++)
            this.tile[tileNumber].setPrintRawLevel(raw);
    }

    public void updateStringBoardBuilding(Square squareToModify) {
        int tileNumber = squareToModify.getTile()-1;
        this.tile[tileNumber].setBuildingType(squareToModify.getBuilding());
        this.tile[tileNumber].setBuildingLevel(squareToModify.getBuildingLevel());
        for(int raw=0; raw<7; raw++)
            this.tile[tileNumber].setPrintRawLevel(raw);
    }

    public int getTileFromCoordinate(int x, int y) {
        for(int tileNumber=0; tileNumber<tile.length; tileNumber++) {
            if(tile[tileNumber].getCoordinates()[0] == x && tile[tileNumber].getCoordinates()[1] == y)
                return tileNumber;
        }
        return -1;
    }

    public int[] getCoordinatesFromTile(int tileNumber) {
        return tile[tileNumber].getCoordinates();
    }

    public List<Integer> getAvailableTiles() {
        return availableTiles;
    }

    public void resetAvailableTiles() {
        for(int availableTile: availableTiles) {
            tile[availableTile].setAvailable(false);
            resetTileBackground(availableTile);
        }
        this.availableTiles.clear();
    }

    public void resetTileBackground (int tileNumber) {
        tile[tileNumber].resetBackground();
        for(int raw=0; raw<7; raw++)
            tile[tileNumber].setPrintRawLevel(raw);
    }

    public void setAvailableTiles(List<Integer> availableTiles) {
        this.availableTiles.addAll(availableTiles);
        setAvailableTilesBackground(availableTiles);
    }

    public void addAvailableTile (Integer availableTile) {
        this.availableTiles.add(availableTile);
        setAvailableTilesBackground(availableTiles);
    }

    public void removeTileFromAvailableTiles(Integer tileToRemove) {
        this.availableTiles.remove(tileToRemove);
    }

    private void setAvailableTilesBackground(List<Integer> availableTiles) {
        for(int availableTile: availableTiles) {
            tile[availableTile].setAvailable(true);
            for(int raw=0; raw<7; raw++)
                this.tile[availableTile].setPrintRawLevel(raw);
        }
    }

    public void setPlaceWorkerNotAvailableTiles(List<Integer> modifiedSquares) {
        for(int availableTile: modifiedSquares)
            tile[availableTile].setAvailable(false);
        this.availableTiles.removeAll(modifiedSquares);
        setAvailableTilesBackground(availableTiles);
    }

    public Building getAvailableBuildingFromTile (int tileNumber) {
        return tile[tileNumber].getAvailableBuilding();
    }

    public Building getTileBuilding (int tileNumber) {
        return this.tile[tileNumber].getBuildingType();
    }

    //----- GETTER & SETTER -----
    /*public boolean isFirstPrint() {
        return firstPrint;
    }

    public void setFirstPrint(boolean firstPrint) {
        this.firstPrint = firstPrint;
    }*/



}
