package it.polimi.ingsw.controller;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.messages.client.PlayAssistantMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
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
        if(game.getActualState()!= GameState.planPlayCard || message.getPlayerId()!= game.getCurrentPlayerId())
            throw new IllegalMoveException();
        modelRound.playCard(message.getPlayerId(),message.getPlayedCard());
        if(game.isLastPlayerTurn())
            game.moveToNextPhase();
        else {
            game.changePlayerTurn();
        }
    }
    private List<Integer> getActualOrder() {
        return Round.getRoundOrder(game);
    }

}
