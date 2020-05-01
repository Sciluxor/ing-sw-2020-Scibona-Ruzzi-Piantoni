package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.GameConfigMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;
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


    List<VirtualView> players;
    ClientHandler connection1, connection2, connection3;
    VirtualView viewPlayer1, viewPlayer2, viewPlayer3, viewPlayer4;
    GameController controller;
    Server server;

    @BeforeEach
    void setup(){
        controller = new GameController(3,"GID01");
        connection1 = new ClientHandler(server,new Socket());
        connection2 = new ClientHandler(server,new Socket());
        connection3 = new ClientHandler(server,new Socket());
        viewPlayer1 = new StubVirtualView(connection1, controller);
        viewPlayer2 = new StubVirtualView(connection2, controller);
        viewPlayer3 = new StubVirtualView(connection3, controller);
        viewPlayer4 = new StubVirtualView(connection3, controller);
        players = new ArrayList<>();
    }

    @Test
    void handleNewPlayer() {
        viewPlayer1.addObservers(controller);
        viewPlayer2.addObservers(controller);
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
    }

    @Test
    void handleNewNickname() {
        viewPlayer1.addObservers(controller);
        viewPlayer2.addObservers(controller);
        GameConfigMessage message = new GameConfigMessage("UID1","primo", MessageSubType.ANSWER,3,false,false,false);
        message.setView(viewPlayer1);
        controller.addUserID(viewPlayer1,"UID1");
        viewPlayer1.notify(message);
        assertFalse(controller.isGameStarted());
        message = new GameConfigMessage("UID2","primo", MessageSubType.ANSWER,3,false,false,false);
        message.setView(viewPlayer2);
        controller.addUserID(viewPlayer2,"UID2");
        viewPlayer2.notify(message);
        message = new GameConfigMessage("UID2","primo", MessageSubType.ANSWER,3,false,false,false);
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
    }

    @Test
    void getActualPlayers() {
    }

    @Test
    void getCurrentPlayer() {
    }

    @Test
    void getNewPlayer() {
    }

    @Test
    void isGameStarted() {
    }

    @Test
    void initializeGame() {
    }

    @Test
    void addUserID() {
    }

    @Test
    void getViewFromUserID() {
    }

    @Test
    void getViewFromNickName() {
    }

    @Test
    void isFull() {
    }

    @Test
    void getGameID() {
    }

    @Test
    void getNumberOfPlayers() {
    }

    @Test
    void stopStartedGame() {
    }

    @Test
    void resetPlayer() {
    }

    @Test
    void getUserIDFromPlayer() {
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

    @Test
    void handleMatchBeginning() {
    }

    @Test
    void changeTurnPlayer() {
    }

    @Test
    void handleTurnBeginning() {
    }

    @Test
    void handleEndTun() {
    }

    @Test
    void sendToRoundController() {
    }

    @Test
    void startRoundTimer() {
    }

    @Test
    void stopRoundTimer() {
    }

    @Test
    void processMessage() {
    }

    @Test
    void update() {
    }
}