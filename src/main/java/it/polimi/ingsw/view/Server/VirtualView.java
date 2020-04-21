package it.polimi.ingsw.view.Server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;

import java.util.LinkedList;


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
               handleYourPlayerAdded();
               break;
           case NICKUSED:
               handleNickUsed();
               break;
           case GAMESTARTED:
               handleStartGame();
               break;
           case PLACEWORKERSERROR:
           case STATUSERROR:
           case CARDCHOICEERROR:
           case CHALLENGERCHOICEERROR:
           case STARTURNERROR:
               handleClientError();
               break;
           case CHALLENGERCHOICE:
               //aggoingere metodo
               break;
           case PLAYERLOSE:
               //handleYourPlayerLose(); //finire questi
               break;
           default:
       }

   }

   public void onUpdatedInstance(Response status){
        switch (status){
            case PLAYERADDED:
                handleNewPlayerAdded();
                break;
            case REMOVEDPLAYER:
                handleRemovedPlayer();
                break;
            case CHALLENGERCHOICE:
                handleChallengerChoice();
                break;
            case PLAYERLOSE:
               // handleOtherPlayerLoose();
                break;
            default:
        }
   }

    //
    // methods for the turn of player
    //

   public void handleYourPlayerAdded(){
        WaitPlayerMessage message = new WaitPlayerMessage(ConstantsContainer.SERVERNAME,MessageSubType.UPDATE,connection.getUserID());
        connection.sendMessage(buildWaitLobbyMessage(message));
   }

   public void handleNickUsed(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME, MessageType.CONFIG,MessageSubType.NICKUSED,connection.getUserID()));
        connection.startLobbyTimer();
   }

   public void handleStartGame(){
       connection.sendMessage(new GameStartedMessage(ConstantsContainer.SERVERNAME,MessageSubType.UPDATE, controller.getNumberOfPlayers(), controller.getGameID()));
   }

   public Message buildWaitLobbyMessage(WaitPlayerMessage message){
       for(Player player: controller.getActualPlayers()){
           message.addColor(player.getColor());
           message.addNickName(player.getNickname());

       }
       return message;

   }

   public void handleClientError(){
                                    //connection.sendMessage(new Message());
   }

    //
    //methods for the idle turn of the player
    //

    public void handleNewPlayerAdded(){
        WaitPlayerMessage message =  new WaitPlayerMessage(ConstantsContainer.SERVERNAME,MessageSubType.NEWPLAYER,connection.getUserID());
        connection.sendMessage(buildWaitLobbyMessage(message));
    }
    public void handleRemovedPlayer(){
        WaitPlayerMessage message =  new WaitPlayerMessage(ConstantsContainer.SERVERNAME,MessageSubType.REMOVEDPLAYER,connection.getUserID());
        connection.sendMessage(buildWaitLobbyMessage(message));
    }

    public void handleNotYourTurn(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.NOTYOURTURN,MessageSubType.ERROR));
    }

    //
    //methods for both
    //

    public void handleChallengerChoice(){
        //connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.CHALLENGERCHOICE,MessageSubType.REQUEST,controller.getCurrentPlayer().getNickname()));
    }
    //getNickname manca la maiuscola


    @Override
    public void update(Response status) {
      if(isYourTurn)
       onUpdatedStatus(status);
      else onUpdatedInstance(status);
    }
}
//creare un'interfaccia comune per virtualView e view del client