package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardSubType;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.utils.ConstantsContainer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements all methods and declare all attributes regarding the model of a player
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/28
 */

public class Player implements Serializable {

    private String nickname;
    private transient Card power = null ;
    private transient Color color;
    private transient TurnStatus turnStatus;
    private transient List<Card> constraint;
    private transient List<Worker> workers;
    private transient Worker currentWorker;
    private transient Worker unmovedWorker;
    private transient boolean hasPlacedWorkers;

    /**
     * Constructor of a player's object, used to initialize all attributes
     * @param nickname Nickname of the new player
     */

    public Player (String nickname){

        workers = new ArrayList<>();
        constraint = new ArrayList<>();
        this.nickname = nickname;
        this.turnStatus = TurnStatus.PREGAME;

        workers.add(new Worker(WorkerName.WORKER1));
        workers.add(new Worker(WorkerName.WORKER2));
        hasPlacedWorkers = false;
    }

    /**
     * Method used to check if current player has placed his workers on the board (getter of hasPlacedWorkers)
     * @return hasPlacedWorkers boolean value (true = has placed | falce = hasn't placed)
     */

    public boolean hasPlacedWorkers() {
        return hasPlacedWorkers;
    }

    /**
     * Method used to set if a player has placed his workers (setter of hasPlacedWorkers)
     * @param hasPlacedWorkers boolean value corresponding to the workers' placed status
     */

    public void setHasPlacedWorkers(boolean hasPlacedWorkers) {
        this.hasPlacedWorkers = hasPlacedWorkers;
    }

    /**
     * Method used to get the player color
     * @return color current color of the current player
     */

    public Color getColor() {
        return color;
    }

    /**
     * Method used to set the color of the player
     * @param color player color to set
     */

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Method used to get the nickname of the current player
     * @return nickname String of the nickname
     */

    public String getNickName() {
        return nickname;
    }

    /**
     * Method used to get the power of the current player
     * @return power The card corresponding to the power of the current player
     */

    public Card getPower(){ return power;}

    /**
     * Method used to set the power of the current player
     * @param power Card to assign to the current player
     */

