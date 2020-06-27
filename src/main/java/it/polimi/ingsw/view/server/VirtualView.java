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

/**
 * Class that represents the VirtualView of a Specific Client Server Side
 * @author alessandroruzzi
 * @version 1.0
 * @since 2020/06/26
 */

public class VirtualView extends Observable<Message> implements Observer<Response> {

    private final ClientHandler connection;
    private final GameController controller;
    private boolean isYourTurn = false;

    /**
     * Public constructor for the Virtual View
     * @param connection Client Handler of the specific client
     * @param controller Controller of the specific game
     */

    public VirtualView(ClientHandler connection,GameController controller) {
        this.connection = connection;
        this.controller = controller;
    }

    /**
     * Get the Client Handler of the client
     * @return The Client Handler of the client
     */

    public ClientHandler getConnection() {
        return connection;
    }

    /**
     * Function that check if is the turn of the client of the virtual view
     * @return true if is the turn of the client, false otherwise
     */

    public boolean isYourTurn() {
        return isYourTurn;
    }

    /**
     * Set the boolean is your turn to true or false
     * @param yourTurn New value of the boolean is your turn
     */

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }

    /**
     * Function that notify the controller about the message received from the client
     * @param message Message received from the client
     */

    public void processMessageReceived(Message message){
        notify(message);
    }

    /**
     * Function that process the new status of the game and decide which type of message to send to the client
     * @param status The new status of the game
     */

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
            case PLACEWORKERSERROR:
            case STATUSERROR:
            case CARDCHOICEERROR:
            case CHALLENGERCHOICEERROR:
            case STARTTURNERROR:
            case MOVEWINMISMATCH:
            case BUILDWINMISMATCH:
            case WRONGSQUAREBUILD:
            case WRONGSQUAREMOVE:
                handleClientError();
                break;
            case GAMESTOPPEDERROR:
                handleGameStoppedError();
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
            case MOVED:
            case NEWMOVE:
                handleMove();
                break;
            case BUILD:
            case BUILDEDBEFORE:
            case NEWBUILD:
                handleBuild();
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

    /**
     * Function that send the lobby update to the client
     */

    public void handlePlayerAdded(){
        WaitPlayerMessage message = new WaitPlayerMessage(ConstantsContainer.SERVERNAME,MessageSubType.UPDATE,connection.getUserID());
        connection.sendMessage(buildWaitLobbyMessage(message));
    }

    /**
     * Function that build the lobby message
     * @param message Message to send to the client
     * @return The complete message with the players in the lobby and their colors
     */

    public Message buildWaitLobbyMessage(WaitPlayerMessage message){
        for(Player player: controller.getActualPlayers()){
            message.addColor(player.getColor());
            message.addNickName(player.getNickName());
        }
        return message;
    }

    /**
     * Function that notify the client that the nickname is already in use
     */

    public void handleNickUsed(){
        if(isYourTurn) {
            WaitPlayerMessage message = new WaitPlayerMessage(ConstantsContainer.SERVERNAME, MessageSubType.UPDATE, connection.getUserID());
            connection.sendMessage(buildWaitLobbyMessage(message));
            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME, MessageType.CONFIG, MessageSubType.NICKUSED, connection.getUserID()));
            connection.startLobbyTimer();
        }
    }

    /**
     * Function that notify the client that the game is starting
     */

    public void handleStartGame(){
        connection.sendMessage(new GameStartedMessage(ConstantsContainer.SERVERNAME,MessageSubType.UPDATE,controller.getGameID()));
    }

    /**
     * Function that notify the client about the challenger phase
     */

    public void handleChallengerChoice(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.CHALLENGERCHOICE,MessageSubType.REQUEST,controller.getCurrentPlayer().getNickName()));
    }

    /**
     * Function that notify the client about the challenger choice
     */

    public void handleChallengerChoiceDone(){
        connection.sendMessage(new ChallengerChoiceMessage(ConstantsContainer.SERVERNAME,controller.getCurrentPlayer().getNickName(),MessageSubType.SETTED,null,controller.getAvailableCards()));
    }

    /**
     * Function that notify the client the Card choice phase
     */

    public void  handleCardChoice(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.CHOOSECARD,MessageSubType.REQUEST,controller.getCurrentPlayer().getNickName()));
    }

    /**
     * Function that notify the client about the chosen card
     */

    public void handleCardChoiceDone(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.CHOOSECARD,MessageSubType.SETTED,controller.getCurrentPlayer().getPower().getName()));
    }

    /**
     * Function that notify the client about the place workers phase
     */

    public void handlePlaceWorkers(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.PLACEWORKERS,MessageSubType.REQUEST,controller.getCurrentPlayer().getNickName()));
    }

    /**
     * Function that notify the client about the position of the workers of a specific client
     */

    public void handlePlaceWorkersDone(){
        connection.sendMessage(new PlaceWorkersMessage(ConstantsContainer.SERVERNAME,MessageSubType.SETTED,controller.getModifiedSquares().get(0).getCoordinates(),controller.getModifiedSquares().get(1).getCoordinates()));
    }

    /**
     * Function that notify the client that he has a new permanent constraint
     */

    public void handlePermConstraint(){
        for(Player player: controller.getActualPlayers())
                sendPermConstraint(player);
    }

    /**
     * Function that notify the client that he has a new permanent constraint
     * @param player The Player to analise
     */

    public void sendPermConstraint(Player player){
        for(Card card: player.getConstraint())
            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,player.getNickName(),MessageType.PERMCONSTRAINT,MessageSubType.UPDATE,card.getName()));
    }

    /**
     * Function that notify the client that he has a new non permanent constraint
     */

    public void handleNonPermConstraint(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.NONPERMCONSTRAINT,MessageSubType.UPDATE,controller.getCurrentPlayer().getPower().getName()));
    }

    /**
     * Function that notify the client that someone is starting the turn
     */

    public void handleStartTurn(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.STARTTURN,MessageSubType.REQUEST,controller.getCurrentPlayer().getNickName()));
    }

    /**
     * Function that notify the client about a move of another client
     */

    public void handleMove(){
        connection.sendMessage(new MoveWorkerMessage(ConstantsContainer.SERVERNAME,controller.getCurrentPlayer().getNickName(),controller.getModifiedSquares()));
    }

    /**
     * Function that notify the client about a build of another client
     */

    public void handleBuild(){
        connection.sendMessage(new BuildWorkerMessage(ConstantsContainer.SERVERNAME,controller.getCurrentPlayer().getNickName(),controller.getModifiedSquares()));
    }

    /**
     * Function that notify the client that there is a winner
     */

    public void handleWin(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME, MessageType.WIN, MessageSubType.UPDATE, controller.getWinner()));
        controller.resetPlayer(this);
    }

    /**
     * Function that notify the client that there is a winner(all the other players remained blocked)
     */

    public void handleLoseWin(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.WIN,MessageSubType.REQUEST,controller.getWinner()));
        controller.resetPlayer(this);
    }

    /**
     * Function that notify the client that someone has lost(remained blocked)
     */

    public void handleLose(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.LOSE,MessageSubType.UPDATE,controller.getLastLosePlayer()));
    }

    /**
     * Function that notify the client that the game is ended(a client left the game)
     */

    public void handleGameStopped(){
        if(connection.isConnectionActive()){
            connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.STOPPEDGAME,MessageSubType.UPDATE,controller.getStopper()));
        }
    }

    /**
     * Function that notify the client that turn timer has ended
     */

    public void handlePlayerTimeEnded(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.STOPPEDGAME,MessageSubType.TIMEENDED,controller.getStopper()));
    }

    /**
     * Function that notify the client that he is using a non official version of the game, or that there was an error in the server
     */

    public void handleClientError(){
        if(isYourTurn){
            connection.clientError(new Message(connection.getUserID(),connection.getNickName(), MessageType.DISCONNECTION, MessageSubType.STOPPEDGAMEERROR));
        }
    }

    /**
     * Function that notify the client that the game was stopped because someone was using a non official version of the game, or for an error in the server
     */

    public void handleGameStoppedError(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.STOPPEDGAME,MessageSubType.STOPPEDGAMEERROR,controller.getStopper()));
        if(connection.isErrorStopper())
            connection.closeAfterDisconnection();
    }

    /**
     * Function that send a chat message (received from other clients) to the client
     * @param message The message to forward
     */

    public void handleChatMessage(Message message){
        connection.sendMessage(message);
    }

    /**
     * Function that notify the client that he sent a message (except chat message) during the turn of another player
     */

    public void handleNotYourTurn(){
        connection.sendMessage(new Message(ConstantsContainer.SERVERNAME,MessageType.NOTYOURTURN,MessageSubType.ERROR));
    }

    /**
     * Update function of the observer
     * @param status The new game status
     */

    @Override
    public void update(Response status) {
        onUpdatedStatus(status);
    }
}