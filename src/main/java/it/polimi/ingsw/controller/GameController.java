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
     * Public Constructor
     * @param numberOfPlayer
     * @param gameID
     */

    public GameController(int numberOfPlayer,String gameID) {
        this.game = initializeGame(numberOfPlayer,gameID);
        this.clients = new HashMap<>();
        this.roundController = new RoundController(game);
    }

    /**
     *
     * @param message
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
     *
     * @param message
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
     *
     * @param view
     * @param nick
     */

    public synchronized void addPlayer(VirtualView view, String nick){
        addNick(view,nick);
        game.setGameStatus(Response.PLAYERADDED);
        view.getConnection().setNickName(nick);
        view.setYourTurn(false);
        checkIfGameCanStart();
    }

    /**
     *
     * @param view
     */

    public synchronized void nickUsed(VirtualView view){
        game.setGameStatus(Response.NICKUSED);
        game.removeObserver(view);
        view.setYourTurn(false);
    }

    /**
     *
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
     *
     * @return
     */

    public synchronized List<Player> getActualPlayers(){

        return game.getPlayers();
    }

    /**
     *
     * @return
     */

    public synchronized Player getCurrentPlayer(){
        return game.getCurrentPlayer();
    }

    /**
     *
     * @return
     */

    public synchronized boolean isGameStarted(){
        return game.isGameStarted();
    }

    /**
     *
     * @param numberOfPlayers
     * @param gameID
     * @return
     */

    public synchronized Game initializeGame(int numberOfPlayers, String gameID){

        return new Game(numberOfPlayers,gameID);
    }

    /**
     *
     * @return
     */

    public synchronized String getLastLosePlayer(){
        return game.getLastLosePlayer();
    }

    /**
     *
     * @return
     */

    public synchronized List<Player> getLosePlayers(){
        return game.getLosePlayers();
    }

    /**
     *
     * @return
     */

    public synchronized List<String> getAvailableCards(){
        return game.getAvailableCards();
    }

    /**
     *
     * @return
     */

    public synchronized boolean hasWinner(){
        return game.hasWinner();
    }

    /**
     *
     * @param view
     * @param nickName
     */

    public synchronized void addNick(VirtualView view,String nickName){
        clients.put(nickName,view);
    }

    /**
     *
     * @param view
     * @param userID
     */

    public synchronized void addUserID(VirtualView view,String userID){
        clients.put(userID,view);
    }

    /**
     *
     * @param userID
     * @return
     */

    public VirtualView getViewFromUserID(String userID){
        return clients.get(userID);
    }

    /**
     *
     * @param nick
     * @return
     */

    public VirtualView getViewFromNickName(String nick){
        return clients.get(nick);
    }

    /**
     *
     * @return
     */

    public synchronized List<Square> getModifiedSquares(){
        return  game.getGameMap().getModifiedSquare();
    }

    /**
     *
     * @return
     */

    public synchronized boolean isFull(){
        return (game.getPlayers().size()+game.getConfigPlayer()) == game.getNumberOfPlayers();
    }

    /**
     *
     * @param nickName
     * @return
     */

    public synchronized boolean isStillInGame(String nickName){
        for(Player player: getActualPlayers()){
            if(player.getNickName().equals(nickName))
                return true;
        }
        return false;
    }

    /**
     *
     * @return
     */

    public synchronized String getStopper(){
        return game.getStopper();
    }

    /**
     *
     * @return
     */

    public synchronized boolean hasStopper(){return game.hasStopper();}

    /**
     *
     * @return
     */

    public String getWinner(){
        return game.getWinner().getNickName();
    }

    /**
     *
     * @return
     */

    public String getGameID(){
        return game.getGameID();
    }

    /**
     *
     * @return
     */

    public int getNumberOfPlayers(){
        return game.getNumberOfPlayers();
    }

    /**
     *
     * @param stopper
     * @param newStatus
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
     *
     * @param playerView
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
     *
     * @param player
     * @return
     */

    public synchronized String  getUserIDFromPlayer(Player player){
        return getViewFromNickName(player.getNickName()).getConnection().getUserID();
    }

    /**
     *
     * @param message
     */

    public synchronized void handleLobbyTimerEnded(Message message){
        VirtualView view = clients.get(message.getSender());
        game.removeConfigPlayer();
        clients.remove(message.getSender());
        game.removeObserver(view);
        view.removeObserver(this);
    }

    /**
     *
     */

    public synchronized void eliminatePlayer(){
        VirtualView view = clients.get(getCurrentPlayer().getNickName());
        view.setYourTurn(false);
        removePlayerFromBoard();
        checkIfStillCorrectGame();
    }

    /**
     *
     * @param nickName
     * @return
     */

    public synchronized VirtualView removeViewFromGame(String nickName){
        VirtualView view = clients.get(nickName);
        view.setYourTurn(false);
        clients.remove(nickName);
        clients.remove(view.getConnection().getUserID());
        return view;
    }

    /**
     *
     */

    public synchronized void removePlayerFromBoard(){
        game.removePlayerLose();
        VirtualView newView = clients.get(getCurrentPlayer().getNickName());
        newView.setYourTurn(true);
    }

    /**
     *
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
     *
     * @param message
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
     *
     * @param nick
     * @return
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
     *
     */

    public synchronized void handleMatchBeginning(){
        Player challenger = game.pickChallenger();
        getViewFromNickName(challenger.getNickName()).setYourTurn(true);
        game.setGameStatus(Response.CHALLENGERCHOICE);
        startRoundTimer();
    }

    /**
     *
     * @param message
     */

    public synchronized void changeTurnPlayer(Message message){
        getViewFromNickName(message.getNickName()).setYourTurn(false);
        game.pickPlayer();
        getViewFromNickName(game.getCurrentPlayer().getNickName()).setYourTurn(true);
    }

    /**
     *
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
     *
     * @param message
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
     *
     * @param message
     */

    public synchronized void sendToRoundController(Message message){

        roundController.processRoundEvent(message);

    }

    /**
     *
     */

    public void startRoundTimer(){
      turnTimer = new Timer();
      TurnTimerTask task = new TurnTimerTask(clients.get(getCurrentPlayer().getNickName()).getConnection());
      turnTimer.schedule(task, (long) ConfigLoader.getTurnTimer()*1000);
    }

    /**
     *
     */

    public void stopRoundTimer(){
        turnTimer.cancel();
    }

    /**
     *
     * @param message
     */

    public synchronized void broadcastMessage(Message message){
        for(Player player: game.getPlayers()){
            if(!player.getNickName().equals(message.getNickName()))
                getViewFromNickName(player.getNickName()).handleChatMessage(message);
        }
    }

    /**
     *
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
     *
     * @param message
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
     *
     * @param message
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
