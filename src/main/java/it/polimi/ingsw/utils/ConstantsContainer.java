package it.polimi.ingsw.utils;

public class ConstantsContainer {

    private ConstantsContainer() {
        throw new IllegalStateException("ConstantsContainer cannot be instantiated");
    }

    //Message
    public static final String SERVERNAME = "God";

    //Server
    public static final int MINPLAYERLOBBY = 2;
    public static final int MAXPLAYERLOBBY = 3;
    public static final int MAXTRYTOCHANGENICK = 2;
    public static final String GAMEIDPREFIX = "GID";
    public static final String USERIDPREFIX = "UID";
    public static final String USERDIDDEF = "default";
    public static final String NICKDEF = "def";

    //Lobby
    public static final int MAX_LENGHT_NICK = 20;
    public static final int MIN_LENGHT_NICK = 4;

    //Map and Card
    public static final Integer PERIMETERPOSITION = 16;
    public static final Integer MINMAPPOSITION = 0;
    public static final Integer MAXMAPPOSITION = 25;
    public static final Integer MAXBUILDINGLEVEL = 4;
    public static final Integer WINNINGLEVEL =3;
    public static final Integer CHRONUSTOWERWIN = 5;
    public static final Integer PANLEVELWIN = 2;

}
