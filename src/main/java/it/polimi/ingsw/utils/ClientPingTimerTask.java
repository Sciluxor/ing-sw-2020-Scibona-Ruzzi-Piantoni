package it.polimi.ingsw.utils;

import it.polimi.ingsw.network.client.ClientGameController;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;

import java.util.TimerTask;

/**
 * Utility class that represent the timer task to execute when the client ping timer ends
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/27
 */

public class ClientPingTimerTask extends TimerTask {

    private final ClientGameController controller;

    /**
     * Public constructor
     * @param controller Controller of the game
     */

    public ClientPingTimerTask(ClientGameController controller){
        this.controller = controller;
    }

    /**
     * Function that notify the server when the timer ends, with disconnection information
     */

    @Override
    public void run() {
        controller.handleDisconnection(new Message(ConstantsContainer.USERDIDDEF,ConstantsContainer.NICKDEF, MessageType.DISCONNECTION, MessageSubType.PINGFAIL));
    }
}