    public void setPower(Card power){
        if (power == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        this.power = power;
    }

    /**
     * Method used to get the turn status of the current player
     * @return turnStatus A turn status value corresponding to the status of the player
     */

    public TurnStatus getTurnStatus() { return turnStatus;}

    /**
     * Method used to set the turn status of the current player
     * @param turnStatus TurnStatus value corresponding to the current status of the player
     */

    public void setTurnStatus(TurnStatus turnStatus) {
        if (turnStatus == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        this.turnStatus = turnStatus;
    }

    /**
     * Method used to get the list of constraint assigned to the current player from the other players
     * @return constraint List of Card that assign a constraint to the current player
     */

    public List<Card> getConstraint() { return constraint;}

    /**
     * Method used to set a single constraint to the current player
     * @param constraint Card name of one of the other players that assign a constraint to the current player
     */

    public void setConstraint(Card constraint) {
        if (constraint == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        this.constraint.add(constraint);
    }

    /**
     * Method used to remove a single constraint to the current player
     * @param constraint Card name of the constraint to remove
     */

    public void removeConstraint(Card constraint){
        if (constraint == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        this.constraint.remove(constraint);
    }

    /**
     * Method used to get the list of the current player's workers
     * @return workers List of workers associate to the current player
     */

    public List<Worker> getWorkers() { return workers;}

    /**
     * Method used to set the current worker (one of the two in the list of workers)
     * @param currentWorker Worker to set as current worker
     */

    public void setCurrentWorker(Worker currentWorker) {
        if (currentWorker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        this.currentWorker = currentWorker;
    }

    /**
     * Method used to get the current worker
     * @return currentWorker Worker that actually is the current worker
     */

    public Worker getCurrentWorker() { return currentWorker;}

    /**
     * Method to set the unmoved worker
     * @param unmovedWorker Worker that isn't moved in the current turn
     */

    public void setUnmovedWorker(Worker unmovedWorker) {
        if (unmovedWorker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        this.unmovedWorker = unmovedWorker;
    }

    /**
     * Method used to get the unmoved worker
     * @return unmovedWorker Worker that isn't moved in the current turn
     */

    public Worker getUnmovedWorker() { return unmovedWorker;}

    //
    //function to transform the string with the worker in the WorkerName enumeration
    //

    /**
     * Method used to get the worker from the list of workers of the current player, using a string
     * @param worker String that represents the worker to get
     * @return worker Worker in the list of workers corresponding to the worker name contained in the string passed to this method
     */

    public Worker getWorkerFromString (String worker){
        if (worker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        WorkerName name = WorkerName.parseInput(worker);
        for (Worker work : workers)
            if(work.getName().equals(name))
                return work;
        throw new IllegalArgumentException(ConstantsContainer.NULLPARAMETERS);
    }

    /**
     * Method used to select the current worker on the current game map
     * @param gameMap Current game map of the current game
     * @param worker String corresponding to the current worker's name
     * @return boolean value corresponding to the possibility to select the current worker on the game map (true = it is selected | false = it isn't selected)
     */

    public boolean selectCurrentWorker(GameMap gameMap, String worker){
        if (gameMap == null || worker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        Worker worker1 = getWorkerFromString(worker);
        if (!checkIfCanMove(gameMap, worker1)){
            return false;
        }
        setCurrentWorker(worker1);
        return true;
    }

    /**
     * Method used to check if a worker can move in the current game map
     * @param gameMap Game map of the current game
     * @param worker String representing the name of the worker to check
     * @return boolean value (true = worker can move | false = worker cannot move)
     */

    public boolean checkIfCanMove(GameMap gameMap, Worker worker){
        if (gameMap == null || worker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        List<Directions> direction = findWorkerMove(gameMap, worker);
        if(!direction.isEmpty()){
            for(Card card : constraint){
                if (!checkConstraint(gameMap, worker, card, direction))
                    return  false;
            }
        }
        else return false;

        return true;
    }

    /**
     * Method used to check if to the list of direction is applied the constraint represents to the card
     * @param gameMap Game map of the current game
     * @param worker Worker to move/build
     * @param card Card containing the constraint to check
     * @param direction List of directions in which worker can move/build
     * @return boolean value represents the applied/unapplied constraint status
     */

    private boolean checkConstraint (GameMap gameMap, Worker worker, Card card, List<Directions> direction){
        if(card.getType().equals(CardType.YOURMOVE) && !card.getSubType().equals(CardSubType.NORMAL)){
            return !card.eliminateInvalidMove(gameMap, worker, direction).isEmpty();
        }
        else if(card.getType().equals(CardType.YOURTURN) && !card.getSubType().equals(CardSubType.NORMAL)) {
            return card.canMove(this, worker);
        }
        return  true;
    }

    /**
     * Method used to check if the current player lose, checking if both workers cannot move
     * @param gameMap Game map of the current player
     * @return boolean value representing the losing status of the player (true = has lost | false = hans't lost)
     */

    public boolean checkIfLoose(GameMap gameMap){
        if (gameMap == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);


        return !checkIfCanMove(gameMap, workers.get(0)) && !checkIfCanMove(gameMap, workers.get(1));
    }

    /**
     * Method used to get the first action of the current player, according to the power assigned to him
     * @return firstAction Response enumeration value
     */

    public Response getFirstAction(){
        return power.getFirstAction();
    }

    /**
     * Method used to get the list of directions in which a worker can move
     * @param gameMap Game map of the current game
     * @param worker Worker to find the possible directions
     * @return directions List of directions in which the worker can move
     */

    public List<Directions> findWorkerMove(GameMap gameMap, Worker worker){
        if (gameMap == null || worker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return power.findWorkerMove(gameMap, worker);
    }

    /**
     * Method used to move the selected worker of the current player
     * @param gameMap Game map of the current game
     * @param direction Direction in which move the worker
     * @return movedStatus Response represents the execute move status
     */

    public Response executeWorkerMove(GameMap gameMap, Directions direction){
        if (gameMap == null || direction == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return power.executeWorkerMove(gameMap, direction, this);
    }

    /**
     * Method used to get the list of direction in which a worker can build
     * @param gameMap Game map of the current game
     * @param worker Worker that will build
     * @return directions List of directions in which the worker can build
     */

    public List<Directions> findPossibleBuild(GameMap gameMap, Worker worker){
        if (gameMap == null || worker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return power.findPossibleBuild(gameMap, worker);
    }

    /**
     * Method used to build in a selected direction
     * @param gameMap Game map of the current game
     * @param building Type of Building to build
     * @param direction Direction in which build
     * @return buildStatus Response represents the execute build status
     */

    public Response executeBuild(GameMap gameMap, Building building, Directions direction){
        if (gameMap == null || building == null || direction == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return power.executeBuild(gameMap, building, direction, this.currentWorker);
    }

    /**
     * Method used to check the victory status of the current player
     * @param gameMap Game map of the current game
     * @return victoryStatus Response represents the victory status of the current player
     */

    public Response checkVictory(GameMap gameMap){
        if (gameMap == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return  power.checkVictory(gameMap, this);
    }

    /**
     * Method used to assign a constraint to a list of player
     * @param playerList List of player to assign the constraint contained in the power of the current player
     */

    public void assignConstraint(List<Player> playerList){
        if(playerList == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        for(Player player: playerList){
            if(!player.equals(this))
                player.setConstraint(this.getPower());
        }
    }

}
