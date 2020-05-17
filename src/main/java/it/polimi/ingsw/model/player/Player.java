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

    public Player (String nickname){

        workers = new ArrayList<>();
        constraint = new ArrayList<>();
        this.nickname = nickname;
        this.turnStatus = TurnStatus.PREGAME;

        workers.add(new Worker(WorkerName.WORKER1));
        workers.add(new Worker(WorkerName.WORKER2));
        hasPlacedWorkers = false;
    }

    public boolean hasPlacedWorkers() {
        return hasPlacedWorkers;
    }

    public void setHasPlacedWorkers(boolean hasPlacedWorkers) {
        this.hasPlacedWorkers = hasPlacedWorkers;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    //
    //function that return the nickname of the player
    //

    public String getNickName() {
        return nickname;
    }

    //
    //function that return the power of the player
    //

    public Card getPower(){ return power;}

    //
    //function that set the power of the player
    //

    public void setPower(Card power){
        if (power == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        this.power = power;
    }

    //
    //function that return the TurnStatus of the player
    //

    public TurnStatus getTurnStatus() { return turnStatus;}

    public void setTurnStatus(TurnStatus turnStatus) {
        if (turnStatus == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        this.turnStatus = turnStatus;
    }

    public List<Card> getConstraint() { return constraint;}

    public void setConstraint(Card constraint) {
        if (constraint == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        this.constraint.add(constraint);
    }

    public void removeConstraint(Card constraint){
        if (constraint == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        this.constraint.remove(constraint);
    }

    public List<Worker> getWorkers() { return workers;}


    public void setCurrentWorker(Worker currentWorker) {
        if (currentWorker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        this.currentWorker = currentWorker;
    }

    public Worker getCurrentWorker() { return currentWorker;}

    public void setUnmovedWorker(Worker unmovedWorker) {
        if (unmovedWorker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        this.unmovedWorker = unmovedWorker;
    }

    public Worker getUnmovedWorker() { return unmovedWorker;}

    //
    //function to transform the string with the worker in the WorkerName enumeration
    //

    public Worker getWorkerFromString (String worker){
        if (worker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        WorkerName name = WorkerName.parseInput(worker);
        for (Worker work : workers)
            if(work.getName().equals(name))
                return work;
        throw new IllegalArgumentException(ConstantsContainer.NULLPARAMETERS);
    }

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

    private boolean checkConstraint (GameMap gameMap, Worker worker, Card card, List<Directions> direction){
        if(card.getType().equals(CardType.YOURMOVE) && !card.getSubType().equals(CardSubType.NORMAL)){
            return !card.eliminateInvalidMove(gameMap, worker, direction).isEmpty();
        }
        else if(card.getType().equals(CardType.YOURTURN) && !card.getSubType().equals(CardSubType.NORMAL)) {
            return card.canMove(this, worker);
        }
        return  true;
    }

    public boolean checkIfLoose(GameMap gameMap){
        if (gameMap == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);


        return !checkIfCanMove(gameMap, workers.get(0)) && !checkIfCanMove(gameMap, workers.get(1));
    }

    public Response getFirstAction(){
        return power.getFirstAction();
    }

    public List<Directions> findWorkerMove(GameMap gameMap, Worker worker){
        if (gameMap == null || worker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return power.findWorkerMove(gameMap, worker);
    }

    public Response executeWorkerMove(GameMap gameMap, Directions direction){
        if (gameMap == null || direction == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return power.executeWorkerMove(gameMap, direction, this);
    }

    public List<Directions> findPossibleBuild(GameMap gameMap, Worker worker){
        if (gameMap == null || worker == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return power.findPossibleBuild(gameMap, worker);
    }

    public Response executeBuild(GameMap gameMap, Building building, Directions direction){
        if (gameMap == null || building == null || direction == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return power.executeBuild(gameMap, building, direction, this.currentWorker);
    }

    public Response checkVictory(GameMap gameMap){
        if (gameMap == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        return  power.checkVictory(gameMap, this);
    }


    public void assignConstraint(List<Player> playerList){
        if(playerList == null)
            throw new NullPointerException(ConstantsContainer.NULLPARAMETERS);

        for(Player player: playerList){
            if(!player.equals(this))
                player.setConstraint(this.getPower());
        }
    }




}
