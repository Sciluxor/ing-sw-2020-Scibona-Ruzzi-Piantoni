package it.polimi.ingsw.model.Player;

public enum WorkerName {
    WORKER1, WORKER2;

    public static WorkerName parseInput(String input){ return Enum.valueOf(WorkerName.class, input.toUpperCase());}
}
