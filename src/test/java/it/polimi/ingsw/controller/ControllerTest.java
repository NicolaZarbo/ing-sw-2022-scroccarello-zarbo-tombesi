package it.polimi.ingsw.controller;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.messages.clientmessages.ChooseCloudMessage;
import junit.framework.TestCase;

public class ControllerTest extends TestCase {
    Controller controllerTest;
    @Override
    public void setUp() throws Exception{
        super.setUp();
        this.controllerTest=new Controller(new GameStub(false,4,12));
    }
    public void testGetControllerRound() {
        assertNotNull(controllerTest.getControllerRound());
    }

    public void testGetControllerTurn() {
        assertNotNull(controllerTest.getControllerTurn());
    }

    public void testGetControllerSetup() {
        try{
            controllerTest.getControllerSetup();
            System.out.println("ok");
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    public void testUpdateRightMove() {
        ChooseCloudMessage cloudMessage=new ChooseCloudMessage(1,0);
        try{
            ((GameStub)this.controllerTest.game).setManuallyGamePhase(GameState.actionChooseCloud);
            this.controllerTest.update(cloudMessage);
            assertEquals(cloudMessage.getPlayerId(),this.controllerTest.game.getCurrentPlayerId());
        }
        catch(IllegalMoveException e){
            e.printStackTrace();
        }
    }
    /*public void testUpdateIllegalMove(){
        ChooseCloudMessage cloudMessage=new ChooseCloudMessage(2,4);
        try{
            this.controllerTest.game.setManuallyGamePhase(GameState.actionChooseCloud);
            this.controllerTest.update(cloudMessage);
            assertEquals(cloudMessage.getPlayerId(),this.controllerTest.game.getCurrentPlayerId());
        }
        catch(IllegalMoveException e){
            System.out.println(e.getMessage());
        }
    }*/

}