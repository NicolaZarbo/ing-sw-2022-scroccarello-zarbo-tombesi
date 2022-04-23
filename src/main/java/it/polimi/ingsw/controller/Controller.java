package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.IllegalMoveException;
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
    /** update on remote view notify*/
    @Override
    public void update(ClientMessage message) {
        this.doAction(message);
    }
    /** uses command pattern to choose which controller method to call, catches errors and sends them to remote view*/
    private void doAction(ClientMessage message){
        try {
            if(message.getPlayerId()!=game.getCurrentPlayerId())
                throw new IllegalMoveException("not your turn");
            message.doAction(this); //commandPattern
        }catch (RuntimeException e){
            sendMessageError(e);
        }
    }
    /** notifies the ErrorMessageReceiver in RemoteView*/
    private void sendMessageError(RuntimeException e) {
        ErrorMessageForClient error =new ErrorMessageForClient(game.getCurrentPlayerId(),e);
        this.notify(error);
    }
}