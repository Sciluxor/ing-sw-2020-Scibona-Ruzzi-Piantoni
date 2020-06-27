package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.player.TurnStatus;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.server.VirtualView;
import java.util.*;

/**
 * Class that represents the Controller server side that handle initial and final phases of the game and the turn
 * @author alessandroruzzi
 * @version 1.0
 * @since 2020/06/26
 */

public class GameController implements Observer<Message> {

    protected Game game;
    protected final Map<String, VirtualView> clients;
    private Timer turnTimer = new Timer();
    private final RoundController roundController;

    /**
     * Public Constructor for the Game controller
     * @param numberOfPlayer Number of player of the game
     * @param gameID GameID of the game
     */

    public GameController(int numberOfPlayer,String gameID) {
        this.game = initializeGame(numberOfPlayer,gameID);
        this.clients = new HashMap<>();
        this.roundController = new RoundController(game);
    }

    /**
     * Function that insert new players in the game
     * @param message Message with the name of the new player, and his virtual View
     */

    public synchronized void handleNewPlayer(Message message) {
        VirtualView view = ((GameConfigMessage) message).getView();
        view.setYourTurn(true);
        String nick = message.getNickName();
        if(!game.addPlayer(new Player(nick),view)){
              nickUsed(view);
              return;
        }
        addPlayer(view,nick);
    }

    /**
     * Function that update the nickName of a player , if it was already in use
     * @param message Message with the new NickName
     */

    public synchronized void handleNewNickname(Message message){
        String nick = message.getNickName();
        VirtualView view = getViewFromUserID(message.getSender());
        view.setYourTurn(true);
        game.addObservers(view);

        if(!game.newNickName(new Player(nick))){
            nickUsed(view);
            return;
        }

        addPlayer(view,nick);
        Server.LOGGER.info("Inserted with a new Nickname -> || GameID: " + getGameID() + " || UserID: "+ message.getSender() + " || NickName: " + message.getNickName());

    }

    /**
     * Function that do the setup for the new player
     * @param view VirtualView of the new player
     * @param nick NickName of the new player
     */

    public synchronized void addPlayer(VirtualView view, String nick){
        addNick(view,nick);
        game.setGameStatus(Response.PLAYERADDED);
        view.getConnection().setNickName(nick);
        view.setYourTurn(false);
        checkIfGameCanStart();
    }

    /**
     * Function that notify the Server that the nickName is already used
     * @param view The virtualView of the client
     */

    public synchronized void nickUsed(VirtualView view){
        game.setGameStatus(Response.NICKUSED);
        game.removeObserver(view);
        view.setYourTurn(false);
    }

    /**
     * Function that check if the lobby is full, and if the game can starts
     */

    public void checkIfGameCanStart(){
        if(game.getPlayers().size() == game.getNumberOfPlayers() && game.getConfigPlayer() == 0) {
            game.setGameStarted(true);

            for(VirtualView values :clients.values()){
             values.setYourTurn(true);
            }

            game.setGameStatus(Response.GAMESTARTED);

            for(VirtualView values :clients.values()){
                values.setYourTurn(false);
            }

            handleMatchBeginning();
        }
    }

    /**
     * Get the Players in the game
     * @return A list of the Actual players in the game
     */

    public synchronized List<Player> getActualPlayers(){

        return game.getPlayers();
    }

    /**
     * Get the Player that is playing his turn
     * @return The current player
     */

    public synchronized Player getCurrentPlayer(){
        return game.getCurrentPlayer();
    }

    /**
     * Function that check if the game is started
     * @return True if the game is started,false otherwise
     */

    public synchronized boolean isGameStarted(){
        return game.isGameStarted();
    }

    /**
     * Function that create a new Game
     * @param numberOfPlayers Number of players of the new Game
     * @param gameID GameID of the new Game
     * @return The Game created
     */

    public synchronized Game initializeGame(int numberOfPlayers, String gameID){

        return new Game(numberOfPlayers,gameID);
    }

    /**
     * Get the most recent players that has lost
     * @return The most recent players that has lost
     */

    public synchronized String getLastLosePlayer(){
        return game.getLastLosePlayer();
    }

    /**
     * Get the list of all the players that have lost
     * @return The list of all the players that have lost
     */

    public synchronized List<Player> getLosePlayers(){
        return game.getLosePlayers();
    }

