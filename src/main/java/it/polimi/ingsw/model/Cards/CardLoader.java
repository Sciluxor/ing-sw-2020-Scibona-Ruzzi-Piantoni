package it.polimi.ingsw.model.Cards;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;
import java.util.HashMap;

public class CardLoader {

    private static class CardContainer{

        String name;
        String description;
        boolean isPlayableIn3;
        CardType type;
        CardSubType subType;

    }

    public static HashMap<String, Card> loadCards(){
        Gson gsonCard = new Gson();
        CardLoader.CardContainer[] containers;

        try{
            String mapPath = "/CardsJson/Cards.json";
            InputStreamReader gameCardsInput = new InputStreamReader(CardLoader.class.getResourceAsStream(mapPath));
            JsonReader gameCardsReader = new JsonReader(gameCardsInput);
            containers = gsonCard.fromJson(gameCardsReader, CardLoader.CardContainer[].class);

        }catch (Throwable e){

            throw new IllegalStateException("impossible to charge Cards");

        }

        HashMap<String,Card> deck = new HashMap<>();
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
