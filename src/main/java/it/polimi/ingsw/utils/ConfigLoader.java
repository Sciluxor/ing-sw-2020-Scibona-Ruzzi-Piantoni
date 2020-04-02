package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.Cards.CardLoader;

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


    private class configContainer{
        private int socketPort;
        private int lobbyTimer;
        private int turnTimer;
    }

    public static void loadSetting(){
        Gson gsonServer = new Gson();
        ConfigLoader.configContainer container;

        try{
            String configPath = "/ServerConfig.json";
            InputStreamReader serverConfigInput = new InputStreamReader(ConfigLoader.class.getResourceAsStream(configPath));
            JsonReader serverConfigReader = new JsonReader(serverConfigInput);
            container = gsonServer.fromJson(serverConfigReader, ConfigLoader.configContainer.class);

        }catch (Throwable e){

            throw new IllegalStateException("impossible to charge Cards");

        }

        socketPort = container.socketPort;
        lobbyTimer = container.lobbyTimer;
        turnTimer = container.turnTimer;
    }



}
