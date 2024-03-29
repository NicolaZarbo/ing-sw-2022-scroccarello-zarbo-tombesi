package it.polimi.ingsw.messages;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.messages.clientmessages.*;
import it.polimi.ingsw.messages.servermessages.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.enumerations.Mage;
import it.polimi.ingsw.util.ParameterObject;
import it.polimi.ingsw.enumerations.TowerColor;
import junit.framework.TestCase;

import java.util.ArrayList;

/**The test class for factory of messages. It tests the creation of the messages using the factory method.*/
public class MessageFactoryTest extends TestCase {

    private Game gameTest;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new GameStub(false,4,12);
    }

    /**It tests the creation of server messages from json string.*/
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

        //ErrorMessageForClient
        ((GameStub)gameTest).setManuallyGamePhase(GameState.planPlayCard);
        msg=new ErrorMessageForClient(gameTest.getPlayer(gameTest.getCurrentPlayerId()).getNickname(),new RuntimeException("test exception"));
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof ErrorMessageForClient);

        //MultipleServerMessage
        /*msg=new MultipleServerMessage(messaggio da capire);
        msgTest=MessageFactory.getMessageFromServer(msg.getJson());
        assertTrue(msgTest instanceof MotherPositionMessage);*/

        //CharacterTokenMessage
        if(gameTest.getCharacters().stream().map(s->s.getId()).toList().contains(1)) {
            msg = new CharacterTokenMessage(1, gameTest);
            msgTest = MessageFactory.getMessageFromServer(msg.getJson());
            assertTrue(msgTest instanceof CharacterTokenMessage);

            //CharacterUpdateMessage
            msg = new CharacterUpdateMessage(1, gameTest);
            msgTest = MessageFactory.getMessageFromServer(msg.getJson());
            assertTrue(msgTest instanceof CharacterUpdateMessage);
        }

        //PlayerSetUpMessage
        ArrayList<String> names= new ArrayList<>();
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

    /**It tests the creation of client messages from json string.*/
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
        ((GameStub)gameTest).setManuallyGamePhase(GameState.planPlayCard);
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

        testSerialize(msgTest);
    }

    /**It tests the serialization of a message object.
     * @param message the object message with its parameters*/
    private void testSerialize(GenericMessage message) {
        message.serialize();
        assertEquals(message.getClass().getSimpleName(),message.getType());
        assertNotNull(message.getJson());
    }
}