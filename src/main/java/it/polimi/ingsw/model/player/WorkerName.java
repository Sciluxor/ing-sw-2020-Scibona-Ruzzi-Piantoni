package it.polimi.ingsw.model.player;

/**
 * Enumeration class contains all possible worker's name
 * @author Luigi Scibona, Alessandro Ruzzi, Edoardo Piantoni
 * @version 1.0
 * @since 2020/06/28
 */

public enum WorkerName {
    WORKER1, WORKER2;

    /**
     * Method used to parse an input string to one of the possible enumeration's value
     * @param input String to parse
     * @return parsedWorkerName Worker name in the enumeration corresponding to the input string
     */

    public static WorkerName parseInput(String input){ return Enum.valueOf(WorkerName.class, input.toUpperCase());}

    /**
     * Method used to get the worker's number, given its name
     * @param name Name of the worker to get its number
     * @return workerNumber Integer value represents the number of the worker
     */

    public static Integer getNumberWorker(WorkerName name){
        switch (name){
            case WORKER1:
                return 1;

            case WORKER2:
                return 2;

            default:
                throw new IllegalArgumentException("wrong parameter");
        }
    }
}
