package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.map.*;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utils.FlowStatutsLoader;
import it.polimi.ingsw.view.server.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundControllerTest {

    private static class StubVirtualView extends VirtualView {

        public StubVirtualView(ClientHandler connection, GameController controller) {
            super(connection, controller);
        }

        @Override
        public void update(Response status) {

        }
    }

    private static class StubGameController extends GameController{

        public StubGameController(int numberOfPlayer, String gameID) {
            super(numberOfPlayer, gameID);
        }

        public void changeSquare(int tile){
            game.getGameMap().getMap().get(tile-1).setBuilding(Building.DOME);
        }

        public Response getGameStatus(){
            return game.getGameStatus();
        }

        @Override
        public void startRoundTimer() {

        }

        @Override
        public void stopRoundTimer() {

        }
    }

    List<VirtualView> players;
    ClientHandler connection1, connection2, connection3;
    VirtualView viewPlayer1, viewPlayer2, viewPlayer3, viewPlayer4;
    StubGameController controller;
    Server server;
    List<Square> map = MapLoader.loadMap();

    @BeforeEach
    void setup(){

        //setup to start the game until the very first turn.
        controller = new StubGameController(3,"GID01");
        connection1 = new ClientHandler(server,new Socket());
        connection2 = new ClientHandler(server,new Socket());
        connection3 = new ClientHandler(server,new Socket());
        viewPlayer1 = new StubVirtualView(connection1, controller);
        viewPlayer2 = new StubVirtualView(connection2, controller);
        viewPlayer3 = new StubVirtualView(connection3, controller);
        viewPlayer4 = new StubVirtualView(connection3, controller);
        players = new ArrayList<>();
        viewPlayer1.addObservers(controller);
        viewPlayer2.addObservers(controller);
        viewPlayer3.addObservers(controller);
        FlowStatutsLoader.loadFlow();
        GameConfigMessage message = new GameConfigMessage("UID0", "primo", MessageSubType.ANSWER, 3, false, false, false);
        message.setView(viewPlayer1);
        viewPlayer1.notify(message);
        controller.addUserID(viewPlayer1,"UID0");
        viewPlayer1.getConnection().setUserID("UID0");
        message = new GameConfigMessage("UID1", "secondo", MessageSubType.ANSWER, 3, false, false, false);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        controller.addUserID(viewPlayer2,"UID1");
        viewPlayer2.getConnection().setUserID("UID1");
        message = new GameConfigMessage("UID2", "terzo", MessageSubType.ANSWER, 3, false, false, false);
        message.setView(viewPlayer3);
        viewPlayer3.notify(message);
        controller.addUserID(viewPlayer3,"UID2");
        viewPlayer3.getConnection().setUserID("UID2");
        ArrayList<String> choice = new ArrayList<>();
        choice.add("athena");
        choice.add("atlas");
        choice.add("hypnus");
        ChallengerChoiceMessage message1 = new ChallengerChoiceMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(),MessageSubType.ANSWER,"secondo",choice);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message1);
        Message message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        Message cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"hypnus");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(cardMessage);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"athena");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(cardMessage);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"atlas");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(cardMessage);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        Integer[] tile1 = {0,0};
        Integer[] tile2 = {4,4};
        PlaceWorkersMessage placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(placeMessage);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        tile1 = new Integer[]{3, 2};
        tile2 = new Integer[]{4, 3};
        placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(placeMessage);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        tile1 = new Integer[]{2, 3};
        tile2 = new Integer[]{3, 3};
        placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(placeMessage);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        controller.changeSquare(8);

    }

    @Test
    void handleWorkerChoice() {
        Message workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worde");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(workMessage);
        assertEquals(Response.STARTTURNERROR,controller.getGameStatus());
        workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker2");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(workMessage);
        assertEquals(Response.STARTTURNERROR,controller.getGameStatus());
        workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(workMessage);
        assertEquals(Response.TOMOVE,controller.getGameStatus());
    }

    @Test
    void permanentConstraint() {
        assertEquals(0,controller.getActualPlayers().get(1).getConstraint().size());
        assertEquals("hypnus",controller.getActualPlayers().get(0).getConstraint().get(0).getName());
        assertEquals("hypnus",controller.getActualPlayers().get(2).getConstraint().get(0).getName());
    }

    @Test
    void handleCompleteTurn() {
        Message workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(workMessage);

        map.get(0).setHasPlayer(false);
        map.get(1).setMovement(controller.getCurrentPlayer(),controller.getCurrentPlayer().getWorkerFromString("worker1"));

        List<Square> modSquare = new ArrayList<>();
        modSquare.add(map.get(0));
        modSquare.add(map.get(1));

        MoveWorkerMessage messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickname(),MessageSubType.ANSWER, Directions.EST,Response.NOTWIN,null,modSquare);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(messageMove);
        assertEquals(Response.MOVED,controller.getGameStatus());
        assertEquals(2,controller.getCurrentPlayer().getWorkerFromString("worker1").getBoardPosition().getTile());



        
        BuildWorkerMessage messageBuild = new BuildWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickname(),MessageSubType.ANSWER, Directions.EST,Building.LVL2,Response.NOTWIN,null,modSquare);

    }

    @Test
    void handleConstraint() {
    }

    @Test
    void checkMoveVictory() {
    }

    @Test
    void handleBuilding() {
    }

    @Test
    void checkBuildVictory() {
    }

    @Test
    void removeNonPermanentConstraint() {
    }

    @Test
    void handleEndTurn() {
    }

    @Test
    void areRightSquares() {
    }

    @Test
    void checkSquare() {
    }
}