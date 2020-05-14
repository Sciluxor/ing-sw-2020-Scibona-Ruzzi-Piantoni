package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.network.message.MessageType;

import java.util.List;

public interface FunctionListener {

    void updateLobbyPlayer();

    void nickUsed();

    void startGame();

    void startTurn(String nick,boolean isYourPlayer);

    void challengerChoice(String challengerNick,boolean isYourPlayer);

    void cardChoice(String challengerNick,boolean isYourPlayer);

    void placeWorker(String challengerNick,boolean isYourPlayer);

    void updatePlacedWorkers(List<Square> squares);

    void updateBoard(String nick,List<Square> squares,MessageType type);

    void notifyWin(String nick);

    void notifyLose(String nick,boolean isYourPlayer);

    void displayActions(List<MessageType> actions);

    void addConstraint(String name);

    void removeConstraint(String name);

    void onTurnDisconnection();

    void onLobbyDisconnection();

    void onPingDisconnection();

    void onEndGameDisconnection();

    void newChatMessage(String nick,String message);

    void errorMessage();
}
