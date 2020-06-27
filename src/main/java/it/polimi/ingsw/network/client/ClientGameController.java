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
import it.polimi.ingsw.model.player.TurnStatus;
import it.polimi.ingsw.model.player.Worker;
import it.polimi.ingsw.model.player.WorkerName;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.ConfigLoader;
import it.polimi.ingsw.utils.FlowStatutsLoader;
import java.net.ConnectException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * Abstract Class that represents the Controller client side that handle all the logic functions, extended by both CLI and GUI
 * @author alessandroruzzi
 * @version 1.0
 * @since 2020/06/20
 */

public abstract class ClientGameController implements Runnable, FunctionListener{
    public static final Logger LOGGER = Logger.getLogger("Client");

    private SimplifiedGame game;
    private final BlockingQueue<Runnable> eventQueue = new LinkedBlockingQueue<>();
    private ClientConnection client;

    /**
     * Public constructor for the Client Game Controller, start the thread that run the functions added to the queue, and load configuration parameters
     */

    public ClientGameController(){
        FlowStatutsLoader.loadFlow();
        ConfigLoader.loadSetting();
        new Thread(this).start();
    }

    /**
     * Thread that run all the functions that it finds on the eventQueue
     */

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

    /**
     * Function that open the connection with the server, sending game configuration parameters
     * @param name Nickname inserted by the Client
     * @param numberOfPlayer Number of Player Selected by the Client(2/3)
     * @param address IP address of the Server
     * @param port Port used by the Server
     * @throws ConnectException ConnectException
     */

    public synchronized void openConnection(String name, int numberOfPlayer, String address, int port) throws ConnectException {
        game = new SimplifiedGame(numberOfPlayer);
        client = new ClientConnection(name,address,port,this);
        client.connectToServer(numberOfPlayer);
    }

    /**
     * Function that handle the "back" command, send to the Server the new nickname and new number of player
     * @param nickName The new Nickname selected by the Client
     * @param numberOfPlayer New number of player selected by the Client
     */

    public synchronized void newGame(String nickName, int numberOfPlayer){
        game = new SimplifiedGame(numberOfPlayer);
        client.setNickName(nickName);
        client.sendMessage(new GameConfigMessage(client.getUserID(),nickName, MessageSubType.ANSWER,numberOfPlayer));
    }

    /**
     * Function called when the Client exit from the lobby and go back to nickname and number of player selection(back command), notify the Server
     */

    public synchronized void onBackCommand(){
        client.sendMessage(new Message(client.getUserID(),client.getNickName(),MessageType.DISCONNECTION, MessageSubType.BACK));
    }

    /**
     * Function that handle new players inserted in lobby
     * @param message Message containing all the players in the lobby with their colors
     */

    public synchronized void onUpdateLobbyPlayer(Message message) {
        client.setUserID(message.getMessage());
        game.initPlayers(client.getNickName(),((WaitPlayerMessage) message).getNickNames(),((WaitPlayerMessage) message).getColors());

        eventQueue.add(this::updateLobbyPlayer);
    }

    /**
     * Get Players in the Game
     * @return the Players in the Game
     */

    public synchronized List<Player> getPlayers(){
        return new ArrayList<>(game.getPlayers());
    }

    /**
     * Function called when the nickname selected is already in use in the game
     * @param message Message received from the Server
     */

    public synchronized void nickUsed(Message message){
        client.setUserID(message.getMessage());
        eventQueue.add(this::nickUsed);
    }

    /**
     * Function that update the NickName of the client, if the old one was already in use, and also notify the Server
     * @param nickName The new NickName selected by the Client
     */

    public synchronized void updateNickName(String nickName){
        client.setNickName(nickName);
        client.sendMessage(new GameConfigMessage(client.getUserID(),nickName,MessageSubType.UPDATE,game.getNumberOfPlayers()));
    }

    /**
     * Function that handle the setup when the game is starting (lobby full)
     * @param message Message received from the Server
     */

    public synchronized void onGameStart(Message message){
        game.setGameStarted(true);
        game.setGameID(((GameStartedMessage) message).getGameID());
        game.setGameStatus(Response.GAMESTARTED);
        eventQueue.add(this::startGame);
    }

