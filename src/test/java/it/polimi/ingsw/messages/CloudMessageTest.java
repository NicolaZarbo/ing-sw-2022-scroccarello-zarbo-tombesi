package it.polimi.ingsw.messages;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.messages.server.CloudMessage;
import it.polimi.ingsw.messages.server.ServerMessage;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;
import junit.framework.TestCase;

import java.util.Arrays;

public class CloudMessageTest extends TestCase {

    public void testSerialize() {
        Game game = new Game(true,2);
        ServerMessage message =new CloudMessage(game);
        String js=message.getJson();
        System.out.println(js);
        JsonObject jj = JsonParser.parseString(js).getAsJsonObject();
        assertTrue(jj.get("messageType").getAsString(),"CloudMessage".equals( jj.get("messageType").getAsString()));
        tParseMessage(js);

    }



    public void testGetClouds() {
    }

    public void tParseMessage(String js) {
        ServerMessage message =  MessageFactory.getMessageFromServer(js);
        System.out.println("kkkk0"+ message.getState());
        for (Integer[] cloud:((CloudMessage)message).getClouds()) {
            System.out.println( "nuvola deserializzata, contains : "+ Arrays.toString(cloud));
        }
    }
}