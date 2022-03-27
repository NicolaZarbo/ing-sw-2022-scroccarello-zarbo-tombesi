package it.polimi.ingsw.model;

import junit.framework.TestCase;

public class GameTest extends TestCase {
    int n=2;
    int numeroIsole=12;
Game testG = new Game(true, n, numeroIsole);
    public void testGetIslands() {
       assertNotNull(testG.getIslands());
        for (Island island: testG.getIslands()) {
            assertNotNull(island);
            System.out.println("island id "+island.getID());
        }
    }

    public void testIsEasy() {
        System.out.println("is easy "+testG.isEasy());

    }

    public void testGetNPlayers() {
     //   assertNotNull("errore", testG.getNPlayers());
        assertEquals("errore",n, testG.getNPlayers());
        System.out.println(testG.getNPlayers());
    }

    public void testGetMotherNature() {

    }

    public void testSetMotherNature() {
    }

    public void testGetClouds() {
        assertNotNull("clouds are null",testG.getClouds());
        for (Cloud cloud : testG.getClouds()) {
            assertNotNull("cloud is null",cloud);
            System.out.println(cloud.getId());
        }

    }
    public void testGetPlayers(){
        assertNotNull("Players are null",testG.getPlayers());
        for (Player player: testG.getPlayers()) {
            System.out.println();
            assertNotNull("a player is null",player);
            Board board=player.getBoard();
            Hand hand = player.hand;
            assertNotNull(board);
            assertNotNull("mano null", hand);
            assertNotNull("missin coins", hand.coin);
            System.out.println("ncoin"+hand.coin);

            int i=0;
            int j=0;
            /* make board.tower momentarily public to test
            for( Tower tower : board.){
                assertNotNull(tower);
                System.out.println(tower.getColor() + " "+ i);
            i++;}*/
            for (AssistantCard ass: hand.assistant) {
                assertNotNull("carta ass"+ j+ "null",ass);
                System.out.println(ass.getMage() + " "+ j);

            j++;}

        }
    }
    public void testSetClouds() {
    }


}