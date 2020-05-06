package it.polimi.ingsw.view.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.utils.ConstantsContainer;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;



public class VirtualView extends Observable<Message> implements Observer<Response> {

    private final ClientHandler connection;
    private final GameController controller;
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
            case STARTTURNERROR:
                handleClientError();
                break;
            case CHALLENGERCHOICE:
                handleChallengerChoice();
                break;
            case CARDCHOICE:
                handleCardChoice();
                break;
            case PLACEWORKERS:
                handlePlaceWorkers();
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
                handleOtherChallengerChoice();
                break;
            case CHALLENGERCHOICEDONE:
                handleChallengerChoiceDone();
                break;
            case CARDCHOICE:
                handleOtherCardChoice();
                break;
            case CARDCHOICEDONE:
                handleOtherCardChoiceDone();
                break;
            case PLACEWORKERS:
                handleOtherPlaceWorkers();
                break;
            case PLACEWORKERSDONE:
                handleOtherPlaceWorkersDone();
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
        WaitPlayerMessage message = new WaitPlayerMessage(ConstantsContainer.SERVERNAME,MessageSubType.UPDATE,connection.getUserID());
        connection.sendMessage(buildWaitLobbyMessage(message));
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME, MessageType.CONFIG,MessageSubType.NICKUSED,connection.getUserID()));
        connection.startLobbyTimer();
    }

    public void handleStartGame(){
        connection.sendMessage(new GameStartedMessage(ConstantsContainer.SERVERNAME,MessageSubType.UPDATE,controller.getGameID()));
    }

    public Message buildWaitLobbyMessage(WaitPlayerMessage message){
        for(Player player: controller.getActualPlayers()){
            message.addColor(player.getColor());
            message.addNickName(player.getNickname());
        }
        return message;
    }

    public void handleChallengerChoice(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.CHALLENGERCHOICE,MessageSubType.REQUEST,controller.getCurrentPlayer().getNickname()));
    }

    public void  handleCardChoice(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.CHOOSECARD,MessageSubType.REQUEST,controller.getCurrentPlayer().getNickname()));
    }

    public void handlePlaceWorkers(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.PLACEWORKERS,MessageSubType.REQUEST,controller.getCurrentPlayer().getNickname()));
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

    public void handleOtherChallengerChoice(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.CHALLENGERCHOICE,MessageSubType.UPDATE,controller.getCurrentPlayer().getNickname()));
    }

    public void handleChallengerChoiceDone(){
        connection.sendMessage(new ChallengerChoiceMessage(ConstantsContainer.SERVERNAME,controller.getCurrentPlayer().getNickname(),MessageSubType.SETTED,null,controller.getAvailableCards()));
    }
    public void handleOtherCardChoice(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.CHOOSECARD,MessageSubType.UPDATE,controller.getCurrentPlayer().getNickname()));
    }
    public void handleOtherCardChoiceDone(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.CHOOSECARD,MessageSubType.SETTED,controller.getCurrentPlayer().getPower().getName()));
    }
    public void  handleOtherPlaceWorkers(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.PLACEWORKERS,MessageSubType.UPDATE,controller.getCurrentPlayer().getNickname()));
    }
    public void handleOtherPlaceWorkersDone(){
        connection.sendMessage(new PlaceWorkersMessage(ConstantsContainer.SERVERNAME,MessageSubType.SETTED,controller.getModifiedSquares().get(0).getCoordinates(),controller.getModifiedSquares().get(1).getCoordinates()));
    }

    //
    //methods for both
    //


    //getNickname manca la maiuscola


    @Override
    public void update(Response status) {
        if(isYourTurn)
            onUpdatedStatus(status);
        else onUpdatedInstance(status);
    }
}