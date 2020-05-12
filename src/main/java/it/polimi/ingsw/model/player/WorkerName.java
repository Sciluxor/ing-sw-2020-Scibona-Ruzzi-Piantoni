package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.map.Building;

public enum WorkerName {
    WORKER1, WORKER2;

    public static WorkerName parseInput(String input){ return Enum.valueOf(WorkerName.class, input.toUpperCase());}


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
