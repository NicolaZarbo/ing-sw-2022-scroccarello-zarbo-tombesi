package it.polimi.ingsw.model;


import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.model.tokens.Student;
import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.model.tokens.Tower;
import it.polimi.ingsw.enumerations.TowerColor;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

/**It tests the action component of the game.
 * @see Action*/
public class ActionTest extends TestCase {
    Game game;
    Action turn;
    Planning round;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        game = new GameStub(false,4,12);
        turn= game.getActionPhase();
        round= game.getPlanningPhase();
    }

    /**It tests movement of token from entrance to dining room.*/
    public void testMoveInHall() {
        ArrayList<Student> entrance=game.getPlayer(0).getBoard().getEntrance();
        assertNotNull(entrance);
        for(int i=0;i<=entrance.size();i++) {
            Student stud = entrance.get((int) (Math.random() * entrance.size()));
            try {
                turn.moveInDiningRoom(0,stud.getId());
                assertEquals(stud,game.getPlayer(0).getBoard().getFromDiningRoom(stud.getId()));
            }
            catch(NoTokenFoundException e){
                break;
            }
        }
    }

    /**It tests the movement of token from entrance to island.*/
    public void testMoveToIsland() {
        Player player= game.getPlayer(0);
        int idIsland=(int)(Math.random()*12);
        Student stud=player.getBoard().getEntrance().get((int)(Math.random()*player.getBoard().getEntrance().size()));
        assertNotNull(stud);
        turn.moveToIsland(0,stud.getId(),idIsland);
        assertTrue(game.getIsland(idIsland).getStudents().contains(stud));
    }

    public void testMoveMotherNature() {
        int nSteps=45;
        int posIniz=game.getMotherNature().getPosition();
        turn.moveMotherNature(nSteps);
        assertTrue(game.getMotherNature().getPosition()==Math.floorMod(posIniz+nSteps,game.getIslands().size()));
        System.out.println("isola iniziale : "+posIniz+ "  pos finale :"+ game.getMotherNature().getPosition());

    }

    /**It tests refill of entrance after cloud selection.*/
    public void testMoveFromCloudToEntrance() {
        Board board=game.getPlayer(1).getBoard();
        Cloud cloud =game.getClouds()[1];
        for (int i = 0; i < 3; i++) {
            board.getStudentFromEntrance(board.getEntrance().get(4).getId());
        }
        round.setCloud();
        Integer[] studOnCloudID =  Arrays.stream(cloud.getStud()).map(student -> student.getId()).toArray(Integer[]::new);
        assertTrue(board.getEntrance().size()<=board.entranceSize);
        turn.moveFromCloudToEntrance(1,1);
        int nOfStudentsMovedFound=0;
        for (Integer studId:studOnCloudID) {
            if(board.getEntrance().stream().map(student -> student.getId()).anyMatch(s-> s.equals(studId)))
                nOfStudentsMovedFound++;
        }
        assertEquals(game.getClouds()[0].getStud().length,nOfStudentsMovedFound);
    }

    /**It tests if the island is unifiable with its following.*/
    public void testIsUnifiableNext() {
        int pos;
        pos = game.getIslands().size()-1;//works in all position

        game.getIsland(pos).setTower(new Tower(TowerColor.black,1));
        game.getIsland(Math.floorMod(pos+1,game.getIslands().size())).setTower(new Tower(TowerColor.black,3));
        assertTrue(turn.isUnifiableNext(pos));
    }

    /**It tests if the island is unifiable with its anterior.*/
    public void testIsUnifiableBefore() {
        int pos = 1;
        game.getIsland(pos).setTower(new Tower(TowerColor.black,1));
        game.getIsland(Math.floorMod(pos-1,game.getIslands().size())).setTower(new Tower(TowerColor.black,3));
        assertTrue(turn.isUnifiableBefore(1));
    }

    /**It tests merge of island with its following.*/
    public void testUnifyNext() {
        game.getIsland(1).setTower(new Tower(TowerColor.black,1));
        game.getIsland(2).setTower(new Tower(TowerColor.black,3));
        if(turn.isUnifiableNext(1))
            turn.unifyNext(1);
        assertTrue(game.getIsland(1).getIslandSize()==2);
    }

    /**It tests merge of island with its anterior.*/
    public void testUnifyBefore() {
        game.getIsland(1).setTower(new Tower(TowerColor.black,1));
        game.getIsland(0).setTower(new Tower(TowerColor.black,3));
        Island target =game.getIsland(1);
        if(turn.isUnifiableBefore(1))
            turn.unifyBefore(1);
        assertFalse(game.getIslands().contains(target));
        assertTrue(game.getIsland(0).getSubIslands().contains(target));
        assertEquals(2, game.getIsland(0).getIslandSize());
    }

    /**It tests if a teacher can visit the board.*/
    public void testCanHaveTeacher() {
        Player player=game.getPlayer((int)(Math.random()*4));
        Student stud=player.getBoard().getEntrance().get(0);
        turn.moveInDiningRoom(player.getId(),stud.getId());
        assertTrue(player.getBoard().hasProfessor(stud.getColor()));
    }

    /**It tests visit of the professor on the player's board.*/
    public void testSetTeacher() {
        Player player=game.getPlayer((int)(Math.random()*4));
        turn.setTeacher(TokenColor.getColor(player.getId()), player.getId());
        Student stud=player.getBoard().getEntrance().get((int)(Math.random()*player.getBoard().getEntrance().size()));
        assertNotNull(stud);
        turn.moveInDiningRoom(player.getId(), stud.getId());
        assertTrue(player.getBoard().hasProfessor(stud.getColor()));
        turn.setTeacher(stud.getColor(), player.getId());
    }

    /**It tests the influence calculation.*/
    public void testCalculateInfluence() {
        game.getIsland(0).setTower(new Tower(TowerColor.getColor(1),1));
        game.getIsland(0).setTower(new Tower(TowerColor.getColor(1),1));

        int influence = turn.calculateInfluence(1);
        assertEquals(2,influence);
    }

    /**It tests the conquest of an island.*/
    public  void testConquerIsland() {
        Student stud=game.getPlayer(1).getBoard().getEntrance().get(0);
        turn.moveInDiningRoom(1,stud.getId());
        int inf, numberOfIsland=game.getIslands().size();
        try{
         for (Island isl: game.getIslands()) {
             inf = 0;
             for (Student student : isl.getStudents()) {
                 if (stud.getColor() == student.getColor())
                     inf++;
             }
             turn.moveMotherNature(1);
         }
        }
        catch (ConcurrentModificationException e){
            assertTrue(game.getIslands().size()<numberOfIsland);
        }

    }

    /**It tests the conquest of an island from a different player than its owner.*/
    public void testChangeIslandOwner() {
        Island isl= game.getIsland(7);
        turn.putTowerFromBoardToIsland(isl,game.getPlayer(1));
        ArrayList<Student> sttuddd= new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            sttuddd.add(new Student(i+100, TokenColor.blue));
        }
        isl.addAllStudents(sttuddd);
        Player pl = game.getPlayer(0);
        pl.getBoard().moveToDiningRoom(new Student(500,TokenColor.blue));
        if(turn.canHaveTeacher(TokenColor.blue,pl.getId()))
            turn.setTeacher(TokenColor.blue,pl.getId());
        assertTrue(pl.getBoard().hasProfessor(TokenColor.blue));
        turn.moveMotherNature(7);
        assertTrue(isl.getTowers().get(0).getColor()==TowerColor.black);


    }
}