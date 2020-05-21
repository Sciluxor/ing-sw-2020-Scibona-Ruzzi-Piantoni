package it.polimi.ingsw;

import it.polimi.ingsw.view.client.cli.SantoriniMapArrows;

import it.polimi.ingsw.view.client.cli.Cli_copy.*;

import java.util.Scanner;

import static it.polimi.ingsw.view.client.cli.CliUtils.*;

public class MainTestPrintMapCli {

    private static SantoriniMapArrows mapArrows = new SantoriniMapArrows();

    public static void main(String[] args) {

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
        mapArrows.setCellaHasPlayer(coordinate[0], coordinate[1]);

        mapArrows.printMap();

        boolean goOut = false, terminate = false;
        int option = 0;
        int keyboardIn;

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
            coordinate = mapArrows.getCoordinatesFromString();

            selectCorrectExec(keyboard, coordinate);

            mapArrows.printMap();
        }while(keyboard.equals("MOVE") || keyboard.equals("BUILD"));

        printRed("FINE ESECUZIONE!");
    }

    public static void selectCorrectExec(String choice, int[] coordinate) {
        if(choice.equals("MOVE"))
            mapArrows.setCellaHasPlayer(coordinate[0], coordinate[1]);
        if(choice.equals("BUILD")) {
            Scanner input = new Scanner(System.in);
            printRed("Inserire il tipo di edificio da costruire: ");
            mapArrows.setCellaBuildingType(coordinate[0], coordinate[1], input.nextLine().toUpperCase());
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
