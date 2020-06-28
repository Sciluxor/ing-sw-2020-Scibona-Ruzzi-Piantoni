package it.polimi.ingsw.model.map;

import java.io.Serializable;

/**
 * Enumeration used to represent the building type
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/28
 */

public enum Building implements Serializable {
    GROUND, LVL1, LVL2, LVL3, DOME;

    /**
     * Method used to parse an input string to one of the possible enumeration's value
     * @param input The string to parse
     * @return The value of the enumeration corresponding to the string
     */

    public static Building parseInput(String input){ return Enum.valueOf(Building.class, input.toUpperCase());}

    /**
     * Function that calculate the building to place above another building
     * @param building The building to build on
     * @return The Building to build
     */

    public static Building mapNext(Building building){

        switch (building){
            case GROUND:
                return Building.LVL1;

            case LVL1:
                return Building.LVL2;

            case LVL2:
                return Building.LVL3;

            case LVL3:
                return Building.DOME;

            default:
                throw new IllegalArgumentException("wrong parameter");
        }



    }

}
