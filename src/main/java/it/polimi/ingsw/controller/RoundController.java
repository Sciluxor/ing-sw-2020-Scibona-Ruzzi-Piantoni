package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Cards.Card;
import it.polimi.ingsw.model.Cards.CardSubType;
import it.polimi.ingsw.model.Cards.CardType;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.BuildWorkerMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MoveWorkerMessage;
import it.polimi.ingsw.network.message.WorkersPositionMessage;

import java.util.ArrayList;

public class RoundController {

    private Game game;
    private int errorCounter;

    public RoundController(Game game){

        this.game = game;
        this.errorCounter = 0;
    }

    public void processRoundEvent(Message message){

        if(isNotRightStatus(message)){
            new Error();
        }

        switch (message.getType()){
            case MOVEWORKER:
                handleMovement(message);
                break;
            case WORKERCHOICE:
                handleFirstAction();
                break;
            case BUILDWORKER:
                handleBuilding(message);
                break;
            default:
                throw new IllegalStateException("no Action");
        }
    }


    public void mapNextAction(Response nextStatus){

        switch (nextStatus){
            case ASSIGNCONSTRAINT:
                handleConstraint();
                break;
            case BUILD:
                handleEndTurn();
                break;
            default:
        }
    }

    public boolean isNotRightStatus(Message message){

        //check in the json if is the right status
        return false;
    }



    public void handleRoundBeginning(){
       //forse non serve questo metodo

    }

    public void handleWorkerPositioning(Message message){
        //check if the square is free

        game.setGameStatus(Response.ENDTURN);

    }
    public void handleWorkerChoice(Message message){

        if(game.getCurrentPlayer().checkIfCanMove(game.getCurrentPlayer().getWorkerFromString(((gwgew)messege).getWorker)){
            handleFirstAction();
        }
    }

    public void handleFirstAction(){
        game.setGameStatus(game.getCurrentPlayer().getFirstAction());
    }

    public void handleMovement(Message message) {
        ArrayList<Directions> possibleMoveSquare = game.getCurrentPlayer().findWorkerMove(game.getGameMap(), game.getCurrentPlayer().getCurrentWorker());
        Directions direction = ((MoveWorkerMessage) message).getDirection();
        Response response = Response.NOTMOVED;


        for (Card constraint : game.getCurrentPlayer().getConstraint()) {
            if (constraint.getType().equals(CardType.YOURMOVE) && !constraint.getSubType().equals(CardSubType.NORMAL))
                possibleMoveSquare = constraint.eliminateInvalidMove(game.getGameMap(), game.getCurrentPlayer().getCurrentWorker(), possibleMoveSquare);
        }

        for (Directions possibleDir : possibleMoveSquare) {
            if (possibleDir.equals(direction)) {
                response = game.getCurrentPlayer().executeWorkerMove(game.getGameMap(), direction);
                break;
            }
        }

        if(!checkRightSquares(message)) {
            game.setGameStatus(Response.WINMISMATCH);  //vedere se si deve cambiare Response,se qualcuno ha vinto si invia il messaggio e si cambia la schermata
            return;
        }

        if (!response.equals(Response.NOTMOVED))
            if(!checkMoveVictory(message));
                game.setGameStatus(Response.NOTMOVED);  //vedere che response usare

        if(!game.hasWinner()) {
            game.setGameStatus(response);
            mapNextAction(response);
        }
    }

    public void handleConstraint() {
        game.getCurrentPlayer().assignConstraint(game.getPlayers());
        game.setGameStatus(Response.ASSIGNEDCONSTRAINT);
        mapNextAction(Response.ASSIGNEDCONSTRAINT);

    }

    public boolean checkMoveVictory(Message message){
        Response response = game.getCurrentPlayer().checkVictory(game.getGameMap());
        if(response.equals(Response.WIN)) {
            for (Card constraint : game.getCurrentPlayer().getConstraint()) {
                if (constraint.getType().equals(CardType.MOVEVICTORY) && !constraint.getSubType().equals(CardSubType.NORMAL) &&
                        !constraint.isValidVictory(game.getGameMap(), game.getCurrentPlayer().getCurrentWorker()))
                    response = Response.NOTWIN;
            }
        }

        if(!response.equals(((WorkersPositionMessage)message).getWinResponse())){ // devo bloccare l'handle move
            Error;
            return false;
        }

        if(response.equals(Response.WIN)) {
            game.setWinner(game.getCurrentPlayer());
            game.setHasWinner(true);
        }

        game.setGameStatus(response);
        return true;

    }


    public void handleBuilding(Message message){
        ArrayList<Directions> possibleBuildSquare = game.getCurrentPlayer().findPossibleBuild(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker());
        Directions direction = ((BuildWorkerMessage) message).getDirection();
        Building building = ((BuildWorkerMessage) message).getBuilding();
        Response response = Response.NOTBUILDPLACE;

        for(Directions possibleDir: possibleBuildSquare){
            if(possibleDir.equals(direction)){
                response = game.getCurrentPlayer().executeBuild(game.getGameMap(),building,direction);
            }

        }

        if(!checkRightSquares(message)) {
            game.setGameStatus(Response.NOTBUILD);  //vedere se si deve cambiare
            return;
        }

        //vedere come gestore il mismatch delle build victory

        if (!response.equals(Response.NOTBUILD) && !response.equals(Response.NOTBUILDPLACE))
            checkBuildVictory(message);

        if(!game.hasWinner()) {
            game.setGameStatus(response);
            mapNextAction(response);
        }
    }


    public boolean checkBuildVictory(Message message){
        Response response = Response.NOTBUILDWIN;

        for(Player player: game.getPlayers()){
            if(player.getPower().getType().equals(CardType.BUILDVICTORY) && player.getPower().getSubType().equals(CardSubType.NORMAL)){
                response = game.getCurrentPlayer().checkVictory(game.getGameMap());
                if(response.equals(Response.BUILDWIN)) {
                    game.setWinner(player);
                    game.setHasWinner(true);
                    break;
                }
            }
        }

        if(!response.equals(((BuildWorkerMessage)message).getWinResponse())){
            Error; // gestire in maniera diversa
        }

        if(response.equals(Response.NOTWIN))
            response = Response.NOTBUILDWIN;

        game.setGameStatus(response);
    }

    public void removeNonPermanentConstraint(){
        ArrayList<Card> nonPermanentConstraint = new ArrayList<>();
        for(Card constraint : game.getCurrentPlayer().getConstraint()){
            if(constraint.getSubType().equals(CardSubType.NONPERMANENTCONSTRAINT))
                nonPermanentConstraint.add(constraint);
        }

        for(Card constraint : nonPermanentConstraint){
            game.getCurrentPlayer().removeConstraint(constraint);
        }

    }

    public void handleEndTurn(){
        removeNonPermanentConstraint();
        game.setGameStatus(Response.ENDTURN);
    }

    public boolean checkRightSquares(Message message){
        return false;hihih
    }


}
