package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.MessageType;

import java.io.InputStreamReader;
import java.util.*;

/**
 * Utility class that contains all the constant of the game
 * @author alessandroruzzi
 * @version 1.0
 * @since 2020/06/27
 */

public class FlowStatutsLoader {

    /**
     *
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
     *
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
     *
     * @param types
     * @return
     */

    private static List<MessageType> createArrayListFromArray(MessageType[] types) {

            return new ArrayList<>(Arrays.asList(types));
        }

    /**
     *
     * @param status
     * @return
     */

    public static List<MessageType> getNextMessageFromStatus(Response status){
            return new ArrayList<>(nextMessageFromStatus.get(status));
        }

    /**
     *
     * @param status
     * @param type
     * @return
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
