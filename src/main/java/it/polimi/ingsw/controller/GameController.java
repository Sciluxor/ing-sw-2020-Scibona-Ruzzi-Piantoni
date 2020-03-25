package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.Server.VirtualView;

import java.util.HashMap;

public class GameController implements Observer<Message> {

    private HashMap<String, VirtualView> clients = new HashMap<>();

    public void processMessage(Message message){


    }

    public VirtualView getVirtualViewFromName(){

        return null;

    }


    @Override
    public void update(Message message) {
        processMessage(message);
    }


}
