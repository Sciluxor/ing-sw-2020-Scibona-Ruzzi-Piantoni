package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;

/**
 * Class that loads all the server configuration parameters
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/27
 */

public class ConfigLoader {

    /**
     * Private constructor, Since it's a loader class it can't be instantiated.
     */

    private ConfigLoader() { throw new IllegalStateException("ConfigLoader cannot be instantiated"); }

    private static int socketPort;
    private static int lobbyTimer;
    private static int turnTimer;
    private static int pingTimer;

    /**
     * Get the Port of the Server
     * @return The port of the Server
     */

    public static int getSocketPort() {
        return socketPort;
    }

    /**
     * Get lobby timer time
     * @return The duration of the lobby timer
     */

    public static int getLobbyTimer() {
        return lobbyTimer;
    }

    /**
     * Get turn timer time
     * @return The duration of the turn timer
     */

    public static int getTurnTimer() {
        return turnTimer;
    }

    /**
     * Get ping timer time
     * @return The duration of the ping timer
     */

    public static int getPingTimer() { return pingTimer; }

    private class ConfigContainer{
        private int socketPort;
        private int lobbyTimer;
        private int turnTimer;
        private int pingTimer;
    }

    /**
     * Method that load the setting for the server from a json file
     */

    public static void loadSetting(){
        Gson gsonServer = new Gson();
        ConfigLoader.ConfigContainer container;

        try{
            String configPath = PathContainer.CONFIG;
            InputStreamReader serverConfigInput = new InputStreamReader(ConfigLoader.class.getResourceAsStream(configPath));
            JsonReader serverConfigReader = new JsonReader(serverConfigInput);
            container = gsonServer.fromJson(serverConfigReader, ConfigLoader.ConfigContainer.class);

        }catch (Exception e){

            throw new IllegalStateException("impossible to charge Setting");

        }

        socketPort = container.socketPort;
        lobbyTimer = container.lobbyTimer;
        turnTimer = container.turnTimer;
        pingTimer = container.pingTimer;
    }



}
