package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.message.GameConfigMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.Server.VirtualView;


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

    public boolean isStarted(){
        return controller.isGameStarted();
    }

    public boolean checkNick(Message message){

        String nick = message.getNickName();
        return controller.isFreeNick(nick);


    }

    public boolean checkIfGameCanStart(){
        return controller.checkIfGameCanStart();
    }


}
