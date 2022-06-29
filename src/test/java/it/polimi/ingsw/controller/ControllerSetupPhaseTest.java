package it.polimi.ingsw.controller;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.messages.clientmessages.PrePlayerMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LobbyPlayer;
import it.polimi.ingsw.enumerations.TowerColor;
import it.polimi.ingsw.enumerations.Mage;
import it.polimi.ingsw.enumerations.GameState;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**It tests the setup sub-controller.
 * @see ControllerSetupPhaseTest*/
public class ControllerSetupPhaseTest extends TestCase {
    ControllerSetupPhase cTest;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Game gTest=new GameStub(false, 4, 12);
        this.cTest=new ControllerSetupPhase(gTest);
    }

    /**It fails the creation of the player and tests the management of the error.*/
    public void testFailCreatePlayer() {
        LobbyPlayer player= new LobbyPlayer(TowerColor.black, Mage.mage1,"pippo");
        PrePlayerMessage message=new PrePlayerMessage(0,player.getTowerColor().ordinal(),player.getMage().ordinal(), player.getNickname());
        try{
            this.cTest.createPlayer(message);
        }
        catch(RuntimeException e){
            assertNotNull(e);
        }

    }

    /**It tests the player creation.*/
    public void testCreatePlayer(){
        LobbyPlayer player1= new LobbyPlayer(TowerColor.black, Mage.mage1,"pippo");
        LobbyPlayer player2=new LobbyPlayer(TowerColor.white,Mage.mage2,"donald");
        List<String> list= new ArrayList<>(4);
        list.add(player1.getNickname());
        list.add(player2.getNickname());
        this.cTest.getSetup().setPreOrder(list);
        this.cTest.getSetup().addPrePlayer(player1);
        PrePlayerMessage message=new PrePlayerMessage(0,player1.getTowerColor().ordinal(),player1.getMage().ordinal(), player1.getNickname());
        ((GameStub)this.cTest.getModel()).setManuallyGamePhase(GameState.setupPlayers);
        try{
            this.cTest.createPlayer(message);
            assertTrue(true);
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }
}