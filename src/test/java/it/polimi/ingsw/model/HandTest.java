package it.polimi.ingsw.model;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.ArrayList;

public class HandTest extends TestCase {
ArrayList<AssistantCard> assistants = new ArrayList<>() ;
Hand hand ;
@Before
    public void testCreateHand() {
        assertNotNull(assistants);
        assertEquals(assistants.size(),0);
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
        this.tAddCoin();
        tPlayAssistant();

    }

    public void tAddCoin() {
        assertNotNull(this.hand);
        for (int i = 0; i < 8; i++) {
            hand.addCoin();
            assertTrue(hand.enoughCoin(i));
            System.out.println("coin rimasti "+hand.getCoin());
        }
        this.tPayCoin();
    }

    public void tPayCoin() {
        for (int i = 0; i < 8; i++) {
            assertTrue("coin presenti"+hand.getCoin(),hand.enoughCoin(1));

            hand.payCoin(1);
            System.out.println("coin rimasti "+hand.getCoin());

        }
    }

    public void testEnoughCoin() {
    }

    public void tPlayAssistant() {
        for (AssistantCard ass:hand.getAssistant()) {
            assertNotNull(ass);
            System.out.println("id : "+ ass.getId() + " mother "+ass.getMoveMother()+" turn "+ ass.getValTurn());

        }
    }


}