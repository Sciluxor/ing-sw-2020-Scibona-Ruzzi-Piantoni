package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.SimplifiedGame;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.*;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public abstract class ClientGameController implements Runnable, FunctionListener{ //rimettere implements
    public static final Logger LOGGER = Logger.getLogger("Client");

    private SimplifiedGame game;
    private BlockingQueue<Runnable> eventQueue = new LinkedBlockingQueue<>();
    private ClientConnection client;


    public ClientGameController(){
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            try{
                 eventQueue.take().run();  //bisogna sincronizzarla?

            }catch (InterruptedException e) {
            LOGGER.severe(e.getMessage());
            Thread.currentThread().interrupt();
           }
        }
    }

    public void openConnection(String name, int numberOfPlayer, String address, int port) {
        client = new ClientConnection(name,address,port,this);
        client.connectToServer(numberOfPlayer);
        game = new SimplifiedGame(numberOfPlayer);

    }

    public void onBackCommand(String name, int numberOfPlayer){
        game = new SimplifiedGame(numberOfPlayer);
        //inviare un altro messaggio   ma vedere se fare sincronizzati gli in e out

    }

    public void onUpdateLobbyPlayer(Message message) {
        client.setUserID(message.getMessage());
        game.initPlayers(client.getUserName(),((WaitPlayerMessage) message).getNickNames(),((WaitPlayerMessage) message).getColors());

        eventQueue.add(this::updateLobbyPlayer);
    }

    public List<Player> getPlayers(){
        return game.getPlayers();
    }

    public void nickUsed(Message message){
        client.setUserID(message.getMessage());
        eventQueue.add(this::nickUsed);
    }

    public void updateUserName(String userName){
        client.setUserName(userName);
        client.sendMessage(new GameConfigMessage(client.getUserID(),userName,MessageSubType.UPDATE,game.getNumberOfPlayers() ,false,false,false));
    }//gestire anche gli errori

    public void onGameStart(Message message){
        game.setGameStarted(true);
        game.setGameID(((GameStartedMessage) message).getGameID());
        game.setGameStatus(Response.GAMESTARTED);
        eventQueue.add(this::startGame);
    }

    public void onUpdate(Message message){  //farlo synchronized?
        switch (message.getType()){
            case WAITPLAYER:
                onUpdateLobbyPlayer(message);
                break;
            case CONFIG:
                nickUsed(message);
                break;
            case GAMESTART:
                onGameStart(message);
                break;
            default:

        }
    }
}
