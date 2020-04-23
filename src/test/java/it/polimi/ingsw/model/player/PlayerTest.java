package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player, player1, player2;
    Card cardA, cardAp, cardHy, cardHe;
    Worker worker1,worker2;
    GameMap gameMap;
    List<Directions> directions, directions2;
    List<Player> players;



    @BeforeEach
    void setup(){
        player = new Player("GoodPlayer");
        player1 = new Player("uno");
        player2 = new Player("due");
        players = new ArrayList<>();
        players.add(player);
        players.add(player1);
        players.add(player2);
        cardA = CardLoader.loadCards().get("athena");
        cardAp = CardLoader.loadCards().get("apollo");
        cardHy = CardLoader.loadCards().get("hypnus");
        cardHe = CardLoader.loadCards().get("hera");
        worker1 = new Worker(WorkerName.WORKER1);
        worker2 = new Worker(WorkerName.WORKER2);
        gameMap = new GameMap();
        gameMap.getGameMap().get(22).setMovement(player1,player1.getWorkers().get(0));
        player1.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(22));
        gameMap.getGameMap().get(4).setMovement(player1,player1.getWorkers().get(1));
        player1.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(4));
        gameMap.getGameMap().get(21).setMovement(player2,player2.getWorkers().get(0));
        player2.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(21));
        gameMap.getGameMap().get(18).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(18));
    }

    @Test
    void hasPlacedWorkers() {
        assertFalse(player.hasPlacedWorkers());
        player.setHasPlacedWorkers(true);
        assertTrue(player.hasPlacedWorkers());
    }

    @Test
    void setHasPlacedWorkers() {
        assertFalse(player.hasPlacedWorkers());
        player.setHasPlacedWorkers(true);
        assertTrue(player.hasPlacedWorkers());
    }

    @Test
    void getColor() {
        assertNull (player.getColor());
        player.setColor(Color.BLUE);
        assertEquals(Color.BLUE,player.getColor());
    }

    @Test
    void setColor() {
        assertNull (player.getColor());
        player.setColor(Color.BLUE);
        assertEquals(Color.BLUE,player.getColor());
    }

    @Test
    void getNickname() {
        assertEquals ("GoodPlayer",player.getNickname());
    }

    @Test
    void getPower() {
        assertNull (player.getPower());
        player.setPower(cardA);
        assertEquals ("athena",player.getPower().getName());
        player.setPower(cardHe);
        player.setPower(cardHy);
        assertEquals ("hypnus",player.getPower().getName());
    }

    @Test
    void setPower() {
        assertNull (player.getPower());
        player.setPower(cardA);
        assertEquals ("athena",player.getPower().getName());
        player.setPower(cardHy);
        player.setPower(cardHe);
        player.setPower(cardHe);
        assertEquals ("hera",player.getPower().getName());
        player.setPower(cardA);
        player.setPower(cardHe);
        player.setPower(cardHy);
        assertEquals ("hypnus",player.getPower().getName());
        player.setPower(cardA);
        assertEquals ("athena",player.getPower().getName());

        assertThrows(NullPointerException.class , () -> player.setPower(null));
    }

    @Test
    void getTurnStatus() {
        assertEquals (TurnStatus.PREGAME,player.getTurnStatus());
        player.setTurnStatus(TurnStatus.GAMEENDED);
        assertEquals (TurnStatus.GAMEENDED,player.getTurnStatus());
        player.setTurnStatus(TurnStatus.WORKERSELECTION);
        player.setTurnStatus(TurnStatus.WORKERTURN);
        player.setTurnStatus(TurnStatus.IDLE);
        player.setTurnStatus(TurnStatus.WORKERSELECTION);
        player.setTurnStatus(TurnStatus.IDLE);
        assertEquals (TurnStatus.IDLE,player.getTurnStatus());
        player.setTurnStatus(TurnStatus.ENDTURN);
        player.setTurnStatus(TurnStatus.WORKERSELECTION);
        player.setTurnStatus(TurnStatus.WORKERSELECTION);
        assertEquals (TurnStatus.WORKERSELECTION,player.getTurnStatus());
    }

    @Test
    void setTurnStatus() {
        assertEquals (TurnStatus.PREGAME,player.getTurnStatus());
        player.setTurnStatus(TurnStatus.CHECKIFLOSE);
        assertEquals (TurnStatus.CHECKIFLOSE,player.getTurnStatus());
        player.setTurnStatus(TurnStatus.ENDTURN);
        assertEquals (TurnStatus.ENDTURN,player.getTurnStatus());
        player.setTurnStatus(TurnStatus.GAMEENDED);
        assertEquals (TurnStatus.GAMEENDED,player.getTurnStatus());
        player.setTurnStatus(TurnStatus.IDLE);
        assertEquals (TurnStatus.IDLE,player.getTurnStatus());
        player.setTurnStatus(TurnStatus.PREGAME);
        assertEquals (TurnStatus.PREGAME,player.getTurnStatus());
        player.setTurnStatus(TurnStatus.WORKERSELECTION);
        assertEquals (TurnStatus.WORKERSELECTION,player.getTurnStatus());
        player.setTurnStatus(TurnStatus.WORKERTURN);
        assertEquals (TurnStatus.WORKERTURN,player.getTurnStatus());

        assertThrows(NullPointerException.class , () -> player.setTurnStatus(null));
    }

    @Test
    void getConstraint() {
        assertEquals (0,player.getConstraint().size());
        player.setConstraint(cardA);
        player.setConstraint(cardHy);
        player.setConstraint(cardHe);
        assertEquals (3,player.getConstraint().size());
        assertEquals(player.getConstraint().get(0), cardA);
        assertEquals(player.getConstraint().get(1), cardHy);
        assertEquals(player.getConstraint().get(2), cardHe);
    }

    @Test
    void setConstraint() {
        assertEquals (0,player.getConstraint().size());
        player.setConstraint(cardA);
        player.setConstraint(cardHe);
        player.setConstraint(cardHy);
        assertNotNull (player.getConstraint());
        assertEquals (3,player.getConstraint().size());
        assertEquals(player.getConstraint().get(0), cardA);
        assertEquals(player.getConstraint().get(2), cardHy);
        assertEquals(player.getConstraint().get(1), cardHe);

        assertThrows(NullPointerException.class , () -> player.setConstraint(null));
    }

    @Test
    void removeConstraint() {
        assertEquals (0,player.getConstraint().size());
        player.setConstraint(cardA);
        player.setConstraint(cardHy);
        player.setConstraint(cardHe);
        assertEquals (3,player.getConstraint().size());
        assertEquals(player.getConstraint().get(0), cardA);
        assertEquals(player.getConstraint().get(1), cardHy);
        assertEquals(player.getConstraint().get(2), cardHe);
        player.removeConstraint(cardA);
        assertEquals (2,player.getConstraint().size());
        assertEquals(player.getConstraint().get(0), cardHy);
        assertEquals(player.getConstraint().get(1), cardHe);
        player.removeConstraint(cardHe);
        assertEquals (1,player.getConstraint().size());
        assertEquals(player.getConstraint().get(0), cardHy);
        player.removeConstraint(cardHy);
        assertEquals (0,player.getConstraint().size());

        assertThrows(NullPointerException.class , () -> player.removeConstraint(null));
    }

    @Test
    void getWorkers() {
        assertEquals (2,player.getWorkers().size());
        assertEquals(WorkerName.WORKER1,player.getWorkers().get(0).getName());
        assertEquals(WorkerName.WORKER2,player.getWorkers().get(1).getName());
    }

    @Test
    void setCurrentWorker() {
        assertNull(player.getCurrentWorker());
        player.setCurrentWorker(worker1);
        assertEquals(player.getCurrentWorker(), worker1);
        player.setCurrentWorker(worker2);
        player.setCurrentWorker(worker1);
        assertEquals(player.getCurrentWorker(), worker1);

        assertThrows(NullPointerException.class , () -> player.setCurrentWorker(null));
    }

    @Test
    void getCurrentWorker() {
        assertNull(player.getCurrentWorker());
        player.setCurrentWorker(worker1);
        assertEquals(player.getCurrentWorker(), worker1);
        player.setCurrentWorker(worker2);
        player.setCurrentWorker(worker1);
        player.setCurrentWorker(worker2);
        assertEquals(player.getCurrentWorker(), worker2);
    }

    @Test
    void setUnmovedWorker() {
        assertNull(player.getUnmovedWorker());
        player.setUnmovedWorker(worker1);
        assertEquals(player.getUnmovedWorker(), worker1);
        player.setUnmovedWorker(worker2);
        player.setUnmovedWorker(worker1);
        player.setUnmovedWorker(worker2);
        assertEquals(player.getUnmovedWorker(), worker2);

        assertThrows(NullPointerException.class , () -> player.setUnmovedWorker(null));
    }

    @Test
    void getUnmovedWorker() {
        assertNull(player.getUnmovedWorker());
        player.setUnmovedWorker(worker1);
        assertEquals(player.getUnmovedWorker(), worker1);
        player.setUnmovedWorker(worker2);
        player.setUnmovedWorker(worker1);
        player.setUnmovedWorker(worker2);
        assertEquals(player.getUnmovedWorker(), worker2);
    }

    @Test
    void getWorkerFromString() {
        assertEquals(player.getWorkerFromString("worker1"), player.getWorkers().get(0));
        assertEquals(player.getWorkerFromString("worker2"), player.getWorkers().get(1));

        assertThrows(NullPointerException.class , () -> player.getWorkerFromString(null));

        assertThrows(IllegalArgumentException.class , () -> assertEquals(player.getWorkerFromString("bhu"), player.getWorkers().get(0)));
    }

    @Test
    void selectCurrentWorker() {
        assertThrows(NullPointerException.class , () -> player.selectCurrentWorker(null, "worker1"));
        assertThrows(NullPointerException.class , () -> player.selectCurrentWorker(gameMap, null));

        player1.setPower(cardA);
        player2.setPower(cardHy);
        player1.setConstraint(cardHy);
        player2.setConstraint(cardA);

        player1.getWorkers().get(0).getBoardPosition().addBuildingLevel();
        assertFalse(player1.selectCurrentWorker(gameMap, "worker1"));

    }

    @Test
    void checkIfCanMove() {
        assertThrows(NullPointerException.class , () -> player.selectCurrentWorker(null, "worker1"));
        assertThrows(NullPointerException.class , () -> player.selectCurrentWorker(gameMap, null));


        player1.setPower(cardA);
        player2.setPower(cardHy);
        assertTrue(player1.checkIfCanMove(gameMap, player1.getWorkers().get(0)));

        player1.setConstraint(cardHy);
        player2.setConstraint(cardA);
        assertTrue(player1.checkIfCanMove(gameMap, player1.getWorkers().get(0)));
        assertTrue(player1.checkIfCanMove(gameMap, player1.getWorkers().get(1)));
        assertTrue(player2.checkIfCanMove(gameMap, player2.getWorkers().get(0)));
        assertTrue(player2.checkIfCanMove(gameMap, player2.getWorkers().get(1)));

        player1.getWorkers().get(0).getBoardPosition().addBuildingLevel();
        assertFalse(player1.checkIfCanMove(gameMap, player1.getWorkers().get(0)));
        assertTrue(player1.checkIfCanMove(gameMap, player1.getWorkers().get(1)));

        player1.getWorkers().get(1).getBoardPosition().addBuildingLevel();
        assertTrue(player1.checkIfCanMove(gameMap, player1.getWorkers().get(0)));
        assertTrue(player1.checkIfCanMove(gameMap, player1.getWorkers().get(1)));

        assertEquals(7,player2.findWorkerMove(gameMap, player2.getWorkers().get(0)).size());
        gameMap.getGameMap().get(10).addBuildingLevel();
        directions = player2.findWorkerMove(gameMap, player2.getWorkers().get(0));
        directions2 = player2.getConstraint().get(0).eliminateInvalidMove(gameMap, player2.getWorkers().get(0), directions);
        assertEquals(6,directions2.size());

        gameMap.getGameMap().get(8).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(8));
        gameMap.getGameMap().get(9).addBuildingLevel();
        gameMap.getGameMap().get(9).addBuildingLevel();
        assertEquals(2,gameMap.getGameMap().get(9).getBuildingLevel());
        gameMap.getGameMap().get(7).addBuildingLevel();
        gameMap.getGameMap().get(7).addBuildingLevel();
        assertEquals(2,gameMap.getGameMap().get(7).getBuildingLevel());
        gameMap.getGameMap().get(20).addBuildingLevel();
        gameMap.getGameMap().get(20).addBuildingLevel();
        assertEquals(2,gameMap.getGameMap().get(7).getBuildingLevel());
        assertFalse(player2.checkIfCanMove(gameMap, player2.getWorkers().get(1)));

    }

    @Test
    void checkIfLoose() {

        assertThrows(NullPointerException.class , () -> player.checkIfLoose(null));

        player1.setPower(cardAp);
        assertFalse(player1.checkIfLoose(gameMap));

        gameMap.getGameMap().get(8).setMovement(player1,player1.getWorkers().get(1));
        player1.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(8));
        gameMap.getGameMap().get(9).addBuildingLevel();
        gameMap.getGameMap().get(9).addBuildingLevel();
        assertEquals(2,gameMap.getGameMap().get(9).getBuildingLevel());
        gameMap.getGameMap().get(7).addBuildingLevel();
        gameMap.getGameMap().get(7).addBuildingLevel();
        assertEquals(2,gameMap.getGameMap().get(7).getBuildingLevel());
        gameMap.getGameMap().get(20).addBuildingLevel();
        gameMap.getGameMap().get(20).addBuildingLevel();
        assertEquals(2,gameMap.getGameMap().get(20).getBuildingLevel());
        gameMap.getGameMap().get(0).setMovement(player1,player1.getWorkers().get(0));
        player1.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(0));
        gameMap.getGameMap().get(1).addBuildingLevel();
        gameMap.getGameMap().get(1).addBuildingLevel();
        assertEquals(2,gameMap.getGameMap().get(1).getBuildingLevel());
        gameMap.getGameMap().get(16).addBuildingLevel();
        gameMap.getGameMap().get(16).addBuildingLevel();
        assertEquals(2,gameMap.getGameMap().get(16).getBuildingLevel());
        gameMap.getGameMap().get(15).addBuildingLevel();
        gameMap.getGameMap().get(15).addBuildingLevel();
        assertEquals(2,gameMap.getGameMap().get(15).getBuildingLevel());
        assertTrue(player1.checkIfLoose(gameMap));
    }

    @Test
    void getFirstAction() {
        player.setPower(cardAp);
        assertEquals(Response.TOMOVE,player.getFirstAction());
    }

    @Test
    void findWorkerMove() {
        assertThrows(NullPointerException.class , () -> player.findWorkerMove(null, worker1));
        assertThrows(NullPointerException.class , () -> player.findWorkerMove(gameMap, null));
    }

    @Test
    void executeWorkerMove() {
        assertThrows(NullPointerException.class , () -> player.executeWorkerMove(null, Directions.NORD));
        assertThrows(NullPointerException.class , () -> player.executeWorkerMove(gameMap, null));

        player1.setPower(cardAp);
        player1.selectCurrentWorker(gameMap, "worker1");
        assertEquals(player1.executeWorkerMove(gameMap, Directions.NORD), cardAp.executeWorkerMove(gameMap, Directions.NORD, player1));
    }

    @Test
    void findPossibleBuild() {
        assertThrows(NullPointerException.class , () -> player.findPossibleBuild(null, worker1));
        assertThrows(NullPointerException.class , () -> player.findPossibleBuild(gameMap, null));

        player1.setPower(cardAp);
        assertEquals(player1.findPossibleBuild(gameMap, player1.getWorkers().get(0)), cardAp.findPossibleBuild(gameMap, player1.getWorkers().get(0)));
    }

    @Test
    void executeBuild() {
        assertThrows(NullPointerException.class , () -> player.executeBuild(null, Building.DOME, Directions.OVEST));
        assertThrows(NullPointerException.class , () -> player.executeBuild(gameMap, null, Directions.OVEST));
        assertThrows(NullPointerException.class , () -> player.executeBuild(gameMap, Building.DOME, null));

        player1.setPower(cardAp);
        player1.selectCurrentWorker(gameMap, "worker1");
        assertEquals(Response.BUILD,player1.executeBuild(gameMap, Building.LVL1, Directions.OVEST));
    }

    @Test
    void checkVictory() {
        assertThrows(NullPointerException.class , () -> player.checkVictory(null));

        player1.setPower(cardAp);
        player1.selectCurrentWorker(gameMap, "worker1");
        assertEquals( Response.NOTWIN,player1.checkVictory(gameMap));
    }

    @Test
    void assignConstraint() {
        assertThrows(NullPointerException.class , () -> player.assignConstraint(null));

        player.setPower(cardA);
        player1.setPower(cardHe);
        player2.setPower(cardAp);

        player.assignConstraint(players);
        assertEquals(0,players.get(0).getConstraint().size());
        assertEquals(1,players.get(1).getConstraint().size());
        assertEquals(1,players.get(2).getConstraint().size());
        assertEquals(players.get(1).getConstraint().get(0), cardA);
        assertEquals(players.get(2).getConstraint().get(0), cardA);
        player1.assignConstraint(players);
        assertEquals(1,players.get(0).getConstraint().size());
        assertEquals(1,players.get(1).getConstraint().size());
        assertEquals(2,players.get(2).getConstraint().size());
        assertEquals(players.get(0).getConstraint().get(0), cardHe);
        assertEquals(players.get(1).getConstraint().get(0), cardA);
        assertEquals(players.get(2).getConstraint().get(0), cardA);
        assertEquals(players.get(2).getConstraint().get(1), cardHe);
        player2.assignConstraint(players);
        assertEquals(2,players.get(0).getConstraint().size());
        assertEquals(2,players.get(1).getConstraint().size());
        assertEquals(2,players.get(2).getConstraint().size());
        assertEquals(players.get(0).getConstraint().get(0), cardHe);
        assertEquals(players.get(0).getConstraint().get(1), cardAp);
        assertEquals(players.get(1).getConstraint().get(0), cardA);
        assertEquals(players.get(1).getConstraint().get(1), cardAp);
        assertEquals(players.get(2).getConstraint().get(0), cardA);
        assertEquals(players.get(2).getConstraint().get(1), cardHe);
    }
}