package it.polimi.ingsw.view.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.cards.Card;
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
            case REMOVEDPLAYER:
                handlePlayerAdded();
                break;
            case NICKUSED:
                handleNickUsed();
                break;
            case GAMESTARTED:
                handleStartGame();
                break;
            case PLACEWORKERSERROR:        //vedere bene gli errori
            case STATUSERROR:
            case CARDCHOICEERROR:
            case CHALLENGERCHOICEERROR:
            case STARTTURNERROR:
                handleClientError();
                break;
            case CHALLENGERCHOICE:
                handleChallengerChoice();
                break;
            case CHALLENGERCHOICEDONE:
                handleChallengerChoiceDone();
                break;
            case CARDCHOICE:
                handleCardChoice();
                break;
            case CARDCHOICEDONE:
                handleCardChoiceDone();
                break;
            case PLACEWORKERS:
                handlePlaceWorkers();
                break;
            case PLACEWORKERSDONE:
                handlePlaceWorkersDone();
                break;
            case ASSIGNEDPERMCONSTRAINT:
                handlePermConstraint();
                break;
            case ASSIGNEDCONSTRAINT:
                handleNonPermConstraint();
                break;
            case STARTTURN:
                handleStartTurn();
                break;
            case PLAYERLOSE:
                handleLose();
                break;
            case WIN:
                handleWin();
                break;
            case LOSEWIN:
                handleLoseWin();  //mancano caso stoppedGame e playerTimerEnded
                break;
            case GAMESTOPPED:
                handleGameStopped();
                break;
            case PLAYERTIMERENDED:
                handlePlayerTimeEnded();
                break;
            default:
        }

    }

    public void onUpdatedInstance(Response status){
        switch (status){
            case PLAYERADDED:
                //handleNewPlayerAdded();
                break;
            case REMOVEDPLAYER:
                //handleRemovedPlayer();
                break;
            case CHALLENGERCHOICE:
                //handleOtherChallengerChoice();
                break;
            case CHALLENGERCHOICEDONE:
                //handleChallengerChoiceDone();
                break;
            case CARDCHOICE:
                //handleOtherCardChoice();
                break;
            case CARDCHOICEDONE:
                //handleOtherCardChoiceDone();
                break;
            case PLACEWORKERS:
                //handleOtherPlaceWorkers();
                break;
            case PLACEWORKERSDONE:
                //handleOtherPlaceWorkersDone();
                break;
            case ASSIGNEDPERMCONSTRAINT:
                //handleOtherPermConstraint();
                break;
            case STARTTURN:
                //handleOtherTurnStarted();
                break;
            case MOVED:
            case NEWMOVE:
                handleOtherMove();
                break;
            case BUILD:
            case BUILDEDBEFORE:
            case NEWBUILD:
                handleOtherBuild();
                break;
            case ASSIGNEDCONSTRAINT:
                //handleNonPermConstraint();
                break;
            case PLAYERLOSE:
                 handleLose();
                break;
            case WIN:
                handleWin();
                break;
            case LOSEWIN:
                handleLoseWin();
                break;
            case GAMESTOPPED:
                handleGameStopped();
                break;
            case PLAYERTIMERENDED:
                handlePlayerTimeEnded();
                break;
            default:
        }
    }

    //
    // methods for the turn of player
    //

    public void handlePlayerAdded(){
        WaitPlayerMessage message = new WaitPlayerMessage(ConstantsContainer.SERVERNAME,MessageSubType.UPDATE,connection.getUserID());
        connection.sendMessage(buildWaitLobbyMessage(message));
    }

    public Message buildWaitLobbyMessage(WaitPlayerMessage message){
        for(Player player: controller.getActualPlayers()){
            message.addColor(player.getColor());
            message.addNickName(player.getNickName());
        }
        return message;
    }

    public void handleNickUsed(){
        if(isYourTurn) {
            WaitPlayerMessage message = new WaitPlayerMessage(ConstantsContainer.SERVERNAME, MessageSubType.UPDATE, connection.getUserID());
            connection.sendMessage(buildWaitLobbyMessage(message));
            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME, MessageType.CONFIG, MessageSubType.NICKUSED, connection.getUserID()));
            connection.startLobbyTimer();
        }
    }

    public void handleStartGame(){
        connection.sendMessage(new GameStartedMessage(ConstantsContainer.SERVERNAME,MessageSubType.UPDATE,controller.getGameID()));
    }

    public void handleChallengerChoice(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.CHALLENGERCHOICE,MessageSubType.REQUEST,controller.getCurrentPlayer().getNickName()));
    }

    public void handleChallengerChoiceDone(){
        connection.sendMessage(new ChallengerChoiceMessage(ConstantsContainer.SERVERNAME,controller.getCurrentPlayer().getNickName(),MessageSubType.SETTED,null,controller.getAvailableCards()));
    }

    public void  handleCardChoice(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.CHOOSECARD,MessageSubType.REQUEST,controller.getCurrentPlayer().getNickName()));
    }

    public void handleCardChoiceDone(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.CHOOSECARD,MessageSubType.SETTED,controller.getCurrentPlayer().getPower().getName()));
    }

    public void handlePlaceWorkers(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.PLACEWORKERS,MessageSubType.REQUEST,controller.getCurrentPlayer().getNickName()));
    }

    public void handlePlaceWorkersDone(){
        connection.sendMessage(new PlaceWorkersMessage(ConstantsContainer.SERVERNAME,MessageSubType.SETTED,controller.getModifiedSquares().get(0).getCoordinates(),controller.getModifiedSquares().get(1).getCoordinates()));
    }

    public void handlePermConstraint(){
        for(Player player: controller.getActualPlayers())
                sendPermConstraint(player);
    }

    public void sendPermConstraint(Player player){
        for(Card card: player.getConstraint())
            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,player.getNickName(),MessageType.PERMCONSTRAINT,MessageSubType.UPDATE,card.getName()));
    }

    public void handleNonPermConstraint(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.NONPERMCONSTRAINT,MessageSubType.UPDATE,controller.getCurrentPlayer().getPower().getName()));
    }

    public void handleStartTurn(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.STARTTURN,MessageSubType.REQUEST,controller.getCurrentPlayer().getNickName()));
    }

    public void handleClientError(){
        //connection.sendMessage(new Message());
    }

    //
    //methods for the idle turn of the player
    //



    public void handleNotYourTurn(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.NOTYOURTURN,MessageSubType.ERROR));
    }




    public void handleOtherMove(){
        connection.sendMessage(new MoveWorkerMessage(ConstantsContainer.SERVERNAME,controller.getCurrentPlayer().getNickName(),controller.getModifiedSquares()));
    }

    public void handleOtherBuild(){
        connection.sendMessage(new BuildWorkerMessage(ConstantsContainer.SERVERNAME,controller.getCurrentPlayer().getNickName(),controller.getModifiedSquares()));
    }

    //
    //methods for both
    //

    public void handleChatMessage(Message message){
        connection.sendMessage(message);
    }

    public void handleWin(){
        if(!connection.getNickName().equals(controller.getCurrentPlayer().getNickName())) {
            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME, MessageType.WIN, MessageSubType.UPDATE, controller.getWinner()));
        }
        controller.resetPlayer(this);
    }

    public void handleLoseWin(){
        if(!connection.getNickName().equals(controller.getCurrentPlayer().getNickName()))
            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.WIN,MessageSubType.UPDATE,controller.getWinner()));
        else{
            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.WIN,MessageSubType.UPDATE,controller.getWinner()));
        }
        controller.resetPlayer(this);
    }

    public void handleLose(){
        if(connection.getNickName().equals(controller.getLastLosePlayer()))
            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.LOSE,MessageSubType.REQUEST,controller.getLastLosePlayer()));
        else
            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.LOSE,MessageSubType.UPDATE,controller.getLastLosePlayer()));

    }

    public void handleGameStopped(){
        if(connection.isConnectionActive()){
            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.STOPPEDGAME,MessageSubType.UPDATE,controller.getStopper()));
        }
    }

    public void handlePlayerTimeEnded(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.STOPPEDGAME,MessageSubType.TIMEENDED,controller.getStopper()));
    }


    @Override
    public void update(Response status) {
        onUpdatedStatus(status);
    }
}