package it.polimi.ingsw.network.client;

public class MessageController implements MessageListener {

    //run in another thread and listen to the message received from the server, without blocking GUI and CLI

    public MessageController(){

    }

    @Override
    public void connect() {

    }

    @Override
    public void startGUI() {

    }

    @Override
    public void startCLI() {

    }

    //vedere se metterle qui queste funzioni o da un'altra parte. probabile meglio creare un process message nel controller del client e mandare il messaggio lì,usare invokelater per gui
    // oppure creare due controller differenti per cli e gui, vedere come gestire involkeLater nella CLI --> Thread.currentThread().interrupt();
    @Override
    public void updateView() {

    }

    @Override
    public void processMessage() {

    }


    //le funzioni handle metteranno un runnable nelle coda degli eseguibili di cli e gui.
    @Override
    public void handleErrorMessage() {

    }

    @Override
    public void handleStartTurn() {

    }

    @Override
    public void handleWin() {

    }

    @Override
    public void handleChallengerChoice() {

    }

    @Override
    public void handleCardChoice() {

    }

    @Override
    public void handleLose() {

    }

    @Override
    public void handlePlayerEliminated() {

    }

    @Override
    public void lostConnection() {

    }


}
