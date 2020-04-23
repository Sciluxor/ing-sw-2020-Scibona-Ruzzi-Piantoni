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

        System.out.println("Scegli " + numberOfPlayers + " tra le seguenti carte: (oppure inserisci il nome di una divinità per vederne gli effetti)");
        for(int i=0; i<cards.size(); i++) {
            System.out.println(cards);
        }

        keyboard = input.nextLine();
        while(!keyboard.contains(" "))
        {
            System.out.println("Questo è il potere della divinità " + keyboard + ":");
            Cards.printPower(keyboard);

            keyboard = input.nextLine();
        }

        System.out.println("Il deck di questa partita è composto da: " + Color.ANSI_BLUE + keyboard + Color.RESET);
        //deck.add(keyboard)
    }

    private void initializeCards() {
        Cards[] cardsArray = Cards.values();
        for(Cards c: cardsArray) {
            cards.add(c.toString());
        }
    }
}
