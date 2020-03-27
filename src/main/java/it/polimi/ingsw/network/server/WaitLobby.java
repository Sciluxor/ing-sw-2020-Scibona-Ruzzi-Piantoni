package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.Client;

import java.util.ArrayList;

public class WaitLobby {

    private int numberOfPlayers;
    private ArrayList<ClientHandler> matchPlayers = new ArrayList<>();

    public WaitLobby(ClientHandler firstPlayer,int numberOfPlayers) {
        this.matchPlayers.add(firstPlayer);
        this.numberOfPlayers = numberOfPlayers;

    }

    public ArrayList<ClientHandler> getMatchPlayers() {
        return matchPlayers;
    }

    public void addMatchPlayer(ClientHandler matchPlayer) {
        this.matchPlayers.add(matchPlayer);
    }

    public void removePlayer(ClientHandler toRemovePlayer){
        this.matchPlayers.remove(toRemovePlayer);

    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayer(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }


}
