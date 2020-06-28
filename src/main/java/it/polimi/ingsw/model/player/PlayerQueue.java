package it.polimi.ingsw.model.player;

import java.util.ArrayDeque;
import java.util.List;

/**
 * Class that extends the Array deque of player. It is used to control the player queue
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/28
 */

public class PlayerQueue extends ArrayDeque<Player> {

    /**
     * Constructor method of the playerQueue's object
     * @param players List of player to insert in the queue of players
     */

    public PlayerQueue(List<Player> players) {
        super(players);
    }

    /**
     * Method used to update the queue when the turn changes
     */

    public void changeTurn(){
        if(!isEmpty()){
            addLast(removeFirst());
        }
    }
}
