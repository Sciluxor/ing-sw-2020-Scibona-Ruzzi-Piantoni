package it.polimi.ingsw.view.Server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;



public class VirtualView extends Observable<Message> implements Observer<Response> {

    private ClientHandler connection;
    private GameController controller;
    private boolean isGameStarted = false;
    private boolean isYourTurn = false;

    public VirtualView(ClientHandler connection,GameController controller) {
        this.connection = connection;
        this.controller = controller;

    }

    public ClientHandler getConnection() {
        return connection;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public void processMessageReceived(Message message){
       notify(message);
   }

   public void onUpdatedStatus(Response status){
       switch (status) {
           case PLAYERADDED:
               handlePlayerAdded();
               break;
           case NICKUSED:
               handleNickUsed();
               break;
           case GAMESTARTED:
               handleStartGame();
               break;
       }

   }

   public void onUpdatedInstance(Response status){
        switch (status){

            default:
        }
   }

   public void handlePlayerAdded(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.WAITPLAYER,MessageSubType.UPDATE,connection.getUserID()));

   }

   public void handleNickUsed(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME, MessageType.CONFIG,MessageSubType.NICKUSED,connection.getUserID()));
        connection.startLobbyTimer();
   }

   public void handleStartGame(){
       connection.sendMessage(new GameStartedMessage(ConstantsContainer.SERVERNAME,MessageSubType.UPDATE, controller.getNumberOfPlayers(), controller.getGameID()));
   }



    @Override
    public void update(Response status) {
      if(isYourTurn)
       onUpdatedStatus(status);
      else onUpdatedInstance(status);
    }
}
