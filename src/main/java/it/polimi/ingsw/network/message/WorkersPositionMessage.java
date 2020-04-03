package it.polimi.ingsw.network.message;

public class WorkersPositionMessage extends Message {

    private int worker1Tile;
    private int worker2Tile;

    public WorkersPositionMessage(String sender, String nickName, MessageType type, MessageSubType subType,int worker1Tile,int worker2Tile) {
        super(sender, nickName, MessageType.PLACEWORKERS, subType);
        this.worker1Tile = worker1Tile;
        this.worker2Tile = worker2Tile;
    }

    public int getWorker1Tile() {
        return worker1Tile;
    }

    public int getWorker2Tile() {
        return worker2Tile;
    }
}
