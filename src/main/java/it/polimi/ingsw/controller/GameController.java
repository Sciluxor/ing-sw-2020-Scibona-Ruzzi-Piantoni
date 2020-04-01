package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.GameConfigMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.Server.VirtualView;

import java.util.HashMap;
import java.util.Timer;

public class GameController implements Observer<Message> {

    private Game game;
    private int numberOfPlayers;
    private HashMap<String, VirtualView> clients;
    private Timer turnTimer ;
    private Timer reconnectionTimer;
    private RoundController roundController;

    public GameController(int numberOfPlayer,String gameID) {
        this.numberOfPlayers = numberOfPlayer;
        this.roundController = new RoundController();
        this.game = initializeGame(gameID);
        this.clients = new HashMap<>();
    }

    //
    //methods for new player
    //

    public void handleNewPlayer(Message message) {
        VirtualView view = ((GameConfigMessage) message).getView();
        view.setYourTurn(true);
        String nick = message.getNickName();
        if(!game.addPlayer(new Player(nick),view)){
              game.setGameStatus(Response.NICKUSED);
              return;
        }
        clients.put(nick,view);
        game.setGameStatus(Response.PLAYERADDED);
        view.setYourTurn(false);
        checkIfGameCanStart();

    }

    public void handleNewNickname(Message message){
        String nick = message.getNickName();
        VirtualView view = getViewFromUserID(message.getSender());
        view.setYourTurn(true);
        if(!game.newNickName(new Player(nick))){
            game.setGameStatus(Response.NICKUSED);
            return;
        }
        clients.put(nick,view);
        game.setGameStatus(Response.PLAYERADDED);
        view.setYourTurn(false);
        checkIfGameCanStart();
    }

    public void checkIfGameCanStart(){
        if(game.getSettedPlayers().size() == game.getNumberOfPlayers() && game.getConfigPlayer() == 0) {
            game.setGameStarted(true);
            for(VirtualView values :clients.values()){
             values.setYourTurn(true);
            }

            game.setGameStatus(Response.GAMESTARTED);

            for(VirtualView values :clients.values()){
                values.setYourTurn(false);
            }
        }
    }


    public Game initializeGame(String gameID){

        return new Game(numberOfPlayers,gameID);
    }

    public void addUserID(VirtualView view,String userID){
        clients.put(userID,view);
    }

    public VirtualView getViewFromUserID(String userID){
        return clients.get(userID);
    }

    public VirtualView getViewFromNickName(String nick){
        return clients.get(nick);
    }

    public boolean isFull(){
        return (game.getSettedPlayers().size()+game.getConfigPlayer()) == game.getNumberOfPlayers();
    }

    public String getgameID(){
        return game.getGameID();
    }
    public int getNumberOfPlayers(){
        return game.getNumberOfPlayers();
    }

    public void handleDisconntectionBeforeStart(Message message){
        disconnectPlayer(message);
        if(message.getSubType().equals(MessageSubType.REQUEST)) {
            VirtualView view = clients.get(message.getSender());
            view.getConnection().closeConnection();
        }


    }

    public void disconnectPlayer(Message message){
        VirtualView view = clients.get(message.getSender());
        view.getConnection().setViewActive(false);
        game.removeObserver(view);
        game.removePlayer(message.getMessage());
        clients.remove(message.getMessage());
        clients.remove(message.getSender());
    }

    public void handleMatchBeginning(){

    }

    public void handleTurnBeginning(){

    }

    public void handleWorkerChoice(){

    }

    public void removeNonPermanentConstraint(){


    }

    public void handleEndTun(){


    }

    //aggiungere metodi per la disconnesione dei player

    public void sendToRoundController(Message message){


    }




    public void processMessage(Message message){

        switch (message.getType()){
            case CONFIG:
                if(message.getSubType().equals(MessageSubType.ANSWER))
                    handleNewPlayer(message);
                else if(message.getSubType().equals(MessageSubType.UPDATE))
                    handleNewNickname(message);
                break;
            case DISCONNECTION:
                handleDisconntectionBeforeStart(message);
                break;
            default:
        }


    }



    @Override
    public void update(Message message) {

        processMessage(message);
    }


}
