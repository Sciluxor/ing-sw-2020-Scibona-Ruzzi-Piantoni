package it.polimi.ingsw.view.client.gui;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Objects;


import static it.polimi.ingsw.view.client.gui.Gui.LOGGER;

public class MP3 {
    private String filename;
    private Player player;
    private Thread sound;
    private boolean closed = false;

    public MP3(String name) {
        filename = name;
        filename = filename.replaceFirst("resources", "");
    }

    private void close() {
        if (player != null) {
            player.close();
            closed = true;
        }
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
            BufferedInputStream bis = new BufferedInputStream(Objects.requireNonNull(stream));
            this.player = new Player(bis);
        } catch (Exception e) {
            LOGGER.severe("Problem playing file " + filename);
            LOGGER.severe(e.getMessage());
        }

        this.sound = new Thread(() -> {
            try
            {
                while (!sound.isInterrupted()) {
                    player.play();

                    if( player.isComplete() && !closed){playLoop();}
                }
                System.out.println("Interrupted");

            }
            catch ( JavaLayerException e){LOGGER.severe("::: there was an error to play " + filename + e.getMessage());}
        });
        sound.start();
    }

    public void stop(){
        this.close();
        if (this.sound != null) {
            this.sound.interrupt();
        }
    }

}