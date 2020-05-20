package it.polimi.ingsw.view.client.cli;

import java.util.Scanner;

import static it.polimi.ingsw.view.client.cli.CliUtils.*;

public class SantoriniMapArrows {

    private Square[][] square = new Square[5][5];
    private Color color = Color.ANSI_YELLOW;
    private static Color actualColor = Color.ANSI_YELLOW;
    private boolean hasDome = false;
    private boolean firstExec = true;
    private boolean firstMove = true;
    private int[] previousTile = new int[2];
    private int[] currentTile = new int[2];

    private int[][] tile = new int[5][5];
    //private boolean[][] isOnPerimeter = new boolean[5][5];

    public SantoriniMapArrows() {
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                this.square[i][j] = new Square();
            }
        }

        //IS ON PERIMETER
        /*for(int y=0; y<5; y++) {
            this.isOnPerimeter[0][y] = true;
            this.isOnPerimeter[4][y] = true;
        }

        for(int x=1; x<4; x++) {
            this.isOnPerimeter[x][0] = true;
            this.isOnPerimeter[x][4] = true;
        }*/

        //NUMBERING TILES
        //Posso beccare il numero di tile e salvarlo invece di settarlo con tutti i for (soluzione con un doppio for incatenato)?
        int number = 1;
        for(int y=0; y<5; y++) {
            this.tile[0][y] = number;
            number++;
        }
        for(int x=1; x<4; x++) {
            this.tile[x][4] = number;
            number++;
        }
        for(int y=4; y>=0; y--) {
            this.tile[4][y] = number;
            number++;
        }
        for(int x=3; x>=1; x--) {
            this.tile[x][0] = number;
            number++;
        }
        for(int y=1; y<4; y++) {
            this.tile[1][y] = number;
            number++;
        }
        for(int x=2; x<=3; x++) {
            this.tile[x][3] = number;
            number++;
        }
        for(int y=2; y>=1; y--) {
            this.tile[3][y] = number;
            number++;
        }
        for(int y=1; y<3; y++) {
            this.tile[2][y] = number;
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

    public int[] getCoordinatesFromString() {
        Scanner input = new Scanner(System.in);
        int[] coordinate = new int[2];

        int keyboard = input.nextInt();
        for(int x=0; x<5; x++) {
            for(int y=0; y<5; y++) {
                if(keyboard == this.tile[x][y]) {
                    coordinate[0] = x;
                    coordinate[1] = y;
                }
            }
        }


        return coordinate;
    }

    /*public int getTileFromCoordinates() {
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
        if(!isFirstExec())
            Color.clearConsole();
        setFirstExec(false);
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
                if(this.tile[i][c]<10)
                    print("                 " + this.tile[i][c], Color.ANSI_CYAN);
                else
                    print("                " + this.tile[i][c], Color.ANSI_CYAN);
            } else
                printRed("                  ");
        } else {
            if(z==0) {
                if (this.tile[i][c] < 10) {
                    printRed("---------------- ");
                }
                else {
                    printRed("--------------- ");
                }
                print(Integer.toString(this.tile[i][c]), Color.ANSI_CYAN);
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

        /*switch (this.square[i][c].getBuildingType()) {
            case GROUND:
                if(z==3) {
                    printer = "        " + this.printPlayer(i, c) + "       ";
                } else if(z==0) {
                    if(this.tile[i][c]<10)
                        printer = "                 " + this.tile[i][c];
                    else
                        printer = "                " + this.tile[i][c];
                } else
                    printer = "                  ";
                break;

            case LVL1:
                if(z==0) {
                    if(this.tile[i][c]<10)
                        printer = "---------------- " + this.tile[i][c];
                    else
                        printer = "--------------- " + this.tile[i][c];
                } else if(z==3)
                    printer = "|       " + this.printPlayer(i, c) + "      |";
                else if(z==6)
                    printer = "------------------";
                else
                    printer = "|                |";
                break;

            case LVL2:
                if(z==0 || z==6)
                    printer = "------------------";
                else if(z==1 || z==5)
                    printer = "| -------------- |";
                else if(z==3)
                    printer = "| |     " + this.printPlayer(i, c) + "    | |";
                else
                    printer = "| |            | |";
                break;

            case LVL3:
                if(z==0 || z==6)
                    printer = "------------------";
                else if(z==1 || z==5)
                    printer = "| -------------- |";
                else if(z==2 || z==4)
                    printer = "| | ---------- | |";
                else if(z==3)
                    printer = "| | |   " + this.printPlayer(i, c) + "  | | |";
                else
                    printer = "| | |        | | |";
                break;

            case DOME:
                setHasDome(true);
                if(z==0 || z==6)
                    printer = "------------------";
                else if(z==1 || z==5)
                    printer = "| -------------- |";
                else if(z==2 || z==4)
                    printer = "| | ---------- | |";
                else if(z==3)
                    printer = "| | | ------ | | |";
                else
                    printer = "| | |        | | |";
                break;

            default:
                if(z==3)
                    printer = "     !ERRORE!     ";
                else
                    printer = "                  ";
                break;
        }*/
    }

    //----- SETTER & GETTER -----

    public int[] getPreviousTile() {
        return previousTile;
    }

    public void setPreviousTile(int[] previousTile) {
        this.square[previousTile[0]][previousTile[1]].setHasPlayer(false);
        this.previousTile[0] = previousTile[0];
        this.previousTile[1] = previousTile[1];
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public int[] getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(int[] currentTile) {
        this.currentTile = currentTile;
    }

    public boolean isCellaHasPlayer(int x, int y) {
        return this.square[x][y].isHasPlayer();
    }

    public void setCellaHasPlayer(int x, int y) {
        int[] coordinate = {x, y};
        if(isFirstMove()) {
            this.setFirstMove(false);
            this.setPreviousTile(coordinate);
            this.square[x][y].setHasPlayer(true);
            this.setCurrentTile(coordinate);
        }
        else {
            this.square[x][y].setHasPlayer(true);
            this.setPreviousTile(this.getCurrentTile());
            this.setCurrentTile(coordinate);
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
