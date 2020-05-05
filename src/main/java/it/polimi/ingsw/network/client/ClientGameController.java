package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.SimplifiedGame;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConfigLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public abstract class ClientGameController implements Runnable, FunctionListener{ //rimettere implements
    public static final Logger LOGGER = Logger.getLogger("Client");

    private SimplifiedGame game;
    private final BlockingQueue<Runnable> eventQueue = new LinkedBlockingQueue<>();
    private ClientConnection client;


    public ClientGameController(){
        ConfigLoader.loadSetting();    //farne uno solo per il client?
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
        return new ArrayList<>(game.getPlayers());
    }

    public synchronized void nickUsed(Message message){
        client.setUserID(message.getMessage());
        eventQueue.add(this::nickUsed);
    }

    public synchronized void updateNickName(String nickName){
        client.setNickName(nickName);
        client.sendMessage(new GameConfigMessage(client.getUserID(),nickName,MessageSubType.UPDATE,game.getNumberOfPlayers() ,false,false,false));
    }//gestire anche gli errori false ecc.

    public synchronized void onGameStart(Message message){
        game.setGameStarted(true);
        game.setGameID(((GameStartedMessage) message).getGameID());
        game.setGameStatus(Response.GAMESTARTED);
        eventQueue.add(this::startGame);
    }

    public synchronized List<String> getAvailableCards(){
        return game.getAvailableCards();
    }

    public synchronized void challengerResponse(String firstPlayer,List<String> cards){
        game.setAvailableCards(cards);
        client.sendMessage(new ChallengerChoiceMessage(client.getUserID(),client.getNickName(),MessageSubType.ANSWER,firstPlayer,cards));
    }

    public synchronized void cardChoiceResponse(String card){
        game.removeCard(card);
        client.sendMessage(new Message(client.getUserID(),MessageType.CHOOSECARD,MessageSubType.ANSWER,card));
    }

    public synchronized void placeWorkersResponse(int tile1,int tile2){
        client.sendMessage(new PlaceWorkersMessage(client.getUserID(),MessageSubType.ANSWER,game.getCoordinatesFromTile(tile1),
                game.getCoordinatesFromTile(tile2)));
    }

    public synchronized void endTurn(){
        client.sendMessage(new Message(client.getUserID(),client.getNickName(),MessageType.ENDTURN,MessageSubType.UPDATE));
    }

    public synchronized void updateCardChoice(Message message){

    }

    public synchronized void updatePlaceWorkers(Message message){

    }

    public synchronized void updateChallengerChoice(Message message){

    }

    public synchronized void handleChallengerChoice(Message message){
        if(message.getSubType().equals(MessageSubType.REQUEST)) {
            game.setCurrentPlayer(message.getMessage());
            eventQueue.add(() -> challengerChoice(message.getMessage(), true));
        }
        else if(message.getSubType().equals(MessageSubType.UPDATE)) {
            game.setCurrentPlayer(message.getMessage());
            eventQueue.add(() -> challengerChoice(message.getMessage(), false));
        }
        else{
            game.setAvailableCards(((ChallengerChoiceMessage) message).getCards());
        }
    }

    public synchronized void handleCardChoice(Message message){
        if(message.getSubType().equals(MessageSubType.REQUEST)) {
            game.setCurrentPlayer(message.getMessage());
            eventQueue.add(() -> cardChoice(message.getMessage(), true));
        }
        else if(message.getSubType().equals(MessageSubType.UPDATE)) {
            game.setCurrentPlayer(message.getMessage());
            eventQueue.add(() -> cardChoice(message.getMessage(), false));
        }
        else{
             game.removeCard(message.getMessage());
        }
    }

    public synchronized void handlePlaceWorkers(Message message){
        if(message.getSubType().equals(MessageSubType.REQUEST)) {
            game.setCurrentPlayer(message.getMessage());
            eventQueue.add(() -> placeWorker(message.getMessage(), true));
        }
        else if(message.getSubType().equals(MessageSubType.UPDATE)) {
            game.setCurrentPlayer(message.getMessage());
            eventQueue.add(() -> placeWorker(message.getMessage(), false));
        }
        else {
            //aggiornaree le posizioni dei workers
        }
    }

    public synchronized void handleDisconnection(Message message){
        client.closeConnection();
        LOGGER.info("lost connection");
        switch (message.getSubType()) {
            case TIMEENDED:
                eventQueue.add(this::onLobbyDisconnection);
                break;
            case PINGFAIL:
                eventQueue.add(this::onPingDisconnection);
                break;
            default:
        }
    }

    public synchronized void onUpdate(Message message){
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
            case DISCONNECTION:
                handleDisconnection(message);
                break;
            case CHALLENGERCHOICE:
                handleChallengerChoice(message);
                break;
            case CHOOSECARD:
                handleCardChoice(message);
                break;
            case PLACEWORKERS:
                handlePlaceWorkers(message);
                break;
            default:

        }
    }
}
