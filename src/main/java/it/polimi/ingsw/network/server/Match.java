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
    private final Object matchLock = new Object();
    private HashMap<String, VirtualView> matchClients;
    private final int numberOfPlayer;

    public Match(ArrayList<VirtualView> actualPlayers,int numberOfPlayer) {
        this.actualPlayers = actualPlayers;
        this.numberOfPlayer = numberOfPlayer;
        this.matchClients = createViewMap(actualPlayers);
        this.controller = new GameController();
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public HashMap<String, VirtualView> createViewMap(ArrayList<VirtualView> actualPlayers){
        HashMap<String, VirtualView> clients = new HashMap<>();
        for(VirtualView view:actualPlayers){
            clients.put(view.getPlayer().getNickname(),view);
        }
        return clients;
    }

    public VirtualView getViewFromString(String nick){
        return this.matchClients.get(nick);

    }

    public void sendMsgToVirtualView(Message msg) {
        getViewFromString(msg.getSender()).processMessageReceived(msg);
    }

    public void addPlayer(ClientHandler connection,String nickName){
        VirtualView view = new VirtualView(connection,nickName);
        view.addObservers(controller);
        matchClients.put(view.getPlayer().getNickname(),view);

    }

    public void startGame(){

        //manda messaggi per iniziare a scegliere le carte, bisogna inizializzare la queue dei giocatori tramite controller e game.
        //vedere se per la queue basta la queue o serve deque?


    }

}
