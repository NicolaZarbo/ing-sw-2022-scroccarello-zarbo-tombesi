package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.client.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.observer.Observer;

public class Controller implements Observer<ClientMessage> {
    protected final Game game;
    private final ControllerPlanningPhase controllerRound;
    private final ControllerActionPhase controllerTurn;
    public  Controller(Game game){
        this.game=game;
        this.controllerRound =new ControllerPlanningPhase(game);
        this.controllerTurn = new ControllerActionPhase(game);
    }
    @Override
    public void update(ClientMessage message) {
        this.doAction(message);
    }
    private void doAction(ClientMessage message){
        // checks if the move can be done from this player in this phase
        //returns an error back to the player if it isn't the case
        if(message.getPlayerId()!= game.getCurrentPlayerId() )
        {}//send player message error
        if(message instanceof MoveMotherMessage)
            controllerTurn.moveMotherNature((MoveMotherMessage) message);
        if(message instanceof StudentToHallMessage)
            controllerTurn.moveStudentToHall((StudentToHallMessage) message);
        if(message instanceof StudentToIslandMessage)
            controllerTurn.moveStudentToIsland((StudentToIslandMessage) message);
        if(message instanceof ChooseCloudMessage)
            controllerTurn.chooseCloud((ChooseCloudMessage) message);


        if( message instanceof  PlayAssistantMessage)
            controllerRound.playAssistantCard((PlayAssistantMessage) message);
    }
}
