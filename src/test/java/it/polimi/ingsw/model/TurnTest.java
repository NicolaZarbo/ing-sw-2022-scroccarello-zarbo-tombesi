package it.polimi.ingsw.model;

import it.polimi.ingsw.model.character.Character1;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.model.token.TokenColor;
import it.polimi.ingsw.model.token.Tower;
import it.polimi.ingsw.model.token.TowerColor;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class TurnTest extends TestCase {
    Game game;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        game = new Game(false,2,12);
        Student[] stud = new Student[3];
        stud[0]=game.getBag().getToken();
        game.getClouds()[1].setStud(stud);

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
        Board board=game.getPlayer(1).getBoard();
        Cloud cloud =game.getClouds()[1];
        for (int i = 0; i < 3; i++) {
            board.getStudentFromEntrance(board.getEntrance().get(4).getId());
        }

        Round.SetCloud(1,game);
        Integer[] studOnCloudID =  Arrays.stream(cloud.getStud()).map(student -> Integer.valueOf( student.getId())).toArray(Integer[]::new);
        assertTrue(board.getEntrance().size()<=board.entranceSize);
        Turn.moveFromCloudToEntrance(game,1,1);
        int nOfStudentsMovedFound=0;
        for (Integer studId:studOnCloudID) {
            if(board.getEntrance().stream().map(student -> student.getId()).anyMatch(s->s==studId))
                nOfStudentsMovedFound++;
        }
        assertEquals(game.getClouds()[0].getStud().length,nOfStudentsMovedFound);

    }

    public void testIsUnifiableNext() {
        int pos;
        pos = game.getIslands().size()-1;//works in all position

        game.getIsland(pos).setTower(new Tower(TowerColor.black,1));
        game.getIsland(Math.floorMod(pos+1,game.getIslandList().size())).setTower(new Tower(TowerColor.black,3));
        assertTrue(Turn.isUnifiableNext(game,pos));
    }

    public void testIsUnifiableBefore() {
        int pos = 1;
        game.getIsland(pos).setTower(new Tower(TowerColor.black,1));
        game.getIsland(Math.floorMod(pos-1,game.getIslandList().size())).setTower(new Tower(TowerColor.black,3));
        assertTrue(Turn.isUnifiableBefore(game,1));
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

    public void testCanHaveTeacher() {

        for (Player player: game.getPlayers()) {
            Turn.moveInHall(player.getId(), player.getBoard().getEntrance().get(0).getId(), game);
            for (TokenColor color: TokenColor.values()) {
                System.out.println(color+" "+Arrays.stream(player.getBoard().getDiningRoom()[color.ordinal()]).filter(student -> student!= null).count());
                if(player.getBoard().hasProfessor(color)) {
                    System.out.println("player "+player.getId() + " can have " + color + " professor");

                }
            }

        }

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
        for (Island island: game.getIslandList()) {
            Turn.moveMotherNature(1,game);
            System.out.println("id ISola : "+island.getID());
            for (Tower tower:island.getTowers()) {
                System.out.println( "torre : "+ tower.getId()+" "+tower.getColor());
            };
        }

    }
    public  void testCharacter1(){
        Character1 c=(Character1)game.getCharacter(1);
        Student stud =(c).getStudents().get(1);
        Island isl = game.getIsland(1);
        int studIdOnCard = stud.getId();
        System.out.println("id studente target "+studIdOnCard);
        ParameterObject par = new ParameterObject(stud.getId(),1);
        assertTrue(game.getPlayer(1).getHand().enoughCoin(1));
        Turn.useCharacter(1,par,1,game);
        for (Student student:isl.getStudents()) {
            System.out.println(student.getId());
        }
        assertTrue(game.getIsland(1).getStudents().stream().map(Student::getId).toList().contains(studIdOnCard));
    }
}