package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.messages.server.CloudMessage;
import it.polimi.ingsw.messages.server.ServerMessage;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;
import junit.framework.TestCase;

public class CloudMessageTest extends TestCase {

    public void testSerialize() {
        Game game = new Game(true,2,12);
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
        for (Cloud cloud:((CloudMessage)message).getClouds()) {
            System.out.println( "nuvola deserializzata : "+cloud.getId());
        }
    }
}