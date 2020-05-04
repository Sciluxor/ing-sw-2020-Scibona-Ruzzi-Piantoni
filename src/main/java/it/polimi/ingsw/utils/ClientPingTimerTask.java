package it.polimi.ingsw.utils;

import it.polimi.ingsw.network.client.ClientGameController;

import java.util.TimerTask;

public class ClientPingTimerTask extends TimerTask {

    private ClientGameController controller;

    public ClientPingTimerTask(ClientGameController controller){
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.handleDisconnection();
    }
}
