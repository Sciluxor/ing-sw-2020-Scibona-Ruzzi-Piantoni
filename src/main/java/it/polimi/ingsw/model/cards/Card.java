package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.utils.ConstantsContainer;

import java.io.Serializable;
import java.util.List;

/**
 * Generic class that build tha card
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/27
 */

public class Card implements Serializable {

    private final String name;
    private final String description;
    private final boolean isPlayableIn3;
    private final CardType type;
    private final CardSubType subType;

    /**
     * Class Builder
     * @param name Name of the card
     * @param description Description of the power of the card
     * @param isPlayableIn3 Boolean saying if the card is playable in 3 Players
     * @param type Type of the card
     * @param subType Subtype of the card
     */

    public Card(String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType)
    {
        this.name = name;
        this.description = description;
        this.isPlayableIn3 = isPlayableIn3;
        this.type = type;
        this.subType = subType;
    }

    /**
     * Getter of the name
     * @return Name of the card
     */

    public String getName() {
        return name;
    }

    /**
     * Getter of the description
     * @return Description of the card
     */

    public String getDescription() {
        return description;
    }

    /**
     * Getter of the boolean isPlayableIn3
     * @return Boolean isPlayableIn3
     */

    public boolean isPlayableIn3() {
        return isPlayableIn3;
    }

    /**
     * Getter of the type
     * @return Type of the card
     */

    public CardType getType() {
        return type;
    }

    /**
     * Getter of the Subtype
     * @return Subtype of the card
     */

    public CardSubType getSubType() {
        return subType;
    }

    /**
     * Getter of the First Action of the card
     * @return Response of the First Action
     */

    public Response getFirstAction(){
        return Response.TOMOVE;

    }

    /**
     * Method that return the reachable square of the Worker provided
     * @param gameMap Game map
     * @param worker Worker you want to know the reachable squares
     * @return List of directions of the reachable square
     */

    public List<Directions> findWorkerMove(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return gameMap.reachableSquares(worker);
    }

    /**
     * Method that move the chosen worker of the Player in the direction provided
     * @param gameMap Game map
     * @param directions Direction where to move the Worker
     * @param player Player to move in the direction
     * @return Response after the player is moved
     */

    public Response executeWorkerMove(GameMap gameMap, Directions directions, Player player) {
        if(gameMap == null || player == null || directions == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        gameMap.moveWorkerTo(player, directions);
        return  Response.MOVED;
    }

    /**
     * Method that find the possible buildable square in which the provided Worker can build
     * @param gameMap Game map
     * @param worker Worker you want to know the buildable squares
     * @return List of directions of the buildable squares
     */

    public List<Directions> findPossibleBuild(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return gameMap.buildableSquare(worker);
    }

    /**
     * Method that build the Building provided in the direction provided by the Worker chosen
     * @param gameMap Map of the game
     * @param building Type of the Building to build
     * @param directions Direction where to build the Building
     * @param worker Worker that build in the provided direction
     * @return Response after the build
     */

    public Response executeBuild(GameMap gameMap, Building building, Directions directions, Worker worker) {
        if(gameMap == null || worker == null || building == null || directions == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        if(gameMap.buildInSquare(worker, directions, building))
            return Response.BUILD;
        else
            return Response.NOTBUILD;
    }

    /**
     * Method that check the victory for the provided Player
     * @param gameMap Map of the game
     * @param player Player to check the victory
     * @return Response after the check
     */

    public Response checkVictory(GameMap gameMap, Player player) {
        if(gameMap == null || player == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        if(player.getCurrentWorker().getBoardPosition().getBuildingLevel() == ConstantsContainer.WINNINGLEVEL
                && player.getCurrentWorker().getPreviousBoardPosition().getBuildingLevel() == ConstantsContainer.WINNINGLEVEL -1)
            return Response.WIN;
        return Response.NOTWIN;
    }

    /**
     * Method that eliminate the invalid moves from the List of directions provided
     * @param gameMap Map of the game
     * @param worker Worker that moves
     * @param directionsArrayList List of directions
     * @return The list of directions without the invalid moves
     */

    public List<Directions> eliminateInvalidMove(GameMap gameMap, Worker worker, List<Directions> directionsArrayList) {
        if(gameMap == null || worker == null || directionsArrayList == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return directionsArrayList;
    }

    /**
     * Method that says if the Worker of the Player provided can move
     * @param player Player provided
     * @param worker Worker of the player
     * @return Boolean that says if the Worker can move
     */

    public boolean canMove(Player player, Worker worker) {
        if(player == null || worker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return true;
    }

    /**
     * Method that says if is a valid victory for the Worker provided
     * @param gameMap Map of the game
     * @param worker Worker whose victory must be checked
     * @return Boolean that says if is a valid victory
     */

    public boolean isValidVictory(GameMap gameMap, Worker worker) {
        if(gameMap == null || worker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return true;
    }

    /**
     * Method that reset the card
     */

    public void resetCard(){
       //used only in some specific cards
    }

    @Override
    public String toString() {
        String result = "Card Name -> " + name + "\nCard Description -> " + description;
        if(isPlayableIn3)
            result += "\nPlayable with 3 Player -> Yes\n";
        else
            result += "\nPlayable with 3 Player -> No\n";
        return result;
    }
}
