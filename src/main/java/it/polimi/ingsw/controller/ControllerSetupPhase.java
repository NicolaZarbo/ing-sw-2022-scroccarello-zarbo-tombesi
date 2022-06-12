package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.messages.clientmessages.PrePlayerMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Setup;

public class ControllerSetupPhase {
private final Setup setup;
private final Game model;
    /**used to manage the player choices at the start of the game  */
    public ControllerSetupPhase(Game game){
        this.setup=game.getSetupPhase();
        this.model=game;
    }
    /** Used to convert the choices for personalization to the player the user will control*/
    public void createPlayer(PrePlayerMessage message){
        if(model.getActualState()!= GameState.setupPlayers || !setup.isPreOrderTurnOf(message.getName()))
            throw new IllegalMoveException("curr player is "+setup.getPreGameTurnOf());
        setup.addPrePlayer(message.getPrePlayer());
    }

    public Game getModel(){return this.model;}
    public Setup getSetup(){return this.setup;}

}
