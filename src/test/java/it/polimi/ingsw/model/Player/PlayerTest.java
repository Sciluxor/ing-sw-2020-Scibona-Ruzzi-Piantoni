package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.Cards.CardLoader;
import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Map.MapLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;
    Card cardA, cardHy, cardHe;
    Worker worker1,worker2;
    GameMap gameMap;
    Building building;
    Directions directions;



    @BeforeEach
    void setup(){
        player = new Player("GoodPlayer", TurnStatus.PREGAME);
        cardA = CardLoader.loadCards().get("Athena");
        cardHy = CardLoader.loadCards().get("Hypnus");
        cardHe = CardLoader.loadCards().get("Hera");
        worker1 = new Worker(WorkerName.WORKER1);
        worker2 = new Worker(WorkerName.WORKER2);
        gameMap = new GameMap();
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

        assertThrows(NullPointerException.class , () -> {
            player.setPower(null);
        });
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

        assertThrows(NullPointerException.class , () -> {
            player.setTurnStatus(null);
        });
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

        assertThrows(NullPointerException.class , () -> {
            player.setConstraint(null);
        });
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

        assertThrows(NullPointerException.class , () -> {
            player.removeConstraint(null);
        });
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

        assertThrows(NullPointerException.class , () -> {
            player.setCurrentWorker(null);
        });
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

        assertThrows(NullPointerException.class , () -> {
            player.setUnmovedWorker(null);
        });
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

        assertThrows(NullPointerException.class , () -> {
            player.getWorkerFromString(null);
        });

        assertThrows(IllegalArgumentException.class , () -> {
            assertEquals(player.getWorkerFromString("bhu"), player.getWorkers().get(0));
        });
    }

    @Test
    void selectCurrentWorker() {
        assertThrows(NullPointerException.class , () -> {
            player.selectCurrentWorker(null, "worker1");
        });
        assertThrows(NullPointerException.class , () -> {
            player.selectCurrentWorker(gameMap, null);
        });

    }

    @Test
    void checkIfCanMove() {
        assertThrows(NullPointerException.class , () -> {
            player.selectCurrentWorker(null, "worker1");
        });
        assertThrows(NullPointerException.class , () -> {
            player.selectCurrentWorker(gameMap, null);
        });
    }

    @Test
    void checkConstraint() {

    }

    @Test
    void checkIfLoose() {
        assertThrows(NullPointerException.class , () -> {
            player.checkIfLoose(null);
        });
    }

    @Test
    void findWorkerMove() {
    }

    @Test
    void executeWorkerMove() {
    }

    @Test
    void findPossibleBuild() {
    }

    @Test
    void executeBuild() {
        assertThrows(NullPointerException.class , () -> {
            player.executeBuild(null, Building.DOME, Directions.OVEST);
        });
        assertThrows(NullPointerException.class , () -> {
            player.executeBuild(gameMap, null, Directions.OVEST);
        });
        assertThrows(NullPointerException.class , () -> {
            player.executeBuild(gameMap, Building.DOME, null);
        });
    }

    @Test
    void checkVictory() {
        assertThrows(NullPointerException.class , () -> {
            player.checkVictory(null, worker1);
        });
        assertThrows(NullPointerException.class , () -> {
            player.checkVictory(gameMap, null);
        });
    }
}