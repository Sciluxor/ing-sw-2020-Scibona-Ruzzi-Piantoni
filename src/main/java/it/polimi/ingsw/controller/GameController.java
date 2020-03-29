package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.Server.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class GameController implements Observer<Message> {

    private Game game;
    private HashMap<String, VirtualView> clients;
    private Timer turnTimer ;
    private Timer reconnesionTimer;
    private RoundController roundController;

    public GameController(Game game, HashMap<String, VirtualView> clients, ArrayList<VirtualView> clientsVirtualview) {
        this.game = game;
        this.clients = clients;
        for(VirtualView view: clientsVirtualview){
            view.addObservers(this);
        }
        this.roundController = new RoundController();
    }

    public void processMessage(Message message){



    }

    public VirtualView getVirtualViewFromName(){

        return null;

    }

    public void handleMatchBeginning(){

    }

    public void handleTurnBeginning(){

    }

    public void handleWorkerChoice(){

    }

    public void handleEndTun(){


    }

    //aggiungere metodi per la disconnesione dei player

    public void sendToRoundController(Message message){


    }



    @Override
    public void update(Message message) {

        processMessage(message);
    }


}
