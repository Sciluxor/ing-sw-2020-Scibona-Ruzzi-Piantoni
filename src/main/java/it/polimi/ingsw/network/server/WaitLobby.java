package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.Client;

import java.util.ArrayList;

public class WaitLobby {

    private int numberOfPlayers;
    private ArrayList<ClientHandler> matchPlayers = new ArrayList<>();

    public WaitLobby(ClientHandler fisrtPlayer,int numberOfPlayers) {
        this.matchPlayers.add(fisrtPlayer);
        this.numberOfPlayers = numberOfPlayers;

    }

    public ArrayList<ClientHandler> getMatchPlayers() {
        return matchPlayers;
    }

    public void setMatchPlayers(ClientHandler matchPlayer) {
        this.matchPlayers.add(matchPlayer);
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayer(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }


}
