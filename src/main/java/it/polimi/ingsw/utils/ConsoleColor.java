package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Player.Color;
import it.polimi.ingsw.network.message.WaitPlayerMessage;

import java.util.HashMap;

public class ConsoleColor {
//implementare meglio con un Json

    public final static String CLEARCONSOLE = "\033[H\033[2J";
    public final static String RESET = "\033[0m";
    public final static String PURPLE = "\033[0;35m";
    public final static String WHITE = "\033[0;37m";
    public final static String BLUE = "\033[0;34m";

    private static HashMap<Color,String> colorString;

    public static void loadColor(){
        colorString = new HashMap<>();
        colorString.put(Color.PURPLE,PURPLE);
        colorString.put(Color.WHITE,WHITE);
        colorString.put(Color.BLUE,BLUE);
    }

    public static String getColor(Color color){
        return colorString.get(color);
    }

}
