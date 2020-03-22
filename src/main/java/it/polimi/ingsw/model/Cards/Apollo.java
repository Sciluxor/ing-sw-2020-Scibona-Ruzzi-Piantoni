package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Square;
import it.polimi.ingsw.model.Player.Worker;

public class Apollo extends Card {

    public Apollo(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    public void swapWorker(Worker worker1, Worker worker2) {
        Square tempswap;
        tempswap = worker1.getBoardPosition();
        worker1.setBoardPosition(worker2.getBoardPosition());
        worker2.setBoardPosition(tempswap);
    }
}
