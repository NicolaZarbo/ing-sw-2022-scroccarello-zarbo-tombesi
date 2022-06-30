package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.messages.clientmessages.*;
import it.polimi.ingsw.messages.servermessages.ErrorMessageForClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

/**The controller of the MVC pattern. It is involved in getting information from the View and invoking the relative methods of the Model. It is specialized in sub-controllers which help resolve certain tasks. */
public class Controller extends Observable<ErrorMessageForClient> implements Observer<ClientMessage> {
    protected final Game game;
    private final ControllerPlanningPhase controllerRound;
    private final ControllerActionPhase controllerTurn;
    private final ControllerSetupPhase controllerSetup;

    /** It creates an instance of controller and its sub-controllers.
     * @param game the model on which this controller acts*/
    public  Controller(Game game){
        this.game=game;
        this.controllerRound =new ControllerPlanningPhase(game);
        this.controllerTurn = new ControllerActionPhase(game);
        this.controllerSetup= new ControllerSetupPhase(game);
    }

    /**@return the planning controller*/
    public ControllerPlanningPhase getControllerRound() {
        return controllerRound;
    }

    /**@return the action controller*/
    public ControllerActionPhase getControllerTurn() {
        return controllerTurn;
    }

    /**@return the setup controller*/
    public ControllerSetupPhase getControllerSetup() {
        return controllerSetup;
    }


    /** It is called by the Remote View's <i>notify</i>, it is used to act on the model as effect of a user's interaction.
     * @param message the message related to the user interaction*/
    @Override
    public void update(ClientMessage message) {
        if(message.getPlayerId()!=this.game.getCurrentPlayerId())
            sendMessageError( new IllegalMoveException("not your turn, now plays " +message.getPlayerId()));
        else
            try{
                message.doAction(this);
            }
            catch(IllegalMoveException e){
                sendMessageError(e);
            }
    }

    /** It notifies the error occurred in the Remote View and send the client relative message.
     * @param e the exception thrown after the error*/
    private void sendMessageError(RuntimeException e) {
        ErrorMessageForClient error =new ErrorMessageForClient(game.getPlayer(game.getCurrentPlayerId()).getNickname(),e);
        this.notify(error);
    }

    /**@return the model game*/
    protected Game getGame(){return this.game;}
}
