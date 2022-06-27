package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.Mage;
import it.polimi.ingsw.exceptions.CardNotFoundException;
import junit.framework.TestCase;

import java.util.ArrayList;

/**It tests the hand of the player and operations it can do.
 * @see Hand*/
public class HandTest extends TestCase {
ArrayList<AssistantCard> assistants;
Hand hand ;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        assistants = new ArrayList<>() ;
        assertNotNull(assistants);
        assertEquals(0,assistants.size());
        for (int i = 0; i < 10; i++) {
            assistants.add( new AssistantCard(i,i+1,(i/2)+1, Mage.mage1));
            assertNotNull(assistants.get(i));
        }
        hand= new Hand(assistants);
        assertNotNull(hand);
        for (AssistantCard ass: hand.getAssistant()) {
            assertNotNull(ass);
            assertEquals(ass.getMage(),Mage.mage1);

        }
    }


    /**It tests the pick of a coin from the dining room (expert mode only).*/
    public void testAddCoin() {
        assertNotNull(this.hand);
        for (int i = 0; i < 8; i++) {
            hand.addCoin();
            assertTrue(hand.enoughCoin(i));
        }
        this.tPayCoin();
    }

    /**It tests coin payment.*/
    public void tPayCoin() {
        for (int i = 0; i < 8; i++) {
            int prevc=hand.getCoin();
            assertTrue(hand.enoughCoin(1));
            hand.payCoin(1);
            assertEquals(prevc-1,hand.getCoin());
        }
    }

    /**It tests assistant cards playing.*/
    public void testPlayAssistant() {
        assertEquals(10,hand.getAssistant().size());
        assertEquals(0,hand.getDiscarded().size());
        for(int i=0;i<=10;i++){
            try{
                AssistantCard ac=hand.playAssistant(i);
                assertNotNull(ac);
            }
            catch(CardNotFoundException e){
                break;
            }
        }
        assertEquals(10, hand.getDiscarded().size());
        assertEquals(0, hand.getAssistant().size());
    }


}