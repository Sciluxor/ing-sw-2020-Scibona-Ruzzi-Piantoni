package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.map.*;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utils.ConstantsContainer;
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


class GameControllerTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    private static class StubVirtualView extends VirtualView{

        public StubVirtualView(ClientHandler connection, GameController controller) {
            super(connection, controller);
        }

        @Override
        public void update(Response status) {

        }
    }

    private static class StubConnection extends ClientHandler {
        public StubConnection(Server server, Socket socket) {
            super(server, socket);
        }

        @Override
        public void startLobbyTimer() {

        }

        @Override
        public void stopLobbyTimer() {

        }
    }

    private static class StubGameController extends GameController{

        public StubGameController(int numberOfPlayer, String gameID) {
            super(numberOfPlayer, gameID);
        }

        public Response getGameStatus(){
            return game.getGameStatus();
        }

        public void setGameStatus(Response status){ game.setGameStatus(status);}

        public int getNumClients(){
            return clients.size();
        }

        public GameMap getGameMap(){
            return game.getGameMap();
        }

        public void createQueue(){
            game.createQueue();
        }

        public void setCurrPlayer(Player player){game.setCurrentPlayer(player);
        }
        public int getConfigPlayer(){
            return game.getConfigPlayer();
        }

        public void setCard(){game.setAvailableCards(new ArrayList<>());
        }

        @Override
        public void startRoundTimer() {

        }

        @Override
        public void stopRoundTimer() {

        }

        public String getWinner(){
            return game.getWinner().getNickName();
        }
    }


    List<VirtualView> players;
    StubConnection connection1, connection2, connection3;
    VirtualView viewPlayer1, viewPlayer2, viewPlayer3, viewPlayer4;
    StubGameController controller;
    Server server;
    Map<String, Card> deck = CardLoader.loadCards();

    @BeforeEach
    void setup(){
        controller = new StubGameController(3,"GID01");
        connection1 = new StubConnection(server,new Socket());
        connection2 = new StubConnection(server,new Socket());
        connection3 = new StubConnection(server,new Socket());
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
        //
        //test to see the controller add correctly 3 players
        //

        assertEquals(3,controller.getNumberOfPlayers());
        GameConfigMessage message = new GameConfigMessage("deafult","primo", MessageSubType.ANSWER,3);
        message.setView(viewPlayer1);
        viewPlayer1.notify(message);
        assertFalse(viewPlayer1.isYourTurn());
        assertEquals(1,controller.getActualPlayers().size());
        assertEquals("GID01",controller.getGameID());
        assertEquals("primo",viewPlayer1.getConnection().getNickName());
        assertEquals("primo",controller.getActualPlayers().get(0).getNickName());
        assertFalse(controller.isGameStarted());
        message = new GameConfigMessage("deafult","primo", MessageSubType.ANSWER,3);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        assertFalse(viewPlayer2.isYourTurn());
        assertEquals(1,controller.getActualPlayers().size());
        assertThrows(IllegalStateException.class,() -> viewPlayer2.notify(null));
    }

    @Test
    void handleNewNickname() {
        //
        //test to see if the controller handle well a nick already in use
        //

        GameConfigMessage message = new GameConfigMessage("UID1","primo", MessageSubType.ANSWER,3);
        message.setView(viewPlayer1);
        controller.addUserID(viewPlayer1,"UID1");
        viewPlayer1.notify(message);
        assertFalse(controller.isGameStarted());
        message = new GameConfigMessage("UID2","primo", MessageSubType.ANSWER,3);
        message.setView(viewPlayer2);
        controller.addUserID(viewPlayer2,"UID2");
        viewPlayer2.notify(message);
        message = new GameConfigMessage("UID2","primo", MessageSubType.UPDATE,3);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        assertEquals(1,controller.getActualPlayers().size());
        assertEquals("primo",viewPlayer1.getConnection().getNickName());
        assertEquals("primo",controller.getActualPlayers().get(0).getNickName());
        assertFalse(viewPlayer2.isYourTurn());
        message = new GameConfigMessage("UID2","secondo", MessageSubType.UPDATE,3);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        assertEquals(2,controller.getActualPlayers().size());
        assertEquals("primo",controller.getActualPlayers().get(0).getNickName());
        assertEquals("secondo",controller.getActualPlayers().get(1).getNickName());
        assertFalse(viewPlayer2.isYourTurn());
    }

    @Test()
    void checkIfGameCanStart() {
        //
        //test to see if the controller start the game when the lobbyis full
        //

        GameConfigMessage message = new GameConfigMessage("deafult", "primo", MessageSubType.ANSWER, 3);
        message.setView(viewPlayer1);
        viewPlayer1.notify(message);
        message = new GameConfigMessage("deafult", "secondo", MessageSubType.ANSWER, 3);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        message = new GameConfigMessage("deafult", "terzo", MessageSubType.ANSWER, 3);
        message.setView(viewPlayer3);
        viewPlayer3.notify(message);
        assertEquals(3, controller.getActualPlayers().size());
        assertTrue(controller.isGameStarted());
        assertEquals(Response.CHALLENGERCHOICE,controller.getGameStatus());
    }

    @Test
    void isFull() {
        //
        //test to see if the controller knows correctly when the game is full
        //

        GameConfigMessage message = new GameConfigMessage("deafult","primo", MessageSubType.ANSWER,3);
        message.setView(viewPlayer1);
        viewPlayer1.notify(message);
        message = new GameConfigMessage("deafult","secondo", MessageSubType.ANSWER,3);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        message = new GameConfigMessage("deafult","secondo", MessageSubType.ANSWER,3);
        message.setView(viewPlayer3);
        viewPlayer3.notify(message);
        assertTrue(controller.isFull());
        assertEquals(2,controller.getActualPlayers().size());
    }


    @Test
    void testChallengerChoice() {
        //
        //setup phase for this test, to check if the challeger phase works correctly
        //

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

        //
        //test phase
        //

        ArrayList<String> choice = new ArrayList<>();
        choice.add("athena");
        choice.add("atlas");
        choice.add("apollo1");
        ChallengerChoiceMessage message1 = new ChallengerChoiceMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(),MessageSubType.ANSWER,"primo",choice);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message1);
        assertEquals(Response.CHALLENGERCHOICEERROR,controller.getGameStatus());
        choice.clear();
        choice.add("athena");
        choice.add("atlas");
        choice.add("apollo");
        message1 = new ChallengerChoiceMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(),MessageSubType.ANSWER,"quarto",choice);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message1);
        assertEquals(Response.CHALLENGERCHOICEERROR,controller.getGameStatus());
        choice.clear();
        choice.add("athena");
        choice.add("atlas");
        choice.add("apollo");
        message1 = new ChallengerChoiceMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(),MessageSubType.ANSWER,"secondo",choice);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message1);
        assertEquals(Response.CHALLENGERCHOICEDONE,controller.getGameStatus());
        assertEquals(3,controller.getAvailableCards().size());
        Message message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);
        assertEquals(Response.CARDCHOICE,controller.getGameStatus());
        assertEquals("secondo",controller.getCurrentPlayer().getNickName());
        assertTrue(viewPlayer2.isYourTurn());

        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        assertEquals(Response.STATUSERROR,controller.getGameStatus());
    }

    @Test
    void testChooseCard(){
        //
        //setup phase for this test, to see if the players can choose the card correctly, and if the card are assigned correctly
        //

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
        choice.add("atlas");
        choice.add("apollo");
        ChallengerChoiceMessage message1 = new ChallengerChoiceMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(),MessageSubType.ANSWER,"secondo",choice);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message1);

        Message message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        //
        //test phase
        //

        Message cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"athenas");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(cardMessage);
        assertEquals(Response.CARDCHOICEERROR,controller.getGameStatus());

        cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"atlas");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(cardMessage);
        assertEquals(Response.CARDCHOICEDONE,controller.getGameStatus());
        assertEquals("atlas",controller.getCurrentPlayer().getPower().getName());
        controller.setCard();
        controller.getActualPlayers().get(1).setPower(deck.get("hera"));
        controller.getActualPlayers().get(0).setPower(deck.get("chronus"));
        controller.getActualPlayers().get(2).setPower(deck.get("athena"));
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        assertEquals(Response.PLACEWORKERS,controller.getGameStatus());
    }

    @Test
    void testChooseCardDone(){
        //
        //setup phase for this test, to see if the players can choose the card correctly, and if the card are assigned correctly
        //

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
        choice.add("atlas");
        choice.add("apollo");
        ChallengerChoiceMessage message1 = new ChallengerChoiceMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(),MessageSubType.ANSWER,"secondo",choice);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message1);

        Message message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        //
        //test phase
        //

        Message cardMessage = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,MessageType.CHOOSECARD,MessageSubType.ANSWER,"atlas");
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(cardMessage);
        assertEquals(Response.CARDCHOICEDONE,controller.getGameStatus());
        assertEquals("atlas",controller.getCurrentPlayer().getPower().getName());
        message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        assertEquals(Response.CARDCHOICE,controller.getGameStatus());
    }

    @Test
    void testPlaceWorkers(){
        //
        //setup phase for this test to see if the workers are placed correctly in the tiles selected by the player
        //

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
        controller.setGameStatus(Response.PLACEWORKERS);
        controller.createQueue();

        //
        //test phase
        //

        Integer[] tile1 = new Integer[]{0, 0};
        Integer[] tile2 = new Integer[]{3, 5};
        PlaceWorkersMessage placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(placeMessage);
        assertFalse(controller.getCurrentPlayer().hasPlacedWorkers());
        assertEquals(Response.PLACEWORKERSERROR,controller.getGameStatus());

        tile1 = new Integer[]{0,0};
        tile2 = new Integer[]{4,4};
        placeMessage = new PlaceWorkersMessage(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID(),
                MessageSubType.ANSWER,tile1,tile2);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(placeMessage);
        assertEquals(Response.PLACEWORKERSDONE,controller.getGameStatus());
        assertEquals(1,controller.getCurrentPlayer().getWorkers().get(0).getBoardPosition().getTile());
        assertEquals(9,controller.getCurrentPlayer().getWorkers().get(1).getBoardPosition().getTile());
        assertTrue(controller.getCurrentPlayer().hasPlacedWorkers());
        controller.setCurrPlayer(controller.getActualPlayers().get(1));
        viewPlayer2.setYourTurn(true);
        Message message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);

        assertEquals(Response.PLACEWORKERS,controller.getGameStatus());

    }

    @Test
    void testStartTurn(){
        //
        //setup phase for this test to see if the turn starts correctly after all workers are placed
        //

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
        controller.setGameStatus(Response.PLACEWORKERSDONE);
        controller.createQueue();

        //
        //test phase
        //

        controller.getActualPlayers().get(0).setHasPlacedWorkers(true);
        controller.getActualPlayers().get(1).setHasPlacedWorkers(true);
        controller.getActualPlayers().get(2).setHasPlacedWorkers(true);

        controller.setCurrPlayer(controller.getActualPlayers().get(2));
        viewPlayer3.setYourTurn(true);
        Message message2 = new Message(controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).getConnection().getUserID()
                ,controller.getCurrentPlayer().getNickName(), MessageType.ENDTURN,MessageSubType.UPDATE);
        controller.getViewFromNickName(controller.getCurrentPlayer().getNickName()).notify(message2);
        assertEquals(Response.STARTTURN,controller.getGameStatus());

    }

    @Test
    void stopStartedGame() {
        //
        //test to see if the game is stopped after a disconnection or a lose
        //

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

        assertEquals(6,controller.getNumClients());

        controller.stopStartedGame("terzo",Response.GAMESTOPPED);

        assertEquals(0,controller.getNumClients());

        assertEquals(ConstantsContainer.USERDIDDEF,connection1.getUserID());
        assertEquals(ConstantsContainer.NICKDEF,connection1.getNickName());

        assertEquals(ConstantsContainer.USERDIDDEF,connection2.getUserID());
        assertEquals(ConstantsContainer.NICKDEF,connection2.getNickName());

        assertEquals(ConstantsContainer.USERDIDDEF,connection3.getUserID());
        assertEquals(ConstantsContainer.NICKDEF,connection3.getNickName());

    }

    @Test
    void disconnectTimerEnded() {
        //
        //test to see if a player is disconnected when the timer for the lobby end
        //

        GameConfigMessage message = new GameConfigMessage("UID1","primo", MessageSubType.ANSWER,3);
        message.setView(viewPlayer1);
        controller.addUserID(viewPlayer1,"UID1");
        viewPlayer1.notify(message);
        assertFalse(controller.isGameStarted());
        message = new GameConfigMessage("UID2","primo", MessageSubType.ANSWER,3);
        message.setView(viewPlayer2);
        controller.addUserID(viewPlayer2,"UID2");
        viewPlayer2.notify(message);
        assertEquals(1,controller.getConfigPlayer());
        message = new GameConfigMessage("UID2","primo", MessageSubType.UPDATE,3);
        message.setView(viewPlayer2);
        viewPlayer2.notify(message);
        assertEquals(1,controller.getConfigPlayer());
        assertEquals(3,controller.getNumClients());
        assertEquals(1,controller.getActualPlayers().size());
        controller.handleLobbyTimerEnded(new Message ("UID2","primo", MessageType.DISCONNECTION, MessageSubType.TIMEENDED));

        assertEquals(0,controller.getConfigPlayer());
        assertEquals(1,controller.getActualPlayers().size());
        assertEquals(2,controller.getNumClients());
    }

    @Test
    void disconnectBack() {
        //
        //test to see if the player is removed from the lobby when he press back
        //

        GameConfigMessage message = new GameConfigMessage("UID1", "primo", MessageSubType.ANSWER, 3);
        message.setView(viewPlayer1);
        controller.addUserID(viewPlayer1, "UID1");
        viewPlayer1.notify(message);
        assertFalse(controller.isGameStarted());
        message = new GameConfigMessage("UID2", "secondo", MessageSubType.ANSWER, 3);
        message.setView(viewPlayer2);
        controller.addUserID(viewPlayer2, "UID2");
        viewPlayer2.notify(message);

        assertEquals(0, controller.getConfigPlayer());

        assertEquals(2, controller.getActualPlayers().size());
        assertEquals(4, controller.getNumClients());

        controller.disconnectPlayerBeforeGameStart(new Message("UID2", "secondo", MessageType.DISCONNECTION, MessageSubType.BACK));

        assertEquals(0, controller.getConfigPlayer());
        assertEquals(1, controller.getActualPlayers().size());
        assertEquals(2, controller.getNumClients());
        assertEquals(Response.REMOVEDPLAYER, controller.getGameStatus());
        controller.resetPlayer(viewPlayer2);

        assertEquals(2, controller.getNumClients());
        assertEquals(ConstantsContainer.USERDIDDEF, connection2.getUserID());
        assertEquals(ConstantsContainer.NICKDEF, connection2.getNickName());
    }

    @Test
    void disconnectNickMaxTry(){
        //
        //test to see if the player is moved on another game after he puts the same nick for 3 times
        //

        GameConfigMessage message = new GameConfigMessage("UID1", "primo", MessageSubType.ANSWER, 3);
        message.setView(viewPlayer1);
        controller.addUserID(viewPlayer1, "UID1");
        viewPlayer1.notify(message);
        assertFalse(controller.isGameStarted());
        message = new GameConfigMessage("UID2", "secondo", MessageSubType.ANSWER, 3);
        message.setView(viewPlayer2);
        controller.addUserID(viewPlayer2, "UID2");
        viewPlayer2.notify(message);


        message = new GameConfigMessage("UID5","primo", MessageSubType.ANSWER,3);
        message.setView(viewPlayer3);
        controller.addUserID(viewPlayer3,"UID5");
        viewPlayer3.notify(message);

        assertEquals(1,controller.getConfigPlayer());
        assertEquals(2,controller.getActualPlayers().size());
        assertTrue(controller.isFull());
        assertEquals(5,controller.getNumClients());

        controller.disconnectPlayerBeforeGameStart(new Message("UID5","primo", MessageType.DISCONNECTION, MessageSubType.NICKMAXTRY));

        assertFalse(controller.isFull());
        assertEquals(0,controller.getConfigPlayer());
        assertEquals(4,controller.getNumClients());
    }

    @Test
    void isFreeNick() {
        //
        //test to see if the controller could check correctly the presence of a nickname
        //

        GameConfigMessage message = new GameConfigMessage("UID1","primo", MessageSubType.ANSWER,3);
        message.setView(viewPlayer1);
        controller.addUserID(viewPlayer1,"UID1");
        viewPlayer1.notify(message);

        assertFalse(controller.isFreeNick("primo"));
        assertTrue(controller.isFreeNick("secondo"));
    }

    @Test
    void stopper(){
        //
        //test to see if the game don't have a stopper at the beginning of the match
        //

        assertNull(controller.getStopper());
        assertFalse(controller.hasStopper());
    }

}