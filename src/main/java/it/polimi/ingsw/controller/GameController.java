package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Cards.Response;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.network.message.GameConfigMessage;
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
    private Timer reconnectionTimer;
    private RoundController roundController;

    public GameController(Game game, HashMap<String, VirtualView> clients, ArrayList<VirtualView> clientsVirtualview) {
        this.game = game;
        this.clients = clients;
        for(VirtualView view: clientsVirtualview){
            view.addObservers(this);
        }
        this.roundController = new RoundController();
    }

    //
    //methods for new player
    //

    public void handleNewPlayer(Message message){
        ArrayList<Player> players = game.getPlayers();
        for(Player player: players){
            if(player.getNickname().equalsIgnoreCase(((GameConfigMessage) message).getNickName()));
              game.setGameStatus(Response.NICKUSED);
              return;
        }
        game.setGameStatus(Response.PLAYERADDED);

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

    public void removeNonPermanentConstraint(){


    }

    public void handleEndTun(){


    }

    //aggiungere metodi per la disconnesione dei player

    public void sendToRoundController(Message message){


    }




    public void processMessage(Message message){

        switch (message.getType()){
            case CONFIG:
                handleNewPlayer(message);
        }


    }



    @Override
    public void update(Message message) {

        processMessage(message);
    }


}
