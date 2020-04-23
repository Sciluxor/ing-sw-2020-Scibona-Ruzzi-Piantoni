package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.cards.Card;

import java.util.HashMap;

public enum Cards {
    APOLLO, ARTEMIS, ATHENA, ATLAS, CHRONUS, DEMETER, HEPHAESTUS, HERA, HESTIA, HYPNUS, MINOTAUR, PAN, PROMETHEUS, ZEUS;

    private HashMap<String, Card> deck;

    public static void printPower(String keyboard) {

        switch (keyboard) {
            case "APOLLO":
                System.out.println(printColorType("YOUR MOVE: ") + "Your worker may move into an opponent worker's space by forcing thei worker to the space yours just vacated");
                break;
            case "ARTEMIS":
                System.out.println(printColorType("YOUR MOVE: ") + "Your worker may move one additional time, but not back to its initial space");
                break;
            case "ATHENA":
                System.out.println(printColorType("OPPONENT'S TURN: ") + "If one of your workers moved up on your last turn, opponent workers cannot move up this turn");
                break;
            case "ATLAS":
                System.out.println(printColorType("YOUR BUILD: ") + "Your worker may build a dome at any level");
                break;
            case "CHRONUS":
                System.out.println(printColorType("WIN CONDITION: ") + "You also win when there are at least five complete towers on the board");
                break;
            case "DEMETER":
                System.out.println(printColorType("YOUR BUILD: ") + "Your worker may build one additional time, but not on the same space");
                break;
            case "HEPHAESTUS":
                System.out.println(printColorType("YOUR BUILD: ") + "Your worker may build one additional block (not dome) on top of your first block");
                break;
            case "HERA":
                System.out.println(printColorType("OPPONENT'S TURN: ") + "An opponent cannot win by moving into a perimeter space");
                break;
            case "HESTIA":
                System.out.println(printColorType("YOUR BUILD: ") + "Your worker may build one additional time, but this cannot be on a perimeter space");
                break;
            case "HYPNUS":
                System.out.println(printColorType("START OF OPPONENT'S TURN: ") + "If one of your opponent's workers is higher than all of their others, it cannot move");
                break;
            case "MINOTAUR":
                System.out.println(printColorType("YOUR MOVE: ") + "Your worker may move into an opponent worker's space, if their worker can be forced one space straight backwards to an unoccupied space at any level");
                break;
            case "PAN":
                System.out.println(printColorType("WIN CONDITION: ") + "You also win if your worker moves down two or more levels");
                break;
            case "PROMETHEUS":
                System.out.println(printColorType("YOUR TURN: ") + "If your worker does not move up, it may build both before and after moving");
                break;
            case "ZEUS":
                System.out.println(printColorType("YOUR BUILD: ") + "Your worker may build a block under itself");
                break;

        }
        System.out.println();
    }

    public static String printColorType(String string) {
        return Color.ANSI_BLUE + string + Color.RESET;
    }
}
