package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.Server.VirtualView;

import java.util.HashMap;

public class GameController implements Observer<Message> {

    private Game game;
    private HashMap<String, VirtualView> clients;

    public GameController(Game game, HashMap<String, VirtualView> clients) {
        this.game = game;
        this.clients = clients;
    }

    public void processMessage(Message message){


    }

    public VirtualView getVirtualViewFromName(){

        return null;

    }

    public void handleTurnBeginning(){

    }

    public void handleEndTun(){


    }

    @Override
    public void update(Message message) {
        processMessage(message);
    }


}
