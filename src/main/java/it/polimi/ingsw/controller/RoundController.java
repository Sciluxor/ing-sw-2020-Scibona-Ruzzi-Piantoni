package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.model.cards.CardSubType;
import it.polimi.ingsw.model.cards.CardType;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.map.Building;
import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.Square;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.utils.FlowStatutsLoader;

import java.util.ArrayList;
import java.util.List;

public class RoundController {

    private final Game game;

    public RoundController(Game game){
        this.game = game;
    }

    public void processRoundEvent(Message message){

        if(FlowStatutsLoader.isRightMessage(game.getGameStatus(),message.getType())) {

            switch (message.getType()) {
                case CHALLENGERCHOICE:
                    handleChallengerChoice(message);
                    break;
                case CHOOSECARD:
                    handleCardChoice(message);
                    break;
                case PLACEWORKERS:
                    handleWorkerPositioning(message);
                    break;
                case WORKERCHOICE:
                    handleWorkerChoice(message);
                    break;
                case MOVEWORKER:
                    if(message.getSubType().equals(MessageSubType.ANSWER))
                        handleMovement(message);
                    else
                        game.setGameStatus(Response.STATUSERROR);
                    break;
                case BUILDWORKER:
                    if(message.getSubType().equals(MessageSubType.ANSWER))
                        handleBuilding(message);
                    else
                        game.setGameStatus(Response.STATUSERROR);
                    break;
                default:
                    throw new IllegalStateException("no Action");
            }
        }
        else{
            game.setGameStatus(Response.STATUSERROR);
        }
    }


    public void mapNextAction(Response nextStatus){

        switch (nextStatus){
            case ASSIGNCONSTRAINT:
                handleConstraint();
                break;
            case ASSIGNEDCONSTRAINT:
                game.setGameStatus(Response.MOVED);
                break;
            case BUILD:
            case WRONGSQUAREBUILD:
                handleEndTurn();
                break;
            default:
        }
    }

    //
    //methods for challenger choice handling
    //

    public synchronized void handleChallengerChoice(Message message){
            List<String> cards = ((ChallengerChoiceMessage) message).getCards();
            String firstPlayer = ((ChallengerChoiceMessage) message).getFirstPlayer();


            if (checkCardsChoice(cards) && checkFirstPlayerChoice(firstPlayer)) {
                game.setAvailableCards(cards);
                game.setFirstPlayer(firstPlayer);
                game.createCardQueue();
                Server.LOGGER.info("GameID : "+ game.getGameID() + " || Cards -> " + cards.toString() + " || First Player-> " + firstPlayer);
                game.setGameStatus(Response.CHALLENGERCHOICEDONE);
            } else {
                game.setGameStatus(Response.CHALLENGERCHOICEERROR);
            }
        }

    public boolean checkCardsChoice(List<String> cards){
        if(cards.size() != game.getNumberOfPlayers())
            return false;
        for(String cardName : cards){
            if(game.getCardFromDeck(cardName) == null){
                return false;
            }
        }
        return true;
    }

    public boolean checkFirstPlayerChoice(String firstPlayer){
        for(Player player : game.getPlayers()){
            if(player.getNickName().equals(firstPlayer)){
                return true;
            }
        }
        return false;
    }

    //
    //methods for the card choice of each player
    //

    public synchronized void handleCardChoice(Message message) {
            String cardName = message.getMessage();
            if (game.getCardFromAvailableCards(cardName) == null)
                game.setGameStatus(Response.CARDCHOICEERROR);
            else {
                game.removeCard(cardName);
                game.getCurrentPlayer().setPower(game.getCardFromDeck(cardName));
                game.setGameStatus(Response.CARDCHOICEDONE);
                Server.LOGGER.info("GameID : "+ game.getGameID() + " -> " + game.getCurrentPlayer().getNickName() + " Has Chosen -> " + cardName);
                if(game.getAvailableCards().isEmpty())
                    game.createQueue();
            }
        }

    //
    //methods for the workers positioning of each player
    //

    public void handleWorkerPositioning(Message message){
            Integer[] tile1 = ((PlaceWorkersMessage) message).getTile1();
            Integer[] tile2 = ((PlaceWorkersMessage) message).getTile2();

            if(game.placeWorkersOnMap(tile1,tile2)){
                game.setGameStatus(Response.PLACEWORKERSDONE);
            }
            else{
                game.setGameStatus(Response.PLACEWORKERSERROR);
            }
        }

    //
    //methods for the workers to use in the turn
    //

    public void handleWorkerChoice(Message message){

        if((message.getMessage().equalsIgnoreCase("worker1") || message.getMessage().equalsIgnoreCase("worker2"))
                && game.getCurrentPlayer().checkIfCanMove(game.getGameMap(),game.getCurrentPlayer().getWorkerFromString(message.getMessage()))){
            game.getCurrentPlayer().setCurrentWorker(game.getCurrentPlayer().getWorkerFromString(message.getMessage()));
            handleFirstAction();
        }
        else{
            game.setGameStatus(Response.STARTTURNERROR);
        }
    }

    public void handleFirstAction(){
        game.setGameStatus(game.getCurrentPlayer().getFirstAction());
    }

    //
    //methods for the movement of the worker
    //

