package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.model.player.TurnStatus;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.*;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.server.VirtualView;
import java.util.*;

public class GameController implements Observer<Message> {

    protected Game game;
    protected final Map<String, VirtualView> clients;
    private Timer turnTimer = new Timer();
    private final RoundController roundController;

    public GameController(int numberOfPlayer,String gameID) {
        this.game = initializeGame(numberOfPlayer,gameID);
        this.clients = new HashMap<>();
        this.roundController = new RoundController(game);
    }

    //
    //methods for new player
    //

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

    }

    public synchronized void addPlayer(VirtualView view, String nick){
        addNick(view,nick);
        game.setGameStatus(Response.PLAYERADDED);
        view.getConnection().setNickName(nick);
        view.setYourTurn(false);
        checkIfGameCanStart();
    }

    public synchronized void nickUsed(VirtualView view){
        game.setGameStatus(Response.NICKUSED);
        game.removeObserver(view);
        view.setYourTurn(false);
    }

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

    public synchronized List<Player> getActualPlayers(){

        return game.getPlayers();
    }

    public synchronized Player getCurrentPlayer(){
        return game.getCurrentPlayer();
    }

    public synchronized boolean isGameStarted(){
        return game.isGameStarted();
    }


    public synchronized Game initializeGame(int numberOfPlayers, String gameID){

        return new Game(numberOfPlayers,gameID);
    }

    public synchronized List<String> getAvailableCards(){
        return game.getAvailableCards();
    }

    public synchronized boolean hasWinner(){ return game.hasWinner();}

    public synchronized void addNick(VirtualView view,String nickName){
        clients.put(nickName,view);
    }

    public synchronized void addUserID(VirtualView view,String userID){
        clients.put(userID,view);
    }

    public VirtualView getViewFromUserID(String userID){
        return clients.get(userID);
    }

    public VirtualView getViewFromNickName(String nick){
        return clients.get(nick);
    }

    public synchronized List<Square> getModifiedSquares(){
        return  game.getGameMap().getModifiedSquare();
    }

    public synchronized boolean isFull(){
        return (game.getPlayers().size()+game.getConfigPlayer()) == game.getNumberOfPlayers();
    }

    public boolean isStillInGame(String nickName){
        for(Player player: getActualPlayers()){
            if(player.getNickname().equals(nickName))
                return true;
        }
        return false;
    }

    public String getGameID(){
        return game.getGameID();
    }

    public int getNumberOfPlayers(){
        return game.getNumberOfPlayers();
    }

    //
    //methods for disconnection from the Game
    //

    public synchronized void stopStartedGame(Response newStatus){

        game.setGameStatus(newStatus);
        stopRoundTimer();                               //devo stoppare il  timer qui?

        for(Player player :getActualPlayers()){
            VirtualView playerView = removeViewFromGame(player.getNickname());
            resetPlayer(playerView);
        }

    }

    public synchronized void resetPlayer(VirtualView playerView){
        stopRoundTimer();  //si può stoppare più volte il timer? per quando finisce il game e deve iniziarne un'altro
        playerView.getConnection().setUserID(ConstantsContainer.USERDIDDEF);
        playerView.getConnection().setNickName(ConstantsContainer.NICKDEF);
        playerView.removeObserver(this);
        game.removeObserver(playerView);

        if(playerView.getConnection().isConnectionActive()) {
            playerView.getConnection().startLobbyTimer();
        }
    }

    public synchronized String  getUserIDFromPlayer(Player player){
        return getViewFromNickName(player.getNickname()).getConnection().getUserID();
    }

    public synchronized void handleLobbyTimerEnded(Message message){
        VirtualView view = clients.get(message.getSender());
        game.removeConfigPlayer();
        clients.remove(message.getSender());
        game.removeObserver(view);
        view.removeObserver(this);
    }

    public synchronized void eliminatePlayer(){
        VirtualView view = clients.get(getCurrentPlayer().getNickname());
        view.setYourTurn(false);
        removePlayerFromBoard();

        game.setGameStatus(Response.PLAYERLOSE);
        checkIfStillCorrectGame();

    }

    public synchronized VirtualView removeViewFromGame(String nickName){
        VirtualView view = clients.get(nickName);
        view.setYourTurn(false);
        clients.remove(nickName);
        clients.remove(view.getConnection().getUserID());
        return view;
    }

    public synchronized void removePlayerFromBoard(){
        game.removePlayerLose();
        VirtualView newView = clients.get(getCurrentPlayer().getNickname());
        newView.setYourTurn(true);
    }

    public synchronized void checkIfStillCorrectGame(){
        int numberOfPlayer = game.getPlayers().size();
        if( numberOfPlayer >= ConstantsContainer.MINPLAYERLOBBY && numberOfPlayer <= game.getNumberOfPlayers()){
            startRoundTimer();
            handleTurnBeginning();
        }
        else{
            game.setWinner(getCurrentPlayer());
            game.setHasWinner(true);
            game.setGameStatus(Response.WIN);
        }
    }


    public synchronized void disconnectPlayerBeforeGameStart(Message message) {
        VirtualView view = clients.get(message.getSender());
        view.setYourTurn(false);

        game.removeObserver(view);
        clients.remove(message.getSender());


        if (message.getSubType().equals(MessageSubType.NICKMAXTRY) || view.getConnection().getNickName().equalsIgnoreCase(ConstantsContainer.NICKDEF)) {
            game.removeConfigPlayer();
        }
        else {
            game.removeSettedPlayer(message.getNickName());
            clients.remove(message.getNickName());
            game.setGameStatus(Response.REMOVEDPLAYER);
        }

    }

    public synchronized boolean isFreeNick(String nick){
        List<Player> players = game.getPlayers();

        for(Player player : players){
            if(player.getNickname().equals(nick))
                return false;
        }

        return true;
    }

    //
    //methods for reconnection
    //

    //inserire metodi

    //
    //methods for Game beginning handling
    //

    public synchronized void handleMatchBeginning(){
        Player challenger = game.pickChallenger();
        getViewFromNickName(challenger.getNickname()).setYourTurn(true);
        game.setGameStatus(Response.CHALLENGERCHOICE);
        startRoundTimer();
    }

    public synchronized void changeTurnPlayer(Message message){
        getViewFromNickName(message.getNickName()).setYourTurn(false);
        game.pickPlayer();
        getViewFromNickName(game.getCurrentPlayer().getNickname()).setYourTurn(true);
    }

    //
    //methods for ending the current turn and starting the next turn
    //


    public synchronized void handleTurnBeginning() {//to start timer
        if (!game.getCurrentPlayer().checkIfLoose(game.getGameMap())) {
              game.getCurrentPlayer().setTurnStatus(TurnStatus.PLAYTURN);
              game.setGameStatus(Response.STARTTURN);
        } else {
            stopRoundTimer();
            eliminatePlayer();
        }
    }

    public synchronized void handleEndTun(Message message){
        stopRoundTimer();
        if(FlowStatutsLoader.isRightMessage(game.getGameStatus(),message.getType())) {
            game.getCurrentPlayer().setTurnStatus(TurnStatus.IDLE);
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
                        game.setGameStatus(Response.STARTTURN);
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



    //
    //method to send the messagge to the round controller
    //

    public synchronized void sendToRoundController(Message message){

        roundController.processRoundEvent(message);

    }

    //
    //methods to start and stop the timer of the turn, and to handle the timer disconnection
    //

    public void startRoundTimer(){
      turnTimer = new Timer();
      TurnTimerTask task = new TurnTimerTask(clients.get(getCurrentPlayer().getNickname()).getConnection());
      turnTimer.schedule(task, (long) ConfigLoader.getTurnTimer()*1000);
    }

    public void stopRoundTimer(){
        turnTimer.cancel();
    }


    //
    //method to dispatch the new messagge to the right place
    //

    public synchronized void processMessage(Message message){

        switch (message.getType()) {
            case CONFIG:
                if (message.getSubType().equals(MessageSubType.ANSWER))
                    handleNewPlayer(message);
                else if (message.getSubType().equals(MessageSubType.UPDATE))
                    handleNewNickname(message);
                break;
            case ENDTURN:
                if(!getViewFromUserID(message.getSender()).isYourTurn()){
                    getViewFromUserID(message.getSender()).handleNotYourTurn();
                }else {
                    handleEndTun(message);
                    break;
                }
                break;
            default:
                if(!getViewFromUserID(message.getSender()).isYourTurn()){
                    getViewFromUserID(message.getSender()).handleNotYourTurn(); //decidere come gestire questa eccezione e aggiungere al logger l'errore
                }else {
                    sendToRoundController(message);
                    break;
                }
        }
    }

    @Override
    public synchronized void update(Message message) {
        if(message == null)
            throw new IllegalStateException("invalid message");
        try{
            processMessage(message);
        }catch (IllegalStateException ill){
            Logger.info(ill.getMessage());
        }
    }


}
