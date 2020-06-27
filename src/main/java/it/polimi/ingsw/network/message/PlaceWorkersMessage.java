package it.polimi.ingsw.network.message;

/**
 * Class that extends Message and Represent the Message with the coordinates in which the Client wants to insert his workers
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/20
 */

public class PlaceWorkersMessage extends Message {

    private Integer[] tile1;
    private Integer[] tile2;

    /**
     * Public constructor of the message
     * @param sender The Client that send the Message
     * @param subType The Subtype of the Message
     * @param tile1 The first tile in which to insert the first worker
     * @param tile2 The second tile in which to insert the second worker
     */

    public PlaceWorkersMessage(String sender,MessageSubType subType,Integer[] tile1,Integer[] tile2) {
        super(sender, MessageType.PLACEWORKERS, subType);
        this.tile1 = tile1;
        this.tile2 = tile2;
    }

    /**
     * Get the first tile
     * @return The coordinates of the first tile
     */

    public Integer[] getTile1() {
        return tile1;
    }

    /**
     * Get the second tile
     * @return The coordinates of the second tile
     */

    public Integer[] getTile2() {
        return tile2;
    }
}
