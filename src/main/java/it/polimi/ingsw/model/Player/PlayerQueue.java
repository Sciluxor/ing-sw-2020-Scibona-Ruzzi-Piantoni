package it.polimi.ingsw.model.Player;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

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
