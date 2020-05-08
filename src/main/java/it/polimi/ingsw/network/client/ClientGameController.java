package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.SimplifiedGame;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConfigLoader;

import java.net.ConnectException;


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

    public synchronized void openConnection(String name, int numberOfPlayer, String address, int port) throws ConnectException {
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
        game.getCurrentPlayer().setPower(CardLoader.loadCards().get(card));
        client.sendMessage(new Message(client.getUserID(),MessageType.CHOOSECARD,MessageSubType.ANSWER,card));
    }

    public synchronized void placeWorkersResponse(int tile1,int tile2){
        game.placeWorkersOnMap(tile1,tile2);
        client.sendMessage(new PlaceWorkersMessage(client.getUserID(),MessageSubType.ANSWER,game.getCoordinatesFromTile(tile1),
                game.getCoordinatesFromTile(tile2)));
    }

    public synchronized void cliPlaceWorkersResponse(Integer[] tile1,Integer[] tile2){

    }

    public synchronized void endTurn(){
        client.sendMessage(new Message(client.getUserID(),client.getNickName(),MessageType.ENDTURN,MessageSubType.UPDATE));
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
             game.getCurrentPlayer().setPower(CardLoader.loadCards().get(message.getMessage()));
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
            int tile1 = game.getGameMap().getTileFromCoordinates(((PlaceWorkersMessage) message).getTile1()).getTile();
            int tile2 = game.getGameMap().getTileFromCoordinates(((PlaceWorkersMessage) message).getTile2()).getTile();
            game.placeWorkersOnMap(tile1,tile2);
            eventQueue.add(() -> updatePlacedWorkers(game.getGameMap().getModifiedSquare()));
        }
    }

    public synchronized void handleStartTurn(){
        //modificare lo stato del gioco
        //chimare la start turn, acnhe per gli altri client
        //il controllo se ha perso lo fa il server
    }

    public synchronized void availableWorkers(){
        //mandarti i workers che possono muoversi
    }

    public synchronized void availableActions(int worker){
        //ritornare le azioni disponibili per quel worker, fare il controllo se può muoversi o costruire
        //e settare nel game il worker scelto
        //chiamare display actions
    }

    public synchronized void availableMoveSquare(){
        //ritornare una lista con il numero di caselle dove può muoversi
    }

    public synchronized void moveWorker(int tile){
        //prendersi la direzione dalla tile
        //muovo il worker sul client cambiando lo stato del gioco
        //vedo se ha vinto
        //invio il messaggio al server
        //se ha vinto chiamo notifyWin sennò chiamo display actions
    }

    public synchronized void availableBuildSquare(){

    }

    public synchronized void buildWorker(int tile, Building building){
        //prendersi la direzione dalla tile, mi serve anche il building
        //buildo il worker sul client cambiando lo stato del gioco
        //vedo se ha vinto qualcuno con crono
        //invio il messaggio al server
        //se ha vinto chiamo notifyWin sennò chiamo display actions
    }

    public synchronized void handleUpdateBoard(){

    }

    public synchronized void handleDisconnection(Message message){
        client.stopPingTimer();
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
