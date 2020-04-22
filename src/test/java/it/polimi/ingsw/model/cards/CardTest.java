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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    Player player, player2;
    Card cardA, cardAp, cardHy, cardHe, cardCr, cardDe;
    Worker worker1,worker2;
    GameMap gameMap;
    ArrayList<Directions> directions;


    @BeforeEach
    void setup(){
        player = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardA = CardLoader.loadCards().get("Athena");
        cardAp = CardLoader.loadCards().get("Apollo");
        cardHy = CardLoader.loadCards().get("Hypnus");
        cardHe = CardLoader.loadCards().get("Hera");
        cardCr = CardLoader.loadCards().get("Chronus");
        cardDe = CardLoader.loadCards().get("Demeter");
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
        assertEquals (cardA.getName(), "Athena");
        assertEquals (cardHy.getName(), "Hypnus");
        assertEquals (cardHe.getName(), "Hera");
        assertEquals (cardCr.getName(), "Chronus");
        assertEquals (cardDe.getName(), "Demeter");
    }

    @Test
    void getDescription() {
        assertEquals (cardA.getDescription(), "Opponent's Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.");
        assertEquals (cardCr.getDescription(), "Win Condition: You also win when there are at least five Complete Towers on the board.");
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
        assertEquals(cardA.getType(), CardType.YOURMOVE);
        assertEquals(cardHy.getType(), CardType.YOURTURN);
        assertEquals(cardHe.getType(), CardType.MOVEVICTORY);
        assertEquals(cardCr.getType(), CardType.BUILDVICTORY);
        assertEquals(cardDe.getType(), CardType.YOURBUILD);
    }

    @Test
    void getSubType() {
        assertEquals(cardA.getSubType(), CardSubType.NONPERMANENTCONSTRAINT);
        assertEquals(cardHy.getSubType(), CardSubType.PERMANENTCONSTRAINT);
        assertEquals(cardHe.getSubType(), CardSubType.PERMANENTCONSTRAINT);
        assertEquals(cardCr.getSubType(), CardSubType.NORMAL);
        assertEquals(cardDe.getSubType(), CardSubType.NORMAL);
    }

    @Test
    void getFirstAction() {
        assertEquals(cardA.getFirstAction(), Response.TOMOVE);
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

        assertEquals(cardDe.executeWorkerMove(gameMap, Directions.OVEST, player), Response.MOVED);
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

        assertEquals(cardCr.executeBuild(gameMap, Building.LVL1, Directions.OVEST, player.getWorkers().get(0)), Response.BUILD);
        assertEquals(cardCr.executeBuild(gameMap, Building.LVL3, Directions.OVEST, player.getWorkers().get(0)), Response.NOTBUILD);
    }

    @Test
    void checkVictory() {
        assertThrows(NullPointerException.class , () -> cardA.checkVictory(null, player));
        assertThrows(NullPointerException.class , () -> cardA.checkVictory(gameMap, null));

        player.setPower(cardAp);
        gameMap.getGameMap().get(22).addBuildingLevel();
        gameMap.getGameMap().get(22).addBuildingLevel();
        assertEquals(gameMap.getGameMap().get(22).getBuildingLevel(), 2);
        gameMap.getGameMap().get(23).addBuildingLevel();
        gameMap.getGameMap().get(23).addBuildingLevel();
        gameMap.getGameMap().get(23).addBuildingLevel();
        assertEquals(gameMap.getGameMap().get(23).getBuildingLevel(), 3);
        player.selectCurrentWorker(gameMap, "worker1");
        cardAp.executeWorkerMove(gameMap, Directions.NORD, player);
        assertEquals(cardAp.checkVictory(gameMap, player), Response.WIN);
        cardAp.executeWorkerMove(gameMap, Directions.NORD, player);
        assertEquals(cardAp.checkVictory(gameMap, player), Response.NOTWIN);



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
        assertEquals(cardAp.toString(), "Card Name -> Apollo\nCard Description -> Your Move: Your worker may move into an opponent Worker's space by " +
                "forcing their Worker to the space yours just vacated.\nPlayable with 3 Player -> Yes\n");
        assertEquals(cardCr.toString(), "Card Name -> Chronus\nCard Description -> Win Condition: You also win when there are at least five Complete " +
                "Towers on the board.\nPlayable with 3 Player -> No\n");
    }
}