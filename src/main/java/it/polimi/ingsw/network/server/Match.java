package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.network.message.GameConfigMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.view.Server.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;

public class Match {


    private GameController controller;
    private final int numberOfPlayer;

    public Match(int numberOfPlayer,String gameID) {
        this.numberOfPlayer = numberOfPlayer;
        this.controller = new GameController(numberOfPlayer,gameID);
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }



    public void sendMsgToVirtualView(Message msg,VirtualView view) {
        view.processMessageReceived(msg);
    }

    public void addPlayer(ClientHandler connection,Message message,String userID){
        VirtualView view = new VirtualView(connection,controller);
        ((GameConfigMessage) message).setView(view);
        connection.setView(view);
        connection.setViewActive(true);
        view.addObservers(controller);
        connection.setUserID(userID);
        controller.addUserID(view,userID);
        sendMsgToVirtualView(message,view);

    }

    public boolean isFull()
    {
        return controller.isFull();
    }

    public void startGame(){

        //manda messaggi per iniziare a scegliere le carte, bisogna inizializzare la queue dei giocatori tramite controller e game.
        //vedere se per la queue basta la queue o serve deque?


    }

}