    /**
     * Get the Available cards, from the ones selected by the Challenger
     * @return The Available Cards
     */

    public synchronized List<String> getAvailableCards(){
        return game.getAvailableCards();
    }

    /**
     * Function that check if there is a winner
     * @return True if there is a winner, false otherwise
     */

    public synchronized boolean hasWinner(){
        return game.hasWinner();
    }

    /**
     * Function that add the player and his virtualView in the hashmap
     * @param view VirtualView of the new player
     * @param nickName NickName of the new player
     */

    public synchronized void addNick(VirtualView view,String nickName){
        clients.put(nickName,view);
    }

    /**
     * Function that add the UserID of the player and his virtualView in the hashmap
     * @param view VirtualView of the new player
     * @param userID UserID of the new player
     */

    public synchronized void addUserID(VirtualView view,String userID){
        clients.put(userID,view);
    }

    /**
     * Get the VirtualView of the client given his userID
     * @param userID UserID of the player
     * @return The VirtualView of the player
     */

    public VirtualView getViewFromUserID(String userID){
        return clients.get(userID);
    }

    /**
     * Get the VirtualView of the client given his nickname
     * @param nick NickName of the player
     * @return The VirtualView of the player
     */

    public VirtualView getViewFromNickName(String nick){
        return clients.get(nick);
    }

    /**
     * Get the squares modified by a move or a build
     * @return The Squares modified
     */

    public synchronized List<Square> getModifiedSquares(){
        return  game.getGameMap().getModifiedSquare();
    }

    /**
     * Function that check if the game is full
     * @return True if the game is full, false otherwise
     */

    public synchronized boolean isFull(){
        return (game.getPlayers().size()+game.getConfigPlayer()) == game.getNumberOfPlayers();
    }

    /**
     * Function that check if a specific player is still in the game or if he has lost
     * @param nickName NickName of the player to search for
     * @return True if the player is still in the game, false otherwise
     */

    public synchronized boolean isStillInGame(String nickName){
        for(Player player: getActualPlayers()){
            if(player.getNickName().equals(nickName))
                return true;
        }
        return false;
    }

    /**
     * Get the name of player that has left the game
     * @return The name of the player that has left the game
     */

    public synchronized String getStopper(){
        return game.getStopper();
    }

    /**
     * Function that check if someone has left the game
     * @return True if someone has left the game, false otherwise
     */

    public synchronized boolean hasStopper(){return game.hasStopper();}

    /**
     * Get the name of the winner
     * @return The name of the winner
     */

    public String getWinner(){
        return game.getWinner().getNickName();
    }

    /**
     * Get the GameID
     * @return The GameID
     */

    public String getGameID(){
        return game.getGameID();
    }

    /**
     * Get the number of players of the game
     * @return The number of players of the game
     */

    public int getNumberOfPlayers(){
        return game.getNumberOfPlayers();
    }

    /**
     * Function that stop the Game if someone left the game
     * @param stopper The nickName of the player that left the game
     * @param newStatus The Game Status to set
     */

    public synchronized void stopStartedGame(String stopper,Response newStatus){

        game.setStopper(stopper);
        game.setHasStopper(true);
        game.setGameStatus(newStatus);
        stopRoundTimer();

        for(Player player :getActualPlayers()){
            VirtualView playerView = removeViewFromGame(player.getNickName());
            resetPlayer(playerView);
        }

    }

    /**
     * Function that reset a client (nickname,userID...) and remove the observers
     * @param playerView The VirtualView of the Client to reset
     */

    public synchronized void resetPlayer(VirtualView playerView){
        stopRoundTimer();
        playerView.getConnection().setUserID(ConstantsContainer.USERDIDDEF);
        playerView.getConnection().setNickName(ConstantsContainer.NICKDEF);
        if(!game.hasWinner()) {
            playerView.removeObserver(this);
            game.removeObserver(playerView);
        }
        if(playerView.getConnection().isConnectionActive()) {
            playerView.getConnection().startLobbyTimer();
        }
    }

    /**
     * Get UserID given the player
     * @param player Player that we want to analise
     * @return The UserID of the specific player
     */

    public synchronized String  getUserIDFromPlayer(Player player){
        return getViewFromNickName(player.getNickName()).getConnection().getUserID();
    }

    /**
     * Function called when the lobby timer end,remove the player from the lobby
     * @param message Message received, with disconnection information
     */

