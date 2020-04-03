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
                handleBuilding(message);
                break;
            default:
                throw new IllegalStateException("no Action");
        }
    }


    public void mapNextAction(Response nextStatus){

        switch (nextStatus){
            case MOVED:
            case ASSIGNEDCONSTRAINT:
                checkMoveVictory();
                break;
            case NEWMOVE:
                break;
            case ASSIGNCONSTRAINT:
                handleConstraint();
                break;
            case BUILD:
                checkBuildVictory();
                break;
            case NOTBUILDWIN:
                handleEndTurn();
                break;
            default:

        }

    }

    public void handleFirstAction(){
        game.setGameStatus(game.getCurrentPlayer().getFirstAction());

    }

    public void handleRoundBeginning(){
       //forse non serve questo metodo

    }

    public void handleWorkerChoice(Message message){
        //check if the square is free

        game.setGameStatus(Response.ENDTURN);

    }

    public void handleMovement(Message message){
        ArrayList<Directions> possibleMoveSquare = game.getCurrentPlayer().findWorkerMove(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker());
        Directions direction = ((MoveWorkerMessage) message).getDirection();
        Response response = Response.NOTMOVED;


        for (Card constraint : game.getCurrentPlayer().getConstraint()) {
            if (constraint.getType().equals(CardType.YOURMOVE) && !constraint.getSubType().equals(CardSubType.NORMAL))
                possibleMoveSquare = constraint.eliminateInvalidMove(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker(),possibleMoveSquare);
        }

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
        game.getCurrentPlayer().assignConstraint(game.getSettedPlayers());
        game.setGameStatus(Response.ASSIGNEDCONSTRAINT);
        mapNextAction(Response.ASSIGNEDCONSTRAINT);

    }

    public void checkMoveVictory(){
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
        game.setGameStatus(response);
        mapNextAction(response);

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

        game.setGameStatus(response);
        mapNextAction(response);

    }


    public void checkBuildVictory(){
        Response response = Response.NOTBUILDWIN;

        for(Player player: game.getSettedPlayers()){
            if(player.getPower().getType().equals(CardType.BUILDVICTORY) && player.getPower().getSubType().equals(CardSubType.NORMAL)){
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
        game.setGameStatus(Response.ENDTURN);//rivedere se va bene fatto cosi o se si deve togliere.
    }


}
