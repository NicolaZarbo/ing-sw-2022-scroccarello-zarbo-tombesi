package it.polimi.ingsw.messages;

import junit.framework.TestCase;

public class MessageExchangeTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    private void testSerialize(GenericMessage message) {
        message.serialize();
        assertEquals(message.getClass().getSimpleName(),message.getType());
        assertNotNull(message.getJson());
    }


}