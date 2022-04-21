package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.messages.client.PrePlayerMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Setup;

public class ControllerSetupPhase {
private Setup setup;
private Game model;
 /**used to manage the player choices at the start of the game  */
    public ControllerSetupPhase(Game game){
        this.setup=game.getSetupPhase();
        this.model=game;
    }
    public void createPlayer(PrePlayerMessage message){
        if(model.getActualState()!= GameState.setupPlayers)
            throw new IllegalMoveException();
        setup.addPrePlayer(message.getPrePlayer());

    }

}
