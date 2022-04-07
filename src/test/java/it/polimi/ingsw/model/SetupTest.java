package it.polimi.ingsw.model;

import it.polimi.ingsw.model.token.Student;
import junit.framework.TestCase;

import java.util.List;

public class SetupTest extends TestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        bag=new Bag(10,5);
    }

    Bag bag;
    public void testCreateIslands() {
        List<Island> islands = Setup.createIslands(12, bag);
        assertNotNull(islands);
        for (Island island: islands) {
            System.out.println("id island : "+island.getID());
            assertEquals(1,island.getIslandSize());
            for (Student stud : island.getStudents()) {
                System.out.println("student : " +stud.getId()+" color : "+stud.getColor());
            }
        }
    }

    public void testCreateProfessor() {

    }

    public void testCreatePlayer() {
    }

    public void testCreateClouds() {
    }
}