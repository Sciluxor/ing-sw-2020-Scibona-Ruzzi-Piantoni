package it.polimi.ingsw.model.Player;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerQueueTest {

    ArrayList<Player> players;
    Player player1, player2, player3;

    @Test
    void changeTurn() {
        players.add(player1);
        players.add(player2);
        players.add(player3);
        assertEquals(players.size(), 3);
        assertEquals(players.get(0), player1);
        assertEquals(players.get(1), player2);
        assertEquals(players.get(2), player3);
        changeTurn();
    }
}