package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.Server.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;

public class Match {

    private ArrayList<VirtualView> actualPlayers;
    private GameController controller;
    private Game game;
    private final int numberOfPlayer;

    public Match(ArrayList<VirtualView> actualPlayers,int numberOfPlayer) {
        this.actualPlayers = actualPlayers;
        this.numberOfPlayer = numberOfPlayer;
        this.game = new Game(actualPlayers,numberOfPlayer);
    }

    public void createViewMap(ArrayList<VirtualView> actualPlayers){
        HashMap<String, VirtualView> clients = new HashMap<>();
        for(VirtualView view:actualPlayers){
            clients.put(view.getPlayer().getNickname(),view);
        }
    }

    public void sendMsgToVirtualView(Message msg) {
        for (VirtualView view : actualPlayers) {
            if (view.getPlayer().getNickname().equals(msg.getSender())) {
                view.processMessageReceived(msg);
                break;
            }
        }
    }

    public void startGame(){

        //manda messaggi per iniziare a scegliere le carte, bisogna inizializzare la queue dei giocatori tramite controller e game.
        //vedere se per la queue basta la queue o serve deque?


    }

}
