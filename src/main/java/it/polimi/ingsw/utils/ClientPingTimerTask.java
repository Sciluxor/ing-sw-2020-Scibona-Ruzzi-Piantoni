package it.polimi.ingsw.utils;

import it.polimi.ingsw.network.client.ClientGameController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;

import java.util.TimerTask;

/**
 * Utility class that contains all the constant of the game
 * @author alessandroruzzi
 * @version 1.0
 * @since 2020/06/27
 */

public class ClientPingTimerTask extends TimerTask {

    private final ClientGameController controller;

    /**
     *
     * @param controller
     */

    public ClientPingTimerTask(ClientGameController controller){
        this.controller = controller;
    }

    /**
     *
     */

    @Override
    public void run() {
        controller.handleDisconnection(new Message(ConstantsContainer.USERDIDDEF,ConstantsContainer.NICKDEF, MessageType.DISCONNECTION, MessageSubType.PINGFAIL));
    }
}
