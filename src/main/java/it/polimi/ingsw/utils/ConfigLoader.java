package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStreamReader;

public class ConfigLoader {

    private static int socketPort;
    private static int lobbyTimer;
    private static int turnTimer;

    public static int getSocketPort() {
        return socketPort;
    }

    public static int getLobbyTimer() {
        return lobbyTimer;
    }

    public static int getTurnTimer() {
        return turnTimer;
    }


    private class ConfigContainer{
        private int socketPort;
        private int lobbyTimer;
        private int turnTimer;
    }

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
    }



}
