package it.polimi.ingsw.model.player;

import java.util.ArrayDeque;
import java.util.List;

public class PlayerQueue extends ArrayDeque<Player> {


    public PlayerQueue(List<Player> players) {
        super(players);
    }


    public void changeTurn(){
        if(!isEmpty()){
            addLast(removeFirst());
        }
    }
}
