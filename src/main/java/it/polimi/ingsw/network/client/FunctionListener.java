package it.polimi.ingsw.network.client;

public interface FunctionListener {

    void updateLobbyPlayer();

    void nickUsed();

    void startGame();

    void challengerChoice();

    void cardChoice();

    void placeWorker();

    void updateBoard();

    void notifyWin();

    void addConstraint();

    void onLobbyDisconnection();

    void onPingDisconnection();

    void onDisconnection();

    void errorMessage();

    void startTurn();
}
