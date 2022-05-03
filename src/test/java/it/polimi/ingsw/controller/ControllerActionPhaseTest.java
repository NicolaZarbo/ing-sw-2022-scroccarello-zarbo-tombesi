package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.messages.client.ChooseCloudMessage;
import it.polimi.ingsw.messages.client.MoveMotherMessage;
import it.polimi.ingsw.messages.client.StudentToHallMessage;
import it.polimi.ingsw.messages.client.StudentToIslandMessage;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.token.Student;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Random;

public class ControllerActionPhaseTest extends TestCase {

    ControllerActionPhase controllerTest;
    Game gameTest;
    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new Game(false,4,12);
        this.controllerTest=new ControllerActionPhase(gameTest);
        this.controllerTest.getGame().getPlanningPhase().setCloud();
    }
    public void testMoveStudentToHall() {
        Player player=this.controllerTest.getGame().getPlayer(0);
        Board board=player.getBoard();
        ArrayList<Student> entrance=board.getEntrance();
        this.controllerTest.getGame().setManuallyGamePhase(GameState.actionMoveStudent);
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
            catch(NoTokenFoundException e){
                e.printStackTrace();
            }
        }
        assertEquals(this.controllerTest.getGame().getActualState(),GameState.actionMoveMother);
    }

    public void testMoveWrongStudentToHall() {
        this.controllerTest.getGame().setManuallyGamePhase(GameState.actionMoveStudent);
        for (int i = 0; i < 3; i++) {
            StudentToHallMessage message = new StudentToHallMessage(0, i);
            try {
                this.controllerTest.moveStudentToHall(message);
            } catch (IllegalMoveException e) {
                e.printStackTrace();
            } catch (NoTokenFoundException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    public void testMoveStudentWrongPhase() {
        for (int i = 0; i < 3; i++) {
            StudentToHallMessage message = new StudentToHallMessage(0, i);
            try {
                this.controllerTest.moveStudentToHall(message);
            } catch (IllegalMoveException e) {
                System.out.println(e.getMessage());
            } catch (NoTokenFoundException e) {
                e.printStackTrace();
            }
        }
        assertNotSame(this.controllerTest.getGame().getActualState(),GameState.actionMoveStudent);
    }
    public void testChooseCloud(){
        this.controllerTest.getGame().setManuallyGamePhase(GameState.actionChooseCloud);
        ChooseCloudMessage message=new ChooseCloudMessage(0,0);
        Player player=this.controllerTest.getGame().getPlayer(0);
        Board board=player.getBoard();
        board.setEntrance(new ArrayList<>(board.getEntrance().size()));
        try{
            this.controllerTest.chooseCloud(message);
        }
        catch(IllegalMoveException e){
            e.printStackTrace();
        }
    }
    public void testChooseCloudFullEntrance() {
        this.controllerTest.getGame().setManuallyGamePhase(GameState.actionChooseCloud);
        ChooseCloudMessage message=new ChooseCloudMessage(0,0);
        try{
            this.controllerTest.chooseCloud(message);
        }
        catch(IllegalMoveException e){
            System.out.println(e.getMessage());
        }
    }

    public void testMoveStudentToIsland() {
        Player player=this.controllerTest.getGame().getPlayer(0);
        Board board=player.getBoard();
        ArrayList<Student> entrance=board.getEntrance();
        this.controllerTest.getGame().setManuallyGamePhase(GameState.actionMoveStudent);
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
            catch(NoTokenFoundException e){
                e.printStackTrace();
            }
        }
        assertEquals(this.controllerTest.getGame().getActualState(),GameState.actionMoveMother);
    }

    public void testMoveStudentNotFound() {
        this.controllerTest.getGame().setManuallyGamePhase(GameState.actionMoveStudent);
        for(int i=0;i<3;i++) {
            StudentToIslandMessage message=new StudentToIslandMessage(0,i,i);
            try {
                this.controllerTest.moveStudentToIsland(message);
            }
            catch(IllegalMoveException e) {
                e.printStackTrace();
            }
            catch(NoTokenFoundException e){
                System.out.println(e.getMessage());
            }
        }
        assertNotSame(this.controllerTest.getGame().getActualState(),GameState.actionMoveMother);
    }

    public void testMoveStudentWrongState() {

        for(int i=0;i<3;i++) {
            StudentToIslandMessage message=new StudentToIslandMessage(0,i,i);
            try {
                this.controllerTest.moveStudentToIsland(message);
            }
            catch(IllegalMoveException e) {
                System.out.println(e.getMessage());
            }
            catch(NoTokenFoundException e){
                e.printStackTrace();
            }
        }
        assertNotSame(this.controllerTest.getGame().getActualState(),GameState.actionMoveMother);
    }

    public void testMoveMotherNature() {
        this.controllerTest.getGame().setManuallyGamePhase(GameState.actionMoveMother);
        int steps=this.controllerTest.getGame().getPlayedCard(this.controllerTest.getGame().getCurrentPlayerId()).getMoveMother();
        MoveMotherMessage message=new MoveMotherMessage(steps,0);
        try{
            this.controllerTest.moveMotherNature(message);
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }
    public void testWrongMoveMotherNature() {
        this.controllerTest.getGame().setManuallyGamePhase(GameState.actionMoveMother);
        MoveMotherMessage message=new MoveMotherMessage(800,0);
        try{
            this.controllerTest.moveMotherNature(message);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    public void testChangeTurn() {
        int rnd=(new Random()).nextInt(0,1);
        if(rnd==0) this.testMoveStudentToIsland();
        else this.testMoveStudentToHall();
        this.testMoveMotherNature();
        this.testChooseCloud();
        assertEquals(1,this.controllerTest.getGame().getCurrentPlayerId());
    }

    public void testPlayCharacter() {
        return;
        //still implementing
    }
}