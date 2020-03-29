package it.polimi.ingsw.utils;

public class ConstantsContainer {

    private ConstantsContainer() {
        throw new IllegalStateException("ConstantsContainer cannot be instantiated");
    }

    //Message
    public static final String SERVERNAME = "God";

    //Server
    public static final int MAXWAITTIME = 10000;
    public static final int MINPLAYERLOBBY = 2;
    public static final int MAXPLAYERLOBBY = 3;

    //Lobby
    public static final int MAX_LENGHT_NICK = 20;
    public static final int MIN_LENGHT_NICK = 4;

    //Map
    public static final Integer PERIMETERPOSITION = 16;

}
