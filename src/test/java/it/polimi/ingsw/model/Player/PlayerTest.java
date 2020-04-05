package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.Cards.CardLoader;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player, player1, player2;
    Card cardA, cardAp, cardHy, cardHe;
    Worker worker1,worker2;
    GameMap gameMap;
    ArrayList<Directions> directions, directions2;
    ArrayList<Player> players;



    @BeforeEach
    void setup(){
        player = new Player("GoodPlayer");
        player1 = new Player("uno");
        player2 = new Player("due");
        players = new ArrayList<>();
        players.add(player);
        players.add(player1);
        players.add(player2);
        cardA = CardLoader.loadCards().get("Athena");
        cardAp = CardLoader.loadCards().get("Apollo");
        cardHy = CardLoader.loadCards().get("Hypnus");
        cardHe = CardLoader.loadCards().get("Hera");
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
    void getColor() {
        assertNull (player.getColor());
        player.setColor(Color.BLUE);
        assertEquals(player.getColor(), Color.BLUE);
    }

    @Test
    void setColor() {
        assertNull (player.getColor());
        player.setColor(Color.BLUE);
        assertEquals(player.getColor(), Color.BLUE);
    }

    @Test
    void getNickname() {
        assertEquals (player.getNickname(), "GoodPlayer");
    }

    @Test
    void getPower() {
        assertNull (player.getPower());
        player.setPower(cardA);
        assertEquals (player.getPower().getName(), "Athena");
        player.setPower(cardHe);
        player.setPower(cardHy);
        assertEquals (player.getPower().getName(), "Hypnus");
    }

    @Test
    void setPower() {
        assertNull (player.getPower());
        player.setPower(cardA);
        assertEquals (player.getPower().getName(), "Athena");
        player.setPower(cardHy);
        player.setPower(cardHe);
        player.setPower(cardHe);
        assertEquals (player.getPower().getName(), "Hera");
        player.setPower(cardA);
        player.setPower(cardHe);
        player.setPower(cardHy);
        assertEquals (player.getPower().getName(), "Hypnus");
        player.setPower(cardA);
        assertEquals (player.getPower().getName(), "Athena");

        assertThrows(NullPointerException.class , () -> player.setPower(null));
    }

    @Test
    void getTurnStatus() {
        assertEquals (player.getTurnStatus(), TurnStatus.PREGAME);
        player.setTurnStatus(TurnStatus.GAMEENDED);
        assertEquals (player.getTurnStatus(), TurnStatus.GAMEENDED);
        player.setTurnStatus(TurnStatus.WORKERSELECTION);
        player.setTurnStatus(TurnStatus.WORKERTURN);
        player.setTurnStatus(TurnStatus.IDLE);
        player.setTurnStatus(TurnStatus.WORKERSELECTION);
        player.setTurnStatus(TurnStatus.IDLE);
        assertEquals (player.getTurnStatus(), TurnStatus.IDLE);
        player.setTurnStatus(TurnStatus.ENDTURN);
        player.setTurnStatus(TurnStatus.WORKERSELECTION);
        player.setTurnStatus(TurnStatus.WORKERSELECTION);
        assertEquals (player.getTurnStatus(), TurnStatus.WORKERSELECTION);
    }

    @Test
    void setTurnStatus() {
        assertEquals (player.getTurnStatus(), TurnStatus.PREGAME);
        player.setTurnStatus(TurnStatus.CHECKIFLOSE);
        assertEquals (player.getTurnStatus(), TurnStatus.CHECKIFLOSE);
        player.setTurnStatus(TurnStatus.ENDTURN);
        assertEquals (player.getTurnStatus(), TurnStatus.ENDTURN);
        player.setTurnStatus(TurnStatus.GAMEENDED);
        assertEquals (player.getTurnStatus(), TurnStatus.GAMEENDED);
        player.setTurnStatus(TurnStatus.IDLE);
        assertEquals (player.getTurnStatus(), TurnStatus.IDLE);
        player.setTurnStatus(TurnStatus.PREGAME);
        assertEquals (player.getTurnStatus(), TurnStatus.PREGAME);
        player.setTurnStatus(TurnStatus.WORKERSELECTION);
        assertEquals (player.getTurnStatus(), TurnStatus.WORKERSELECTION);
        player.setTurnStatus(TurnStatus.WORKERTURN);
        assertEquals (player.getTurnStatus(), TurnStatus.WORKERTURN);

        assertThrows(NullPointerException.class , () -> player.setTurnStatus(null));
    }

    @Test
    void getConstraint() {
        assertEquals (player.getConstraint().size(), 0);
        player.setConstraint(cardA);
        player.setConstraint(cardHy);
        player.setConstraint(cardHe);
        assertEquals (player.getConstraint().size(), 3);
        assertEquals(player.getConstraint().get(0), cardA);
        assertEquals(player.getConstraint().get(1), cardHy);
        assertEquals(player.getConstraint().get(2), cardHe);
    }

    @Test
    void setConstraint() {
        assertEquals (player.getConstraint().size(), 0);
        player.setConstraint(cardA);
        player.setConstraint(cardHe);
        player.setConstraint(cardHy);
        assertNotNull (player.getConstraint());
        assertEquals (player.getConstraint().size(), 3);
        assertEquals(player.getConstraint().get(0), cardA);
        assertEquals(player.getConstraint().get(2), cardHy);
        assertEquals(player.getConstraint().get(1), cardHe);

        assertThrows(NullPointerException.class , () -> player.setConstraint(null));
    }

    @Test
    void removeConstraint() {
        assertEquals (player.getConstraint().size(), 0);
        player.setConstraint(cardA);
        player.setConstraint(cardHy);
        player.setConstraint(cardHe);
        assertEquals (player.getConstraint().size(), 3);
        assertEquals(player.getConstraint().get(0), cardA);
        assertEquals(player.getConstraint().get(1), cardHy);
        assertEquals(player.getConstraint().get(2), cardHe);
        player.removeConstraint(cardA);
        assertEquals (player.getConstraint().size(), 2);
        assertEquals(player.getConstraint().get(0), cardHy);
        assertEquals(player.getConstraint().get(1), cardHe);
        player.removeConstraint(cardHe);
        assertEquals (player.getConstraint().size(), 1);
        assertEquals(player.getConstraint().get(0), cardHy);
        player.removeConstraint(cardHy);
        assertEquals (player.getConstraint().size(), 0);

        assertThrows(NullPointerException.class , () -> player.removeConstraint(null));
    }

    @Test
    void getWorkers() {
        assertEquals (player.getWorkers().size(), 2);
        assertEquals(player.getWorkers().get(0).getName(), WorkerName.WORKER1);
        assertEquals(player.getWorkers().get(1).getName(), WorkerName.WORKER2);
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

        assertEquals(player2.findWorkerMove(gameMap, player2.getWorkers().get(0)).size(), 7);
        gameMap.getGameMap().get(10).addBuildingLevel();
        directions = player2.findWorkerMove(gameMap, player2.getWorkers().get(0));
        directions2 = player2.getConstraint().get(0).eliminateInvalidMove(gameMap, player2.getWorkers().get(0), directions);
        assertEquals(directions2.size(), 6);

        gameMap.getGameMap().get(8).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(gameMap.getGameMap().get(8));
        gameMap.getGameMap().get(9).addBuildingLevel();
        gameMap.getGameMap().get(9).addBuildingLevel();
        assertEquals(gameMap.getGameMap().get(9).getBuildingLevel(), 2);
        gameMap.getGameMap().get(7).addBuildingLevel();
        gameMap.getGameMap().get(7).addBuildingLevel();
        assertEquals(gameMap.getGameMap().get(7).getBuildingLevel(), 2);
        gameMap.getGameMap().get(20).addBuildingLevel();
        gameMap.getGameMap().get(20).addBuildingLevel();
        assertEquals(gameMap.getGameMap().get(7).getBuildingLevel(), 2);
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
        assertEquals(gameMap.getGameMap().get(9).getBuildingLevel(), 2);
        gameMap.getGameMap().get(7).addBuildingLevel();
        gameMap.getGameMap().get(7).addBuildingLevel();
        assertEquals(gameMap.getGameMap().get(7).getBuildingLevel(), 2);
        gameMap.getGameMap().get(20).addBuildingLevel();
        gameMap.getGameMap().get(20).addBuildingLevel();
        assertEquals(gameMap.getGameMap().get(20).getBuildingLevel(), 2);
        gameMap.getGameMap().get(0).setMovement(player1,player1.getWorkers().get(0));
        player1.getWorkers().get(0).setBoardPosition(gameMap.getGameMap().get(0));
        gameMap.getGameMap().get(1).addBuildingLevel();
        gameMap.getGameMap().get(1).addBuildingLevel();
        assertEquals(gameMap.getGameMap().get(1).getBuildingLevel(), 2);
        gameMap.getGameMap().get(16).addBuildingLevel();
        gameMap.getGameMap().get(16).addBuildingLevel();
        assertEquals(gameMap.getGameMap().get(16).getBuildingLevel(), 2);
        gameMap.getGameMap().get(15).addBuildingLevel();
        gameMap.getGameMap().get(15).addBuildingLevel();
        assertEquals(gameMap.getGameMap().get(15).getBuildingLevel(), 2);
        assertTrue(player1.checkIfLoose(gameMap));
    }

    @Test
    void getFirstAction() {
        player.setPower(cardAp);
        assertEquals(player.getFirstAction(), Response.TOMOVE);
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
        assertEquals(player1.executeBuild(gameMap, Building.LVL1, Directions.OVEST), Response.BUILD);
    }

    @Test
    void checkVictory() {
        assertThrows(NullPointerException.class , () -> player.checkVictory(null));

        player1.setPower(cardAp);
        player1.selectCurrentWorker(gameMap, "worker1");
        assertEquals(player1.checkVictory(gameMap), Response.NOTWIN);
    }

    @Test
    void assignConstraint() {
        assertThrows(NullPointerException.class , () -> player.assignConstraint(null));

        player.setPower(cardA);
        player1.setPower(cardHe);
        player2.setPower(cardAp);

        player.assignConstraint(players);
        assertEquals(players.get(0).getConstraint().size(), 0);
        assertEquals(players.get(1).getConstraint().size(), 1);
        assertEquals(players.get(2).getConstraint().size(), 1);
        assertEquals(players.get(1).getConstraint().get(0), cardA);
        assertEquals(players.get(2).getConstraint().get(0), cardA);
        player1.assignConstraint(players);
        assertEquals(players.get(0).getConstraint().size(), 1);
        assertEquals(players.get(1).getConstraint().size(), 1);
        assertEquals(players.get(2).getConstraint().size(), 2);
        assertEquals(players.get(0).getConstraint().get(0), cardHe);
        assertEquals(players.get(1).getConstraint().get(0), cardA);
        assertEquals(players.get(2).getConstraint().get(0), cardA);
        assertEquals(players.get(2).getConstraint().get(1), cardHe);
        player2.assignConstraint(players);
        assertEquals(players.get(0).getConstraint().size(), 2);
        assertEquals(players.get(1).getConstraint().size(), 2);
        assertEquals(players.get(2).getConstraint().size(), 2);
        assertEquals(players.get(0).getConstraint().get(0), cardHe);
        assertEquals(players.get(0).getConstraint().get(1), cardAp);
        assertEquals(players.get(1).getConstraint().get(0), cardA);
        assertEquals(players.get(1).getConstraint().get(1), cardAp);
        assertEquals(players.get(2).getConstraint().get(0), cardA);
        assertEquals(players.get(2).getConstraint().get(1), cardHe);
    }
}