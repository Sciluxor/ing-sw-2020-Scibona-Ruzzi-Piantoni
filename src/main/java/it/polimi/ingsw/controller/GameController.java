package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.utils.FlowStatutsLoader;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.Server.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class GameController implements Observer<Message> {

    private Game game;
    private HashMap<String, VirtualView> clients;
    private Timer turnTimer ;
    private Timer reconnectionTimer;
    private RoundController roundController;

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
              game.setGameStatus(Response.NICKUSED);
              view.setYourTurn(false);
              return;
        }
        clients.put(nick,view);

        game.setGameStatus(Response.PLAYERADDED);
        view.setYourTurn(false);
        checkIfGameCanStart();

    }

    public synchronized void handleNewNickname(Message message){
        String nick = message.getNickName();
        VirtualView view = getViewFromUserID(message.getSender());
        view.setYourTurn(true);

        if(!game.newNickName(new Player(nick))){
            game.setGameStatus(Response.NICKUSED);
            view.setYourTurn(false);
            return;
        }


        clients.put(nick,view);
        game.setGameStatus(Response.PLAYERADDED);
        view.setYourTurn(false);
        checkIfGameCanStart();
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

    public synchronized ArrayList<Player> getActualPlayers(){

        return game.getPlayers();
    }

    public synchronized Player getNewPlayer(){
        return game.getPlayers().get(game.getPlayers().size() -1);
    }

    public synchronized boolean isGameStarted(){
        return game.isGameStarted();
    }


    public synchronized Game initializeGame(int numberOfPlayers, String gameID){

        return new Game(numberOfPlayers,gameID);
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

    public synchronized boolean isFull(){
        return (game.getPlayers().size()+game.getConfigPlayer()) == game.getNumberOfPlayers();
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

    public synchronized void handleDisconnectionBeforeStart(Message message){
        VirtualView view = clients.get(message.getSender());
        if(message.getSubType().equals(MessageSubType.TIMEENDED)){
            handleLobbyTimerEnded(message);
            return;
        }
        if(message.getSubType().equals(MessageSubType.BACK)) //testare cosa succede se il back o il close arriva mentre sta iniziando la partita, si deve gestire
            if(!game.isGameStarted())
                view.getConnection().startLobbyTimer();
            else
                return; //far terminare il game

        disconnectPlayer(message);

        if(message.getSubType().equals(MessageSubType.REQUEST)) {

            view.getConnection().closeConnection();
        }
    }

    public synchronized void handleLobbyTimerEnded(Message message){
        VirtualView view = clients.get(message.getSender());
        view.getConnection().setViewActive(false);
        game.removeObserver(view);
        game.removeConfigPlayer();
        clients.remove(message.getSender());
        view.getConnection().closeConnection();
    }


    public synchronized void disconnectPlayer(Message message) {
        VirtualView view = clients.get(message.getSender());
        view.getConnection().setViewActive(false);
        game.removeObserver(view);
        clients.remove(message.getMessage());
        clients.remove(message.getSender());

        if (message.getSubType().equals(MessageSubType.NICKMAXTRY))
            game.removeConfigPlayer();

        else {
            game.removeSettedPlayer(message.getMessage());
            game.setGameStatus(Response.REMOVEDPLAYER);
        }

    }

    public synchronized boolean isFreeNick(String nick){
        ArrayList<Player> players = game.getPlayers();

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
    }

    public synchronized void changeTurnPlayer(Message message){
        getViewFromNickName(message.getNickName()).setYourTurn(false);
        game.peekPlayer();
        getViewFromNickName(game.getCurrentPlayer().getNickname()).setYourTurn(true);
    }



    //
    //methods for ending the current turn and starting the next turn
    //


    public synchronized void handleTurnBeginning() {//to start timer
        if (game.getCurrentPlayer().checkIfLoose(game.getGameMap())) {
              game.setGameStatus(Response.STARTTURN);
        } else {
            stopRoundTimer();
            eliminatePlayer();

            //controllare se la partita può continuare o se è finita
        }
    }

    public synchronized void eliminatePlayer(){

    }


    public synchronized void handleEndTun(Message message){
        stopRoundTimer();
        if(FlowStatutsLoader.isRightMessage(game.getGameStatus(),message.getType())) {

            changeTurnPlayer(message);
            startRoundTimer();

            switch (game.getGameStatus()) {
                case CHALLENGERCHOICEDONE:
                    game.setGameStatus(Response.CARDCHOICE);
                    break;
                case CARDCHOICEDONE:
                    if (game.getAvailableCards().size() == 0) {
                        game.assignPermanentConstraint();
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
                    changeTurnPlayer(message);
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

    }

    public void stopRoundTimer(){

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
            case DISCONNECTION:
                handleDisconnectionBeforeStart(message);
                break;
            case ENDTURN:
                if(!getViewFromUserID(message.getSender()).isYourTurn()){
                    getViewFromUserID(message.getSender()).handleNotYourTurn();
                    //throw new IllegalStateException("not the turn of player: " + message.getNickName());
                }else {
                    handleEndTun(message);
                    break;
                }
            default:
                if(!getViewFromUserID(message.getSender()).isYourTurn()){
                    getViewFromUserID(message.getSender()).handleNotYourTurn(); //decidere come gestire questa eccezione e aggiungere al logger l'errore
                    //throw new IllegalStateException("not the turn of player: " + message.getNickName());
                }else {
                    sendToRoundController(message);
                    break;
                }
        }
    }

    @Override
    public synchronized void update(Message message) {

        try{
            processMessage(message);
        }catch (IllegalStateException ill){
            Logger.info(ill.getMessage());
        }
    }


}
