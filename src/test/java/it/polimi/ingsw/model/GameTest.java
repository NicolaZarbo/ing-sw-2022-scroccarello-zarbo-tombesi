package it.polimi.ingsw.model;

import junit.framework.TestCase;

public class GameTest extends TestCase {
    int n=2;
    int numeroIsole=12;
Game game = new Game(true, n, numeroIsole);
    public void testGetIslands() {
       assertNotNull(game.getIslands());
        for (Island island: game.getIslands()) {
            assertNotNull(island);
            System.out.println("island id "+island.getID());
        }
    }
    public void testIsEasy() {
        System.out.println("is easy "+ game.isEasy());

    }

    public void testGetNPlayers() {
     //   assertNotNull("errore", testG.getNPlayers());
        assertEquals("errore",n, game.getNPlayers());
        System.out.println(game.getNPlayers());
    }

    public void testGetMotherNature() {

    }

    public void testSetMotherNature() {

    }

    public void testGetClouds() {
        assertNotNull("clouds are null", game.getClouds());
        for (Cloud cloud : game.getClouds()) {
            assertNotNull("cloud is null",cloud);
            System.out.println(cloud.getId());
        }

    }
    public void testGetPlayers(){
        assertNotNull("Players are null", game.getPlayers());
        for (Player player: game.getPlayers()) {
            System.out.println();
            assertNotNull("a player is null",player);
            Board board=player.getBoard();
            Hand hand = player.getHand();
            assertNotNull(board);
            assertNotNull("mano null", hand);
            assertNotNull("missin coins", hand.getCoin());
            System.out.println("ncoin"+hand.getCoin());

            int i=0;
            int j=0;
            /* make board.tower momentarily public to test
            for( Tower tower : board.){
                assertNotNull(tower);
                System.out.println(tower.getColor() + " "+ i);
            i++;}*/
            for (AssistantCard ass: hand.getAssistant()) {
                assertNotNull("carta ass"+ j+ "null",ass);
                System.out.println(ass.getMage() + " "+ j);

            j++;}

        }
    }
    public void testSetClouds() {
    }
    public void testCreateTestAmbient(){
     /*   Round round = new Round(0,2, game.getPlayers(), game);
        for (Cloud cloud: game.getClouds()) {
            round.setClouds();
        }*/
    }


}