package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.view.Server.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {

    private final int MAX_LENGHT_NICK = 20;
    private final int MIN_LENGHT_NICK = 4;

    private ArrayList<Match> matches = new ArrayList<>();
    private HashMap<String,Match> linkToMatch = new HashMap<>();
    private HashMap<String,WaitLobby> linkToWaitLobby = new HashMap<>();
    private ArrayList<VirtualView> lobbyPlayer = new ArrayList<>();
    private ArrayList<WaitLobby> lobbies = new ArrayList<>();


    public Match getMatchfromName(String playerName){

       return linkToMatch.get(playerName);

    }

    public boolean setNickName(String nickName, ClientHandler connection){
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

    public void startNewWaitLobby(ClientHandler connection, int numberOfPlayers){
        WaitLobby waitLobby = new WaitLobby(connection,numberOfPlayers);
        lobbies.add(waitLobby);
        linkToWaitLobby.put(connection.getView().getPlayer().getNickname(),waitLobby);
    }

    public WaitLobby getWaitLobbyFromString(String nickName){
        return linkToWaitLobby.get(nickName);
    }

    public void insertPlayerInWaitLobby( ClientHandler connection ,int numberOfplayers) {
        for (WaitLobby wait : lobbies) {
            if (wait.getMatchPlayers().size() < wait.getNumberOfPlayers() && wait.getNumberOfPlayers() == numberOfplayers) {
                wait.addMatchPlayer(connection);
                linkToWaitLobby.put(connection.getView().getPlayer().getNickname(), wait);
                connection.sendMessage(new Message("God", MessageType.WAITPLAYER, MessageSubType.UPDATE));
                if ((wait.getMatchPlayers().size() == wait.getNumberOfPlayers())) {
                        handleStartMatch(wait);
                }
                return;
            }
        }
        startNewWaitLobby(connection,numberOfplayers);
        connection.sendMessage(new Message("God", MessageType.WAITPLAYER, MessageSubType.UPDATE));
    }

    public void handleStartMatch(WaitLobby waitLobby){
        ArrayList<VirtualView> actualPlayers = new ArrayList<>();

        for (ClientHandler client: waitLobby.getMatchPlayers()){
            actualPlayers.add(client.getView());
        }

        eliminateWaitLobby(waitLobby);
        Match match = new Match(actualPlayers,waitLobby.getNumberOfPlayers()); //dividere questa applicazione in più parti
        matches.add(match);
        createMaptoMatch(match,actualPlayers);

        for(VirtualView view:actualPlayers){
            view.setGameStarted(true);
            view.sendGamestartedMessage(actualPlayers.size());
        }

        match.startGame();

    }

    public void createMaptoMatch(Match match,ArrayList<VirtualView> actualPlayers){
        for(VirtualView view: actualPlayers){
            linkToMatch.put(view.getPlayer().getNickname(),match);
        }
    }

    public void eliminateWaitLobby(WaitLobby waitLobby){
        lobbies.remove(waitLobby);

        for(ClientHandler view: waitLobby.getMatchPlayers()) {
            linkToWaitLobby.remove(view.getView().getPlayer().getNickname());
        }
    }

    public void disconnectPlayer(ClientHandler connection,String nickName){
        WaitLobby waitLobby = getWaitLobbyFromString(nickName);
        waitLobby.removePlayer(connection);
        linkToWaitLobby.remove(nickName);
        lobbyPlayer.remove(connection.getView());

    }

    public void moveBackPlayer(ClientHandler connection,String nickName){
        WaitLobby waitLobby = getWaitLobbyFromString(nickName);
        waitLobby.removePlayer(connection);
        linkToWaitLobby.remove(nickName);

    }

   // cambiare il task e controllare che venga eliminato il player,vedere come gestire le riconnesioni;
    // mettere un comando per fare riconnettere il player se perde la connesione o inattvità;

}
