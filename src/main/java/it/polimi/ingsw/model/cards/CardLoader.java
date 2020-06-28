package it.polimi.ingsw.model.cards;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.utils.PathContainer;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that build tha cards deck
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/27
 */

public class CardLoader {

    /**
     * Private constructor, Since it's a loader class it can't be instantiated.
     */

    private CardLoader() {
        throw new IllegalStateException("CardLoader class cannot be instantiated");
    }

    /**
     * Class CardContainer
     */

    private static class CardContainer{
        String name;
        String description;
        boolean isPlayableIn3;
        CardType type;
        CardSubType subType;
    }

    /**
     * Method that return the deck of cards
     * @return Deck of the cards
     */

    public static Map<String, Card> loadCards(){

        Gson gsonCard = new Gson();
        CardLoader.CardContainer[] containers;

        try{
            String mapPath = PathContainer.CARD;
            InputStreamReader gameCardsInput = new InputStreamReader(CardLoader.class.getResourceAsStream(mapPath));
            JsonReader gameCardsReader = new JsonReader(gameCardsInput);
            containers = gsonCard.fromJson(gameCardsReader, CardLoader.CardContainer[].class);

        }catch (Exception e){
            throw new IllegalStateException("impossible to charge Cards");
        }

        Map<String,Card> deck = new HashMap<>();
        int i=0;
        deck.put(containers[i].name,new Apollo(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Artemis(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Athena(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Atlas(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Demeter(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Hephaestus(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Minotaur(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Pan(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Prometheus(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Chronus(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Hera(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Hestia(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Hypnus(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));
        i++;
        deck.put(containers[i].name,new Zeus(containers[i].name,containers[i].description,containers[i].isPlayableIn3,containers[i].type,containers[i].subType));

        return deck;
    }



}
