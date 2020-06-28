package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.MessageType;

import java.io.InputStreamReader;
import java.util.*;

/**
 * Class that load the flow of the game from a json file
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/27
 */

public class FlowStatutsLoader {

    /**
     * Private constructor, Since it's a loader class it can't be instantiated.
     */

    private FlowStatutsLoader() {
            throw new IllegalStateException("FLowStatusLoader class cannot be instantiated");
        }

    private static final Map<Response,List<MessageType>> nextMessageFromStatus = new EnumMap<>(Response.class);

    private static class FlowContainer{
            Response type;
            MessageType[] next;
        }

    /**
     * Function that load the flow of the game from a json file
     */

    public static void loadFlow(){
            Gson gsonFlow = new Gson();
            FlowContainer[] containers;

            try{
                String flowPath = PathContainer.FLOW;
                InputStreamReader flowInput = new InputStreamReader(FlowStatutsLoader.class.getResourceAsStream(flowPath));
                JsonReader flowReader = new JsonReader(flowInput);
                containers = gsonFlow.fromJson(flowReader,FlowContainer[].class);

            }catch (Exception e){

                throw new IllegalStateException("impossible to charge Flow");

            }


            for(FlowContainer container: containers){
                List<MessageType> arrayList = createArrayListFromArray(container.next);
                nextMessageFromStatus.put(container.type,arrayList);

            }

        }

    /**
     * Function that create a List of MessageType from an array
     * @param types Array to convert
     * @return The new List of MessageType
     */

    private static List<MessageType> createArrayListFromArray(MessageType[] types) {

            return new ArrayList<>(Arrays.asList(types));
        }

    /**
     * Function that take the next Actions available(depends on game status)
     * @param status The game status
     * @return The available actions
     */

    public static List<MessageType> getNextMessageFromStatus(Response status){
            return new ArrayList<>(nextMessageFromStatus.get(status));
        }

    /**
     * Function that check if the Message received from the client is correct for the flow of the game
     * @param status Game status
     * @param type Type of the message received from the client
     * @return True if the message is correct,false otherwise
     */

    public static boolean isRightMessage(Response status, MessageType type){
            List<MessageType> possibleType = getNextMessageFromStatus(status);
            for(MessageType messageType: possibleType){
                if(type.equals(messageType)){
                    return true;
                }
            }
            return false;
        }

}
