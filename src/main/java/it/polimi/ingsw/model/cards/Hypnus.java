package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;

/**
 * Class that extends Card that build the Hypnus card
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/27
 */

public class Hypnus extends Card {

    /**
     * Class Builder
     * @param name Name of the card
     * @param description Description of the power of the card
     * @param isPlayableIn3 Boolean saying if the card is playable in 3 Players
     * @param type Type of the card
     * @param subType Subtype of the card
     */

    public Hypnus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    @Override
    public boolean canMove(Player player, Worker worker ) {
        if(player == null || worker == null)
            throw new NullPointerException("null player or worker");

          for(Worker wor: player.getWorkers()) {
              if (!wor.equals(worker) && worker.getBoardPosition().getBuildingLevel() > wor.getBoardPosition().getBuildingLevel()) {
                      return false;
              }
          }
         return true;
    }

}
