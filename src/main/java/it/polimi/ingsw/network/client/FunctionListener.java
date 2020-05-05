package it.polimi.ingsw.network.client;

public interface FunctionListener {

    void updateLobbyPlayer();

    void nickUsed();

    void startGame();

    void challengerChoice(String challengerNick,boolean isYourPlayer);

    void cardChoice(String challengerNick,boolean isYourPlayer);

    void placeWorker(String challengerNick,boolean isYourPlayer);

    void updateBoard();

    void notifyWin();

    void addConstraint();

    void onLobbyDisconnection();

    void onPingDisconnection();

    void onDisconnection();

    void errorMessage();

    void startTurn();
}
