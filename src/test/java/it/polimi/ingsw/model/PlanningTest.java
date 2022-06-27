package it.polimi.ingsw.model;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.exceptions.EmptyBagException;
import junit.framework.TestCase;

import java.util.*;

/**It tests the planning component of the game.
 * @see Planning*/
public class PlanningTest extends TestCase {

    private Game gameTest;
    private Planning roundTest;
    private Cloud[] clouds;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new GameStub(false,4,12);
        this.roundTest=gameTest.getPlanningPhase();
    }

    /**It tests the refill of clouds at the beginning of the round.*/
    public void testSetCloud(){
        try {
            roundTest.setCloud();
            clouds=gameTest.getClouds();
            for(int i=0;i<gameTest.getNPlayers();i++){
                assertNotNull(clouds[i]);
                assertEquals((gameTest.getNPlayers()%2)+3,clouds[i].getStud().length);
            }
        }
        catch(EmptyBagException e){
            e.printStackTrace();
        }
    }

    /**It tests the order calculation by comparing it with a prediction.*/
    public void testRoundOrder(){
        int[] cardTest=new int[4];
        List<Integer> predict=new ArrayList<>();
        for(int i=0;i<4;i++){
            cardTest[i]=i*10;
            predict.add(i);
        }
        for(Player p : gameTest.getPlayers()){
            roundTest.playCard(p.getId(),cardTest[p.getId()]);
        }
        roundTest.roundOrder();
        List<Integer> order=roundTest.getRoundOrder(gameTest);
        assertEquals(predict,order);
    }
}