package it.polimi.ingsw.utils;

/**
 * Utility class that contains all the constant of the game
 * @author Alessandro Ruzzi, Luigi Scibona, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/27
 */

public class ConstantsContainer {

    /**
     * Private constructor, Since it's an utility class it can't be instantiated.
     */

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
    public static final String NULLPARAMETERS = "null parameters";

    //LobbyGui
    public static final int MAX_LENGHT_NICK = 13;
    public static final int MIN_LENGHT_NICK = 4;

    //Map and Card
    public static final Integer PERIMETERPOSITION = 16;
    public static final Integer MINMAPPOSITION = 0;
    public static final Integer MAXMAPCOORD = 4;
    public static final Integer MAXMAPPOSITION = 25;
    public static final Integer MAXBUILDINGLEVEL = 4;
    public static final Integer WINNINGLEVEL =3;
    public static final Integer CHRONUSTOWERWIN = 5;
    public static final Integer PANLEVELWIN = 2;

    //Board
    public static final String LOSEBORDER = "resources/Graphics/gods/podium/lose_border.png";
    public static final String PNG = ".png";
    public static final String RESOURCES_GRAPHICS = "resources/Graphics/";
    public static final String MUSIC = "resources/Music/";
    public static final String TEXT = "resources/Graphics/Texts/";
    public static final String PODIUM = "resources/Graphics/gods/podium/";
    public static final String GODS = "resources/Graphics/gods/";
    public static final String DESCRIPTION = "_description.png";
    public static final String INSERTFAILED = "InsertString Failed";

    //Cli
    public static final String END_TURN_STRING = "endTurn";
    public static final String UP_AND_DOWN_STRING = "up&down";
    public static final String PLAYER_STRING = "PLAYER ";
    public static final String NO_KEYBOARD_CAUGHT = "NO KEYBOARD CAUGHT";
    public static final String END_TURN_CASE = "END TURN";
    public static final String SELECT_WORKER_CASE = "SELECT WORKER";
    public static final String CHAT_CASE = "CHAT";
    public static final String RAW_STRING = "raw";
    public static final String SANE_STRING = "sane";
}
