package it.polimi.ingsw.network.client;

public class MessageController implements MessageListener {

    //run in another thread and listen to the message received from the server, without blocking GUI and CLI

    public MessageController(){
        //da implementare
    }

    @Override
    public void connect() {
        //da implementare

    }

    @Override
    public void startGUI() {
        //da implementare

    }

    @Override
    public void startCLI() {
        //da implementare

    }

    //vedere se metterle qui queste funzioni o da un'altra parte. probabile meglio creare un process message nel controller del client e mandare il messaggio lì,usare invokelater per gui
    // oppure creare due controller differenti per cli e gui, vedere come gestire involkeLater nella CLI --> con un interrupt
    @Override
    public void updateView() {
        //da implementare

    }

    @Override
    public void processMessage() {
        //da implementare

    }


    //le funzioni handle metteranno un runnable nelle coda degli eseguibili di cli e gui.
    @Override
    public void handleErrorMessage() {
        //da implementare

    }

    @Override
    public void handleStartTurn() {
        //da implementare

    }

    @Override
    public void handleWin() {
        //da implementare

    }

    @Override
    public void handleChallengerChoice() {
        //da implementare

    }

    @Override
    public void handleCardChoice() {
        //da implementare

    }

    @Override
    public void handleLose() {
        //da implementare

    }

    @Override
    public void handlePlayerEliminated() {
        //da implementare

    }

    @Override
    public void lostConnection() {
        //da implementare

    }


}
