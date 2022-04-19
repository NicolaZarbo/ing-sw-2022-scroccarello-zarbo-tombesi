package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.client.*;
import it.polimi.ingsw.messages.server.ErrorMessageForClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

public class Controller extends Observable<ErrorMessageForClient> implements Observer<ClientMessage> {
    protected final Game game;
    private final ControllerPlanningPhase controllerRound;
    private final ControllerActionPhase controllerTurn;
    private final ControllerSetupPhase controllerSetup;

    public  Controller(Game game){
        this.game=game;
        this.controllerRound =new ControllerPlanningPhase(game);
        this.controllerTurn = new ControllerActionPhase(game);
        this.controllerSetup= new ControllerSetupPhase(game);
    }

    public ControllerPlanningPhase getControllerRound() {
        return controllerRound;
    }

    public ControllerActionPhase getControllerTurn() {
        return controllerTurn;
    }

    public ControllerSetupPhase getControllerSetup() {
        return controllerSetup;
    }

    @Override
    public void update(ClientMessage message) {
        this.doAction(message);
    }
    private void doAction(ClientMessage message){
        try {
            message.doAction(this); //commandPattern
        }catch (RuntimeException e){
            sendMessageError(e);
        }
    }
    private void sendMessageError(RuntimeException e) {
        ErrorMessageForClient error =new ErrorMessageForClient(game.getCurrentPlayerId(),e);
        this.notify(error);
    }
}
