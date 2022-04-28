package it.polimi.ingsw.messages.server;

import it.polimi.ingsw.model.Game;
import junit.framework.TestCase;

public class CharacterTokenMessageTest extends TestCase {

    public void testGetStudents() {
    }

    public void testParseMessage() {
    }
    public void test(){
        CharacterTokenMessage mex1 = new CharacterTokenMessage(1, new Game(false,2));
        System.out.println(mex1.getType()+" " + mex1.getJson());
    }
}