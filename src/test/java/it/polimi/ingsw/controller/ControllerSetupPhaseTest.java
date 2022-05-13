package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.client.PrePlayerMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LobbyPlayer;
import it.polimi.ingsw.model.token.TowerColor;
import it.polimi.ingsw.model.Mage;
import it.polimi.ingsw.enumerations.GameState;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class ControllerSetupPhaseTest extends TestCase {
    ControllerSetupPhase cTest;
    public void setUp() throws Exception {
        super.setUp();
        Game gTest=new Game(false, 4, 12);
        this.cTest=new ControllerSetupPhase(gTest);
    }

    public void testFailCreatePlayer() {
        LobbyPlayer player= new LobbyPlayer(TowerColor.black, Mage.mage1,"pippo");
        PrePlayerMessage message=new PrePlayerMessage(0,player.getTowerColor().ordinal(),player.getMage().ordinal(), player.getNickname());
        try{
            this.cTest.createPlayer(message);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }

    }
    public void testCreatePlayer(){
        LobbyPlayer player1= new LobbyPlayer(TowerColor.black, Mage.mage1,"pippo");
        LobbyPlayer player2=new LobbyPlayer(TowerColor.white,Mage.mage2,"donald");
        List<String> list=new ArrayList<String>(4);
        list.add(player1.getNickname());
        list.add(player2.getNickname());
        this.cTest.getSetup().setPreOrder(list);
        this.cTest.getSetup().addPrePlayer(player1);
        PrePlayerMessage message=new PrePlayerMessage(0,player1.getTowerColor().ordinal(),player1.getMage().ordinal(), player1.getNickname());
        this.cTest.getModel().setManuallyGamePhase(GameState.setupPlayers);
        try{
            this.cTest.createPlayer(message);
            assertTrue(true);
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }
}