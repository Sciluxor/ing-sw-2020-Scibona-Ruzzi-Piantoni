package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Cards.Card;

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




}