    /**
     * Get The remaining cards, from the ones selected by the challenger (to chose a card)
     * @return The remaining cards
     */

    public synchronized List<String> getAvailableCards(){
        return game.getAvailableCards();
    }

    /**
     * Function that notify the Server about the choice of the challenger
     * @param firstPlayer The name of the first player selected by the challenger
     * @param cards The list of cards selected by the challenger
     */

    public synchronized void challengerResponse(String firstPlayer,List<String> cards){
        game.setAvailableCards(cards);
        client.sendMessage(new ChallengerChoiceMessage(client.getUserID(),client.getNickName(),MessageSubType.ANSWER,firstPlayer,cards));
    }

    /**
     * Function that notify the Server about the card selected by the client
     * @param card The name of the card selected by the client
     */

    public synchronized void cardChoiceResponse(String card){
        game.removeCard(card);
        game.getCurrentPlayer().setPower(game.getDeck().get(card));
        client.sendMessage(new Message(client.getUserID(),MessageType.CHOOSECARD,MessageSubType.ANSWER,card));
    }

    /**
     * Function that notify the Server about the tile in which tha Client want to put his workers
     * @param tile1 Tile of the first worker
     * @param tile2 Tile of the second worker
     */

    public synchronized void placeWorkersResponse(int tile1,int tile2){
        game.placeWorkersOnMap(tile1,tile2);
        client.sendMessage(new PlaceWorkersMessage(client.getUserID(),MessageSubType.ANSWER,game.getCoordinatesFromTile(tile1),
                game.getCoordinatesFromTile(tile2)));
    }

    /**
     * Get The level of Building in a specific tile
     * @param tile The number of the tile that we want to analise
     * @return The level of building
     */

    public synchronized int getLevel(int tile){
       return game.getGameMap().getMap().get(tile-1).getBuildingLevel();
    }

    /**
     * Function that notify the Server when the Client ends his turn
     */

    public synchronized void endTurn(){
        if(game.getCurrentPlayer().getPower() != null)
            game.getCurrentPlayer().getPower().resetCard();

        game.getCurrentPlayer().setTurnStatus(TurnStatus.IDLE);
        client.sendMessage(new Message(client.getUserID(),client.getNickName(),MessageType.ENDTURN,MessageSubType.UPDATE));
    }

    /**
     * Function that handle the Challenger choice phase and notify the other Clients about the choice
     * @param message Message received from the Server
     */

    public synchronized void handleChallengerChoice(Message message){
        if(message.getSubType().equals(MessageSubType.REQUEST)) {
            boolean isYourPlayer = message.getMessage().equals(client.getNickName());
            game.setCurrentPlayer(message.getMessage());
            eventQueue.add(() -> challengerChoice(message.getMessage(),isYourPlayer));
        }
        else{
            if(!game.getCurrentPlayer().getNickName().equals(client.getNickName()))
                game.setAvailableCards(((ChallengerChoiceMessage) message).getCards());
        }
    }

    /**
     * Function that handle the Card choice phase and notify the other Clients about the choice
     * @param message Message received from the Server
     */

    public synchronized void handleCardChoice(Message message){
        if(message.getSubType().equals(MessageSubType.REQUEST)) {
            boolean isYourPlayer = message.getMessage().equals(client.getNickName());
            game.getCurrentPlayer().setTurnStatus(TurnStatus.IDLE);
            game.setCurrentPlayer(message.getMessage());
            game.getCurrentPlayer().setTurnStatus(TurnStatus.PLAYTURN);
            eventQueue.add(() -> cardChoice(message.getMessage(), isYourPlayer));
        }
        else{
            if(!game.getCurrentPlayer().getNickName().equals(client.getNickName())) {
                game.getCurrentPlayer().setPower(CardLoader.loadCards().get(message.getMessage()));
                game.removeCard(message.getMessage());
            }
        }
    }

    /**
     * Function that handle the Place Workers phase and notify the other Clients about the choice
     * @param message Message received from the Server
     */

