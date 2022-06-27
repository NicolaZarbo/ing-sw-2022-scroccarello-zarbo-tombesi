package it.polimi.ingsw.model;

import it.polimi.ingsw.model.tokens.Student;
import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.model.tokens.Tower;
import it.polimi.ingsw.enumerations.TowerColor;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Random;

/**It tests the islands of the game.
 * @see Island*/
public class IslandTest extends TestCase {
     static Island island ;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        island = new Island(1);
    }

    /**It tests the insert of a student token on an island.*/
    public void testAddStudent() {
    assertEquals(island.getIslandSize(),1);
    int prevdim=island.getStudents().size();
        for (int i = 0; i < 5; i++) {
            Random rr = new Random();
            Student st = new Student(i,TokenColor.getColor(rr.nextInt(4)));
            island.addStudent(st);
        }
        assertEquals(prevdim+5,island.getStudents().size());
    }

    /**It tests insert of groups of students on island.*/
    public void testAddAllStudents() {
        ArrayList<Student> s= new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Random r = new Random();
            Student stud = new Student(i,TokenColor.getColor(r.nextInt(3)));
            s.add(stud);
        }
        island.addAllStudents(s);
        assertEquals(s,island.getStudents());
    }

    /**It tests insert of groups of towers on island*/
    public void testAddAllTower() {
    ArrayList<Tower> t= new ArrayList<>();
    for (int i = 0; i < 7; i++) {
            Random r = new Random();
            Tower tower = new Tower(TowerColor.getColor(r.nextInt(3)),i);
            t.add(tower);
        }
        island.addAllTowers(t);
        assertEquals(t,island.getTowers());
    }

    /**It tests the insert of single towers on the island.*/
    public void testSetTower() {
        for (int i = 0; i < 100; i++) {
            Random r = new Random();
            Tower tower = new Tower(TowerColor.getColor(r.nextInt(3)),i);
            island.setTower(tower);
            assertTrue(island.getTowers().contains(tower));
        }
    }

    /**It tests sub-island insert after a merge is invoked.*/
    public void testAddSubIsland(){
        int prevsize= island.getSubIslands().size();
        ArrayList<Student> prevs=island.getEveryStudents();
        ArrayList<Tower> prevt=island.getEveryTower();
        Island sub=new Island(2);
        assertNotNull(sub);
        for(int i=0;i<3;i++){
            Random r = new Random();
            Tower tower = new Tower(TowerColor.getColor(r.nextInt(3)),i);
            prevt.add(tower);
            island.setTower(tower);
            Random rr = new Random();
            Student st = new Student(i,TokenColor.getColor(rr.nextInt(4)));
            prevs.add(st);
            island.addStudent(st);
        }
        island.addSubIsland(sub);
        assertTrue(island.getSubIslands().size()==prevsize+1);
        assertEquals(prevt,island.getEveryTower());
        assertEquals(prevs,island.getEveryStudents());
    }
}