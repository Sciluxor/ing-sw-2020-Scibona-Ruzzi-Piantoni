package it.polimi.ingsw.utils;

public class ConstantsContainer {

    private ConstantsContainer() {
        throw new IllegalStateException("ConstantsContainer cannot be instantiated");
    }

    //Message
    public static final String SERVERNAME = "God";

    //Server
    public static final int MAXWAITTIME = 20000;
    public static final int MINPLAYERLOBBY = 2;
    public static final int MAXPLAYERLOBBY = 3;
    public static final int MAXTRYTOCHANGENICK = 2;
    public static final String GAMEIDPREFIX = "GID";
    public static final String USERIDPREFIX = "UID";

    //Lobby
    public static final int MAX_LENGHT_NICK = 20;
    public static final int MIN_LENGHT_NICK = 4;

    //Map
    public static final Integer PERIMETERPOSITION = 16;

}