    public synchronized void handleLobbyTimerEnded(Message message){
        VirtualView view = clients.get(message.getSender());
        game.removeConfigPlayer();
        clients.remove(message.getSender());
        game.removeObserver(view);
        view.removeObserver(this);
    }

    /**
     * Function that eliminate a player from the board and the game when he lose
     */

    public synchronized void eliminatePlayer(){
        VirtualView view = clients.get(getCurrentPlayer().getNickName());
        view.setYourTurn(false);
        removePlayerFromBoard();
        checkIfStillCorrectGame();
    }

    /**
     * Function that remove The Virtual View of the Player from the controller
     * @param nickName The Nickname of the player to remove
     * @return The Virtual View removed
     */

    public synchronized VirtualView removeViewFromGame(String nickName){
        VirtualView view = clients.get(nickName);
        view.setYourTurn(false);
        clients.remove(nickName);
        clients.remove(view.getConnection().getUserID());
        return view;
    }

    /**
     * Function that remove the workers of the player from the board, and change the current player
     */

    public synchronized void removePlayerFromBoard(){
        game.removePlayerLose();
        VirtualView newView = clients.get(getCurrentPlayer().getNickName());
        newView.setYourTurn(true);
    }

    /**
     * Function that check if the game can continue, called if a player gets eliminated
     */

    public synchronized void checkIfStillCorrectGame(){
        int numberOfPlayer = game.getPlayers().size();
        if( numberOfPlayer >= ConstantsContainer.MINPLAYERLOBBY && numberOfPlayer <= game.getNumberOfPlayers()){
            game.setGameStatus(Response.PLAYERLOSE);
            startRoundTimer();
            handleTurnBeginning();
        }
        else{
            game.setWinner(getCurrentPlayer());
            game.setHasWinner(true);
            game.setGameStatus(Response.LOSEWIN);
        }
    }

    /**
     * Function that disconnect the player from the lobby (before the game is started)
     * @param message Message received, with disconnection information
     */

    public synchronized void disconnectPlayerBeforeGameStart(Message message) {
        VirtualView view = clients.get(message.getSender());
        view.setYourTurn(false);

        game.removeObserver(view);
        clients.remove(message.getSender());


        if (message.getSubType().equals(MessageSubType.NICKMAXTRY) || message.getSubType().equals(MessageSubType.STOPPEDGAMEERROR)
              || view.getConnection().getNickName().equalsIgnoreCase(ConstantsContainer.NICKDEF)) {
            game.removeConfigPlayer();
        }
        else {
            game.removeSettedPlayer(message.getNickName());
            clients.remove(message.getNickName());
            game.setGameStatus(Response.REMOVEDPLAYER);
        }

    }

    /**
     * Function that check if a nickName is already in use or if it is available
     * @param nick NickName to check
     * @return True if the nickname if free, false otherwise
     */

    public synchronized boolean isFreeNick(String nick){
        List<Player> players = game.getPlayers();

        for(Player player : players){
            if(player.getNickName().equals(nick))
                return false;
        }

        return true;
    }

    /**
     * Function that select the challenger and start the first turn
     */

    public synchronized void handleMatchBeginning(){
        Player challenger = game.pickChallenger();
        getViewFromNickName(challenger.getNickName()).setYourTurn(true);
        game.setGameStatus(Response.CHALLENGERCHOICE);
        startRoundTimer();
    }

    /**
     * Function that change the current player when the turn of a player end
     * @param message Message received from the Client
     */

    public synchronized void changeTurnPlayer(Message message){
        getViewFromNickName(message.getNickName()).setYourTurn(false);
        game.pickPlayer();
        getViewFromNickName(game.getCurrentPlayer().getNickName()).setYourTurn(true);
    }

    /**
     * Function that check if the player has lost, and then start the turn or eliminate the player
     */

    public synchronized void handleTurnBeginning() {
        if (!game.getCurrentPlayer().checkIfLoose(game.getGameMap())) {
              game.getCurrentPlayer().setTurnStatus(TurnStatus.PLAYTURN);
              game.setGameStatus(Response.STARTTURN);
        } else {
            stopRoundTimer();
            eliminatePlayer();
        }
    }

    /**
     * Function that handle the End turn phase,change current player and set the new Game Status
     * @param message Message received from the Client
     */