    public synchronized void handlePlaceWorkers(Message message){
        if(message.getSubType().equals(MessageSubType.REQUEST)) {
            boolean isYourPlayer = message.getMessage().equals(client.getNickName());
            game.getCurrentPlayer().setTurnStatus(TurnStatus.IDLE);
            game.setCurrentPlayer(message.getMessage());
            game.getCurrentPlayer().setTurnStatus(TurnStatus.PLAYTURN);
            eventQueue.add(() -> placeWorker(message.getMessage(), isYourPlayer));
        }
        else {
            if(!game.getCurrentPlayer().getNickName().equals(client.getNickName())) {
                int tile1 = game.getGameMap().getTileFromCoordinates(((PlaceWorkersMessage) message).getTile1()).getTile();
                int tile2 = game.getGameMap().getTileFromCoordinates(((PlaceWorkersMessage) message).getTile2()).getTile();
                game.placeWorkersOnMap(tile1, tile2);
                eventQueue.add(() -> updatePlacedWorkers(game.getGameMap().getModifiedSquare()));
            }
        }
    }

    /**
     * Function that notify that a client is starting his turn
     * @param message Message received from the Server
     */

    public synchronized void handleStartTurn(Message message){
        game.getCurrentPlayer().setTurnStatus(TurnStatus.IDLE);
        game.setCurrentPlayer(message.getMessage());
        game.setGameStatus(game.getCurrentPlayer().getPower().getFirstAction());
        game.getCurrentPlayer().setTurnStatus(TurnStatus.PLAYTURN);
        boolean isYourPlayer = message.getMessage().equals(client.getNickName());
        eventQueue.add(() -> startTurn(message.getMessage(), isYourPlayer));
    }

    /**
     * Get the Squares modified by a move,a build or after the workers are placed
     * @return the Squares modified by a move,a build or after the workers are placed
     */

    public synchronized List<Square> getModifiedsquare(){
        return game.getGameMap().getModifiedSquare();
    }

    /**
     * Function that check which workers of the Client is able to do a move and a build
     * @return The workers that are not blocked
     */

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

    /**
     * Function called to set as Current worker the worker selected by the Client
     * @param worker The worker selected by the Client
     */

    public synchronized void setWorker(int worker){
        game.getCurrentPlayer().setCurrentWorker(game.getCurrentPlayer().getWorkers().get(worker-1));
        client.sendMessage(new Message(client.getUserID(),MessageType.WORKERCHOICE,MessageSubType.ANSWER,worker==1 ? "worker1" : "worker2"));
        availableActions();
    }

    /**
     * Function that create a list of all the available actions for the client(move,build,end turn),and show them to the client
     */

    public synchronized void availableActions(){
        List<MessageType> actions = FlowStatutsLoader.getNextMessageFromStatus(game.getGameStatus());
        List<MessageType> toRemoveActions = new ArrayList<>();
        for(MessageType action: actions){
            if((action.equals(MessageType.MOVEWORKER) && game.getCurrentPlayer().findWorkerMove(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker()).isEmpty()) ||
                    (action.equals(MessageType.BUILDWORKER) && game.getCurrentPlayer().findPossibleBuild(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker()).isEmpty())){

                    toRemoveActions.add(action);
            }
        }

        for(MessageType toRemove :toRemoveActions) {
            actions.remove(toRemove);
        }

        eventQueue.add(() -> displayActions(actions));
    }

    /**
     * Function that decide what will be the next function to call
     * @param winStatus Parameter to check if there is a winner
     */

    public synchronized void mapNextAction(Response winStatus){
        if(winStatus.equals(Response.WIN) || winStatus.equals(Response.BUILDWIN)){
            eventQueue.add(() -> notifyWin(game.getWinner().getNickName()));
        }
        else{
            availableActions();
        }
    }

    /**
     * Function that calculate all the available squares in which a specific worker of the client can move
     * @return A list of the available square
     */

    public synchronized List<Integer> availableMoveSquare(){
        List<Directions> directions = game.getCurrentPlayer().findWorkerMove(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker());
        for (Card constraint : game.getCurrentPlayer().getConstraint()) {
            if (constraint.getType().equals(CardType.YOURMOVE) && !constraint.getSubType().equals(CardSubType.NORMAL))
                directions = constraint.eliminateInvalidMove(game.getGameMap(), game.getCurrentPlayer().getCurrentWorker(), directions);
        }
        List<Integer> availableTile = new ArrayList<>();
        for(Directions direction: directions){
            availableTile.add(game.getCurrentPlayer().getCurrentWorker().getBoardPosition().getCanAccess().get(direction));
        }
        return availableTile;
    }

