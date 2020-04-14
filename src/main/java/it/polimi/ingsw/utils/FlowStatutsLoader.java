package it.polimi.ingsw.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.MessageType;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FlowStatutsLoader {

        private FlowStatutsLoader() {
            throw new IllegalStateException("FLowStatusLoader class cannot be instantiated");
        }

        private static HashMap<Response,ArrayList<MessageType>> nextMessageFromStatus = new HashMap<>();

        private static class FlowContainer{
            Response type;
            MessageType[] next;
        }

        public static void loadFlow(){
            Gson gsonFlow = new Gson();
            FlowContainer[] containers;

            try{
                String flowPath = "/PossibleMoves.json";
                InputStreamReader flowInput = new InputStreamReader(FlowStatutsLoader.class.getResourceAsStream(flowPath));
                JsonReader flowReader = new JsonReader(flowInput);
                containers = gsonFlow.fromJson(flowReader,FlowContainer[].class);

            }catch (Throwable e){

                throw new IllegalStateException("impossible to charge Flow");

            }


            for(FlowContainer container: containers){
                ArrayList<MessageType> arrayList = createArrayListFromArray(container.next);
                nextMessageFromStatus.put(container.type,arrayList);

            }

        }

        private static ArrayList<MessageType> createArrayListFromArray(MessageType[] types) {

            return new ArrayList<>(Arrays.asList(types));
        }

        private static ArrayList<MessageType> getNextMessageFromStatus(Response status){
            return nextMessageFromStatus.get(status);
        }

        public static boolean isRightMessage(Response status, MessageType type){
            ArrayList<MessageType> possibleType = getNextMessageFromStatus(status);
            for(MessageType messageType: possibleType){
                if(type.equals(messageType)){
                    return true;
                }
            }
            return false;
        }

}
