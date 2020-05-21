package it.polimi.ingsw.view.client.cli;

import static it.polimi.ingsw.view.client.cli.CliUtils.*;

public class SantoriniMapArrows {

    private Square[][] square = new Square[5][5];
    //private Square square = new Square();
    private boolean hasDome = false;
    private boolean firstExec = true;
    private boolean firstMove = true;
    private int[] previousTileCoordinates = new int[2];
    private int[] currentTileCoordinates = new int[2];
    private boolean[] hasPlayer = new boolean[25];

    private int[][] tileInt = new int[5][5];
    private boolean[][] isOnPerimeter = new boolean[5][5];

    //private Tile[] tile = new Tile[25];

    public SantoriniMapArrows() {
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                this.square[i][j] = new Square();
            }
        }

        //NUMBERING TILES
        //Posso beccare il numero di tile e salvarlo invece di settarlo con tutti i for (soluzione con un doppio for incatenato)?
        int number = 1;
        for(int y=0; y<5; y++) {
            this.tileInt[0][y] = number;
            number++;
        }
        for(int x=1; x<4; x++) {
            this.tileInt[x][4] = number;
            number++;
        }
        for(int y=4; y>=0; y--) {
            this.tileInt[4][y] = number;
            number++;
        }
        for(int x=3; x>=1; x--) {
            this.tileInt[x][0] = number;
            number++;
        }
        for(int y=1; y<4; y++) {
            this.tileInt[1][y] = number;
            number++;
        }
        for(int x=2; x<=3; x++) {
            this.tileInt[x][3] = number;
            number++;
        }
        for(int y=2; y>=1; y--) {
            this.tileInt[3][y] = number;
            number++;
        }
        for(int y=1; y<3; y++) {
            this.tileInt[2][y] = number;
            number++;
        }

    }

    public int[] selectSquareWithArrows() {

        boolean goOut = false, totalLeft = false, totalRight = false, upper = false, downer = false;
        int counterX = 0, counterY = 0;



        printRed("\nUSE ARROWS TO MOVE...");
        int keyboardIn = getArrow();

        do {
            switch (keyboardIn) {
                case 183: //up
                    if(!upper)
                        counterY--;
                    break;
                case 184: //down
                    if(!downer)
                        counterY++;
                    break;
                case 185: //right
                    if(!totalRight)
                        counterX++;
                    break;
                case 186: //left
                    if(!totalLeft)
                        counterX--;
                    break;
                default:
                    if(keyboardIn != 13)
                        printErr("NO KEYBOARD CAUGHT");
                    else
                        goOut = true;
            }

            if(!goOut) {
                totalLeft = counterY==0;
                totalRight = counterY==4;
                upper = counterX==0;
                downer = counterX==4;

                clearShell();
                setCellaHasPlayer(counterX, counterY);
                printMap();
                keyboardIn = controlWaitEnter("all");
            }
        }while(!goOut);

        int[] coordinate = new int[2];
        coordinate[0] = counterX;
        coordinate[1] = counterY;
        return coordinate;
    }

    /*public int[] getCoordinatesFromString() {
        int keyboard = Integer.parseInt(input());
        return this.tile[keyboard-1].getCoordinate();
    }*/

    /*public int getCoordinatesFromTile() {
        boolean goOut = false;
        int tile = 0;
        for(int x=0; x<5 && !goOut; x++) {
            for(int y=0; y<5; y++) {
                if(this.tile[x][y] == tile)
                    goOut = true;

            }
        }

    }*/

    //----- PRINTER -----

    public void printMap() {
        for(int x=0; x<5; x++) {
            for(int t=0; t<5; t++) {
                printYellow("---------------------");
            }
            printYellow("-\n");
            for(int z=0; z<7; z++) {
                for (int c=0; c<5; c++) {
                    printYellow("| ");
                    printBuildingType(x, c, z);
                    if(c==4)
                        printYellow(" |\n");
                    else
                        printYellow(" ");
                }
            }
        }
        for(int t=0; t<5; t++) {
            printYellow("---------------------");
        }
        printYellow("-\n");
    }

    public String printPlayer(int i, int j) {
        return this.square[i][j].getColorPlayer() + square[i][j].getSimbol() + Color.ANSI_RED;
    }

    public void printBuildingType(int i, int c, int z) {
        //String printer;
        BuildingType type = this.square[i][c].getBuildingType();

        if(type == BuildingType.GROUND) {
            if(z==3) {
                printRed("        " + this.printPlayer(i, c) + "       ");
            } else if(z==0) {
                if (this.tileInt[i][c] < 10)
                    print("                 " + this.tileInt[i][c], Color.ANSI_CYAN);
                else
                    print("                " + this.tileInt[i][c], Color.ANSI_CYAN);
            } else
                printRed("                  ");
        } else {
            if(z==0) {
                if (this.tileInt[i][c] < 10) {
                    printRed("---------------- ");
                } else {
                    printRed("--------------- ");
                }
                print(Integer.toString(this.tileInt[i][c]), Color.ANSI_CYAN);
            }

            switch (type) {
                case LVL1:
                    if(z==3)
                        printRed("|       " + this.printPlayer(i, c) + "      |");
                    else if(z!=0 && z!=6)
                        printRed("|                |");
                    break;

                case LVL2:
                    if(z==1 || z==5)
                        printRed("| -------------- |");
                    else if(z==3)
                        printRed("| |     " + this.printPlayer(i, c) + "    | |");
                    else if(z!=0 && z!=6)
                        printRed("| |            | |");
                    break;

                case LVL3:
                    if(z==1 || z==5)
                        printRed("| -------------- |");
                    else if(z==2 || z==4)
                        printRed("| | ---------- | |");
                    else if(z==3)
                        printRed("| | |   " + this.printPlayer(i, c) + "  | | |");
                    else if(z!=0 && z!=6)
                        printRed("| | |        | | |");
                    break;

                case DOME:
                    setHasDome(true);
                    if(z==1 || z==5)
                        printRed("| -------------- |");
                    else if(z==2 || z==4)
                        printRed("| | ---------- | |");
                    else if(z==3)
                        printRed("| | | ------ | | |");
                    else if(z!=0 && z!=6)
                        printRed("| | |        | | |");
                    break;

                default:
                    if(z==3)
                        print("     !ERRORE!     ", Color.ANSI_CYAN);
                    else
                        printRed("                  ");
                    break;
            }
            if(z==6)
                printRed("------------------");
        }
    }

    //----- SETTER & GETTER -----

    public boolean getHasPlayer(int tileNumber) {
        return hasPlayer[tileNumber];
    }

    public void setHasPlayer(boolean hasPlayer, int tileNumber) {
        this.hasPlayer[tileNumber] = hasPlayer;
    }

    public int[] getPreviousTileCoordinates() {
        return previousTileCoordinates;
    }

    public void setPreviousTileCoordinates(int[] previousTileCoordinates) {
        this.square[previousTileCoordinates[0]][previousTileCoordinates[1]].setHasPlayer(false);
        this.previousTileCoordinates[0] = previousTileCoordinates[0];
        this.previousTileCoordinates[1] = previousTileCoordinates[1];
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public int[] getCurrentTileCoordinates() {
        return currentTileCoordinates;
    }

    public void setCurrentTileCoordinates(int[] currentTileCoordinates) {
        this.currentTileCoordinates = currentTileCoordinates;
    }

    public boolean isCellaHasPlayer(int x, int y) {
        return this.square[x][y].isHasPlayer();
    }

    public void setCellaHasPlayer(int x, int y) {
        int[] coordinate = {x, y};
        if(isFirstMove()) {
            this.setFirstMove(false);
            this.setPreviousTileCoordinates(coordinate);
            this.square[x][y].setHasPlayer(true);
            this.setCurrentTileCoordinates(coordinate);
        }
        else {
            this.square[x][y].setHasPlayer(true);
            this.setPreviousTileCoordinates(this.getCurrentTileCoordinates());
            this.setCurrentTileCoordinates(coordinate);
        }
    }

    public BuildingType getCellaBuildingType(int x, int y) {
        return this.square[x][y].getBuildingType();
    }

    public void setCellaBuildingType(int x, int y, String builgindType) {
        this.square[x][y].setBuildingType(builgindType);
    }

    public boolean isHasDome() {
        return hasDome;
    }

    public void setHasDome(boolean hasDome) {
        this.hasDome = hasDome;
    }

    public boolean isFirstExec() {
        return firstExec;
    }

    public void setFirstExec(boolean firstExec) {
        this.firstExec = firstExec;
    }
}