    /**
     * Function that move a worker in the map
     * @param tile The number of the tile in which the Client wants to move the worker
     * @return The winResponse
     */

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

    /**
     * Function that check if the Client has won after a move
     * @return The winResponse
     */

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

    /**
     * Function that calculate all the available squares in which a specific worker of the client can build
     * @return A list of the available square
     */

    public synchronized List<Integer> availableBuildSquare(){
        List<Directions> directions = game.getCurrentPlayer().findPossibleBuild(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker());
        List<Integer> availableTile = new ArrayList<>();
        for(Directions direction: directions){
            availableTile.add(game.getCurrentPlayer().getCurrentWorker().getBoardPosition().getCanAccess().get(direction));
        }
        return availableTile;
    }

    /**
     * Function that build in the map
     * @param tile The number of the tile in which the Client wants to build
     * @param building The type of building to build
     * @return The winResponse
     */

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

    /**
     * Function that check if the Client has won after a build
     * @return The winResponse
     */

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
        if(response.equals(Response.NOTWIN))
            response = Response.NOTBUILDWIN;
        return response;
    }

    /**
     * Function that remove a constraint from the Client
     */

    public synchronized void removeNonPermanentConstraint(){
            ArrayList<Card> nonPermanentConstraint = new ArrayList<>();
            for(Card constraint : game.getCurrentPlayer().getConstraint()){
                if(constraint.getSubType().equals(CardSubType.NONPERMANENTCONSTRAINT))
                    nonPermanentConstraint.add(constraint);
            }

            for(Card constraint : nonPermanentConstraint){
                game.getCurrentPlayer().removeConstraint(constraint);
                eventQueue.add(() -> removeConstraint(constraint.getName()));
            }
        game.setGameStatus(Response.ENDTURN);
    }

    /**
     * Function that update the board after a move,build of other Clients
     * @param message Message received from the Server, with the modified square
     */

    public synchronized void handleUpdateBoard(Message message){
        if(!message.getNickName().equals(client.getNickName())) {
            List<Square> modifiedSquares = new ArrayList<>();
            List<Square> toSendSquare = new ArrayList<>();
            if (message.getType().equals(MessageType.MOVEWORKER) && message.getSubType().equals(MessageSubType.UPDATE))
                modifiedSquares = ((MoveWorkerMessage) message).getModifiedSquare();
            else if (message.getType().equals(MessageType.BUILDWORKER) && message.getSubType().equals(MessageSubType.UPDATE))
                modifiedSquares = ((BuildWorkerMessage) message).getModifiedSquare();

            List<Square> finalModifiedSquares = modifiedSquares;

            for (Square square : finalModifiedSquares) {
                game.copySquare(game.getGameMap().getMap().get(square.getTile() - 1), square);
                toSendSquare.add(game.getGameMap().getMap().get(square.getTile() - 1));
            }

            eventQueue.add(() -> updateBoard(message.getNickName(), toSendSquare, message.getType()));
        }
    }

    /**
     * Function that add permanent constraint to the Client
     * @param message Message received from the Server, with the name of the constraint card
     */

    public synchronized void addPermConstraint(Message message){
        if(client.getNickName().equals(message.getNickName())) {
            game.getClientPlayer().setConstraint(game.getDeck().get(message.getMessage()));
            eventQueue.add(() -> addConstraint(message.getMessage()));
        }
    }

    /**
     * Function that add non permanent constraint to the Client
     * @param message Message received from the Server, with the name of the constraint card
     */

    public synchronized void addNonPermConstraint(Message message){
        if(!game.getClientPlayer().getPower().getName().equals(message.getMessage())) {
            game.getClientPlayer().setConstraint(game.getDeck().get(message.getMessage()));
            eventQueue.add(() -> addConstraint(message.getMessage()));
        }
    }

    /**
     * Function that handle disconnection events -> Ping timer, turn timer, lobby timer
     * @param message Message received from the Server, with the disconnection information
     */

    public synchronized void handleDisconnection(Message message){
        client.stopPingTimer();
        client.closeConnection();
        LOGGER.info("lost connection");
        switch (message.getSubType()) {
            case TIMEENDED:
                if (game.isGameStarted() && ( game.hasWinner() || game.hasStopper() ))
                    eventQueue.add(this::onEndGameDisconnection);
                else
                    eventQueue.add(this::onLobbyDisconnection);
                break;
            case PINGFAIL:
                eventQueue.add(this::onPingDisconnection);
                break;
            default:
        }
    }

    /**
     * Function that send a message to the other clients (through the Server)
     * @param chatMessage The message to send
     */

    public synchronized void sendChatMessage(String chatMessage){
        client.sendMessage(new Message(client.getUserID(),client.getNickName(),MessageType.CHAT,MessageSubType.UPDATE,chatMessage));
    }

    /**
     * Function that handle the chat messages received from other clients
     * @param message Message received from the Server, with the message of another client
     */

    public synchronized void handleChatMessage(Message message){
        eventQueue.add(() -> newChatMessage(message.getNickName(),message.getMessage()));
    }

    /**
     * Function that notify the Client that there is a winner
     * @param message Message received from the server, with the winner name
     */

    public synchronized void handleWin(Message message){
        if((message.getSubType().equals(MessageSubType.UPDATE) && !game.getCurrentPlayer().getNickName().equals(client.getNickName())) ||
                (message.getSubType().equals(MessageSubType.REQUEST))) {
            eventQueue.add(() -> notifyWin(message.getMessage()));
            game.setHasWinner(true);
        }
    }

    /**
     * Function that notify the Client that someone has lost (worker blocked or other reasons)
     * @param message Message received from the Server, with the name of the player that has lost
     */

    public synchronized void handleLose(Message message){
        game.getCurrentPlayer().setTurnStatus(TurnStatus.IDLE);
        game.setCurrentPlayer(message.getMessage());
        game.setGameStatus(game.getCurrentPlayer().getPower().getFirstAction());
        game.getCurrentPlayer().setTurnStatus(TurnStatus.PLAYTURN);
        game.removePlayerLose();
        boolean isYourPlayer = message.getMessage().equals(client.getNickName());
        eventQueue.add(() -> notifyLose(message.getMessage(), isYourPlayer));
    }

    /**
     * Function that notify the Client that the game has been stopped
     * @param message Message received from the Server, with the name of the player that left the game
     */

    public synchronized void handleGameStopped(Message message){
        game.setHasStopper(true);
        if(message.getSubType().equals(MessageSubType.UPDATE)){
            eventQueue.add(() -> onStoppedGame(message.getMessage()));
        }
        else if(message.getSubType().equals(MessageSubType.TIMEENDED)){
            eventQueue.add(() -> onTurnTimerEnded(message.getMessage()));
        }
        else if(message.getSubType().equals(MessageSubType.STOPPEDGAMEERROR)){
            boolean isYourPlayer = message.getMessage().equals(client.getNickName());
            eventQueue.add(() -> onErrorMessage(message.getMessage(),isYourPlayer));
        }
    }

    /**
     * Function that send a message to the server, if the client wants to left the game after he has lost
     */

    public synchronized void handleLoseExit(){
        client.sendMessage(new Message(client.getUserID(),client.getNickName(),MessageType.DISCONNECTION,MessageSubType.LOSEEXITREQUEST));
    }

    /**
     * Function that notify the Client if he send a message (except chat messages) during the turn of another client
     */

    public synchronized void handleNotYourTurn(){
        eventQueue.add(this::notYourTurn);
    }

    /**
     * Function called when the Client receive a new message from the Server, decide which function to call
     * @param message Message received from the Server
     */

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
                addNonPermConstraint(message);
                break;
            case PERMCONSTRAINT:
                addPermConstraint(message);
                break;
            case CHAT:
                handleChatMessage(message);
                break;
            case WIN:
                handleWin(message);
                break;
            case LOSE:
                handleLose(message);
                break;
            case STOPPEDGAME:
                handleGameStopped(message);
                break;
            case NOTYOURTURN:
                handleNotYourTurn();
                break;
            default:
                throw new IllegalStateException("wrong message type");
        }
    }
}
