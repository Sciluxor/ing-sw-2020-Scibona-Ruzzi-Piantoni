package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.network.message.MessageType;

import java.util.List;

/**
 * Interface implemented by both CLI and GUI, that represents all the graphical functions of the game
 */

public interface FunctionListener {

    /**
     *
     */

    void updateLobbyPlayer();

    /**
     *
     */

    void nickUsed();

    /**
     *
     */

    void startGame();

    /**
     *
     * @param nick
     * @param isYourPlayer
     */

    void startTurn(String nick,boolean isYourPlayer);

    /**
     *
     * @param challengerNick
     * @param isYourPlayer
     */

    void challengerChoice(String challengerNick,boolean isYourPlayer);

    /**
     *
     * @param challengerNick
     * @param isYourPlayer
     */

    void cardChoice(String challengerNick,boolean isYourPlayer);

    /**
     *
     * @param challengerNick
     * @param isYourPlayer
     */

    void placeWorker(String challengerNick,boolean isYourPlayer);

    /**
     *
     * @param squares
     */

    void updatePlacedWorkers(List<Square> squares);

    /**
     *
     * @param nick
     * @param squares
     * @param type
     */

    void updateBoard(String nick,List<Square> squares,MessageType type);

    /**
     *
     * @param nick
     */

    void notifyWin(String nick);

    /**
     *
     * @param nick
     * @param isYourPlayer
     */

    void notifyLose(String nick,boolean isYourPlayer);

    /**
     *
     * @param actions
     */

    void displayActions(List<MessageType> actions);

    /**
     *
     * @param name
     */

    void addConstraint(String name);

    /**
     *
     * @param name
     */

    void removeConstraint(String name);

    /**
     *
     * @param stopper
     */

    void onTurnTimerEnded(String stopper);

    /**
     *
     * @param stopper
     */

    void onStoppedGame(String stopper);

    /**
     *
     */

    void onLobbyDisconnection();

    /**
     *
     */

    void onPingDisconnection();

    /**
     *
     */

    void onEndGameDisconnection();

    /**
     *
     * @param nick
     * @param message
     */

    void newChatMessage(String nick,String message);

    /**
     *
     * @param stopper
     * @param isYourPlayer
     */

    void onErrorMessage(String stopper, boolean isYourPlayer);

    /**
     *
     */

    void notYourTurn();
}
