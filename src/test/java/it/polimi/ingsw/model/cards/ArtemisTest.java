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
        cardArte = CardLoader.loadCards().get("artemis");
        player1.setPower(cardArte);
        gameMap = new GameMap();
        gameMap.getMap().get(22).setMovement(player1,player1.getWorkers().get(0));
        player1.getWorkers().get(0).setBoardPosition(gameMap.getMap().get(22));
        gameMap.getMap().get(4).setMovement(player1,player1.getWorkers().get(1));
        player1.getWorkers().get(1).setBoardPosition(gameMap.getMap().get(4));
        gameMap.getMap().get(21).setMovement(player2,player2.getWorkers().get(0));
        player2.getWorkers().get(0).setBoardPosition(gameMap.getMap().get(21));
        gameMap.getMap().get(18).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(gameMap.getMap().get(18));
        player1.selectCurrentWorker(gameMap, "worker1");
    }

    @Test
    void findWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardArte.findWorkerMove(null, player1.getWorkers().get(0)));
        assertThrows(NullPointerException.class , () -> cardArte.findWorkerMove(gameMap, null));

        assertEquals(player1.getCurrentWorker().getBoardPosition(), gameMap.getMap().get(22));
        assertEquals(7,cardArte.findWorkerMove(gameMap, player1.getCurrentWorker()).size());
        assertEquals(Response.NEWMOVE,cardArte.executeWorkerMove(gameMap, Directions.OVEST, player1));
        assertEquals(4,cardArte.findWorkerMove(gameMap, player1.getCurrentWorker()).size());
        assertEquals(player1.getCurrentWorker().getBoardPosition(), gameMap.getMap().get(13));


    }

    @Test
    void executeWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardArte.executeWorkerMove(null, Directions.OVEST, player1));
        assertThrows(NullPointerException.class , () -> cardArte.executeWorkerMove(gameMap, null, player1));
        assertThrows(NullPointerException.class , () -> cardArte.executeWorkerMove(gameMap, Directions.OVEST, null));

        assertEquals(Response.NEWMOVE,cardArte.executeWorkerMove(gameMap, Directions.OVEST, player1));
        assertEquals(Response.MOVED,cardArte.executeWorkerMove(gameMap, Directions.NORD, player1));
    }
}