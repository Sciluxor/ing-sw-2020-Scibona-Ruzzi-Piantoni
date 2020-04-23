package it.polimi.ingsw.view.client.cli;

import java.util.ArrayList;
import java.util.Scanner;

public class ChallengerChoiceCardsCLI {

    private ArrayList<String> cards = new ArrayList<>();
    private ArrayList<String> deck = new ArrayList<>();

    private String keyboard;
    private Scanner input = new Scanner(System.in);

    public void chooseCards(int numberOfPlayers) {
        initializeCards();

        Color.clearConsole();
        System.out.println("Scegli " + numberOfPlayers + " tra le seguenti carte: (oppure inserisci il nome di una divinità per vederne gli effetti)");
        for(String s: cards) {
            System.out.println(Color.ANSI_YELLOW + s + Color.RESET);
        }
        System.out.println();

        keyboard = input.nextLine().toUpperCase();
        while(!keyboard.contains(" "))
        {
            System.out.println("Questo è il potere della divinità " + Color.ANSI_YELLOW + keyboard + Color.RESET + ":");
            Cards.printPower(keyboard);

            keyboard = input.nextLine().toUpperCase();
        }

        Color.clearConsole();
        System.out.println("Il deck di questa partita è composto da: " + Color.ANSI_YELLOW + keyboard + Color.RESET);
        //deck.add(keyboard)
    }

    private void initializeCards() {
        Cards[] cardsArray = Cards.values();
        for(Cards c: cardsArray) {
            cards.add(c.toString());
        }
    }
}
