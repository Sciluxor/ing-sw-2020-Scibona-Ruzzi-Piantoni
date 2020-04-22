package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;

public class Hypnus extends Card {

    public Hypnus(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType) {
        super(name, description, isPlayableIn3, type, subType);
    }

    @Override
    public boolean canMove(Player player, Worker worker ) {
        if(player == null || worker == null)
            throw new NullPointerException("null player or worker");

          for(Worker wor: player.getWorkers()) {
              if (!wor.equals(worker)) {
                  if(worker.getBoardPosition().getBuildingLevel() > wor.getBoardPosition().getBuildingLevel())
                      return false;
              }
          }
         return true;
    }

}
