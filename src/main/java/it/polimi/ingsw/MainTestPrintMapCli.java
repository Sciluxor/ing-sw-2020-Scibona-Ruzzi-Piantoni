package it.polimi.ingsw;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.view.client.cli.Color;
import it.polimi.ingsw.view.client.cli.SantoriniMap;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.utils.CliUtils.*;

public class MainTestPrintMapCli {

    private static SantoriniMap santoriniMap = new SantoriniMap();
    private static Color playerColor = Color.ANSI_BLUE;

    public static void main(String[] args) {

        printRed(TITLE);

        printRed(WINNER);
        printRed(LOSER);

        System.out.println("Questa è la board vuota: ");
        santoriniMap.printMap();


        santoriniMap.setSelectedTile(19, true);

        santoriniMap.updateStringBoardBuilding(Building.LVL1, 12);
        santoriniMap.updateStringBoardBuilding(Building.LVL2, 11);
        santoriniMap.updateStringBoardBuilding(Building.LVL3, 10);
        santoriniMap.updateStringBoardBuilding(Building.LVL1, 9);
        santoriniMap.updateStringBoardBuilding(Building.LVL2, 9);
        santoriniMap.updateStringBoardBuilding(Building.LVL3, 9);
        santoriniMap.updateStringBoardBuilding(Building.DOME, 9);
        santoriniMap.updateStringBoardBuilding(Building.DOME, 20);
        santoriniMap.setTileHasPlayer(true, 8, playerColor);

        santoriniMap.updateStringBoardBuilding(Building.LVL1, 13);
        santoriniMap.updateStringBoardBuilding(Building.LVL2, 22);
        santoriniMap.updateStringBoardBuilding(Building.LVL3, 21);
        santoriniMap.setTileHasPlayer(true, 13, playerColor);
        santoriniMap.setTileHasPlayer(true, 22, playerColor);
        santoriniMap.setTileHasPlayer(true, 21, playerColor);

        List<Integer> availableTiles = new ArrayList<>();
        availableTiles.add(15);
        availableTiles.add(16);
        availableTiles.add(17);
        availableTiles.add(18);
        santoriniMap.setAvailableTiles(availableTiles);

        santoriniMap.setSelectedTile(14, true);
        santoriniMap.setSelectedTile(23, true);
        santoriniMap.setSelectedTile(24, true);
        santoriniMap.updateStringBoardBuilding(Building.LVL1, 14);
        santoriniMap.updateStringBoardBuilding(Building.LVL2, 23);
        santoriniMap.updateStringBoardBuilding(Building.LVL3, 24);
        santoriniMap.setTileHasPlayer(true, 14, playerColor);
        santoriniMap.setTileHasPlayer(true, 23, playerColor);
        santoriniMap.setTileHasPlayer(true, 24, playerColor);

        santoriniMap.updateStringBoardBuilding(Building.LVL1, 15);
        santoriniMap.updateStringBoardBuilding(Building.LVL2, 16);
        santoriniMap.updateStringBoardBuilding(Building.LVL3, 17);


        santoriniMap.printMap();

        //System.out.println("\nQUESTA CHE SEGUE E' UNA PROVA DI ESECUZIONE CON FLUSSO PREDETERMINATO");
        //this.provaEsecuzione();

        //System.out.println("\nQUESTA CHE SEGUE E' UNA PROVA DI ESECUZIONE NON PREDETERMINATA");
        //provaEsecuzioneNonPredeterminato();

        /*Cli_copy cli_copy = new Cli_copy();
        cli_copy.printCLI();*/
    }

    /*public static void provaEsecuzioneNonPredeterminato() {
        String keyboard;
        Scanner input = new Scanner(System.in);

        printRed("INSERT COORDINATES IN WHICH YOU WANT TO INSERT YOUR WORKER: ");
        int[] coordinate = getCoordinatesFromString();
        previousTile = mapArrows.getTileFromCoordinate(coordinate[0], coordinate[1]);
        //mapArrows.setTileHasPlayer(true, previousTile, Color.ANSI_PURPLE);

        mapArrows.printMap();

        /*boolean goOut = false, terminate = false;
        int option = 0;
        int keyboardIn;*/

        //clearShell();
        /*printRed("SELECT WITH ARROWS ONE OF THESE OPTIONS:\n  [MOVE]\n  [BUILD]\n");
        keyboardIn = getArrowUpDown();
        do {
            switch (keyboardIn) {
                case 183:
                    printRed("SELECT WITH ARROWS ONE OF THESE OPTIONS:\n");
                    printYellow("> [MOVE]\n");
                    printRed("  [BUILD]\n");
                    option = 1;
                    break;
                case 184:
                    printRed("SELECT WITH ARROWS ONE OF THESE OPTIONS:\n  [MOVE]\n");
                    printYellow("> [BUILD]");
                    option = 2;
                    break;
                case 13:
                    /*printRed("INSERT THE NUMBER OF THE TILE YOU WANT TO SELECT: ");
                    coordinate = mapArrows.getCoordinatesFromString();*/
                    /*coordinate = mapArrows.selectSquareWithArrows();

                    if (option == 1)
                        selectCorrectExec("MOVE", coordinate);
                    else if (option == 2)
                        selectCorrectExec("BUILD", coordinate);

                    printRed("INSERT c TO TERMINATE: ");
                    keyboard = input.nextLine();
                    if(keyboard.equalsIgnoreCase("c"))
                        goOut = true;
                    break;
                default:
                    printErr("NO KEYBOARD CAUGHT");
            }
            keyboardIn = controlWaitEnter("up&down");
            mapArrows.printMap();
        } while (!goOut);*/
        /*do {
            printRed("INSERT [MOVE] or [BUILD]: (everything else to go out): ");
            keyboard = input.nextLine().toUpperCase();
            if(!keyboard.equals("MOVE") && !keyboard.equals("BUILD"))
                break;

            printRed("INSERT COORDINATES: ");
            String[] split = splitter(input());
            coordinate[0] = Integer.parseInt(split[0]);
            coordinate[1] = Integer.parseInt(split[1]);

            selectCorrectExec(keyboard, coordinate);

            mapArrows.printMap();
        }while(keyboard.equals("MOVE") || keyboard.equals("BUILD"));

        printRed("FINE ESECUZIONE!");
    }*/

    /*public static void selectCorrectExec(String choice, int[] coordinate) {
        int tile = mapArrows.getTileFromCoordinate(coordinate[0], coordinate[1]);

        if(choice.equals("MOVE")) {
            //mapArrows.setTileHasPlayer(false, previousTile, Color.ANSI_PURPLE);
            //mapArrows.setTileHasPlayer(true, tile, Color.ANSI_PURPLE);
            previousTile = tile;
        } else if(choice.equals("BUILD")) {
            printRed("INSERT THE TYPE OF BUILDING YOU WANT TO BUILD: ");
            //mapArrows.updateStringBoardBuilding(CliUtils.input(), tile);
        }

    }*/

    /*public static int[] getCoordinatesFromString() {
        String keyboard;
        Scanner input = new Scanner(System.in);

        keyboard = input.nextLine();

        int[] coordinate = new int[2];
        String[] split = keyboard.split("\\s");
        for(int i=0; i<2; i++)
            coordinate[i] = Integer.parseInt(split[i]);

        return coordinate;
    }*/
}
