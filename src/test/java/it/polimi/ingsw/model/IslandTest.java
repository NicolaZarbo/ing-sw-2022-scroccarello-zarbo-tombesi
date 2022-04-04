package it.polimi.ingsw.model;

import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.model.token.TokenColor;
import it.polimi.ingsw.model.token.Tower;
import it.polimi.ingsw.model.token.TowerColor;
import junit.framework.TestCase;
import org.junit.*;

import java.util.ArrayList;
import java.util.Random;

public class IslandTest extends TestCase {
     static Island island ;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        System.out.println("oooo");
        island = new Island(1);
    }




    public void testAddStudent() {
    assertEquals(island.getIslandSize(),0);
        for (int i = 0; i < 5; i++) {
            Random rr = new Random();
            Student st = new Student(i,TokenColor.getColor(rr.nextInt(4)));
            island.addStudent(st);

        }
        assertNotNull(island.getStudents());
        tGetStudents();
    }

    public void tAddAllTower() {
    ArrayList<Tower> t= new ArrayList<>();

    for (int i = 0; i < 7; i++) {
            Random r = new Random();
            Tower tower = new Tower(TowerColor.getColor(r.nextInt(3)),i);
            t.add(tower);
        }
        island.addAllTowers(t);
        assertEquals(island.getTowers(),t);
    tGetTower();
    }

    public void testSetTower() {

    }

    public void testAddAllStudent() {

    }
    public  void tGetStudents(){
        for (Student stud: island.getStudents()) {
            System.out.println("stud "+ stud.getColor()+" id " +stud.getId());
        }
    }

    public void tGetTower() {
        for (Tower t: island.getTowers()) {
            assertNotNull(t);
            System.out.println(t.getColor()+" "+t.getId());
        }
    }

}