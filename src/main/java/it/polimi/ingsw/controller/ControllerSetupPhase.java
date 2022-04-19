package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.client.PrePlayerMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Setup;

public class ControllerSetupPhase {
private Setup setup;
 /**used to manage the player choices at the start of the game  */
    public ControllerSetupPhase(Game game){
        this.setup=game.getSetupPhase();
    }
    public void createPlayer(PrePlayerMessage message){
            setup.addPrePlayer(message.getPrePlayer());
    }

}
