package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.network.message.MessageType;

import java.util.List;

/**
 * Interface implemented by both CLI and GUI, that represents all the graphical functions of the game
 */

public interface FunctionListener {

    /**
     * Method that handle the graphical update of the players in the lobby
     */

    void updateLobbyPlayer();

    /**
     * Method that notify the Client that the nickName selected is already in use in the Game
     */

    void nickUsed();

    /**
     * Method that notify the Client that the game is about to Start (The Lobby is Full)
     */

    void startGame();

    /**
     * Method that notify the Client that someone is Starting the turn
     * @param nick The NickName of the Player Starting the turn
     * @param isYourPlayer Boolean that is True if the Client is starting the turn
     */

    void startTurn(String nick,boolean isYourPlayer);

    /**
     * Method that handle the Challenger phase
     * @param challengerNick The NickName of the challenger
     * @param isYourPlayer Boolean that is True if the Client is the Challenger
     */

    void challengerChoice(String challengerNick,boolean isYourPlayer);

    /**
     * Method that handle the Card Choice phase
     * @param challengerNick The NickName of the Player that is choosing the Card
     * @param isYourPlayer Boolean that is True if the Client is choosing the Card
     */

    void cardChoice(String challengerNick,boolean isYourPlayer);

    /**
     * Method that handle the Place Workers phase
     * @param challengerNick The NickName of the Player that is placing the workers
     * @param isYourPlayer Boolean that is True if the Client is placing the workers
     */

    void placeWorker(String challengerNick,boolean isYourPlayer);

    /**
     * Method that handle the graphical update of the board after the workers of the other Clients are placed
     * @param squares The squares in which the workers have been placed
     */

    void updatePlacedWorkers(List<Square> squares);

    /**
     * Method that handle the graphical update of the board after a move or build of other Clients
     * @param nick Nickname of the Client that made the move or build
     * @param squares The squares modified by the move or build
     * @param type The type of the Message(move or build)
     */

    void updateBoard(String nick,List<Square> squares,MessageType type);

    /**
     * Method that notify the Client that there is a winner
     * @param nick NickName of the Winner
     */

    void notifyWin(String nick);

    /**
     * Method that notify the Client that there is a loser
     * @param nick NickName of the Winner
     * @param isYourPlayer Boolean that is True the Client is the loser
     */

    void notifyLose(String nick,boolean isYourPlayer);

    /**
     * Method that handle the graphical display of the actions available in that moment(build,move,end turn)
     * @param actions The Actions available in that moment
     */

    void displayActions(List<MessageType> actions);

    /**
     * Method that add constraint to the Client
     * @param name Name of the hero(constraint) to add to the Client
     */

    void addConstraint(String name);

    /**
     * Method that remove constraint from the Client
     * @param name Name of the hero(constraint) to remove from the Client
     */

    void removeConstraint(String name);

    /**
     * Method that handle the Turn Time ended event
     * @param stopper Name of the client that stopped the Game
     */

    void onTurnTimerEnded(String stopper);

    /**
     * Method called when a Client leave the game and stop the game
     * @param stopper Name of the client that leave the game
     */

    void onStoppedGame(String stopper);

    /**
     * Method called when the client get disconnected from the lobby (timer ended), notify the Client
     */

    void onLobbyDisconnection();

    /**
     * Method called when the client don't receive ping messages from the server, notify the Client
     */

    void onPingDisconnection();

    /**
     * Method called if the Client get disconnected after the game is ended
     */

    void onEndGameDisconnection();

    /**
     * Method that handle new chat messages written by other clients
     * @param nick NickName of the Client that wrote the message
     * @param message The Message written by the Client
     */

    void newChatMessage(String nick,String message);

    /**
     * Method that handle non official CLI or GUI Version, called when the Server detect an error in the Messages
     * @param stopper The Client that stopped the game
     * @param isYourPlayer Boolean that is True the Client is the stopper
     */

    void onErrorMessage(String stopper, boolean isYourPlayer);

    /**
     * Method called when the Client send a message during the turn of the other Clients (except chat messages)
     */

    void notYourTurn();
}
