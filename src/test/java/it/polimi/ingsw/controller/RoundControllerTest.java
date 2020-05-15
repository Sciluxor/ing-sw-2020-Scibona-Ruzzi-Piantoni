package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.map.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utils.FlowStatutsLoader;
import it.polimi.ingsw.view.server.VirtualView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        public void addLevel(int tile){
            game.getGameMap().getMap().get(tile-1).addBuildingLevel();
        }

        public void setBuilding(int tile,Building building){
            game.getGameMap().getMap().get(tile-1).setBuilding(building);
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
        choice.add("chronus");
        choice.add("hera");
        ChallengerChoiceMessage message1 = new ChallengerChoiceMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(),MessageSubType.ANSWER,"secondo",choice);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message1);
        Message message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        Message cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"hera");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(cardMessage);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"chronus");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(cardMessage);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"athena");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(cardMessage);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        Integer[] tile1 = {0,0};
        Integer[] tile2 = {4,4};
        PlaceWorkersMessage placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(placeMessage);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        tile1 = new Integer[]{3, 2};
        tile2 = new Integer[]{4, 3};
        placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(placeMessage);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        tile1 = new Integer[]{2, 3};
        tile2 = new Integer[]{3, 3};
        placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(placeMessage);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        controller.changeSquare(8);

    }

    @Test
    void handleWorkerChoice() {
        Message workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worde");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);
        assertEquals(Response.STARTTURNERROR,controller.getGameStatus());
        workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker2");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);
        assertEquals(Response.STARTTURNERROR,controller.getGameStatus());
        workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);
        assertEquals(Response.TOMOVE,controller.getGameStatus());
    }

    @Test
    void permanentConstraint() {
        assertEquals(0,controller.getActualPlayers().get(1).getConstraint().size());
        assertEquals("hera",controller.getActualPlayers().get(0).getConstraint().get(0).getName());
        assertEquals("hera",controller.getActualPlayers().get(2).getConstraint().get(0).getName());
    }

    @Test
    void handleCompleteTurn() {
        Message workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);

        map.get(0).setHasPlayer(false);
        map.get(1).setMovement(controller.getCurrentPlayer(),controller.getCurrentPlayer().getWorkerFromString("worker1"));

        List<Square> modSquare = new ArrayList<>();
        modSquare.add(map.get(0));
        modSquare.add(map.get(1));

        MoveWorkerMessage messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Response.NOTWIN,null,modSquare);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.MOVED,controller.getGameStatus());
        assertEquals(2,controller.getCurrentPlayer().getWorkerFromString("worker1").getBoardPosition().getTile());



        modSquare.clear();
        map.get(2).setBuilding(Building.LVL2);
        BuildWorkerMessage messageBuild = new BuildWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Building.LVL2,Response.NOTWIN,null,modSquare);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageBuild);
        assertEquals(Response.NOTBUILD,controller.getGameStatus());

        modSquare.clear();
        map.get(2).setBuilding(Building.LVL1);
        map.get(2).addBuildingLevel();
        modSquare.add(map.get(2));
        messageBuild = new BuildWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Building.LVL1,Response.NOTWIN,null,modSquare);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageBuild);
        assertEquals(3,controller.getCurrentPlayer().getCurrentWorker().getPreviousBuildPosition().getTile());
        assertEquals(Response.ENDTURN,controller.getGameStatus());
        Message message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);



       workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);

        map.get(21).setHasPlayer(false);
        map.get(24).setMovement(controller.getCurrentPlayer(),controller.getCurrentPlayer().getWorkerFromString("worker1"));

        modSquare = new ArrayList<>();
        modSquare.add(map.get(21));
        modSquare.add(map.get(24));

        messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.NORD,Response.NOTWIN,null,modSquare);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.MOVED,controller.getGameStatus());
        assertEquals(25,controller.getCurrentPlayer().getWorkerFromString("worker1").getBoardPosition().getTile());

        modSquare.clear();
        map.get(17).setBuilding(Building.LVL1);
        map.get(17).addBuildingLevel();
        modSquare.add(map.get(17));
        messageBuild = new BuildWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.NORD,Building.LVL1,Response.NOTWIN,null,modSquare);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageBuild);
        assertEquals(Response.ENDTURN,controller.getGameStatus());
        assertEquals(18,controller.getCurrentPlayer().getCurrentWorker().getPreviousBuildPosition().getTile());
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);


        workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);

        map.get(19).setHasPlayer(false);
        map.get(17).setMovement(controller.getCurrentPlayer(),controller.getCurrentPlayer().getWorkerFromString("worker1"));

        modSquare = new ArrayList<>();
        modSquare.add(map.get(19));
        modSquare.add(map.get(17));

        messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.NORD_OVEST,Response.WIN,null,modSquare);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.MOVED,controller.getGameStatus());
        assertEquals(18,controller.getCurrentPlayer().getWorkerFromString("worker1").getBoardPosition().getTile());
        assertEquals("athena",controller.getActualPlayers().get(0).getConstraint().get(1).getName());
        assertEquals("athena",controller.getActualPlayers().get(1).getConstraint().get(0).getName());

        modSquare.clear();
        map.get(18).setBuilding(Building.LVL1);
        map.get(18).addBuildingLevel();
        modSquare.add(map.get(18));
        messageBuild = new BuildWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(),Directions.EST,Building.LVL1,Response.WIN,null,modSquare);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageBuild);
        assertEquals(Response.ENDTURN,controller.getGameStatus());
        assertEquals(19,controller.getCurrentPlayer().getCurrentWorker().getPreviousBuildPosition().getTile());
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);

        map.get(1).setHasPlayer(false);
        map.get(16).setMovement(controller.getCurrentPlayer(),controller.getCurrentPlayer().getWorkerFromString("worker1"));

        modSquare = new ArrayList<>();
        modSquare.add(map.get(19));
        modSquare.add(map.get(17));

        messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.SUD,Response.NOTWIN,null,modSquare);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.WRONGSQUAREMOVE,controller.getGameStatus());
        assertEquals(17,controller.getCurrentPlayer().getWorkerFromString("worker1").getBoardPosition().getTile());

        modSquare.clear();
        map.get(2).setBuilding(Building.LVL2);
        map.get(2).addBuildingLevel();
        assertEquals(1,controller.getCurrentPlayer().getConstraint().size());
        messageBuild = new BuildWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.NORD_EST,Building.LVL2,Response.NOTWIN,null,modSquare);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageBuild);
        assertEquals(Response.ENDTURN,controller.getGameStatus());
        assertEquals(3,controller.getCurrentPlayer().getCurrentWorker().getPreviousBuildPosition().getTile());
        assertEquals(0,controller.getCurrentPlayer().getConstraint().size());
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        controller.addLevel(25);
        controller.addLevel(25);
        controller.setBuilding(25,Building.LVL2);

        controller.addLevel(19);
        controller.addLevel(19);
        controller.setBuilding(19,Building.LVL3);

        workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);

        map.get(24).setHasPlayer(false);
        map.get(24).addBuildingLevel();
        map.get(24).addBuildingLevel();
        map.get(24).setBuilding(Building.LVL2);
        map.get(18).setMovement(controller.getCurrentPlayer(),controller.getCurrentPlayer().getWorkerFromString("worker1"));
        map.get(18).addBuildingLevel();
        map.get(18).addBuildingLevel();
        map.get(18).setBuilding(Building.LVL3);
        controller.getCurrentPlayer().removeConstraint(controller.getActualPlayers().get(2).getPower());

        modSquare = new ArrayList<>();
        modSquare.add(map.get(24));
        modSquare.add(map.get(18));

        messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.NORD_EST,Response.WIN,controller.getCurrentPlayer(),modSquare);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.WIN,controller.getGameStatus());
        assertTrue(controller.hasWinner());


    }

    @Test
    void checkMoveVictory() {
        Message workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);

        map.get(0).setHasPlayer(false);
        map.get(1).setMovement(controller.getCurrentPlayer(),controller.getCurrentPlayer().getWorkerFromString("worker1"));

        List<Square> modSquare = new ArrayList<>();
        modSquare.add(map.get(0));
        modSquare.add(map.get(1));

        MoveWorkerMessage messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Response.NOTWIN,null,modSquare);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.MOVED,controller.getGameStatus());
        assertEquals(2,controller.getCurrentPlayer().getWorkerFromString("worker1").getBoardPosition().getTile());



        modSquare.clear();
        map.get(2).setBuilding(Building.LVL2);
        BuildWorkerMessage messageBuild = new BuildWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Building.LVL2,Response.NOTWIN,null,modSquare);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageBuild);
        assertEquals(Response.NOTBUILD,controller.getGameStatus());

        modSquare.clear();
        map.get(2).setBuilding(Building.LVL1);
        map.get(2).addBuildingLevel();
        modSquare.add(map.get(2));
        messageBuild = new BuildWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Building.LVL1,Response.NOTWIN,null,modSquare);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageBuild);
        assertEquals(3,controller.getCurrentPlayer().getCurrentWorker().getPreviousBuildPosition().getTile());
        assertEquals(Response.ENDTURN,controller.getGameStatus());
        Message message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        controller.addLevel(8);
        controller.addLevel(8);
        controller.addLevel(8);
        controller.setBuilding(8,Building.LVL3);
        controller.addLevel(10);
        controller.addLevel(10);
        controller.setBuilding(10,Building.LVL2);

        modSquare.clear();
        map.get(9).setBuilding(Building.LVL2);
        map.get(9).addBuildingLevel();
        map.get(9).addBuildingLevel();
        map.get(7).setBuilding(Building.LVL3);
        map.get(7).addBuildingLevel();
        map.get(7).addBuildingLevel();
        map.get(7).addBuildingLevel();
        map.get(9).setHasPlayer(false);
        map.get(7).setMovement(controller.getCurrentPlayer(),controller.getCurrentPlayer().getWorkerFromString("worker2"));
        modSquare.add(map.get(9));
        modSquare.add(map.get(7));



        workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker2");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);

        messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.NORD_EST,Response.NOTWIN,null,modSquare);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.MOVED,controller.getGameStatus());
        assertEquals(8,controller.getCurrentPlayer().getWorkerFromString("worker2").getBoardPosition().getTile());

    }

    @Test
    void checkMoveMismatch(){
        Message workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);

        List<Square> modSquare = new ArrayList<>();
        controller.addLevel(2);
        controller.addLevel(2);
        controller.addLevel(2);
        controller.setBuilding(2,Building.LVL3);
        controller.addLevel(1);
        controller.addLevel(1);
        controller.setBuilding(1,Building.LVL2);

        map.get(0).setBuilding(Building.LVL2);
        map.get(0).addBuildingLevel();
        map.get(0).addBuildingLevel();
        map.get(1).setBuilding(Building.LVL3);
        map.get(1).addBuildingLevel();
        map.get(1).addBuildingLevel();
        map.get(1).addBuildingLevel();
        map.get(0).setHasPlayer(false);
        map.get(1).setMovement(controller.getCurrentPlayer(),controller.getCurrentPlayer().getWorkerFromString("worker1"));
        modSquare.add(map.get(0));
        modSquare.add(map.get(1));

        MoveWorkerMessage messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(),Directions.EST,Response.WIN,new Player("ciao"),modSquare);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.WIN,controller.getGameStatus());
        assertEquals(2,controller.getCurrentPlayer().getWorkerFromString("worker1").getBoardPosition().getTile());
    }


    @Test
    void checkBuildVictory() {
        Message workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);

        List<Square> modSquare;
        controller.addLevel(17);
        controller.addLevel(17);
        controller.addLevel(17);
        controller.addLevel(17);
        controller.setBuilding(17,Building.DOME);

        controller.addLevel(4);
        controller.addLevel(4);
        controller.addLevel(4);
        controller.addLevel(4);
        controller.setBuilding(4,Building.DOME);

        controller.addLevel(5);
        controller.addLevel(5);
        controller.addLevel(5);
        controller.addLevel(5);
        controller.setBuilding(5,Building.DOME);

        controller.addLevel(13);
        controller.addLevel(13);
        controller.addLevel(13);
        controller.addLevel(13);
        controller.setBuilding(13,Building.DOME);

        controller.addLevel(14);
        controller.addLevel(14);
        controller.addLevel(14);
        controller.addLevel(14);
        controller.setBuilding(14,Building.DOME);


        map.get(0).setHasPlayer(false);
        map.get(1).setMovement(controller.getCurrentPlayer(),controller.getCurrentPlayer().getWorkerFromString("worker1"));

        modSquare = new ArrayList<>();
        modSquare.add(map.get(0));
        modSquare.add(map.get(1));

        MoveWorkerMessage messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Response.NOTWIN,null,modSquare);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.MOVED,controller.getGameStatus());
        assertEquals(2,controller.getCurrentPlayer().getWorkerFromString("worker1").getBoardPosition().getTile());

        modSquare.clear();
        map.get(2).setBuilding(Building.LVL1);
        map.get(2).addBuildingLevel();
        modSquare.add(map.get(2));
        BuildWorkerMessage messageBuild = new BuildWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Building.LVL1,Response.BUILDWIN,controller.getActualPlayers().get(1),modSquare);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageBuild);
        assertEquals(3,controller.getCurrentPlayer().getCurrentWorker().getPreviousBuildPosition().getTile());

        assertEquals(Response.WIN,controller.getGameStatus());

    }

    @Test
    void flowError(){
        MoveWorkerMessage messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Response.NOTWIN,null,null);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.STATUSERROR,controller.getGameStatus());
    }

    }

