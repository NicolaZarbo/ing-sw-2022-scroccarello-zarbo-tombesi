package it.polimi.ingsw.model;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.exceptions.NoPlaceAvailableException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.model.tokens.Professor;
import it.polimi.ingsw.model.tokens.Student;
import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.model.tokens.Tower;
import junit.framework.TestCase;

/**It tests the board of the player and the movement of tokens.*/
public class BoardTest extends TestCase {

    Game gameTest;
    Board boardTest;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        gameTest=new GameStub(false, 4, 12);
        boardTest=gameTest.getPlayers()[0].getBoard();
    }

    /**It tests the visit of professor in the board.*/
    public void testProfessorInsertion(){
        for(int i=0;i<5;i++){
            boardTest.putProfessor(new Professor(i,TokenColor.getColor(i)));
            assertTrue(boardTest.hasProfessor(TokenColor.getColor(i)));
        }
    }
    /**It tests the movement of tokens in dining room, even the case of full dining room.*/
    public void testMoveInDiningRoom(){
        for(int i=0;i<5;i++){
            for(int j=0;j<=5;j++) {
                try{
                    Student stud=new Student(i,TokenColor.getColor(i));
                    boardTest.moveToDiningRoom(stud);
                    assertNotNull(boardTest.getFromDiningRoom(i));
                }
                catch(NoTokenFoundException e){
                    break;
                }
            }
        }
    }

    /**It tests the insertion of a student token on entrance, even in the case the entrance is full.*/
    public void testInsertionOnEntrance(){
        for(int i=0;i<=8;i++){
            Student stud=new Student(i,TokenColor.getColor(i));
            try{
                boardTest.putStudentOnEntrance(stud);
            }
            catch(NoPlaceAvailableException e){
                Student destroyer = boardTest.getStudentFromEntrance(boardTest.getEntrance().get(i).getId());
                assertNotNull(destroyer);
                boardTest.putStudentOnEntrance(stud);
                break;
            }
        }
    }

    /**It tests the get tower from the available towers.*/
    public void testGetTower(){
        int left= boardTest.towersLeft();
        Tower tower=boardTest.getTower();
        assertNotNull(tower);
        assertTrue(boardTest.towersLeft()==left-1);
    }
}