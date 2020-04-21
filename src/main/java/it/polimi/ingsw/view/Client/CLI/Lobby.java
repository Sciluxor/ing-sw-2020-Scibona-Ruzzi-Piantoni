package it.polimi.ingsw.view.Client.CLI;

import java.util.ArrayList;
import java.util.Random;

public class Lobby {

    private ArrayList<Color> availableColors = new ArrayList<Color>();

    public Lobby() {
        availableColors.add(Color.ANSI_PURPLE);
        availableColors.add(Color.ANSI_CYAN);
        availableColors.add(Color.ANSI_WHITE);
    }

    public ArrayList<Color> availableColors(Color clientColor) {
        availableColors.remove(clientColor);
        return availableColors;
    }

    public void waitingLobby() {

    }

    public Color setColor() {
        Color color = availableColors.get((int) Math.random() * availableColors.size());
        this.availableColors(color);
        return color;
    }
}
