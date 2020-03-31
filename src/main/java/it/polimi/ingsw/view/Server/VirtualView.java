package it.polimi.ingsw.view.Server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Cards.Response;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.gameStartedMessage;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

import java.awt.*;


public class VirtualView extends Observable<Message> implements Observer<Response> {
    private Player player;
    private ClientHandler connection;
    private GameController controller;
    private boolean isGameStarted = false;
    private boolean isYourTurn = false;

    public VirtualView(ClientHandler connection,String nickName) {
        this.connection = connection;
        this.player = new Player(nickName);

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
           case NICKUSED:
               handleNickUsed();
       }

   }

   public void onUpdatedInstance(Response status){
        switch (status){

            default:return;
        }
   }

   public void handlePlayerAdded(){

   }

   public void handleNickUsed(){

   }

   public Player getPlayer(){
       return player;

   }

   public void sendGamestartedMessage(int number){
        connection.sendMessage(new gameStartedMessage(ConstantsContainer.SERVERNAME, MessageSubType.UPDATE,number));

   }


    @Override
    public void update(Response status) {
      if(isYourTurn)
       onUpdatedStatus(status);
      else onUpdatedInstance(status);
    }
}
