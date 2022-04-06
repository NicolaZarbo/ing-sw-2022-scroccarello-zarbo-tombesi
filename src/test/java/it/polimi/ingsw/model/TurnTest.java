package it.polimi.ingsw.model;

import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.model.token.Tower;
import it.polimi.ingsw.model.token.TowerColor;
import junit.framework.TestCase;

import java.util.ArrayList;

public class TurnTest extends TestCase {
    Game game = new Game(true,2,12);
    ArrayList<Tower> torriUguali= new ArrayList<>();
    @Override
    public void setUp() throws Exception {
        super.setUp();
        torriUguali.add(new Tower(TowerColor.black,1));
        torriUguali.add(new Tower(TowerColor.black,10));

    }

    public void testMoveInHall() {

    }

    public void testMoveToIsland() {
        Player player= game.getPlayer(1);
        int idIsland=5;
        Student stud=player.getBoard().getEntrance().get(0);
        int studId= stud.getId();
        Turn.moveToIsland(1,studId,idIsland,game);
        assertTrue(game.getIsland(idIsland).getStudents().contains(stud));
    }

    public void testMoveMotherNature() {
        int nSteps=45;
        int posIniz=game.getMotherNature().getPosition();
        Turn.moveMotherNature(nSteps,game);
        assertTrue(game.getMotherNature().getPosition()==Math.floorMod(posIniz+nSteps,game.getIslands().size()));
        System.out.println("isola iniziale : "+posIniz+ "  pos finale :"+ game.getMotherNature().getPosition());

    }

    public void testMoveFromCloudToEntrance() {
        Student[] studOnCloud=game.getClouds()[1].getStud();
        Turn.moveFromCloudToEntrance(game,2,1);
        for (Student stud:studOnCloud) {
            assertTrue(game.getPlayer(1).getBoard().getEntrance().contains(stud));
        }

    }

    public void testIsUnifiableNext() {

        game.getIsland(1).setTower(new Tower(TowerColor.black,1));
        game.getIsland(2).setTower(new Tower(TowerColor.black,3));
        assertTrue(Turn.isUnifiableNext(game,1));
    }

    public void testIsUnifiableBefore() {

    }

    public void testUnifyNext() {
        game.getIsland(1).setTower(new Tower(TowerColor.black,1));
        game.getIsland(2).setTower(new Tower(TowerColor.black,3));
        if(Turn.isUnifiableNext(game,1))
            Turn.unifyNext(game,1);
        assertTrue(game.getIsland(2)==null);
        assertTrue("size isl: "+game.getIsland(1).getIslandSize(),game.getIsland(1).getIslandSize()==2);
    }

    public void testUnifyBefore() {
        game.getIsland(1).setTower(new Tower(TowerColor.black,1));
        game.getIsland(0).setTower(new Tower(TowerColor.black,3));
        if(Turn.isUnifiableBefore(game,1))
            Turn.unifyBefore(game,1);
        assertNotNull(game.getIsland(1));
        assertTrue(game.getIsland(0)==null);
        assertTrue("size isl: "+game.getIsland(1).getIslandSize(),game.getIsland(1).getIslandSize()==2);
    }

    public void testIsTeacher() {
    }

    public void testGetTeacher() {
    }

    public void testCalculateInfluence() {
        game.getIsland(0).setTower(new Tower(TowerColor.getColor(1),1));
        game.getIsland(0).setTower(new Tower(TowerColor.getColor(1),1));

        int influnce = Turn.calculateInfluence(game,1);
        System.out.println(influnce);
    }

    public void testIslandConquest() {
    }
}