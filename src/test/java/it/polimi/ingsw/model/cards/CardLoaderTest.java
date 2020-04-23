package it.polimi.ingsw.model.cards;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CardLoaderTest {

    //
    //test that the function read the json file correctly and that the deck is created in the correct way
    //
    @Test
    void CardLoadedContentCheck(){

        HashMap<String,Card> deck = CardLoader.loadCards();
        assertFalse(deck.get("chronus").isPlayableIn3());
        assertEquals("apollo",deck.get("apollo").getName());
        assertEquals(CardSubType.PERMANENTCONSTRAINT,deck.get("hypnus").getSubType());
        assertEquals(CardSubType.NORMAL,deck.get("artemis").getSubType());
        assertEquals(CardSubType.NONPERMANENTCONSTRAINT,deck.get("athena").getSubType());
        assertEquals("Your Worker may build a block under itself.",deck.get("zeus").getDescription());
        assertEquals(CardType.YOURMOVE,deck.get("athena").getType());
        assertEquals(CardType.BUILDVICTORY,deck.get("chronus").getType());
        assertEquals(CardType.YOURBUILD,deck.get("atlas").getType());
        assertEquals(CardType.YOURTURN,deck.get("hypnus").getType());
        assertEquals(CardType.YOURTURN,deck.get("prometheus").getType());



    }

}