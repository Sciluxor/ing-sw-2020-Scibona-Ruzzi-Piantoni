package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.map.Square;

import java.util.List;

public interface FunctionListener {

    void updateLobbyPlayer();

    void nickUsed();

    void startGame();

    void challengerChoice(String challengerNick,boolean isYourPlayer);

    void cardChoice(String challengerNick,boolean isYourPlayer);

    void placeWorker(String challengerNick,boolean isYourPlayer);

    void updatePlacedWorkers(List<Square> squares);

    void updateBoard();

    void notifyWin();

    void addConstraint();

    void onLobbyDisconnection();

    void onPingDisconnection();

    void onDisconnection();

    void errorMessage();

    void startTurn();
}
