package it.polimi.ingsw.utils;

import it.polimi.ingsw.network.client.ClientGameController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;

import java.util.TimerTask;

public class ClientPingTimerTask extends TimerTask {

    private final ClientGameController controller;

    public ClientPingTimerTask(ClientGameController controller){
        this.controller = controller;
    }

    @Override
    public void run() {
        System.out.println("ClientPing");
        controller.handleDisconnection(new Message(ConstantsContainer.USERDIDDEF,ConstantsContainer.NICKDEF, MessageType.DISCONNECTION, MessageSubType.PINGFAIL));
    }
}
