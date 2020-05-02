package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Response;
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


class GameControllerTest {

    private static class StubVirtualView extends VirtualView{

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

    @BeforeEach
    void setup(){
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
    }

    @Test
    void handleNewPlayer() {
        assertEquals(3,controller.getNumberOfPlayers());
        GameConfigMessage message = new GameConfigMessage("deafult","primo", MessageSubType.ANSWER,3,false,false,false);
        message.setView(viewPlayer1);
        viewPlayer1.notify(message);
        assertFalse(viewPlayer1.isYourTurn());
        assertEquals(1,controller.getActualPlayers().size());
        assertEquals("GID01",controller.getGameID());
        assertEquals("primo",viewPlayer1.getConnection().getNickName());
        assertEquals("primo",controller.getActualPlayers().get(0).getNickname());
        assertFalse(controller.isGameStarted());
        message = new GameConfigMessage("deafult","primo", MessageSubType.ANSWER,3,false,false,false);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        assertFalse(viewPlayer2.isYourTurn());
        assertEquals(1,controller.getActualPlayers().size());
        assertThrows(IllegalStateException.class,() -> viewPlayer2.notify(null));
    }

    @Test
    void handleNewNickname() {
        GameConfigMessage message = new GameConfigMessage("UID1","primo", MessageSubType.ANSWER,3,false,false,false);
        message.setView(viewPlayer1);
        controller.addUserID(viewPlayer1,"UID1");
        viewPlayer1.notify(message);
        assertFalse(controller.isGameStarted());
        message = new GameConfigMessage("UID2","primo", MessageSubType.ANSWER,3,false,false,false);
        message.setView(viewPlayer2);
        controller.addUserID(viewPlayer2,"UID2");
        viewPlayer2.notify(message);
        message = new GameConfigMessage("UID2","primo", MessageSubType.UPDATE,3,false,false,false);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        assertEquals(1,controller.getActualPlayers().size());
        assertEquals("primo",viewPlayer1.getConnection().getNickName());
        assertEquals("primo",controller.getActualPlayers().get(0).getNickname());
        assertFalse(viewPlayer2.isYourTurn());
        message = new GameConfigMessage("UID2","secondo", MessageSubType.UPDATE,3,false,false,false);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        assertEquals(2,controller.getActualPlayers().size());
        assertEquals("primo",controller.getActualPlayers().get(0).getNickname());
        assertEquals("secondo",controller.getActualPlayers().get(1).getNickname());
        assertFalse(viewPlayer2.isYourTurn());
    }

    @Test
    void checkIfGameCanStart() {
        GameConfigMessage message = new GameConfigMessage("deafult", "primo", MessageSubType.ANSWER, 3, false, false, false);
        message.setView(viewPlayer1);
        viewPlayer1.notify(message);
        message = new GameConfigMessage("deafult", "secondo", MessageSubType.ANSWER, 3, false, false, false);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        message = new GameConfigMessage("deafult", "terzo", MessageSubType.ANSWER, 3, false, false, false);
        message.setView(viewPlayer3);
        viewPlayer3.notify(message);
        assertEquals(3, controller.getActualPlayers().size());
        assertTrue(controller.isGameStarted());
        assertEquals(Response.CHALLENGERCHOICE,controller.getGameStatus());
    }

    @Test
    void isFull() {
        GameConfigMessage message = new GameConfigMessage("deafult","primo", MessageSubType.ANSWER,3,false,false,false);
        message.setView(viewPlayer1);
        viewPlayer1.notify(message);
        message = new GameConfigMessage("deafult","secondo", MessageSubType.ANSWER,3,false,false,false);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        message = new GameConfigMessage("deafult","secondo", MessageSubType.ANSWER,3,false,false,false);
        message.setView(viewPlayer3);
        viewPlayer3.notify(message);
        assertTrue(controller.isFull());
        assertEquals(2,controller.getActualPlayers().size());
    }

