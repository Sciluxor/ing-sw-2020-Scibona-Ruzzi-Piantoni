package it.polimi.ingsw.model.player;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class PlayerQueue extends ArrayDeque<Player> {


    public PlayerQueue(ArrayList<Player> players) {
        super(players);
    }


    public void changeTurn(){
        if(!isEmpty()){
            addLast(removeFirst());
        }
    }
}
