package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.cards.Card;

public enum Cards {
    APOLLO, ARTEMIS, ATHENA, ATLAS, CHRONUS, DEMETER, HEPHAESTUS, HERA, HESTIA, HYPNUS, MINOTAUR, PAN, PROMETHEUS, ZEUS;

    public static void printPower(String keyboard) {

        switch (keyboard) {
            case "APOLLO":
                System.out.println(Color.ANSI_BLUE + "YOUR MOVE: " + Color.RESET + "Your worker may move into an opponent worker's space by forcing thei worker to the space yours just vacated");
                break;
            case "ARTEMIS":
                System.out.println(Color.ANSI_BLUE + "YOUR MOVE: " + Color.RESET + "Your worker may move one additional time, but not back to its initial space");
                break;
            case "ATHENA":
                System.out.println(Color.ANSI_BLUE + "OPPONENT'S TURN: " + Color.RESET + "If one of your workers moved up on your last turn, opponent workers cannot move up this turn");
                break;
            case "ATLAS":
                System.out.println(Color.ANSI_BLUE + "YOUR BUILD: " + Color.RESET + "Your worker may build a dome at any level");
                break;
            case "CHRONUS":
                System.out.println(Color.ANSI_BLUE + "WIN CONDITION: " + Color.RESET + "You also win when there are at least five complete towers on the board");
                break;
            case "DEMETER":
                System.out.println(Color.ANSI_BLUE + "YOUR BUILD: " + Color.RESET + "Your worker may build one additional time, but not on the same space");
                break;
            case "HEPHAESTUS":
                System.out.println(Color.ANSI_BLUE + "YOUR BUILD: " + Color.RESET + "Your worker may build one additional block (not dome) on top of your first block");
                break;
            case "HERA":
                System.out.println(Color.ANSI_BLUE + "OPPONENT'S TURN: " + Color.RESET + "An opponent cannot win by moving into a perimeter space");
                break;
            case "HESTIA":
                System.out.println(Color.ANSI_BLUE + "YOUR BUILD: " + Color.RESET + "Your worker may build one additional time, but this cannot be on a perimeter space");
                break;
            case "HYPNUS":
                System.out.println(Color.ANSI_BLUE + "START OF OPPONENT'S TURN: " + Color.RESET + "If one of your opponent's workers is higher than all of their others, it cannot move");
                break;
            case "MINOTAUR":
                System.out.println(Color.ANSI_BLUE + "YOUR MOVE: " + Color.RESET + "Your worker may move into an opponent worker's space, if their worker can be forced one space straight backwards to an unoccupied space at any level");
                break;
            case "PAN":
                System.out.println(Color.ANSI_BLUE + "WIN CONDITION: " + Color.RESET + "You also win if your worker moves down two or more levels");
                break;
            case "PROMETHEUS":
                System.out.println(Color.ANSI_BLUE + "YOUR TURN: " + Color.RESET + "If your worker does not move up, it may build both before and after moving");
                break;
            case "ZEUS":
                System.out.println(Color.ANSI_BLUE + "YOUR BUILD: " + Color.RESET + "Your worker may build a block under itself");
                break;

        }
        System.out.println();
    }
}
