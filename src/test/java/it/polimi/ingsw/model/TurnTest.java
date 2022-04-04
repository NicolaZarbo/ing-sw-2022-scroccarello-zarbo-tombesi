package it.polimi.ingsw.model;

import it.polimi.ingsw.model.token.Student;
import junit.framework.TestCase;

public class TurnTest extends TestCase {
    Game game = new Game(true,2,12);

    @Override
    public void setUp() throws Exception {
        super.setUp();

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
        Student[] studOnCloud=game.getClouds()[2].getStud();
        Turn.moveFromCloudToEntrance(game,2,1);
        for (Student stud:studOnCloud) {
            assertTrue(game.getPlayer(1).getBoard().getEntrance().contains(stud));
        }

    }

    public void testIsUnifiableNext() {
    }

    public void testIsUnifiableBefore() {
    }

    public void testUnifyNext() {
    }

    public void testUnifyBefore() {
    }

    public void testIsTeacher() {
    }

    public void testGetTeacher() {
    }

    public void testCalculateInfluence() {
    }

    public void testIslandConquest() {
    }
}