    public synchronized void handleEndTurn(Message message){
        stopRoundTimer();
        if(FlowStatutsLoader.isRightMessage(game.getGameStatus(),message.getType())) {
            game.getCurrentPlayer().setTurnStatus(TurnStatus.IDLE);

            if(game.getCurrentPlayer().getPower() != null)
                game.getCurrentPlayer().getPower().resetCard();

            changeTurnPlayer(message);
            startRoundTimer();

            switch (game.getGameStatus()) {
                case CHALLENGERCHOICEDONE:
                    game.setGameStatus(Response.CARDCHOICE);
                    break;
                case CARDCHOICEDONE:
                    if (game.getAvailableCards().isEmpty()) {
                        game.assignPermanentConstraint();
                        game.setGameStatus(Response.ASSIGNEDPERMCONSTRAINT);
                        game.setGameStatus(Response.PLACEWORKERS);
                    } else {
                        game.setGameStatus(Response.CARDCHOICE);
                    }
                    break;
                case PLACEWORKERSDONE:
                    if(game.allWorkersPlaced()){
                        handleTurnBeginning();
                    }
                    else{
                        game.setGameStatus(Response.PLACEWORKERS);
                    }
                    break;
                default:
                    handleTurnBeginning();
            }
        }
        else {
            game.setGameStatus(Response.STATUSERROR);
        }
    }

    /**
     * Function that forward the message to the round controller
     * @param message Message to forward
     */

    public synchronized void sendToRoundController(Message message){

        roundController.processRoundEvent(message);

    }

    /**
     * Function that start the round timer
     */

    public void startRoundTimer(){
      turnTimer = new Timer();
      TurnTimerTask task = new TurnTimerTask(clients.get(getCurrentPlayer().getNickName()).getConnection());
      turnTimer.schedule(task, (long) ConfigLoader.getTurnTimer()*1000);
    }

    /**
     * Function that stop the round timer
     */

    public void stopRoundTimer(){
        turnTimer.cancel();
    }

    /**
     * Function that send a chat message to the other clients
     * @param message Message to deliver
     */

    public synchronized void broadcastMessage(Message message){
        for(Player player: game.getPlayers()){
            if(!player.getNickName().equals(message.getNickName()))
                getViewFromNickName(player.getNickName()).handleChatMessage(message);
        }
    }

    /**
     * Function that check if the game has been stopped or if there is a winner, and (if yes) reset all the players
     */

    public synchronized void checkIfToReset(){
        if(game.getGameStatus().equals(Response.WIN) || game.getGameStatus().equals(Response.LOSEWIN)){
            for(Player player :getActualPlayers()){
                game.removeObserver(getViewFromNickName(player.getNickName()));
            }
            for(Player player :game.getLosePlayers()){
                game.removeObserver(getViewFromNickName(player.getNickName()));
            }
        }
    }

    /**
     * Function that process the message received and decide which function to call
     * @param message Message to analise
     */

    public synchronized void processMessage(Message message){
        String log = String.format("GameID -> %s || Received Message from -> || UserID: %s || Type: %s || SubType: %s",getGameID(),message.getSender(),message.getType().toString(),message.getSubType().toString());
        Server.LOGGER.info(log);
        switch (message.getType()) {
            case CONFIG:
                if (message.getSubType().equals(MessageSubType.ANSWER))
                    handleNewPlayer(message);
                else if (message.getSubType().equals(MessageSubType.UPDATE))
                    handleNewNickname(message);
                break;
            case CHAT:
                broadcastMessage(message);
                break;
            case ENDTURN:
                if(!getViewFromUserID(message.getSender()).isYourTurn()){
                    getViewFromUserID(message.getSender()).handleNotYourTurn();
                }else {
                    handleEndTurn(message);
                    break;
                }
                break;
            default:
                if(!getViewFromUserID(message.getSender()).isYourTurn()){
                    getViewFromUserID(message.getSender()).handleNotYourTurn();
                }else {
                    sendToRoundController(message);
                    checkIfToReset();
                    break;
                }
        }
    }

    /**
     * Update function of the observer
     * @param message Message received from the Client
     */

    @Override
    public synchronized void update(Message message) {
        if(message == null)
            throw new IllegalStateException("invalid message");
        try{
            processMessage(message);
        }catch (IllegalStateException ill){
            Server.LOGGER.severe(ill.getMessage());
        }
    }


}
