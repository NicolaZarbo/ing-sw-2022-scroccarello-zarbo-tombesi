package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;
import junit.framework.TestCase;

public class CloudMessageTest extends TestCase {

    public void testSerialize() {
        Game game = new Game(true,2,12);
        CloudMessage message = CloudMessage.serialize(game);
        String js=message.getJson();
        System.out.println(js);
        tParseMessage(js);
    }

    public void testGetClouds() {
    }

    public void tParseMessage(String js) {
        CloudMessage message = CloudMessage.parseMessage(js);
        for (Cloud cloud:message.getClouds()) {
            System.out.println( "nuvola deserializzata : "+cloud.getId());
        }
    }
}