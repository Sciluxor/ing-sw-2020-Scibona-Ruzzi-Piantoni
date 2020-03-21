package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.Cards.CardLoader;
import it.polimi.ingsw.model.Map.MapLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Player player;
    Card cardA, cardHy, cardHe;



    @BeforeEach
    void setup(){
        player = new Player("GoodPlayer", TurnStatus.PREGAME);
        cardA = CardLoader.loadCards().get("Athena");
        cardHy = CardLoader.loadCards().get("Hypnus");
        cardHe = CardLoader.loadCards().get("Hera");
    }

    @Test
    void getNickname() {
        assertEquals (player.getNickname(), "GoodPlayer");
    }

    @Test
    void getPower() {
        player.setPower(cardA);
        assertEquals (player.getPower().getName(), "Athena");
        player.setPower(cardHe);
        player.setPower(cardHy);
        assertEquals (player.getPower().getName(), "Hypnus");
    }

    @Test
    void setPower() {
        player.setPower(cardA);
        assertEquals (player.getPower().getName(), "Athena");
    }

    @Test
    void getTurnStatus() {
        player.setTurnStatus(TurnStatus.WORKERSELECTION);
        assertEquals (player.getTurnStatus(), TurnStatus.WORKERSELECTION);
    }

    @Test
    void setTurnStatus() {
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
    }

    @Test
    void getConstraint() {
        player.setConstraint(cardA);
        player.setConstraint(cardHy);
        player.setConstraint(cardHe);
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
    }

    @Test
    void removeConstraint() {
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
    }

    @Test
    void getWorkers() {
    }

    @Test
    void setWorkers() {
    }

    @Test
    void setCurrentWorker() {
    }

    @Test
    void getCurrentWorker() {
    }

    @Test
    void setUnmovedWorker() {
    }

    @Test
    void getUnmovedWorker() {
    }

    @Test
    void getWorkerFromString() {
    }

    @Test
    void selectCurrentWorker() {
    }

    @Test
    void checkIfCanMove() {
    }

    @Test
    void checkIfLoose() {
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
    }

    @Test
    void checkVictory() {
    }
}