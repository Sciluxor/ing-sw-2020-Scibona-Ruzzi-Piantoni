package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Cards.CardType;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MoveWorkerMessage;

import java.util.ArrayList;

public class RoundController {

    private Game game;

    public RoundController(Game game){

        this.game = game;
    }

    public void processRoundEvent(Message message){

        switch (message.getType()){
            case MOVEWORKER:
                handleMovement(message);
                break;
            case FIRSTACTION:
                handleFirstAction();
                break;
            case BUILDWORKER:
                break;
            default:
                throw new IllegalStateException("no Action");
        }
    }


    public void mapNextAction(Response nextStatus){

        switch (nextStatus){
            case MOVED:
                checkMoveVictory();
                break;
            case NEWMOVE:
                break;
            case BUILD:
                checkBuildVictory();
            case NOTBUILDWIN:
                handleEndTurn();
            default:

        }

    }

    public void handleFirstAction(){
        game.setGameStatus(game.getCurrentPlayer().getFirstAction());

    }

    public void handleRoundBeginning(){

    }

    public void handleMovement(Message message){
        ArrayList<Directions> possibleMoveSquare = game.getCurrentPlayer().findWorkerMove(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker());
        Directions direction = ((MoveWorkerMessage) message).getDirection();
        Response response = Response.NOTMOVED;
        //aggiungere atena;
        for(Directions possibleDir: possibleMoveSquare){
            if(possibleDir.equals(direction)){
                response = game.getCurrentPlayer().executeWorkerMove(game.getGameMap(),direction);
                game.setGameStatus(response);
                break;
            }
        }

        mapNextAction(response);
    }

    public void handleConstraint() {


    }
    public void handleBuilding(){

    }

    public void checkMoveVictory(){
        Response response = game.getCurrentPlayer().checkVictory(game.getGameMap());

        //vedere hera o hestia
        if(response.equals(Response.WIN)) {
            game.setWinner(game.getCurrentPlayer());
            game.setHasWinner(true);
        }
        game.setGameStatus(response);
        mapNextAction(response);

    }

    public void checkBuildVictory(){
        Response response = Response.NOTBUILDWIN;

        for(Player player: game.getSettedPlayers()){
            if(player.getPower().getType().equals(CardType.BUILDVICTORY)){
                response = game.getCurrentPlayer().checkVictory(game.getGameMap());
                if(response.equals(Response.BUILDWIN)) {
                    game.setWinner(player);
                    game.setHasWinner(true);
                }
            }
        }

        if(response.equals(Response.NOTWIN))
            response = Response.NOTBUILDWIN;

        game.setGameStatus(response);
        mapNextAction(response);

    }

    public void handleEndTurn(){

    }

    public void startRoundTimer(){

    }

    public void stopRoundTimer(){

    }
}
