package it.polimi.ingsw.controller;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.messages.clientmessages.PlayAssistantMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Planning;

import java.util.List;

/**The sub-controller which manages the planning phase. It is responsible of receiving input from the client (View) during all its planning phase and translating it to a model plan.*/
public class ControllerPlanningPhase {

    private final Game game;
    private final Planning modelRound;

    /**It builds the planning controller.
     * @param game part of the model on which it is operating*/
    public ControllerPlanningPhase(Game game){
        this.modelRound= game.getPlanningPhase();
        this.game=game;
    }

    /**It is used to make players playing the assistant card. As a result it invokes the model which keeps the reference to which player has played which card.
     * @param message the input of the player which contains the card played from the player
     * @exception IllegalMoveException if the game is not in the right state or it's not the player's turn*/
    public void playAssistantCard(PlayAssistantMessage message){
        if(game.getActualState()!= GameState.planPlayCard || message.getPlayerId()!= game.getCurrentPlayerId()){
            if(game.getActualState()!=GameState.planPlayCard) throw new IllegalMoveException("not the right state");
            else throw new IllegalMoveException("not your turn");
        }
        modelRound.playCard(message.getPlayerId(),message.getPlayedCard());
        game.changePlayerTurn();
    }

    /**@return the players' order of the previous round*/
    public List<Integer> getActualOrder() {
        return modelRound.getRoundOrder(game);
    }

    /**@return the model on which it's operating*/
    public Game getGame(){return this.game;}

    /**@return the planning phase of the model*/
    public Planning getModelRound(){return this.modelRound;}
}
