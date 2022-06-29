package it.polimi.ingsw.controller;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.messages.clientmessages.ChooseCloudMessage;
import junit.framework.TestCase;

/**It tests the main controller of the MVC implemented by the application.
 * @see Controller*/
public class ControllerTest extends TestCase {
    Controller controllerTest;
    @Override
    public void setUp() throws Exception{
        super.setUp();
        this.controllerTest=new Controller(new GameStub(false,4,12));
    }

    /**It tests the getter of the planning sub-controller*/
    public void testGetControllerRound() {
        assertNotNull(controllerTest.getControllerRound());
    }

    /**It tests the getter of the action sub-controller*/
    public void testGetControllerTurn() {
        assertNotNull(controllerTest.getControllerTurn());
    }

    /**It tests the getter of the setup sub-controller*/
    public void testGetControllerSetup() {
        try{
            controllerTest.getControllerSetup();
            System.out.println("ok");
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    /**It tests the update of the model by sending a notify with a client message containing its parameters.*/
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

    /**It tests the management of wrong message notify (no action on the model).*/
    public void testUpdateIllegalMove(){
        ChooseCloudMessage cloudMessage=new ChooseCloudMessage(2,4);
        try{
            this.controllerTest.update(cloudMessage);
        }
        catch(IllegalMoveException e){
            assertNotNull(this.controllerTest.getGame().getClouds()[2].getStud()[0]);
        }
    }

}