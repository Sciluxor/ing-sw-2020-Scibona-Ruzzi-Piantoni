package it.polimi.ingsw.utils;

import it.polimi.ingsw.controller.GameController;

import java.util.TimerTask;

public class TurnTimerTask extends TimerTask {

    private GameController controller;

    public TurnTimerTask(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.handleTurnLobbyEnded();
    }
}
