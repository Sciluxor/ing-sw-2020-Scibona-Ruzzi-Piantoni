package it.polimi.ingsw.network.message;

import it.polimi.ingsw.view.server.VirtualView;

/**
 * Class that extends Message and Represent the Message with The Game Configuration Parameters
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/20
 */

public class GameConfigMessage extends Message {
    private int numberOfPlayer;
    private transient VirtualView view;

    /**
     * Public constructor of the message
     * @param sender The Client that send the Message
     * @param nickName Nickname selected by the Client
     * @param subType The Subtype of the Message
     * @param numberOfPlayer Number of player selected by the the Client
     */

    public GameConfigMessage(String sender,String nickName,MessageSubType subType,int numberOfPlayer) {
        super(sender, nickName,MessageType.CONFIG, subType);
        this.numberOfPlayer=numberOfPlayer;
    }

    /**
     * Get the number of player selected by the client
     * @return The number of player
     */

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    /**
     * Get the VirtualView of the Client,Used By The Controllers
     * @return The VirtualView of the Client
     */

    public VirtualView getView() {
        return view;
    }

    /**
     * Set the View assigned to the client by the Server
     * @param view The VirtualView of The Client for This game
     */

    public void setView(VirtualView view) {
        this.view = view;
    }

}
