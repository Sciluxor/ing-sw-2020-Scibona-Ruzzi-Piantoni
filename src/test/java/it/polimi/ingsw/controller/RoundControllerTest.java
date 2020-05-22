package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.map.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TurnStatus;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utils.FlowStatutsLoader;
import it.polimi.ingsw.view.server.VirtualView;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RoundControllerTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

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

        public void setGameStatus(Response status){ game.setGameStatus(status);}

        public void setCurrPlayer(Player player){game.setCurrentPlayer(player);
        }

        public void placeWork(Integer[] tile1,Integer[] tile2){
            game.placeWorkersOnMap(tile1,tile2);
        }

        public void assignPermCon(){game.assignPermanentConstraint();}

        public boolean hasPlayer(int tile){
            return game.getGameMap().getMap().get(tile-1).hasPlayer();
        }

        public int getNumClients(){
            return clients.size();
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
    Map<String,Card> deck = CardLoader.loadCards();

    @BeforeEach
    void setup(){
        //
        //setup to start the game until the very first turn.
        //

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
        GameConfigMessage message = new GameConfigMessage("UID0", "primo", MessageSubType.ANSWER, 3);
        message.setView(viewPlayer1);
        viewPlayer1.notify(message);
        controller.addUserID(viewPlayer1,"UID0");
        viewPlayer1.getConnection().setUserID("UID0");
        message = new GameConfigMessage("UID1", "secondo", MessageSubType.ANSWER, 3);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        controller.addUserID(viewPlayer2,"UID1");
        viewPlayer2.getConnection().setUserID("UID1");
        message = new GameConfigMessage("UID2", "terzo", MessageSubType.ANSWER, 3);
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

        controller.getActualPlayers().get(1).setPower(deck.get("hera"));
        controller.getActualPlayers().get(0).setPower(deck.get("chronus"));
        controller.getActualPlayers().get(2).setPower(deck.get("athena"));
        controller.assignPermCon();

        Integer[] tile1 = {0,0};
        Integer[] tile2 = {4,4};
        controller.setCurrPlayer(controller.getActualPlayers().get(1));
        controller.placeWork(tile1,tile2);

        tile1 = new Integer[]{3, 2};
        tile2 = new Integer[]{4, 3};
        controller.setCurrPlayer(controller.getActualPlayers().get(0));
        controller.placeWork(tile1,tile2);

        tile1 = new Integer[]{2, 3};
        tile2 = new Integer[]{3, 3};
        controller.setCurrPlayer(controller.getActualPlayers().get(2));
        controller.placeWork(tile1,tile2);

        controller.changeSquare(8);
        controller.setGameStatus(Response.STARTTURN);
        controller.setCurrPlayer(controller.getActualPlayers().get(1));
        controller.getActualPlayers().get(1).setTurnStatus(TurnStatus.PLAYTURN);
        controller.getActualPlayers().get(0).setTurnStatus(TurnStatus.IDLE);
        controller.getActualPlayers().get(2).setTurnStatus(TurnStatus.IDLE);

    }

    @Test
    void handleWorkerChoice() {
        //
        //test to see the controller add correctly 3 players
        //

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
        //
        //test to see the permanent constraint are assigned to the right players
        //

        assertEquals(0,controller.getActualPlayers().get(1).getConstraint().size());
        assertEquals("hera",controller.getActualPlayers().get(0).getConstraint().get(0).getName());
        assertEquals("hera",controller.getActualPlayers().get(2).getConstraint().get(0).getName());
    }

    @Test
    void handleMove() {
        //
        //test to see if the move works correctly and if are modified the right squares
        //

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
        assertEquals(2,controller.getModifiedSquares().size());
        assertEquals(1,controller.getModifiedSquares().get(0).getTile());
        assertEquals(2,controller.getModifiedSquares().get(1).getTile());
    }

    @Test
    void handleConstraint(){
        //
        //test to see if non Permanent constraint(athena) are assigned and removed at the end of the turn correctly
        //

        controller.setGameStatus(Response.ENDTURN);
        Message message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);
        controller.setGameStatus(Response.ENDTURN);
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        assertEquals("athena",controller.getCurrentPlayer().getPower().getName());

        Message workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);

        controller.setBuilding(18,Building.LVL1);
        controller.addLevel(18);
        controller.setBuilding(20,Building.GROUND);
        map.get(19).setBuilding(Building.GROUND);
        map.get(19).setHasPlayer(false);
        map.get(17).setBuilding(Building.LVL1);
        map.get(17).addBuildingLevel();
        map.get(17).setMovement(controller.getCurrentPlayer(),controller.getCurrentPlayer().getWorkerFromString("worker1"));

        List<Square> modSquare = new ArrayList<>();
        modSquare.add(map.get(19));
        modSquare.add(map.get(17));

        MoveWorkerMessage messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.NORD_OVEST,Response.NOTWIN,null,modSquare);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.MOVED,controller.getGameStatus());
        assertEquals(18,controller.getCurrentPlayer().getWorkerFromString("worker1").getBoardPosition().getTile());
        assertEquals("athena",controller.getActualPlayers().get(0).getConstraint().get(1).getName());
        assertEquals("athena",controller.getActualPlayers().get(1).getConstraint().get(0).getName());

        controller.setGameStatus(Response.ENDTURN);

        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);

        controller.setGameStatus(Response.MOVED);

        modSquare.clear();
        map.get(1).setBuilding(Building.LVL1);
        map.get(1).addBuildingLevel();
        assertEquals(1,controller.getCurrentPlayer().getConstraint().size());
        BuildWorkerMessage messageBuild = new BuildWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Building.LVL1,Response.NOTWIN,null,modSquare);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageBuild);
        assertEquals(Response.ENDTURN,controller.getGameStatus());
        assertEquals(2,controller.getCurrentPlayer().getCurrentWorker().getPreviousBuildPosition().getTile());
        assertEquals(0,controller.getCurrentPlayer().getConstraint().size());


    }

    @Test
    void handleWrongSquare(){
        //
        //test to see if the controller recognise when the client send wrong squares
        //

        Message workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);

        map.get(0).setHasPlayer(false);
        map.get(1).setMovement(controller.getCurrentPlayer(),controller.getCurrentPlayer().getWorkerFromString("worker1"));


        List<Square> modSquare = new ArrayList<>();
        modSquare.add(map.get(1));
        modSquare.add(map.get(16));

        MoveWorkerMessage messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Response.NOTWIN,null,modSquare);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.WRONGSQUAREMOVE,controller.getGameStatus());
        assertEquals(2,controller.getCurrentPlayer().getWorkerFromString("worker1").getBoardPosition().getTile());
    }

    @Test
    void handleBuild(){
        //
        //test to see if the build works correctly
        //

        Message workMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageType.WORKERCHOICE, MessageSubType.ANSWER,"worker1");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(workMessage);
        controller.setGameStatus(Response.MOVED);


        List<Square> modSquare = new ArrayList<>();
        map.get(2).setBuilding(Building.LVL2);
        BuildWorkerMessage messageBuild = new BuildWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Building.LVL2,Response.NOTWIN,null,modSquare);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageBuild);
        assertEquals(Response.NOTBUILD,controller.getGameStatus());

        modSquare.clear();
        map.get(1).setBuilding(Building.LVL1);
        map.get(1).addBuildingLevel();
        modSquare.add(map.get(1));
        messageBuild = new BuildWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Building.LVL1,Response.NOTWIN,null,modSquare);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageBuild);
        assertEquals(2,controller.getCurrentPlayer().getCurrentWorker().getPreviousBuildPosition().getTile());
        assertEquals(Response.ENDTURN,controller.getGameStatus());
    }



    @Test
    void checkMoveVictory() {
        //
        //test to see if the win is notified in the correct moment
        //

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
        //
        //test to see if the controller notify when there is an error in what the client has done
        //

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
        //
        //test to see if the build victory is notified correctly
        //

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
        //
        //test to see if the controller handle well wrong messages in wrong status of the game
        //

        MoveWorkerMessage messageMove = new MoveWorkerMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                controller.getCurrentPlayer().getNickName(), Directions.EST,Response.NOTWIN,null,null);

        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(messageMove);
        assertEquals(Response.STATUSERROR,controller.getGameStatus());
    }

    @Test
    void eliminatePlayerGameEnded() {
        //
        //test to see if the controller correctly remove a player that lose
        //

        controller.setGameStatus(Response.ENDTURN);

        controller.changeSquare(3);
        controller.changeSquare(18);
        controller.changeSquare(19);
        controller.changeSquare(6);
        controller.changeSquare(25);
        controller.changeSquare(7);
        controller.changeSquare(8);
        controller.changeSquare(11);
        controller.changeSquare(12);
        controller.changeSquare(23);
        controller.changeSquare(24);

        controller.changeSquare(22);
        controller.changeSquare(10);

        Message message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        //
        //test phase
        //

        assertEquals(Response.LOSEWIN,controller.getGameStatus());
        assertEquals("secondo",controller.getWinner());

        assertEquals(6,controller.getNumClients());
        assertEquals(1,controller.getActualPlayers().size());

        assertTrue(controller.hasPlayer(1));
        assertTrue(controller.hasPlayer(9));

        assertEquals(2,controller.getLosePlayers().size());
        assertEquals("terzo",controller.getLastLosePlayer());

        assertFalse(controller.isStillInGame("primo"));
        assertFalse(controller.isStillInGame("terzo"));
        assertTrue(controller.isStillInGame("secondo"));

        assertTrue(controller.hasWinner());

    }

    @Test
    void getUserIdFromPlayer(){
        //
        //test to the userID is returned correctly
        //

        assertEquals("UID0",controller.getUserIDFromPlayer(controller.getActualPlayers().get(0)));
    }

    }

