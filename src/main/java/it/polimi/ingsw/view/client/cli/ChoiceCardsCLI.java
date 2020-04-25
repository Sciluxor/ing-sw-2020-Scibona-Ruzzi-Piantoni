package it.polimi.ingsw.view.client.cli;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;

import java.util.*;

public class ChoiceCardsCLI {

    private Map<String, Card> deck = CardLoader.loadCards();
    private List<String> choosenCards = new ArrayList<>();

    private Scanner input = new Scanner(System.in);

    public void selectChoosenCards(int numberOfPlayers) {

        String keyboard;

        Color.clearConsole();
        System.out.println("Scegli " + numberOfPlayers + " tra le seguenti carte: (oppure inserisci il nome di una divinità per vederne gli effetti)");
        for(String s: deck.keySet())
            System.out.println(Color.ANSI_YELLOW + s.toUpperCase() + Color.RESET);

        System.out.println();

        keyboard = this.input.nextLine().toLowerCase();
        String[] cards = splitter(keyboard);
        while(cards.length == 1)
        {
            Card card = deck.get(cards[0]);
            if(card != null) {
                System.out.println("Questo è il potere della divinità " + Color.ANSI_YELLOW + keyboard.toUpperCase() + Color.RESET + ":");
                if (keyboard.equalsIgnoreCase("ATHENA") || keyboard.equalsIgnoreCase("HERA"))
                    System.out.print(printColorType("OPPONENT'S TURN"));
                else if (keyboard.equalsIgnoreCase("HYPNUS"))
                    System.out.print(printColorType("START OF OPPONENT'S TURN:"));
                else
                    System.out.print(printColorType(deck.get(keyboard).getType().toString()));
                System.out.println((deck.get(keyboard).getDescription()));
            }
            else
                System.out.println("WRONG CARD NAME. Please reinsert new card name:");

            keyboard = input.nextLine().toLowerCase();
            cards = splitter(keyboard);
        }
        if(cards.length != numberOfPlayers) {
            System.out.println("WRONG NUMBER OF CARDS. Please reinsert " + numberOfPlayers + " cards:");
            keyboard = input.nextLine().toLowerCase();
            cards = splitter(keyboard);
        }
        for(int i=0; i<cards.length; i++) {
            Card card = deck.get(cards[i]);
            if(card == null) {
                System.out.println("WRONG CARD NAME. Please reinsert new card name:");
                keyboard = input.nextLine().toLowerCase();
                cards = splitter(keyboard);
            }
        }

        choosenCards(cards);

        Color.clearConsole();
        System.out.print("Il deck di questa partita è composto da: ");
        for (String card : cards) {
            System.out.print(Color.ANSI_YELLOW + card.toUpperCase() + " " + Color.RESET);
        }
        System.out.println();
    }

    public void choosenCards(String[] cards) {
        Collections.addAll(choosenCards, cards);
    }

    public static String[] splitter(String keyboard) {
        return keyboard.split("\\s");
    }

    public static String printColorType(String string) {
        return Color.ANSI_BLUE + string + ": " + Color.RESET;
    }

    public void chooseCardsFromChoosenCards() {

    }
}
