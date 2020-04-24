package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.player.Color;

import java.util.EnumMap;
import java.util.Map;

public class ConsoleColor {
//implementare meglio con un Json

    private ConsoleColor() {
        throw new IllegalStateException("ConsoleColor class cannot be instantiated");
    }

    public static final String CLEARCONSOLE = "\033[H\033[2J";
    public static final String RESET = "\033[0m";
    public static final String PURPLE = "\033[0;35m";
    public static final String WHITE = "\033[0;37m";
    public static final String BLUE = "\033[0;34m";

    private static Map<Color,String> colorString;

    public static void loadColor(){
        colorString = new EnumMap<>(Color.class);
        colorString.put(Color.PURPLE,PURPLE);
        colorString.put(Color.WHITE,WHITE);
        colorString.put(Color.BLUE,BLUE);
    }

    public static String getColor(Color color){
        return colorString.get(color);
    }

}
