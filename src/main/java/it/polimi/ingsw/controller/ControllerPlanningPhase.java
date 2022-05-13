package it.polimi.ingsw.controller;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.messages.client.PlayAssistantMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Round;

import java.util.List;

public class ControllerPlanningPhase {

    private final Game game;
    private final Round modelRound;

    public ControllerPlanningPhase(Game game){
        this.modelRound= game.getPlanningPhase();
        this.game=game;
    }

    public void playAssistantCard(PlayAssistantMessage message){
        if(game.getActualState()!= GameState.planPlayCard || message.getPlayerId()!= game.getCurrentPlayerId()){
            if(game.getActualState()!=GameState.planPlayCard) throw new RuntimeException("not the right state");
            else throw new IllegalMoveException("not your turn");
        }
        if(game.isLastPlayerTurn()) {
            game.moveToNextPhase();
            modelRound.playCard(message.getPlayerId(),message.getPlayedCard());
            modelRound.roundOrder();

        }
        else {
            game.changePlayerTurn();
            modelRound.playCard(message.getPlayerId(),message.getPlayedCard());
        }

    }
    public List<Integer> getActualOrder() {
        return modelRound.getRoundOrder(game);
    }
    public Game getGame(){return this.game;}
    public Round getModelRound(){return this.modelRound;}
}
