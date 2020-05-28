package it.polimi.ingsw;

import it.polimi.ingsw.view.client.cli.CliUtils;
import it.polimi.ingsw.view.client.cli.Color;
import it.polimi.ingsw.view.client.cli.NewSantoriniMapArrows;

import java.util.Scanner;

import static it.polimi.ingsw.view.client.cli.CliUtils.*;

public class MainTestPrintMapCli {

    private static NewSantoriniMapArrows mapArrows = new NewSantoriniMapArrows();

    public static void main(String[] args) {

        printRed(TITLE);

        System.out.println("Questa è la board vuota: ");
        mapArrows.printMap();

        //System.out.println("\nQUESTA CHE SEGUE E' UNA PROVA DI ESECUZIONE CON FLUSSO PREDETERMINATO");
        //this.provaEsecuzione();

        System.out.println("\nQUESTA CHE SEGUE E' UNA PROVA DI ESECUZIONE NON PREDETERMINATA");
        provaEsecuzioneNonPredeterminato();

        /*Cli_copy cli_copy = new Cli_copy();
        cli_copy.printCLI();*/
    }

    public static void provaEsecuzioneNonPredeterminato() {
        String keyboard;
        Scanner input = new Scanner(System.in);

        printRed("Inserire le coordinate in cui mettere il worker: ");
        int[] coordinate = getCoordinatesFromString();
        int tile = mapArrows.getTileFromCoordinate(coordinate[0], coordinate[1]);
        mapArrows.setTileHasPlayer(true, "GROUND", tile, Color.ANSI_PURPLE);

        mapArrows.printMap();

        /*boolean goOut = false, terminate = false;
        int option = 0;
        int keyboardIn;*/

        clearShell();
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
        do {
            printRed("INSERT [MOVE] or [BUILD]: (everything else to go out): ");
            keyboard = input.nextLine().toUpperCase();

            printRed("INSERT THE NUMBER OF THE TILE YOU WANT TO SELECT: ");
            coordinate = mapArrows.getCoordinatesFromTile(Integer.parseInt(CliUtils.input()));

            selectCorrectExec(keyboard, coordinate);

            mapArrows.printMap();
        }while(keyboard.equals("MOVE") || keyboard.equals("BUILD"));

        printRed("FINE ESECUZIONE!");
    }

    public static void selectCorrectExec(String choice, int[] coordinate) {
        int tile = mapArrows.getTileFromCoordinate(coordinate[0], coordinate[1]);

        if(choice.equals("MOVE"))
            mapArrows.setTileHasPlayer(true, "GROUND", tile, Color.ANSI_PURPLE);
        if(choice.equals("BUILD")) {
            printRed("Inserire il tipo di edificio da costruire: ");
            String keyboard = CliUtils.input().toUpperCase();
            mapArrows.setTileHasPlayer(true, keyboard, tile, Color.ANSI_PURPLE);
        }

    }

    public static int[] getCoordinatesFromString() {
        String keyboard;
        Scanner input = new Scanner(System.in);

        keyboard = input.nextLine();

        int[] coordinate = new int[2];
        String[] split = keyboard.split("\\s");
        for(int i=0; i<2; i++)
            coordinate[i] = Integer.parseInt(split[i]);

        return coordinate;
    }
}
