package it.polimi.ingsw.network.message;

import it.polimi.ingsw.view.server.VirtualView;

public class GameConfigMessage extends Message {
    private int numberOfPlayer;
    private transient VirtualView view;


    public GameConfigMessage(String sender,String nickName,MessageSubType subType,int numberOfPlayer) {
        super(sender, nickName,MessageType.CONFIG, subType);
        this.numberOfPlayer=numberOfPlayer;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public VirtualView getView() {
        return view;
    }

    public void setView(VirtualView view) {
        this.view = view;
    }

}
