package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Cards.Card;
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
        this.workers = workers;
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

    public void setCurrentWorker(Worker currentWorker) { this.currentWorker = currentWorker;}

    public Worker getCurrentWorker() { return currentWorker;}

    public void setUnmovedWorker(Worker unmovedWorker) { this.unmovedWorker = unmovedWorker;}

    public Worker getUnmovedWorker() { return unmovedWorker;}

    public void selectCurrentWorker(String worker){} //da implementare

    public boolean checkIfCanMove(GameMap gameMap, Worker worker){  return false;}  //da implementare

    public boolean checkIfLoose(){ return true;} //da implementare

    public void findWorkerMove(GameMap gameMap){  } //da implementare

    public void executeWorkerMove(GameMap gameMap, Directions direction){ } //da implementare

    public void findPossibleBuild(GameMap gameMap){ } //da implementare

    public void executeBuild(GameMap gameMap, Building building, Directions direction){ } //da implementare

    public boolean checkVictory(){ return  currentWorker.hasWin();} //da implementare




}
