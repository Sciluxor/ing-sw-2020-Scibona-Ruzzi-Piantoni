package it.polimi.ingsw.view.client.gui;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;




import static it.polimi.ingsw.view.client.gui.Gui.LOGGER;

public class MP3 {
    private String filename;
    private Player player;

    public MP3(String name) {
        filename = name;
        filename = filename.replaceFirst("resources", "");
    }

    public void close() {
        if (player != null)
            player.close();
    }

    public void play() {
        try {

            InputStream stream = MP3.class.getResourceAsStream(filename);
            if(stream == null) {
                LOGGER.severe("Stream is null");
            }

            BufferedInputStream bis = new BufferedInputStream(stream);
            player = new Player(bis);
        } catch (Exception e) {
            LOGGER.severe("Problem playing file " + filename);
            LOGGER.severe(e.getMessage());
        }

        new Thread(() -> {
            try {
                player.play();
            } catch (Exception e) {
                LOGGER.severe(e.getMessage());
            }
        }).start();

    }

    public void playLoop(){
        try {
            InputStream stream = MP3.class.getResourceAsStream(filename);
            if(stream == null) {
                LOGGER.severe("Stream is null");
            }

            BufferedInputStream bis = new BufferedInputStream(stream);
            this.player = new Player(bis);
        } catch (Exception e) {
            LOGGER.severe("Problem playing file " + filename);
            LOGGER.severe(e.getMessage());
        }

        new Thread(() -> {
            try
            {
                player.play();

                if( player.isComplete())
                {
                    playLoop();
                }
            }
            catch ( JavaLayerException e)
            {
               LOGGER.severe("::: there was an error to play " + filename + e.getMessage());
            }
        }).start();
    }

}