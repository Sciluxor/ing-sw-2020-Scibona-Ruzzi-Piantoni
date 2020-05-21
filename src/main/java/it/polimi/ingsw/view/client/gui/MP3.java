package it.polimi.ingsw.view.client.gui;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaView;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import static it.polimi.ingsw.view.client.gui.Gui.LOGGER;

public class MP3 {
    private String filename;
    private MediaPlayer player;
    private AudioClip clip;
    private Thread sound;
    private boolean closed = false;
    private JFXPanel fxPanel = new JFXPanel();

    public MP3(String name) {
        filename = name;
        filename = filename.replaceFirst("resources", "");
        //filename = filename.replaceFirst("resources/Music/", "");

    }

    private void close() {
        if (player != null) {
            player.stop();
            closed = true;
        }
    }

    public void play() {
        try {
            String mediaURL = getClass().getResource(filename).toExternalForm();
            Media media = new Media(mediaURL);

            /*InputStream stream = MP3.class.getResourceAsStream(filename);
            if(stream == null) {
                LOGGER.severe("Stream is null");
            }
            BufferedInputStream bis = new BufferedInputStream(stream);*/
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

    public void playLoop(){
        try {
            String mediaURL = MP3.class.getResource(filename).toExternalForm();
            Media media = new Media(mediaURL);

            /*InputStream stream = MP3.class.getResourceAsStream(filename);
            if(stream == null) {
                LOGGER.severe("Media is null");
            }
            BufferedInputStream bis = new BufferedInputStream(Objects.requireNonNull(stream));*/
            this.player = new MediaPlayer(media);
            player.setCycleCount(MediaPlayer.INDEFINITE);
        } catch (Exception e) {
            LOGGER.severe("Problem playing file " + filename);
            LOGGER.severe(e.getMessage());
        }

        this.sound = new Thread(() -> {
            try
            {
                int x = 0;
                player.setCycleCount(MediaPlayer.INDEFINITE);
                player.play();
                while (!sound.isInterrupted()) {
                    x = 1;
                }
                player.setCycleCount(0);
                player.stop();
            }
            catch ( Exception e){LOGGER.severe("::: there was an error to play " + filename + e.getMessage());}
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