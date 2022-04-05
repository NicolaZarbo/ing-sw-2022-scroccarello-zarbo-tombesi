package it.polimi.ingsw.model;

import junit.framework.TestCase;

import java.util.List;

public class SetupTest extends TestCase {
Bag bag=new Bag(10,5);
    public void testCreateIslands() {
        List<Island> islands = Setup.createIslands(12, bag);
        assertNotNull(islands);
        for (Island island: islands) {
            System.out.println(island.getID());
            assertEquals(0,island.getIslandSize());
        }
    }

    public void testCreateProfessor() {

    }

    public void testCreatePlayer() {
    }

    public void testCreateClouds() {
    }
}