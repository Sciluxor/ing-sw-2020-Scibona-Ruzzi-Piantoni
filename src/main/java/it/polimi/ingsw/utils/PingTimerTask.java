package it.polimi.ingsw.utils;

import it.polimi.ingsw.network.client.ClientGameController;

import java.util.TimerTask;

public class PingTimerTask extends TimerTask {

    private ClientGameController controller;

    public PingTimerTask(ClientGameController controller){
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.handleDisconnection();
    }
}
