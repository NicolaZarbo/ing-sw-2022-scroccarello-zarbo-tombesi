package it.polimi.ingsw.controller;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.messages.clientmessages.PlayAssistantMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.enumerations.GameState;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ControllerPlanningPhaseTest extends TestCase {
    public GameStub gameTest;
    public ControllerPlanningPhase cTest;
    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new GameStub(false,4,12);
        this.cTest=new ControllerPlanningPhase(gameTest);
    }

    public void testRightPlayAssistantCard() {
        PlayAssistantMessage message=new PlayAssistantMessage(0,1);
        ((GameStub)this.cTest.getGame()).setManuallyGamePhase(GameState.planPlayCard);
        try{
            this.cTest.playAssistantCard(message);
            System.out.println("card played correctly");
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    public void testPhaseErrorPlayAssistantCard() {
        PlayAssistantMessage message=new PlayAssistantMessage(0,1);
        try{
            this.cTest.playAssistantCard(message);
            System.out.println("card played correctly");
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    public void testNotYourTurnPlayAssistancCard(){
        PlayAssistantMessage message=new PlayAssistantMessage(1,1);
        ((GameStub)this.cTest.getGame()).setManuallyGamePhase(GameState.planPlayCard);
        try{
            this.cTest.playAssistantCard(message);
            System.out.println("card played correctly");
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    public void testPlanning(){
        List<Integer> prediction=new ArrayList<>(4);
        ((GameStub)this.cTest.getGame()).setManuallyGamePhase(GameState.planPlayCard);
        for(int i=0;i<this.cTest.getGame().getPlayers().length;i++){
            PlayAssistantMessage message=new PlayAssistantMessage(i,11*i);
            this.cTest.playAssistantCard(message);
            prediction.add(i);
        }
        assertEquals(prediction,this.cTest.getActualOrder());
    }

    public void testGetActualOrder(){
        assertNotNull(this.cTest.getActualOrder());
    }

    public void testGetGame(){
        assertNotNull(this.cTest.getGame());
    }

    public void testGetRound(){
        assertNotNull(this.cTest.getModelRound());
    }

}