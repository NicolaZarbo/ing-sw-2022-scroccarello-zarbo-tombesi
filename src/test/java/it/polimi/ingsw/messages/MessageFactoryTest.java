package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.client.*;
import it.polimi.ingsw.messages.server.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Mage;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.model.token.TokenColor.*;
import it.polimi.ingsw.model.token.TowerColor;
import junit.framework.TestCase;

import java.util.ArrayList;

public class MessageFactoryTest extends TestCase {

    private Game gameTest;

    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new Game(false,4,12);
    }

    public void testGetMessageFromServer() {
        GenericMessage msg;
        GenericMessage msgTest;

        //  CloudMessage
        msg=new CloudMessage(gameTest);
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof CloudMessage);

        //MotherPositionMessage
        msg=new MotherPositionMessage(gameTest);
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof MotherPositionMessage);

        //IslandMessage
        msg=new IslandsMessage(gameTest);
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof IslandsMessage);

        //SingleBoardMessage
        msg=new SingleBoardMessage(gameTest,gameTest.getCurrentPlayerId());
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof SingleBoardMessage);

        //ErrorMessageForClient
        msg=new ErrorMessageForClient(gameTest.getCurrentPlayerId(),new RuntimeException("test exception"));
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof ErrorMessageForClient);

        //MultipleServerMessage
        /*msg=new MultipleServerMessage(messaggio da capire);
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof MotherPositionMessage);*/

        //CharacterTokenMessage
        msg=new CharacterTokenMessage(1,gameTest);
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof CharacterTokenMessage);

        //CharacterUpdateMessage
        msg=new CharacterUpdateMessage(1,gameTest);
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof CharacterUpdateMessage);

        //PlayerSetUpMessage
        ArrayList<String> names=new ArrayList<String>();
        names.add("pippo"); names.add("pluto"); names.add("paperino"); names.add("minnie");
        //System.out.println(names);
        gameTest.getSetupPhase().setPreOrder(names);
        msg=new PlayerSetUpMessage(gameTest, gameTest.getCurrentPlayerId());
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof PlayerSetUpMessage);

        //PlayedAssistantMessage
        msg=new PlayedAssistantMessage(gameTest);
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof PlayedAssistantMessage);

        //WholeGameMessage
        msg=new WholeGameMessage(gameTest);
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof WholeGameMessage);

        //ChangePhaseMessage
        msg=new ChangePhaseMessage(gameTest);
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof ChangePhaseMessage);

        //ChangeTurnMessage
        msg=new ChangeTurnMessage(gameTest);
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof ChangeTurnMessage);

        //crash
        try{
            String breaker="lmao";
            msgTest=MessageFactory.getMessageFromClient(breaker);
        }
        catch(RuntimeException e){
            assertNotNull(e);
        }
    }

    public void testGetMessageFromClient() {
        GenericMessage msg;
        GenericMessage msgTest;

        //ChooseCloud
        msg = new ChooseCloudMessage(gameTest.getClouds()[0].getId(), gameTest.getCurrentPlayerId());
        msgTest = MessageFactory.getMessageFromClient(msg.getJson());
        assertTrue(msgTest instanceof ChooseCloudMessage);

        //MoveMother
        msg = new MoveMotherMessage(1, gameTest.getCurrentPlayerId());
        msgTest = MessageFactory.getMessageFromClient(msg.getJson());
        assertTrue(msgTest instanceof MoveMotherMessage);

        //StudentToHall
        msg = new StudentToHallMessage(gameTest.getCurrentPlayerId(),0);
        msgTest = MessageFactory.getMessageFromClient(msg.getJson());
        assertTrue(msgTest instanceof StudentToHallMessage);

        //PlayAssistant
        msg = new PlayAssistantMessage(gameTest.getCurrentPlayerId(), gameTest.getPlayer(gameTest.getCurrentPlayerId()).getHand().getAssistant().get(0).getId());
        msgTest = MessageFactory.getMessageFromClient(msg.getJson());
        assertTrue(msgTest instanceof PlayAssistantMessage);

        //StudentToIsland
        msg = new StudentToIslandMessage(gameTest.getCurrentPlayerId(),0,0);
        msgTest = MessageFactory.getMessageFromClient(msg.getJson());
        assertTrue(msgTest instanceof StudentToIslandMessage);

        //CharacterCardMessage
        msg = new CharacterCardMessage(gameTest.getCurrentPlayerId(),new ParameterObject(),2);
        msgTest = MessageFactory.getMessageFromClient(msg.getJson());
        assertTrue(msgTest instanceof CharacterCardMessage);

        //PrePlayerMessage
        msg = new PrePlayerMessage(0, TowerColor.listGetLastIndex(), Mage.listGetLastIndex(),"donald");
        msgTest = MessageFactory.getMessageFromClient(msg.getJson());
        assertTrue(msgTest instanceof PrePlayerMessage);

        //crash
        try{
            String breaker="lmao";
            msgTest=MessageFactory.getMessageFromClient(breaker);
        }
        catch(RuntimeException e){
            assertNotNull(e);
        }
    }

    public void testSetUpMessageFactory() {

    }
}