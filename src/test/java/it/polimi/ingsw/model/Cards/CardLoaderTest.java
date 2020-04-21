package it.polimi.ingsw.model.Cards;

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
        assertEquals(deck.get("Apollo").getName(),"Apollo");
        assertEquals(deck.get("Hypnus").getSubType(),CardSubType.PERMANENTCONSTRAINT);
        assertEquals(deck.get("Artemis").getSubType(),CardSubType.NORMAL);
        assertEquals(deck.get("Athena").getSubType(),CardSubType.NONPERMANENTCONSTRAINT);
        assertEquals(deck.get("Zeus").getDescription(),"Your Build: Your Worker may build a block under itself.");
        assertEquals(deck.get("Athena").getType(),CardType.YOURMOVE);
        assertEquals(deck.get("Chronus").getType(),CardType.BUILDVICTORY);
        assertEquals(deck.get("Atlas").getType(),CardType.YOURBUILD);
        assertEquals(deck.get("Hypnus").getType(),CardType.YOURTURN);
        assertEquals(deck.get("Prometheus").getType(),CardType.YOURTURN);



    }

}