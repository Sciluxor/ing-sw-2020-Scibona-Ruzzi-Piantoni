package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZeusTest {

    Player player1, player2;
    Card cardZeus;
    GameMap gameMap;
    List<Directions> directions;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardZeus = CardLoader.loadCards().get("zeus");
        player1.setPower(cardZeus);
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
        directions = player1.findWorkerMove(gameMap, player1.getWorkers().get(0));
    }

    @Test
    void findPossibleBuild() {
        assertThrows(NullPointerException.class , () -> cardZeus.findPossibleBuild(null, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardZeus.findPossibleBuild(gameMap, null));

        assertEquals(8,cardZeus.findPossibleBuild(gameMap, player1.getCurrentWorker()).size());
        assertEquals(Directions.CENTER,cardZeus.findPossibleBuild(gameMap, player1.getCurrentWorker()).get(7));
    }
}