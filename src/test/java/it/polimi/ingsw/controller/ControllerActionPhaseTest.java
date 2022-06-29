package it.polimi.ingsw.controller;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.messages.clientmessages.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.util.ParameterObject;
import it.polimi.ingsw.model.tokens.Student;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Random;

/**It tests the action sun-controller.
 * @see ControllerActionPhaseTest*/
public class ControllerActionPhaseTest extends TestCase {

    ControllerActionPhase controllerTest;
    GameStub gameTest;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new GameStub(false,4,12);
        this.controllerTest=new ControllerActionPhase(gameTest);
        this.controllerTest.getGame().getPlanningPhase().setCloud();
    }

    /**It tests the movement of a token from entrance to dining room.*/
    public void testMoveStudentToHall() {
        ((GameStub)this.controllerTest.getGame()).setManuallyGamePhase(GameState.actionMoveStudent);
        Player player=this.controllerTest.getGame().getPlayer(0);
        Board board=player.getBoard();
        ArrayList<Student> entrance=board.getEntrance();
        for(int i=0;i<3;i++) {
            StudentToHallMessage message=new StudentToHallMessage(0,0);
            for(Student stud:entrance){
                message = new StudentToHallMessage(0, stud.getId());
                break;
            }
            try {
                this.controllerTest.moveStudentToHall(message);
            }
            catch(IllegalMoveException e) {
                e.printStackTrace();
            }
        }
        assertEquals(this.controllerTest.getGame().getActualState(),GameState.actionMoveMother);
    }

    /**It tests the error management when moving a token from entrance to dining room.*/
    public void testMoveWrongStudentToHall() {
        ((GameStub)this.controllerTest.getGame()).setManuallyGamePhase(GameState.actionMoveStudent);
        for (int i = 0; i < 3; i++) {
            StudentToHallMessage message = new StudentToHallMessage(0, i);
            try {
                this.controllerTest.moveStudentToHall(message);
            } catch (RuntimeException e) {
                assertNotNull(e);
            }
        }
    }

    /**It tests the error notify when moving a token not in the right game phase.*/
    public void testMoveStudentWrongPhase() {
        for (int i = 0; i < 3; i++) {
            StudentToHallMessage message = new StudentToHallMessage(0, i);
            try {
                this.controllerTest.moveStudentToHall(message);
            } catch (IllegalMoveException e) {
                System.out.println(e.getMessage());
            }
        }
        assertNotSame(this.controllerTest.getGame().getActualState(),GameState.actionMoveStudent);
    }

    /**It tests the cloud selection to refill entrance.*/
    public void testChooseCloud(){
        ((GameStub)this.controllerTest.getGame()).setManuallyGamePhase(GameState.actionChooseCloud);
        ChooseCloudMessage message=new ChooseCloudMessage(0,0);
        Player player=this.controllerTest.getGame().getPlayer(0);
        Board board=player.getBoard();
        board.initEntrance(new ArrayList<>(board.getEntrance().size()));
        try{
            this.controllerTest.chooseCloud(message);
        }
        catch(IllegalMoveException e){
            e.printStackTrace();
        }
    }

    /**It tests error management when choosing a cloud with full entrance.*/
    public void testChooseCloudFullEntrance() {
        ((GameStub)this.controllerTest.getGame()).setManuallyGamePhase(GameState.actionChooseCloud);
        ChooseCloudMessage message=new ChooseCloudMessage(0,0);
        try{
            this.controllerTest.chooseCloud(message);
        }
        catch(RuntimeException e){
            assertNotNull(e);
        }
    }

    /**It tests movement of token from entrance to island.*/
    public void testMoveStudentToIsland() {
        ((GameStub)this.controllerTest.getGame()).setManuallyGamePhase(GameState.actionMoveStudent);
        Player player=this.controllerTest.getGame().getPlayer(0);
        Board board=player.getBoard();
        ArrayList<Student> entrance=board.getEntrance();
        for(int i=0;i<3;i++) {
            StudentToIslandMessage message=new StudentToIslandMessage(0,0,0);
            for(Student stud:entrance){
                message = new StudentToIslandMessage(0,stud.getId(),i);
                break;
            }
            try {
                this.controllerTest.moveStudentToIsland(message);
            }
            catch(IllegalMoveException e) {
                e.printStackTrace();
            }
        }
        assertEquals(this.controllerTest.getGame().getActualState(),GameState.actionMoveMother);
    }

    /**It tests error handling when moving a student which is not in the entrance.*/
    public void testMoveStudentNotFound() {
        ((GameStub)this.controllerTest.getGame()).setManuallyGamePhase(GameState.actionMoveStudent);
        for(int i=0;i<3;i++) {
            StudentToIslandMessage message=new StudentToIslandMessage(0,i,i);
            try {
                this.controllerTest.moveStudentToIsland(message);
            }
            catch(RuntimeException e) {
                assertNotNull(e);
            }
        }
        assertNotSame(this.controllerTest.getGame().getActualState(),GameState.actionMoveMother);
    }

    /**It tests movement of student and error handling when trying to move student during wrong phase.*/
    public void testMoveStudentWrongState() {

        for(int i=0;i<3;i++) {
            StudentToIslandMessage message=new StudentToIslandMessage(0,i,i);
            try {
                this.controllerTest.moveStudentToIsland(message);
            }
            catch(IllegalMoveException e) {
                System.out.println(e.getMessage());
            }
        }
        assertNotSame(this.controllerTest.getGame().getActualState(),GameState.actionMoveMother);
    }

    /**It tests movement of mother nature token.*/
    public void testMoveMotherNature() {
        ((GameStub)this.controllerTest.getGame()).setManuallyGamePhase(GameState.actionMoveMother);
        int steps=this.controllerTest.getGame().getPlayedCard(this.controllerTest.getGame().getCurrentPlayerId()).getMoveMother();
        MoveMotherMessage message=new MoveMotherMessage(steps,0);
        try{
            this.controllerTest.moveMotherNature(message);
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    /**It tests illegal movement of mother nature and error handling (too many steps).*/
    public void testWrongMoveMotherNature() {
        ((GameStub)this.controllerTest.getGame()).setManuallyGamePhase(GameState.actionMoveMother);
        MoveMotherMessage message=new MoveMotherMessage(800,0);
        try{
            this.controllerTest.moveMotherNature(message);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    /**It tests change of turn. The turn has to pass to the following player.*/
    public void testChangeTurn() {
        int rnd=(new Random()).nextInt(0,1);
        if(rnd==0) this.testMoveStudentToIsland();
        else this.testMoveStudentToHall();
        this.testMoveMotherNature();
        this.testChooseCloud();
        assertEquals(1,this.controllerTest.getGame().getCurrentPlayerId());
    }

    /**It tests character invocation (expert mode only).*/
    public void testPlayCharacter() {
        CharacterCardMessage message=new CharacterCardMessage(gameTest.getCurrentPlayerId(), new ParameterObject(),gameTest.getCharacters().get(0).getId());
        int rng=(int) Math.random();
        if(rng==0) {
            gameTest.setManuallyGamePhase(GameState.actionMoveMother);
            assertEquals(GameState.actionMoveMother,gameTest.getActualState());
        }
        else {
            gameTest.setManuallyGamePhase(GameState.actionMoveStudent);
            assertEquals(GameState.actionMoveStudent, gameTest.getActualState());
        }
        gameTest.getPlayer(gameTest.getCurrentPlayerId()).getHand().addCoin();
        try{
            controllerTest.playCharacter(message);
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }

    }
}