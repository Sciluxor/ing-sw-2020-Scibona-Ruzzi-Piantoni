package it.polimi.ingsw.model.Player;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerQueueTest {

    ArrayList<Player> players;
    Player player1, player2, player3;

    @Test
    void changeTurn() {
        players = new ArrayList<>();
        player1 = new Player("GoodPlayer", TurnStatus.PREGAME);
        player2 = new Player("GoodPlayer", TurnStatus.PREGAME);
        player3 = new Player("GoodPlayer", TurnStatus.PREGAME);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        assertEquals(players.size(), 3);
        assertEquals(players.get(0), player1);
        assertEquals(players.get(1), player2);
        assertEquals(players.get(2), player3);
        PlayerQueue queue = new PlayerQueue(players);
        assertEquals(queue.size(), 3);
        assertEquals(queue.peek(), player1);
        queue.changeTurn();
        assertEquals(queue.peek(), player2);
        queue.changeTurn();
        assertEquals(queue.peek(), player3);
        queue.changeTurn();
        assertEquals(queue.peek(), player1);

    }
}