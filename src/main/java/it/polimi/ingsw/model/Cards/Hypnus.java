package it.polimi.ingsw.model.Cards;

import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;

import java.util.ArrayList;

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
