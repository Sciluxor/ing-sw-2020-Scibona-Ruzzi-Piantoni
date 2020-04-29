package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.SimplifiedGame;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public abstract class ClientGameController implements Runnable{ //rimettere implements
    public static final Logger LOGGER = Logger.getLogger("Client");

    private SimplifiedGame game;
    private BlockingQueue<Runnable> eventQueue = new LinkedBlockingQueue<>();


    public ClientGameController(){

        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try{
                 eventQueue.take().run();

            }catch (InterruptedException e) {
            LOGGER.severe(e.getMessage());
            Thread.currentThread().interrupt();
           }
        }
    }

        //da implementare

        //funzione che ti ridà le possibili azioni

        //
        //
    public void openConnection(String name, int numberOfPlayer, String address, int port) {
//creare un nuovo simplfied Game;

    }


    public void onUpdateLobbyPlayer() {

    }

    public ArrayList<Player> getCurrentPlayer(){

        return null;
    }

    public void updateUserName(){

    }

        //aggiungere on update.




}
