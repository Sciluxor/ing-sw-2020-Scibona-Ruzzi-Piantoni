package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.WorkerName;
import it.polimi.ingsw.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    Player player, player2;
    Card cardA, cardAp, cardHy, cardHe, cardCr, cardDe;
    Worker worker1,worker2;
    GameMap gameMap;
    List<Directions> directions;


    @BeforeEach
    void setup(){
        player = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardA = CardLoader.loadCards().get("athena");
        cardAp = CardLoader.loadCards().get("apollo");
        cardHy = CardLoader.loadCards().get("hypnus");
        cardHe = CardLoader.loadCards().get("hera");
        cardCr = CardLoader.loadCards().get("chronus");
        cardDe = CardLoader.loadCards().get("demeter");
        worker1 = new Worker(WorkerName.WORKER1);
        worker2 = new Worker(WorkerName.WORKER2);
        gameMap = new GameMap();
        player.setPower(cardAp);
        player2.setPower(cardHy);
        gameMap.getGameMap().get(22).setMovement(player,player.getWorkers().get(0));
        player.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(22));
        gameMap.getGameMap().get(4).setMovement(player,player.getWorkers().get(1));
        player.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(4));
        gameMap.getGameMap().get(21).setMovement(player2,player2.getWorkers().get(0));
        player2.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(21));
        gameMap.getGameMap().get(18).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(18));
        directions = player.findWorkerMove(gameMap, player.getWorkers().get(0));
    }

    @Test
    void getName() {
        assertEquals ("athena",cardA.getName());
        assertEquals ("hypnus",cardHy.getName());
        assertEquals ("hera",cardHe.getName());
        assertEquals ("chronus",cardCr.getName());
        assertEquals ("demeter",cardDe.getName());
    }

    @Test
    void getDescription() {
        assertEquals ("If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.",cardA.getDescription());
        assertEquals ("You also win when there are at least five Complete Towers on the board.",cardCr.getDescription());
    }

    @Test
    void isPlayableIn3() {
        assertTrue(cardA.isPlayableIn3());
        assertTrue(cardHy.isPlayableIn3());
        assertTrue(cardHe.isPlayableIn3());
        assertFalse(cardCr.isPlayableIn3());
        assertTrue(cardDe.isPlayableIn3());
    }

    @Test
    void getType() {
        assertEquals(CardType.YOURMOVE,cardA.getType());
        assertEquals(CardType.YOURTURN,cardHy.getType());
        assertEquals(CardType.MOVEVICTORY,cardHe.getType());
        assertEquals(CardType.BUILDVICTORY,cardCr.getType());
        assertEquals(CardType.YOURBUILD,cardDe.getType());
    }

    @Test
    void getSubType() {
        assertEquals(CardSubType.NONPERMANENTCONSTRAINT,cardA.getSubType());
        assertEquals(CardSubType.PERMANENTCONSTRAINT,cardHy.getSubType());
        assertEquals(CardSubType.PERMANENTCONSTRAINT,cardHe.getSubType());
        assertEquals(CardSubType.NORMAL,cardCr.getSubType());
        assertEquals(CardSubType.NORMAL,cardDe.getSubType());
    }

    @Test
    void getFirstAction() {
        assertEquals(Response.TOMOVE,cardA.getFirstAction());
    }

    @Test
    void findWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardA.findWorkerMove(null, worker1));
        assertThrows(NullPointerException.class , () -> cardA.findWorkerMove(gameMap, null));
    }

    @Test
    void executeWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardA.executeWorkerMove(null, Directions.OVEST, player));
        assertThrows(NullPointerException.class , () -> cardA.executeWorkerMove(gameMap, null, player));
        assertThrows(NullPointerException.class , () -> cardA.executeWorkerMove(null, null, player));
        assertThrows(NullPointerException.class , () -> cardA.executeWorkerMove(gameMap, Directions.OVEST, null));
        assertThrows(NullPointerException.class , () -> cardA.executeWorkerMove(null, Directions.OVEST, null));
        assertThrows(NullPointerException.class , () -> cardA.executeWorkerMove(gameMap, null, null));
        assertThrows(NullPointerException.class , () -> cardA.executeWorkerMove(null, null, null));

        player.setPower(cardDe);
        player.selectCurrentWorker(gameMap, "worker1");

        assertEquals(Response.MOVED,cardDe.executeWorkerMove(gameMap, Directions.OVEST, player));
    }

    @Test
    void findPossibleBuild() {
        assertThrows(NullPointerException.class , () -> cardA.findPossibleBuild(null, worker1));
        assertThrows(NullPointerException.class , () -> cardA.findPossibleBuild(gameMap, null));

        assertEquals(cardA.findPossibleBuild(gameMap, player.getWorkers().get(0)), gameMap.reachableSquares(player.getWorkers().get(0)));
    }

    @Test
    void executeBuild() {
        assertThrows(NullPointerException.class , () -> cardA.executeBuild(null,Building.LVL1, Directions.OVEST, worker1));
        assertThrows(NullPointerException.class , () -> cardA.executeBuild(gameMap, null, Directions.EST, worker1));
        assertThrows(NullPointerException.class , () -> cardA.executeBuild(gameMap, Building.LVL1, null, worker1));
        assertThrows(NullPointerException.class , () -> cardA.executeBuild(gameMap,Building.LVL1, Directions.EST, null));

        player.setPower(cardCr);

        assertEquals(Response.BUILD,cardCr.executeBuild(gameMap, Building.LVL1, Directions.OVEST, player.getWorkers().get(0)));
        assertEquals(Response.NOTBUILD,cardCr.executeBuild(gameMap, Building.LVL3, Directions.OVEST, player.getWorkers().get(0)));
    }

    @Test
    void checkVictory() {
        assertThrows(NullPointerException.class , () -> cardA.checkVictory(null, player));
        assertThrows(NullPointerException.class , () -> cardA.checkVictory(gameMap, null));

        player.setPower(cardAp);
        gameMap.getGameMap().get(22).addBuildingLevel();
        gameMap.getGameMap().get(22).addBuildingLevel();
        assertEquals(2,gameMap.getGameMap().get(22).getBuildingLevel());
        gameMap.getGameMap().get(23).addBuildingLevel();
        gameMap.getGameMap().get(23).addBuildingLevel();
        gameMap.getGameMap().get(23).addBuildingLevel();
        assertEquals(3,gameMap.getGameMap().get(23).getBuildingLevel());
        player.selectCurrentWorker(gameMap, "worker1");
        cardAp.executeWorkerMove(gameMap, Directions.NORD, player);
        assertEquals(Response.WIN,cardAp.checkVictory(gameMap, player));
        cardAp.executeWorkerMove(gameMap, Directions.NORD, player);
        assertEquals(Response.NOTWIN,cardAp.checkVictory(gameMap, player));



    }

    @Test
    void eliminateInvalidMove() {
        assertThrows(NullPointerException.class , () -> cardA.eliminateInvalidMove(null, worker1, directions));
        assertThrows(NullPointerException.class , () -> cardA.eliminateInvalidMove(gameMap, null, directions));
        assertThrows(NullPointerException.class , () -> cardA.eliminateInvalidMove(gameMap, worker1, null));

        assertEquals(cardAp.eliminateInvalidMove(gameMap, player.getWorkers().get(0), directions), directions);
    }

    @Test
    void canMove() {
        assertThrows(NullPointerException.class , () -> cardAp.canMove(null, worker1));
        assertThrows(NullPointerException.class , () -> cardAp.canMove(player, null));

        assertTrue(cardAp.canMove(player, player.getWorkers().get(0)));
    }

    @Test
    void isValidVictory() {
        assertThrows(NullPointerException.class , () -> cardAp.isValidVictory(null, worker1));
        assertThrows(NullPointerException.class , () -> cardAp.isValidVictory(gameMap, null));

        assertTrue(cardAp.isValidVictory(gameMap, player.getWorkers().get(0)));
    }

    @Test
    void testToString() {
        assertEquals(cardAp.toString(), "Card Name -> apollo\nCard Description -> Your worker may move into an opponent Worker's space by " +
                "forcing their Worker to the space yours just vacated.\nPlayable with 3 Player -> Yes\n");
        assertEquals(cardCr.toString(), "Card Name -> chronus\nCard Description -> You also win when there are at least five Complete " +
                "Towers on the board.\nPlayable with 3 Player -> No\n");
    }
}