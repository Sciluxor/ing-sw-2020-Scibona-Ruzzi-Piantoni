package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.SimplifiedGame;
import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardLoader;
import it.polimi.ingsw.model.cards.CardSubType;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.WorkerName;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConfigLoader;
import it.polimi.ingsw.utils.FlowStatutsLoader;
import javafx.util.Pair;

import java.net.ConnectException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public abstract class ClientGameController implements Runnable, FunctionListener{
    public static final Logger LOGGER = Logger.getLogger("Client");

    private SimplifiedGame game;
    private final BlockingQueue<Runnable> eventQueue = new LinkedBlockingQueue<>();
    private ClientConnection client;


    public ClientGameController(){
        FlowStatutsLoader.loadFlow();
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
        game.getCurrentPlayer().setPower(game.getDeck().get(card));
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

    public synchronized void handleStartTurn(Message message){
        game.setGameStatus(game.getCurrentPlayer().getPower().getFirstAction());
        if(message.getSubType().equals(MessageSubType.REQUEST)) {
            game.setCurrentPlayer(message.getMessage());
            eventQueue.add(() -> startTurn(message.getMessage(), true));
        }
        else if(message.getSubType().equals(MessageSubType.UPDATE)) {
            game.setCurrentPlayer(message.getMessage());
            eventQueue.add(() -> startTurn(message.getMessage(), false));
        }
    }

    public synchronized void handleLoseEvent(){
        //da implementare
    }

    public synchronized List<Square> getModifiedsquare(){
        return game.getGameMap().getModifiedSquare();
    }

    public synchronized void addPermanentConstraint(Message message){
       game.getClientPlayer().setConstraint(game.getDeck().get(message.getMessage()));
    }

    public synchronized List<Integer>  availableWorkers(){
        List<Worker> workers = game.getCurrentPlayer().getWorkers();
        List<Integer> toSendWorkers = new ArrayList<>();
        for(Worker worker: workers){
            if(game.getCurrentPlayer().checkIfCanMove(game.getGameMap(),worker)){
                if(worker.getName().equals(WorkerName.WORKER1))
                    toSendWorkers.add(1);
                else
                    toSendWorkers.add(2);
            }
        }
        return toSendWorkers;
    }

    public synchronized void setWorker(int worker){
        game.getCurrentPlayer().setCurrentWorker(game.getCurrentPlayer().getWorkers().get(worker-1));
        client.sendMessage(new Message(client.getUserID(),MessageType.WORKERCHOICE,MessageSubType.ANSWER,worker==1 ? "worker1" : "worker2"));
        availableActions();
    }

    public synchronized void availableActions(){
        ArrayList<MessageType> actions = FlowStatutsLoader.getNextMessageFromStatus(game.getGameStatus());
        ArrayList<MessageType> toRemoveActions = new ArrayList<>();
        for(MessageType action: actions){
            if(action.equals(MessageType.MOVEWORKER)){
                if(game.getCurrentPlayer().findWorkerMove(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker()).size() <= 0)
                    toRemoveActions.add(action);
            }
            else if(action.equals(MessageType.BUILDWORKER)){
                if(game.getCurrentPlayer().findPossibleBuild(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker()).size() <= 0)
                    toRemoveActions.add(action);
            }
        }

        for(MessageType toRemove :toRemoveActions)
            actions.remove(toRemove);

        eventQueue.add(() -> displayActions(actions));
    }

    public synchronized void mapNextAction(Response winStatus){
        if(winStatus.equals(Response.WIN) || winStatus.equals(Response.BUILDWIN)){       //problema per la win, bisogna cambiare l'ordine delle chiamate
            eventQueue.add(() -> notifyWin(game.getWinner().getNickName()));
        }
        else{
            availableActions();
        }
    }

    public synchronized List<Integer> availableMoveSquare(){
        List<Directions> directions = game.getCurrentPlayer().findWorkerMove(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker());
        List<Integer> availableTile = new ArrayList<>();
        for(Directions direction: directions){
            availableTile.add(game.getCurrentPlayer().getCurrentWorker().getBoardPosition().getCanAccess().get(direction));
        }
        return availableTile;
    }

    public synchronized Response moveWorker(int tile){
        Directions direction = null;
        for(Directions possibleDirection : Directions.values()){
            if(game.getCurrentPlayer().getCurrentWorker().getBoardPosition().getCanAccess().get(possibleDirection) == tile) {
                direction = possibleDirection;
                break;
            }
        }
        Response newStatus = game.getCurrentPlayer().executeWorkerMove(game.getGameMap(),direction);
        game.setGameStatus(newStatus);
        Response winStatus = checkMoveVictory();
        client.sendMessage(new MoveWorkerMessage(client.getUserID(),client.getNickName(),direction,
                winStatus,game.getWinner(),game.getGameMap().getModifiedSquare()));

        return winStatus;

    }



    public synchronized Response checkMoveVictory(){
        Response response = game.getCurrentPlayer().checkVictory(game.getGameMap());
        if(response.equals(Response.WIN)) {
            for (Card constraint : game.getCurrentPlayer().getConstraint()) {
                if (constraint.getType().equals(CardType.MOVEVICTORY) && !constraint.getSubType().equals(CardSubType.NORMAL) &&
                        !constraint.isValidVictory(game.getGameMap(), game.getCurrentPlayer().getCurrentWorker()))
                    response = Response.NOTWIN;
            }
        }

        if(response.equals(Response.WIN)) {
            game.setWinner(game.getCurrentPlayer());
            game.setHasWinner(true);
        }

        return response;
    }

    public synchronized List<Integer> availableBuildSquare(){
        List<Directions> directions = game.getCurrentPlayer().findPossibleBuild(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker());
        List<Integer> availableTile = new ArrayList<>();
        for(Directions direction: directions){
            availableTile.add(game.getCurrentPlayer().getCurrentWorker().getBoardPosition().getCanAccess().get(direction));
        }
        return availableTile;
    }

    public synchronized Response buildWorker(int tile, Building building){
        Directions direction = null;
        for(Directions possibleDirection : Directions.values()){
            if(game.getCurrentPlayer().getCurrentWorker().getBoardPosition().getCanAccess().get(possibleDirection) == tile) {
                direction = possibleDirection;
                break;
            }
        }
        Response newStatus = game.getCurrentPlayer().executeBuild(game.getGameMap(),building,direction);
        game.setGameStatus(newStatus);
        Response winStatus = checkBuildVictory();
        client.sendMessage(new BuildWorkerMessage(client.getUserID(),client.getNickName(),direction
                ,building,winStatus,game.getWinner(),game.getGameMap().getModifiedSquare()));
        if(newStatus.equals(Response.BUILD))
            removeNonPermanentConstraint();
        return winStatus;
    }

    public synchronized Response checkBuildVictory(){
        Response response = Response.NOTWIN;
        for(Player player: game.getPlayers()){
            if(player.getPower().getType().equals(CardType.BUILDVICTORY) && player.getPower().getSubType().equals(CardSubType.NORMAL)){
                response = player.checkVictory(game.getGameMap());
                if(response.equals(Response.BUILDWIN)) {
                    game.setWinner(player);
                    game.setHasWinner(true);
                }
            }
        }
        return response;
    }

    public synchronized void removeNonPermanentConstraint(){
            ArrayList<Card> nonPermanentConstraint = new ArrayList<>();
            for(Card constraint : game.getCurrentPlayer().getConstraint()){
                if(constraint.getSubType().equals(CardSubType.NONPERMANENTCONSTRAINT))
                    nonPermanentConstraint.add(constraint);
            }

            for(Card constraint : nonPermanentConstraint){
                game.getCurrentPlayer().removeConstraint(constraint);
            }
        game.setGameStatus(Response.ENDTURN);
    }


    public synchronized void handleUpdateBoard(Message message){
        List<Square> modifiedSquares = new ArrayList<>();
        if(message.getType().equals(MessageType.MOVEWORKER) && message.getSubType().equals(MessageSubType.UPDATE))
            modifiedSquares = ((MoveWorkerMessage) message).getModifiedSquare();
        else if(message.getType().equals(MessageType.BUILDWORKER) && message.getSubType().equals(MessageSubType.UPDATE))
            modifiedSquares = ((BuildWorkerMessage) message).getModifiedSquare();

        List<Square> finalModifiedSquares = modifiedSquares;

        for(Square square: finalModifiedSquares){
            game.copySquare(game.getGameMap().getMap().get(square.getTile() -1),square);
        }

        eventQueue.add(() -> updateBoard(message.getNickName(), finalModifiedSquares,message.getType()));
    }

    public synchronized void addNonPermanentConstraint(Message message){
        game.getClientPlayer().setConstraint(game.getDeck().get(message.getMessage()));
        eventQueue.add(() -> addConstraint(message.getMessage()));
    }

    public synchronized void handleDisconnection(Message message){
        client.stopPingTimer();
        client.closeConnection();
        LOGGER.info("lost connection");
        switch (message.getSubType()) {
            case TIMEENDED:
                if(game.isGameStarted())
                    eventQueue.add(this::onTurnDisconnection);
                else
                    eventQueue.add(this::onLobbyDisconnection);
                break;
            case PINGFAIL:
                eventQueue.add(this::onPingDisconnection);
                break;
            default:
        }
    }

    public synchronized void sendChatMessage(String chatMessage){
        client.sendMessage(new Message(client.getUserID(),client.getNickName(),MessageType.CHAT,MessageSubType.UPDATE,chatMessage));
    }

    public synchronized void handleChatMessage(Message message){
        eventQueue.add(() -> newChatMessage(message.getNickName(),message.getMessage()));
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
            case STARTTURN:
                handleStartTurn(message);
                break;
            case MOVEWORKER:
            case BUILDWORKER:
                handleUpdateBoard(message);
                break;
            case NONPERMCONSTRAINT:
                addNonPermanentConstraint(message);
                break;
            case PERMCONSTRAINT:
                addPermanentConstraint(message);   //mancano i case di WIN e LOSE
                break;
            case CHAT:
                handleChatMessage(message);
                break;
            default:
        }
    }
}
