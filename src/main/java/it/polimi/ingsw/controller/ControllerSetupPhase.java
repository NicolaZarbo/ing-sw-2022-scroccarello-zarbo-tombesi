package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.messages.clientmessages.PrePlayerMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Setup;

/**The sub-controller which manages setup phase. It is responsible for receiving input from the client (View) during all its setup phase and translating it to a model customization.*/
public class ControllerSetupPhase {
private final Setup setup;
private final Game model;

    /**It builds the setup controller.
     * @param game part of the model on which it is operating*/
    public ControllerSetupPhase(Game game){
        this.setup=game.getSetupPhase();
        this.model=game;
    }

    /** It is used to convert the custom choices of the player.
     * @param message the input received from the player which contains its custom choices such as tower color, the magician or his nickname
     * @exception IllegalMoveException if the game is not in the right state, or it's not the player's time to play*/
    public void createPlayer(PrePlayerMessage message){
        if(model.getActualState()!= GameState.setupPlayers || !setup.isPreOrderTurnOf(message.getName()))
            throw new IllegalMoveException("curr player is "+setup.getPreGameTurnOf());
        setup.addPrePlayer(message.getPrePlayer());
    }

    /**@return the model on which it is operating*/
    public Game getModel(){return this.model;}

    /**@return the model setup on which it's operating*/
    public Setup getSetup(){return this.setup;}

}
