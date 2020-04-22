package it.polimi.ingsw.network.message;

import it.polimi.ingsw.view.server.VirtualView;

public class GameConfigMessage extends Message {
    private int numberOfPlayer;
    private boolean isNickValid;
    private boolean isNumberPlayerValid;
    private boolean isNickUsed;
    private VirtualView view;


    public GameConfigMessage(String sender,String nickName,MessageSubType subType,int numberOfPlayer,boolean isNickValid,boolean isNickUsed,boolean isNumberPlayerValid) {
        super(sender, nickName,MessageType.CONFIG, subType);
        this.numberOfPlayer=numberOfPlayer;
        this.isNumberPlayerValid = isNumberPlayerValid;
        this.isNickUsed = isNickUsed;
        this.isNickValid = isNickValid;
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

    public boolean isNickValid() {
        return isNickValid;
    }

    public boolean isNumberPlayerValid() {
        return isNumberPlayerValid;
    }

    public boolean isNickUsed() {
        return isNickUsed;
    }
}
