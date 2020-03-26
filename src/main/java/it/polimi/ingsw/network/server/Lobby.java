package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.utils.Logger;
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
    private HashMap<String,WaitLobby> linkToWaitLobby = new HashMap<>();
    private ArrayList<VirtualView> lobbyPlayer = new ArrayList<>();
    private ArrayList<WaitLobby> lobbies = new ArrayList<>();

    private boolean isFirst = true;

    public Match getMatchfromName(String playerName){

       return null;

    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean setNickName(String nickName,ClientHandler connection){
        if(nickName.length()>MAX_LENGHT_NICK || nickName.length()< MIN_LENGHT_NICK){
            return false;
        }
        else {
            for(VirtualView view: lobbyPlayer){
                if(nickName.equals(view.getPlayer().getNickname()))
                    return false;
            }
        }
        VirtualView newPlayerView = new VirtualView(connection,nickName);
        lobbyPlayer.add(newPlayerView);
        connection.setView(newPlayerView);
        return true;
    }

    public void startNewWaitLobby(ClientHandler connection){
        WaitLobby waitLobby = new WaitLobby(connection);
        lobbies.add(waitLobby);
        linkToWaitLobby.put(connection.getView().getPlayer().getNickname(),waitLobby);


    }

    public WaitLobby getWaitLobbyFromString(String nickName){
        return linkToWaitLobby.get(nickName);
    }

    public void insertPlayerInWaitLobby( ClientHandler connection) {
        for (WaitLobby wait : lobbies) {
            if (wait.getOtherPlayers().size() < wait.getNumberOfPlayer()-1) {
                Logger.info("here");
                wait.setOtherPlayers(connection);
                linkToWaitLobby.put(connection.getView().getPlayer().getNickname(), wait);
                if (isNumberSet && wait.getOtherPlayers().size() == wait.getNumberOfPlayer() - 1) {
                        handleStartMatch(wait);
                }
                handleWaitLobbySpace();
                break;
            }
        }
    }

    public void handleStartMatch(WaitLobby waitLobby){
        ArrayList<VirtualView> actualPlayers = new ArrayList<>();
        actualPlayers.add(waitLobby.getFisrtPlayer().getView());

        for (ClientHandler client: waitLobby.getOtherPlayers()){
            actualPlayers.add(client.getView());
        }

        eliminateWaitLobby(waitLobby);
        Match match = new Match(actualPlayers,waitLobby.getNumberOfPlayer());
        createMaptoMatch(match,actualPlayers);

        for(VirtualView view:actualPlayers){
            view.sendGamestartedMessage(actualPlayers.size());
        }


    }

    public void handleSettedNumber(WaitLobby waitLobby){
        if(waitLobby.getOtherPlayers().size() == waitLobby.getNumberOfPlayer() -1)
            handleStartMatch(waitLobby);

    }

    public void createMaptoMatch(Match match,ArrayList<VirtualView> actualPlayers){
        for(VirtualView view: actualPlayers){
            linkToMatch.put(view.getPlayer().getNickname(),match);
        }
    }

    public void handleFreeSpace(WaitLobby waitLobby){

        if(waitLobby.getOtherPlayers().size() < waitLobby.getNumberOfPlayer() -1)
            setFirst(false);

    }

    public void eliminateWaitLobby(WaitLobby waitLobby){
        lobbies.remove(waitLobby);
        linkToWaitLobby.remove(waitLobby.getFisrtPlayer().getView().getPlayer().getNickname());
        for(ClientHandler view: waitLobby.getOtherPlayers()) {
            linkToWaitLobby.remove(view.getView().getPlayer().getNickname());
        }

    }

    public void handleWaitLobbySpace(){
        boolean isFirst = true;
        Logger.info(Integer.toString(lobbies.size()));
        for (WaitLobby wait : lobbies) {
            Logger.info(Integer.toString(wait.getOtherPlayers().size()));
            Logger.info(Integer.toString(wait.getNumberOfPlayer()));
            if(wait.getOtherPlayers().size() < wait.getNumberOfPlayer()-1){
                isFirst = false;
                break;
            }
        }
        if(isFirst)
            Logger.info("true");
        else Logger.info("false");
        setFirst(isFirst);
    }
}
