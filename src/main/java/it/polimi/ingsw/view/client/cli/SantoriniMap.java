package it.polimi.ingsw.view.client.cli;

public class SantoriniMap {

    private Square[][] square = new Square[5][5];
    private Color color = Color.ANSI_YELLOW;
    private static Color actualColor = Color.ANSI_YELLOW;
    private boolean hasDome = false;
    private boolean firstExec = true;
    private boolean firstMove = true;
    private int[] previousTile = new int[2];
    private int[] currentTile = new int[2];

    public SantoriniMap() {
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                this.square[i][j] = new Square();
            }
        }
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
        if(builgindType.equals("DOME"))
            this.setCellaHasPlayer(currentTile[0]+1, currentTile[1]+1);
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

    public void printMap() {
        if(!isFirstExec())
            Color.clearConsole();
        setFirstExec(false);
        for(int i=0; i<5; i++) {
            for(int t=0; t<5; t++) {
                System.out.print(setPrinterColor("---------------------"));
            }
            int z = 0;
            System.out.print(setPrinterColor("-\n"));
            for(int j=0; j<=2; j++) {
                System.out.println(setPrinterColor("| " + printBuildingType(i, 0, z) + " | " +
                        printBuildingType(i, 1, z) + " | " +
                        printBuildingType(i, 2, z) + " | " +
                        printBuildingType(i, 3, z) + " | " +
                        printBuildingType(i, 4, z) + " |"));
                z++;
            }
            System.out.println(setPrinterColor("| " + printBuildingType(i, 0, z) + " | " +
                    printBuildingType(i, 1, z) + " | " +
                    printBuildingType(i, 2, z) + " | " +
                    printBuildingType(i, 3, z) + " | " +
                    printBuildingType(i, 4, z) + " |"));
            if(hasDome)
                this.setHasDome(false);
            z++;
            for(int j=0; j<=2; j++) {
                System.out.println(setPrinterColor("| " + printBuildingType(i, 0, z) + " | " +
                        printBuildingType(i, 1, z) + " | " +
                        printBuildingType(i, 2, z) + " | " +
                        printBuildingType(i, 3, z) + " | " +
                        printBuildingType(i, 4, z) + " |"));
                z++;
            }

        }
        for(int t=0; t<5; t++) {
            System.out.print(setPrinterColor("---------------------"));
        }
        System.out.println(setPrinterColor("-\n") + this.color.RESET);
    }

    public String printPlayer(int i, int j) {
        return this.square[i][j].getColorPlayer() + square[i][j].getSimbol() + actualColor;
    }

    public String printBuildingType(int i, int c, int z) {
        //System.out.println("SONO DENTRO");
        String printer = "ERROR";
        switch (this.square[i][c].getBuildingType()) {
            case GROUND:
                if(z==3) {
                    actualColor = Color.ANSI_YELLOW;
                    printer = "        " + this.printPlayer(i, c) + "       ";
                }
                else
                    printer = "                  ";
                break;

            case LVL1:
                actualColor = Color.ANSI_RED;
                if(z==0 || z==6)
                    printer = "------------------";
                else if(z==3)
                    printer = "|       " + this.printPlayer(i, c) + "      |";
                else
                    printer = "|                |";
                break;

            case LVL2:
                actualColor = Color.ANSI_RED;
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
                actualColor = Color.ANSI_RED;
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
                actualColor = Color.ANSI_RED;
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
        }
        //if(hasDome)
        return this.color.ANSI_RED + printer + this.color;
        //return printer;
    }

    public String setPrinterColor(String string) {
        return this.color + string;
    }

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
}