    @Test
    void testFirstStepGame() {
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
        choice.add("apollo1");
        ChallengerChoiceMessage message1 = new ChallengerChoiceMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(),MessageSubType.ANSWER,"primo",choice);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message1);
        assertEquals(Response.CHALLENGERCHOICEERROR,controller.getGameStatus());
        choice.clear();
        choice.add("athena");
        choice.add("atlas");
        choice.add("apollo");
        message1 = new ChallengerChoiceMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(),MessageSubType.ANSWER,"quarto",choice);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message1);
        assertEquals(Response.CHALLENGERCHOICEERROR,controller.getGameStatus());
        choice.clear();
        choice.add("athena");
        choice.add("atlas");
        choice.add("apollo");
        message1 = new ChallengerChoiceMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(),MessageSubType.ANSWER,"secondo",choice);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message1);
        assertEquals(Response.CHALLENGERCHOICEDONE,controller.getGameStatus());
        Message message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);
        assertEquals(Response.CARDCHOICE,controller.getGameStatus());
        assertEquals("secondo",controller.getCurrentPlayer().getNickname());
        assertTrue(viewPlayer2.isYourTurn());

        Message cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"athrena");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(cardMessage);
        assertEquals(Response.CARDCHOICEERROR,controller.getGameStatus());

        cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"athena");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(cardMessage);
        assertEquals(Response.CARDCHOICEDONE,controller.getGameStatus());
        assertEquals("athena",controller.getCurrentPlayer().getPower().getName());
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"apollo");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(cardMessage);
        assertEquals(Response.CARDCHOICEDONE,controller.getGameStatus());
        assertEquals("apollo",controller.getCurrentPlayer().getPower().getName());
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"athena");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(cardMessage);
        assertEquals(Response.CARDCHOICEERROR,controller.getGameStatus());

        cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"atlas");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(cardMessage);
        assertEquals(Response.CARDCHOICEDONE,controller.getGameStatus());
        assertEquals("atlas",controller.getCurrentPlayer().getPower().getName());
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        assertEquals(Response.PLACEWORKERS,controller.getGameStatus());
        assertEquals("secondo",controller.getCurrentPlayer().getNickname());

        Integer[] tile1 = {0,0};
        Integer[] tile2 = {4,4};
        PlaceWorkersMessage placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(placeMessage);
        assertEquals(Response.PLACEWORKERSDONE,controller.getGameStatus());
        assertEquals(1,controller.getCurrentPlayer().getWorkers().get(0).getBoardPosition().getTile());
        assertEquals(9,controller.getCurrentPlayer().getWorkers().get(1).getBoardPosition().getTile());
        assertTrue(controller.getCurrentPlayer().hasPlacedWorkers());
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        tile1 = new Integer[]{0, 0};
        tile2 = new Integer[]{3, 4};
        placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(placeMessage);
        assertFalse(controller.getCurrentPlayer().hasPlacedWorkers());
        assertEquals(Response.PLACEWORKERSERROR,controller.getGameStatus());
        tile1 = new Integer[]{-1, 0};
        tile2 = new Integer[]{3, 4};
        placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(placeMessage);
        assertFalse(controller.getCurrentPlayer().hasPlacedWorkers());
        assertEquals(Response.PLACEWORKERSERROR,controller.getGameStatus());

        tile1 = new Integer[]{2, 0};
        tile2 = new Integer[]{3, 5};
        placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(placeMessage);
        assertFalse(controller.getCurrentPlayer().hasPlacedWorkers());
        assertEquals(Response.PLACEWORKERSERROR,controller.getGameStatus());

        tile1 = new Integer[]{3, 2};
        tile2 = new Integer[]{4, 3};
        placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(placeMessage);
        assertEquals(Response.PLACEWORKERSDONE,controller.getGameStatus());
        assertEquals(22,controller.getCurrentPlayer().getWorkers().get(0).getBoardPosition().getTile());
        assertEquals(10,controller.getCurrentPlayer().getWorkers().get(1).getBoardPosition().getTile());

        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        tile1 = new Integer[]{2, 0};
        tile2 = new Integer[]{-4, 2};
        placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(placeMessage);
        assertFalse(controller.getCurrentPlayer().hasPlacedWorkers());
        assertEquals(Response.PLACEWORKERSERROR,controller.getGameStatus());

        tile1 = new Integer[]{2, 5};
        tile2 = new Integer[]{4, 2};
        placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(placeMessage);
        assertFalse(controller.getCurrentPlayer().hasPlacedWorkers());
        assertEquals(Response.PLACEWORKERSERROR,controller.getGameStatus());

        tile1 = new Integer[]{1, 4};
        tile2 = new Integer[]{0, 3};
        placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(placeMessage);
        assertEquals(Response.PLACEWORKERSDONE,controller.getGameStatus());
        assertEquals(6,controller.getCurrentPlayer().getWorkers().get(0).getBoardPosition().getTile());
        assertEquals(4,controller.getCurrentPlayer().getWorkers().get(1).getBoardPosition().getTile());

        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        assertEquals(Response.STARTTURN,controller.getGameStatus());
        assertEquals("secondo",controller.getCurrentPlayer().getNickname());

        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickname(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickname()).notify(message2);

        assertEquals(Response.STATUSERROR,controller.getGameStatus());

    }

    @Test
    void resetPlayer() {
    }

    @Test
    void handleLobbyTimerEnded() {
    }

    @Test
    void handleTurnLobbyEnded() {
    }

    @Test
    void eliminatePlayer() {
    }

    @Test
    void removeViewFromGame() {
    }

    @Test
    void removePlayerFromBoard() {
    }

    @Test
    void checkIfStillCorrectGame() {
    }

    @Test
    void disconnectPlayerBeforeGameStart() {
    }

    @Test
    void isFreeNick() {
    }

}