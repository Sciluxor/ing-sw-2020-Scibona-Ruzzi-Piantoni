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
        assertFalse(deck.get("Chronus").isPlayableIn3());
        assertEquals("Apollo",deck.get("Apollo").getName());
        assertEquals(CardSubType.PERMANENTCONSTRAINT,deck.get("Hypnus").getSubType());
        assertEquals(CardSubType.NORMAL,deck.get("Artemis").getSubType());
        assertEquals(CardSubType.NONPERMANENTCONSTRAINT,deck.get("Athena").getSubType());
        assertEquals("Your Build: Your Worker may build a block under itself.",deck.get("Zeus").getDescription());
        assertEquals(CardType.YOURMOVE,deck.get("Athena").getType());
        assertEquals(CardType.BUILDVICTORY,deck.get("Chronus").getType());
        assertEquals(CardType.YOURBUILD,deck.get("Atlas").getType());
        assertEquals(CardType.YOURTURN,deck.get("Hypnus").getType());
        assertEquals(CardType.YOURTURN,deck.get("Prometheus").getType());



    }

}