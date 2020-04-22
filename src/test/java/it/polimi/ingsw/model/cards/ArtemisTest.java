package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtemisTest {

    Player player1, player2;
    Card cardArte;
    GameMap gameMap;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardArte = CardLoader.loadCards().get("Artemis");
        player1.setPower(cardArte);
        gameMap = new GameMap();
        gameMap.getGameMap().get(22).setMovement(player1,player1.getWorkers().get(0));
        player1.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(22));
        gameMap.getGameMap().get(4).setMovement(player1,player1.getWorkers().get(1));
        player1.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(4));
        gameMap.getGameMap().get(21).setMovement(player2,player2.getWorkers().get(0));
        player2.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(21));
        gameMap.getGameMap().get(18).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(18));
        player1.selectCurrentWorker(gameMap, "worker1");
    }

    @Test
    void findWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardArte.findWorkerMove(null, player1.getWorkers().get(0)));
        assertThrows(NullPointerException.class , () -> cardArte.findWorkerMove(gameMap, null));

        assertEquals(player1.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(22));
        assertEquals(cardArte.findWorkerMove(gameMap, player1.getCurrentWorker()).size(), 7);
        assertEquals(cardArte.executeWorkerMove(gameMap, Directions.OVEST, player1), Response.NEWMOVE);
        assertEquals(cardArte.findWorkerMove(gameMap, player1.getCurrentWorker()).size(), 4);
        assertEquals(player1.getCurrentWorker().getBoardPosition(), gameMap.getGameMap().get(13));


    }

    @Test
    void executeWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardArte.executeWorkerMove(null, Directions.OVEST, player1));
        assertThrows(NullPointerException.class , () -> cardArte.executeWorkerMove(gameMap, null, player1));
        assertThrows(NullPointerException.class , () -> cardArte.executeWorkerMove(gameMap, Directions.OVEST, null));

        assertEquals(cardArte.executeWorkerMove(gameMap, Directions.OVEST, player1), Response.NEWMOVE);
        assertEquals(cardArte.executeWorkerMove(gameMap, Directions.NORD, player1), Response.MOVED);
    }
}