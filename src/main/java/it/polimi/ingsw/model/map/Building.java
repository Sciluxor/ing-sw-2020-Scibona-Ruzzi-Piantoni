package it.polimi.ingsw.model.map;

import java.io.Serializable;

/**
 *
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/28
 */

public enum Building implements Serializable {
    GROUND, LVL1, LVL2, LVL3, DOME;

    /**
     *
     * @param input
     * @return
     */

    public static Building parseInput(String input){ return Enum.valueOf(Building.class, input.toUpperCase());}

    /**
     *
     * @param building
     * @return
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
