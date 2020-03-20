package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.Cards.CardSubType;
import it.polimi.ingsw.model.Cards.CardType;
import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;

import java.util.ArrayList;

public class Player {

    private String nickname;
    private Card power;
    private TurnStatus turnStatus;
    private ArrayList<Card> constraint;
    private ArrayList<Worker> workers;
    private Worker currentWorker;
    private Worker unmovedWorker;

    public Player (String nickname, Card power, TurnStatus turnStatus, ArrayList<Card> constraint, ArrayList<Worker> workers, Worker currentWorker, Worker unmovedWorker){

        this.nickname = nickname;
        this.power = power;
        this.turnStatus = turnStatus;
        this.constraint = constraint;

        workers.add(new Worker(WorkerName.WORKER1));
        workers.add(new Worker(WorkerName.WORKER2));


        this.currentWorker = currentWorker;
        this.unmovedWorker = unmovedWorker;
    }

    public Card getPower(){ return power;}

    public void setPower(Card power){ this.power = power;}

    public TurnStatus getTurnStatus() { return turnStatus;}

    public void setTurnStatus(TurnStatus turnStatus) { this.turnStatus = turnStatus;}

    public ArrayList<Card> getConstraint() { return constraint;}

    public void setConstraint(Card constraint) { this.constraint.add(constraint);}

    public void removeConstraint(Card constraint){ this.constraint.remove(constraint);}

    public ArrayList<Worker> getWorkers() { return workers;}

    public void setWorkers(ArrayList<Worker> workers) { this.workers = workers;}

    public void setCurrentWorker(Worker currentWorker) { this.currentWorker = currentWorker;}

    public Worker getCurrentWorker() { return currentWorker;}

    public void setUnmovedWorker(Worker unmovedWorker) { this.unmovedWorker = unmovedWorker;}

    public Worker getUnmovedWorker() { return unmovedWorker;}

    //
    //function to find all the reachable square moving from a specific square
    //

    public Worker getWorkerFromString(String worker){
        WorkerName name = WorkerName.parseInput(worker);
        for (Worker work : workers)
            if(work.getName().equals(name))
                return work;
        return null;
    }

    public boolean selectCurrentWorker(GameMap gameMap, String worker){
        Worker worker1 = getWorkerFromString(worker);
        if (!checkIfCanMove(gameMap, worker1)){
            return false;
        }
        setCurrentWorker(worker1);
        return true;
    }

    public boolean checkIfCanMove(GameMap gameMap, Worker worker){
        ArrayList<Directions> direction = findWorkerMove(gameMap, worker);
        if(direction.size() > 0){
            for(Card card : constraint){
                    if(card.getType().equals(CardType.YOURMOVE) && !card.getSubType().equals(CardSubType.NORMAL)){
                        if(card.eliminateInvalidMove(gameMap, direction).size() > 0) {
                            for(Card card2 : constraint)
                                if(card2.getType().equals(CardType.YOURTURN) && !card2.getSubType().equals(CardSubType.NORMAL)) {
                                    return card2.canMove(gameMap, worker).size() > 0;
                                }return  false;

                        }return false;   //aggiustare i return
                    } return false;
            }return  false;
        }
        return false;
    }

    public boolean checkIfLoose(GameMap gameMap){
        for (Worker work : workers){
            checkIfCanMove(gameMap, work);
        }
        return true;
    }

    public ArrayList<Directions> findWorkerMove(GameMap gameMap, Worker worker){ return power.findWorkerMove(gameMap, worker);}

    public void executeWorkerMove(GameMap gameMap, Directions direction, Player player){ power.executeWorkerMove(gameMap, direction, player);}

    public ArrayList<Directions> findPossibleBuild(GameMap gameMap, Worker worker){ return power.findPossibleBuild(gameMap, worker);}

    public void executeBuild(GameMap gameMap, Building building, Directions direction){ power.executeBuild(gameMap, building, direction, this.currentWorker);}

    public boolean checkVictory(GameMap gameMap, Worker worker){ return  power.checkVictory(gameMap, worker);}




}
