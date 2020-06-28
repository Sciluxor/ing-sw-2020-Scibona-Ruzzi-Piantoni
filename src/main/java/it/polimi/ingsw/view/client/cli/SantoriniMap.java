package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Square;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.utils.CliUtils.*;

/**
 * Class that define and implements the visualisation of the board
 * and an external (safety) access to the single tiles that compose the board
 * @author edoardopiantoni
 * @version 1.0
 * @since 2020/06/18
 */

public class SantoriniMap {

    private Tile[] tile = new Tile[25];
    List<Integer> availableTiles = new ArrayList<>();
    private static final String INTERNAL_TILE_SPACE = "         ";

    /**
     * Method that handle the initialization of coordinate value of single tiles and add them all to available tiles. This
     * model adopt this choice: tileNumber correspond to an index of the array Tile[] that contain all the board's tiles
     */

    public SantoriniMap() {
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

    /**
     * Method that handle the visualisation of the board using strings contained in the corresponding tiles
     */

    public void printMap() {
        clearShell();

        printYellow("   ");
        for(int i=0; i<5; i++)
            printYellow(INTERNAL_TILE_SPACE + i + INTERNAL_TILE_SPACE);
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

            printRawTileInfo(x);
        }
        printYellow("   ");
        for(int i=0; i<5; i++)
            printYellow(INTERNAL_TILE_SPACE + i + INTERNAL_TILE_SPACE);
        printYellow("\n");
    }

    /**
     * Method used to print raws of a tile with or without player/building
     * @param x Int value represents coordinate x of the tile to print
     */

    private void printRawTileInfo(int x) {
        for(int raw=0; raw<7; raw++) {
            printInternalTileInfo(x, raw);
        }
    }

    /**
     * Method used to print a single raw of a tile with or without player/building
     * @param x Int value represents coordinate x of the tile to print
     * @param raw Int value represents which raw is to print
     */

