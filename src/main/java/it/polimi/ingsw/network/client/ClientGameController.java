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

    public synchronized void openConnection(String name, int numberOfPlayer, String address, int port) {
        game = new SimplifiedGame(numberOfPlayer);
        client = new ClientConnection(name,address,port,this);
        client.connectToServer(numberOfPlayer);
    }

    public synchronized void newGame(String nickName, int numberOfPlayer){
        game = new SimplifiedGame(numberOfPlayer);
        client.setNickName(nickName);
        client.sendMessage(new GameConfigMessage(client.getUserID(),nickName, MessageSubType.ANSWER,numberOfPlayer,false,false,false));
    }

    public synchronized void onBackCommand(){
        client.sendMessage(new Message(client.getUserID(),client.getNickName(),MessageType.DISCONNECTION, MessageSubType.BACK));
    }

    public synchronized void onUpdateLobbyPlayer(Message message) {
        client.setUserID(message.getMessage());
        game.initPlayers(client.getNickName(),((WaitPlayerMessage) message).getNickNames(),((WaitPlayerMessage) message).getColors());

        eventQueue.add(this::updateLobbyPlayer);
    }

    public synchronized List<Player> getPlayers(){
        return game.getPlayers();
    }

    public synchronized void nickUsed(Message message){
        client.setUserID(message.getMessage());
        eventQueue.add(this::nickUsed);
    }

    public synchronized void updateNickName(String nickName){
        client.setNickName(nickName);
        client.sendMessage(new GameConfigMessage(client.getUserID(),nickName,MessageSubType.UPDATE,game.getNumberOfPlayers() ,false,false,false));
    }//gestire anche gli errori

    public synchronized void onGameStart(Message message){
        game.setGameStarted(true);
        game.setGameID(((GameStartedMessage) message).getGameID());
        game.setGameStatus(Response.GAMESTARTED);
        eventQueue.add(this::startGame);
    }

    public synchronized void onUpdate(Message message){  //farlo synchronized?
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
