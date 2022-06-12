package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.messages.clientmessages.*;
import it.polimi.ingsw.messages.servermessages.ErrorMessageForClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

public class Controller extends Observable<ErrorMessageForClient> implements Observer<ClientMessage> {
    protected final Game game;
    private final ControllerPlanningPhase controllerRound;
    private final ControllerActionPhase controllerTurn;
    private final ControllerSetupPhase controllerSetup;

    /** Creates an instance of controller and subControllers
     * @param game the model on which this controller acts*/
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

    /** Called by RemoteView.notify(), used to act on the model as effect of a user's interaction*/
    @Override
    public void update(ClientMessage message) {
        if(message.getPlayerId()!=this.game.getCurrentPlayerId())
            sendMessageError( new IllegalMoveException("not your turn, now plays " +message.getPlayerId()));
        else
            try{
                message.doAction(this); //commandPattern
            }
            catch(IllegalMoveException e){
                sendMessageError(e);
            }
    }
    /** notifies the ErrorMessageReceiver in RemoteView, used to send to the client an error*/
    private void sendMessageError(RuntimeException e) {
        ErrorMessageForClient error =new ErrorMessageForClient(game.getCurrentPlayerId(),e);
        this.notify(error);
    }
}
