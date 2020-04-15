package it.polimi.ingsw.network.client;

public interface MessageListener {

    //commentare scrivendo cosa fanno le funzioni

void connect();

void startGUI();

void startCLI();

void updateView();

void processMessage();

void handleErrorMessage(); //vedere se eliminare qualche funzione dall'interfaccia

void handleStartTurn();

void handleWin();

void handleChallengerChoice();

void handleCardChoice();

void handleLose();

void handlePlayerEliminated();

void lostConnection();

}
