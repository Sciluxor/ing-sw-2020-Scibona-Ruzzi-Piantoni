package it.polimi.ingsw.view.client.cli;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lobby {

    private List<Color> availableColors = new ArrayList<>();
    private Random rand;

    public Lobby() {
        availableColors.add(Color.ANSI_PURPLE);
        availableColors.add(Color.ANSI_CYAN);
        availableColors.add(Color.ANSI_WHITE);
    }

    public List<Color> availableColors(Color clientColor) {
        availableColors.remove(clientColor);
        return availableColors;
    }

    public void waitingLobby() {

    }

    public Color setColor() {
        try {
            rand = SecureRandom.getInstanceStrong();
        }
        catch (NoSuchAlgorithmException nsa){
            //logger
        }
        Color color = availableColors.get(rand.nextInt(availableColors.size()));
        this.availableColors(color);
        return color;
    }
}