    private void printInternalTileInfo(int x, int raw) {
        int tileNumber;

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

    /**
     * Method that handle the visualisation of a list of available tiles
     */

    public void printAvailableTiles() {
        printRed("AVAILABLE SQUARES:\n");

        for(int availableTile: availableTiles) {
            int[] coordinate = getCoordinatesFromTile(availableTile);
            tile[availableTile].setAvailable(true);
            printRed("  [" + coordinate[0] + "] [" + coordinate[1] + "] Tile number: " + (availableTile+1) + "\n");
        }
    }

    /**
     * Method that set a blue background to the board
     * @param string String value to which this method applies the blue background color
     * @return backgroundString String value with the blue background
     */

    public String setBlueBackgroundColor(String string) {
        return setBackground(string, Color.BACKGROUND_BLUE);
    }

    /**
     * Method that set selected boolean value to tile number identified by tile number. This implies the set of the
     * selected background color on this tile
     * @param tileNumber Tile number that we want to set as selected
     * @param selected Boolean value (true = tile is selected / false = tile is not selected)
     */

    public void setSelectedTile (int tileNumber, boolean selected) {
        this.tile[tileNumber].setSelected(selected);
        iterateOnRawOfTile(tileNumber);
    }

    /**
     * Method that handle the setting of a worker or a player (with corresponding worker color) in a tile
     * @param hasPlayer Boolean value that represent if the tile has or hasn't the player (== false if to remove worker)
     * @param tileNumber Tile number user want to modify
     * @param playerColor Color of the player whose worker we want to set in this tile (== null if to remove worker)
     */

    public void setTileHasPlayer(boolean hasPlayer, int tileNumber, Color playerColor) {
        this.tile[tileNumber].setPlayerInfo(hasPlayer, playerColor);
        this.tile[tileNumber].setPrintRawLevel(3);
    }

    /**
     * Method to check if a tile is or isn't occupied by another worker
     * @param tileNumber Tile number to control
     * @return unoccupied Boolean value (true = unoccupied / false = occupied)
     */

    public boolean checkUnoccupiedTile(int tileNumber) {
        return availableTiles.contains(tileNumber);
    }

    /**
     * Method that update the strings corresponding to a tile with the corresponding building type
     * @param buildingType Type of the building of the tile
     * @param tileNumber Tile number to update type of building
     */

    public void updateStringBoardBuilding(Building buildingType, int tileNumber) {
        this.tile[tileNumber].setBuildingType(buildingType);
        this.tile[tileNumber].setBuildingLevel(tile[tileNumber].getBuildingLevel()+1);
        iterateOnRawOfTile(tileNumber);
    }

    /**
     * Same of the previous method but in this case all info are contained in a Square (from Server)
     * @param squareToModify Square with necessary info to update building type of the corresponding tile
     *                       (to update corresponding tile' strings)
     */

    public void updateStringBoardBuilding(Square squareToModify) {
        int tileNumber = squareToModify.getTile()-1;
        this.tile[tileNumber].setBuildingType(squareToModify.getBuilding());
        this.tile[tileNumber].setBuildingLevel(squareToModify.getBuildingLevel());
        iterateOnRawOfTile(tileNumber);
    }

    /**
     * Method used to get a tile number from corresponding coordinates
     * @param x Coordinate X int value
     * @param y Coordinate Y int value
     * @return tileNumber
     */

    public int getTileFromCoordinate(int x, int y) {
        for(int tileNumber=0; tileNumber<tile.length; tileNumber++) {
            if(tile[tileNumber].getCoordinates()[0] == x && tile[tileNumber].getCoordinates()[1] == y)
                return tileNumber;
        }
        return -1;
    }

    /**
     * Method used to get coordinates from a tile
     * @param tileNumber Tile whose coordinate this method get
     * @return coordinate[] An array of int containing coordinate[0] = coordinate X | coordinate[1] = coordinate Y
     */

    public int[] getCoordinatesFromTile(int tileNumber) {
        return tile[tileNumber].getCoordinates();
    }

    /**
     * Method to get available tiles
     * @return availableTile List of Integer of the available tiles of the current map
     */

    public List<Integer> getAvailableTiles() {
        return availableTiles;
    }

    /**
     * Method used to reset the list of available tiles of the current map
     */

    public void resetAvailableTiles() {
        for(int availableTile: availableTiles) {
            tile[availableTile].setAvailable(false);
            resetTileBackground(availableTile);
        }
        this.availableTiles.clear();
    }

    /**
     * Method used to reset the background of a tile
     * @param tileNumber Tile number of the tile to reset the background
     */

    public void resetTileBackground (int tileNumber) {
        tile[tileNumber].resetBackground();
        iterateOnRawOfTile(tileNumber);
    }

    /**
     * Method used to iterate on the raws of a tile. Every tile is composed by 7 raws which are used to print the
     * correct tile representation
     * @param tileNumber Tile number of the tile on which iterate
     */

    private void iterateOnRawOfTile (int tileNumber) {
        for(int raw=0; raw<7; raw++)
            tile[tileNumber].setPrintRawLevel(raw);
    }

    /**
     * Method used to set available tiles
     * @param availableTiles List of available tile to set
     */

    public void setAvailableTiles(List<Integer> availableTiles) {
        this.availableTiles.addAll(availableTiles);
        setAvailableTilesBackground(availableTiles);
    }

    /**
     * Methd to set the correct background of available tile
     * @param availableTiles List of available tiles on which set the "available" background
     */

    private void setAvailableTilesBackground(List<Integer> availableTiles) {
        for(int availableTile: availableTiles) {
            tile[availableTile].setAvailable(true);
            iterateOnRawOfTile(availableTile);
        }
    }

    /**
     * Method used by Cli.PlaceWorker to set modified tiles as not available
     * @param modifiedTiles List of tiles in which current player has placed his workers
     */

    public void setPlaceWorkerNotAvailableTiles(List<Integer> modifiedTiles) {
        for(int availableTile: modifiedTiles)
            tile[availableTile].setAvailable(false);
        this.availableTiles.removeAll(modifiedTiles);
        setAvailableTilesBackground(availableTiles);
    }

    /**
     * Method used to get available building from a tile
     * @param tileNumber Tile whose available building is asked
     * @return availableBuilding Building that is available in the tile
     */

    public Building getAvailableBuildingFromTile (int tileNumber) {
        return tile[tileNumber].getAvailableBuilding();
    }

    /**
     * Method used to get the current building of a tile
     * @param tileNumber Tile number whose current building is asked
     * @return currentBuilding Building currently present on the tile
     */

    public Building getTileBuilding (int tileNumber) {
        return this.tile[tileNumber].getBuildingType();
    }
}
