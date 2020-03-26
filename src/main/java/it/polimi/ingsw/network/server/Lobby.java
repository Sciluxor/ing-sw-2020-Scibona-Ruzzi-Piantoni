package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.Server.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {

    private final int MAX_LENGHT_NICK = 20;
    private final int MIN_LENGHT_NICK = 4;

    private int NumberOfPlayer = 2;
    private boolean isNumberSet = false;

    private ArrayList<Match> matches = new ArrayList<>();
    private HashMap<String,Match> linkToMatch = new HashMap<>();
    private ArrayList<VirtualView> waitingPlayer = new ArrayList<>();

    private boolean isFirst = true;

    public Match getMatchfromName(String playerName){

       return null;

    }

    public boolean setNickName(String nickName,ClientHandler connection){
        if(nickName.length()>MAX_LENGHT_NICK || nickName.length()< MIN_LENGHT_NICK){
            return false;
        }
        else {
            for(VirtualView view: waitingPlayer){
                if(nickName.equals(view.getPlayer().getNickname()))
                    return false;

            }
        }

        waitingPlayer.add(new VirtualView(connection,nickName));
        return true;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
