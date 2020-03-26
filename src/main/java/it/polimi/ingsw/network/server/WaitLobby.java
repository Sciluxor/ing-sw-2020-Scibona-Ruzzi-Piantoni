package it.polimi.ingsw.network.server;

import java.util.ArrayList;

public class WaitLobby {

    private ClientHandler fisrtPlayer;
    private boolean isNumberset;
    private int numberOfPlayer;
    private ArrayList<ClientHandler> otherPlayers = new ArrayList<>();

    public WaitLobby(ClientHandler fisrtPlayer) {
        this.fisrtPlayer = fisrtPlayer;
        isNumberset = false;
        numberOfPlayer = 2;

    }

    public ClientHandler getFisrtPlayer() {
        return fisrtPlayer;
    }

    public void setFisrtPlayer(ClientHandler fisrtPlayer) {
        this.fisrtPlayer = fisrtPlayer;
    }

    public boolean isNumberset() {
        return isNumberset;
    }

    public void setNumberset(boolean numberset) {
        isNumberset = numberset;
    }

    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }

    public void setNumberOfPlayer(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
    }

    public ArrayList<ClientHandler> getOtherPlayers() {
        return otherPlayers;
    }

    public void setOtherPlayers(ClientHandler otherPlayers) {
        this.otherPlayers.add(otherPlayers);
    }
}
