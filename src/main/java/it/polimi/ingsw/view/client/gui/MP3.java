package it.polimi.ingsw.view.client.gui;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import static it.polimi.ingsw.view.client.gui.Gui.LOGGER;

/**
 * Class that handle the reproduction of sounds
 * @author Scilux
 * @version 1.0
 * @since 2020/06/13
 */

public class MP3 {
    private String filename;
    private MediaPlayer player;
    private Thread sound;
    private final JFXPanel fxPanel = new JFXPanel();

    /**
     * Class builder
     * @param name Path to the file audio
     */

    public MP3(String name) {
        filename = name;
        filename = filename.replaceFirst("resources", "");
    }

    /**
     * Method that close the music player
     */

    private void close() {
        if (player != null) {
            player.stop();
        }
    }

    /**
     * Method that play the audio
     */

    public void play() {
        try {
            String mediaURL = getClass().getResource(filename).toExternalForm();
            Media media = new Media(mediaURL);

            player = new MediaPlayer(media);
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

    /**
     * Method that play the audio in loop mode
     */

    public void playLoop(){
        try {
            String mediaURL = MP3.class.getResource(filename).toExternalForm();
            Media media = new Media(mediaURL);

            this.player = new MediaPlayer(media);
            player.setCycleCount(MediaPlayer.INDEFINITE);
        } catch (Exception e) {
            LOGGER.severe("Problem playing file " + filename);
            LOGGER.severe(e.getMessage());
        }

        this.sound = new Thread(() -> {
            try
            {
                int x = 1;
                player.setCycleCount(MediaPlayer.INDEFINITE);
                player.play();
                while (!sound.isInterrupted()) {
                    x = 0;
                }
                player.setCycleCount(x);
                player.stop();
            }
            catch ( Exception e){LOGGER.severe("::: there was an error to play " + filename + e.getMessage());}
        });
        sound.start();
    }

    /**
     * Method that stop the audio
     */

    public void stop(){
        this.close();
        if (this.sound != null) {
            this.sound.interrupt();
        }
    }

}