    public void handleMovement(Message message) {
        List<Directions> possibleMoveSquare = game.getCurrentPlayer().findWorkerMove(game.getGameMap(), game.getCurrentPlayer().getCurrentWorker());
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
        if(!response.equals(Response.NOTMOVED) && !areRightSquares(((MoveWorkerMessage)message).getModifiedSquare())) {
            game.setGameStatus(Response.WRONGSQUAREMOVE);
            mapNextAction(response);
            return;
        }
        if (!response.equals(Response.NOTMOVED) && !(checkMoveVictory(message)))
            game.setGameStatus(Response.MOVEWINMISMATCH);

        if(game.hasWinner()){
            Server.LOGGER.info("GameID : "+ game.getGameID() + " -> " + "|| Move Winner: " + game.getWinner().getNickName());
            game.setGameStatus(response);
            game.setGameStatus(Response.WIN);
        }
        else{
            game.setGameStatus(response);
            mapNextAction(response);
        }
    }

    public void handleConstraint() {
        game.getCurrentPlayer().assignConstraint(game.getPlayers());
        Response response = Response.ASSIGNEDCONSTRAINT;
        game.setGameStatus(response);
        mapNextAction(response);

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

        if(!response.equals(((MoveWorkerMessage)message).getWinResponse())){
            return false;
        }

        if(response.equals(Response.WIN)) {
            game.setWinner(game.getCurrentPlayer());  // se c'è una vittoria bisogna settare prima la mossa e poi la vittoria per notificare in ordine
            game.setHasWinner(true);
            return game.getCurrentPlayer().getNickName().equals(((MoveWorkerMessage) message).getWinnerPlayer().getNickName());
        }


        return true;

    }

    //
    //methods for the building of the worker
    //


    public void handleBuilding(Message message){
        List<Directions> possibleBuildSquare = game.getCurrentPlayer().findPossibleBuild(game.getGameMap(),game.getCurrentPlayer().getCurrentWorker());
        Directions direction = ((BuildWorkerMessage) message).getDirection();
        Building building = ((BuildWorkerMessage) message).getBuilding();
        Response response = Response.NOTBUILDPLACE;

        for(Directions possibleDir: possibleBuildSquare){
            if(possibleDir.equals(direction)){
                response = game.getCurrentPlayer().executeBuild(game.getGameMap(),building,direction);
            }

        }

        if(!response.equals(Response.NOTBUILD) && !response.equals(Response.NOTBUILDPLACE) &&!areRightSquares(((BuildWorkerMessage)message).getModifiedSquare())) {
            game.setGameStatus(Response.WRONGSQUAREBUILD);
            mapNextAction(Response.WRONGSQUAREBUILD);
            return;
        }

        if (!response.equals(Response.NOTBUILD) && !response.equals(Response.NOTBUILDPLACE) && !checkBuildVictory(message))
             game.setGameStatus(Response.BUILDWINMISMATCH);

        if(game.hasWinner()){
            Server.LOGGER.info("GameID : "+ game.getGameID() + " -> " + "|| Build Winner: " + game.getWinner().getNickName());
            game.setGameStatus(response);
            game.setGameStatus(Response.WIN);
        }
        else{
            game.setGameStatus(response);
            mapNextAction(response);
        }
    }


    public boolean checkBuildVictory(Message message){
        Response response = Response.NOTBUILDWIN;
        for(Player player: game.getPlayers()){
            if(player.getPower().getType().equals(CardType.BUILDVICTORY) && player.getPower().getSubType().equals(CardSubType.NORMAL)){
                response = player.checkVictory(game.getGameMap());
                if(response.equals(Response.BUILDWIN)) {
                    game.setWinner(player);
                    game.setHasWinner(true);
                    return response.equals(((BuildWorkerMessage) message).getWinResponse()) &&
                            player.getNickName().equals(((BuildWorkerMessage) message).getWinnerPlayer().getNickName());
                }
            }
        }

        if(response.equals(Response.NOTWIN))
            response = Response.NOTBUILDWIN;

        if(Response.NOTBUILDWIN.equals(response))
            return response.equals(((BuildWorkerMessage) message).getWinResponse());

        return false;
    }

    //
    //methods for the end of the turn of the worker
    //

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

    //
    //method to check if client has changed the right squares
    //

    public boolean areRightSquares(List<Square> clientModifiedSquares){
        List<Square> realModifiedSquares = game.getGameMap().getModifiedSquare();

        if(realModifiedSquares.size() != clientModifiedSquares.size())
            return false;
        for(int i = 0; i < realModifiedSquares.size();i++){
            if(!checkSquare(clientModifiedSquares.get(i),realModifiedSquares.get(i)))
                return false;
        }

        return true;
    }

    public boolean checkSquare(Square q1, Square q2){

        if(q1.getTile().equals(q2.getTile()) &&
                q1.getBuildingLevel() == q2.getBuildingLevel() && q1.hasPlayer() == q2.hasPlayer() && q1.getBuilding().equals(q2.getBuilding())) {
                if(q1.hasPlayer()) {
                    return q1.getPlayer().getNickName().equals(q2.getPlayer().getNickName()) && q1.getWorker().getName().equals(q2.getWorker().getName());
                }
                else return true;
        }
        return false;
    }


}
