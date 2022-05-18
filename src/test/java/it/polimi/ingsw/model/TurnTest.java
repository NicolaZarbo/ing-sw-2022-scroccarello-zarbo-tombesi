package it.polimi.ingsw.model;


import it.polimi.ingsw.model.character.Character1;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.model.token.Tower;
import it.polimi.ingsw.enumerations.TowerColor;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;

public class TurnTest extends TestCase {
    Game game;
    Turn turn;
    Round round;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        game = new Game(false,2,12);
        turn= new Turn(game);
        Student[] stud = new Student[3];
        stud[0]=game.getBag().getToken();
        game.getClouds()[1].setStud(stud);
        round= new Round(game);

    }

    public void testMoveInHall() {

    }

    public void testMoveToIsland() {
        Player player= game.getPlayer(1);
        int idIsland=5;
        Student stud=player.getBoard().getEntrance().get(0);
        int studId= stud.getId();
        turn.moveToIsland(1,studId,idIsland);
        assertTrue(game.getIsland(idIsland).getStudents().contains(stud));
    }

    public void testMoveMotherNature() {
        int nSteps=45;
        int posIniz=game.getMotherNature().getPosition();
        turn.moveMotherNature(nSteps);
        assertTrue(game.getMotherNature().getPosition()==Math.floorMod(posIniz+nSteps,game.getIslands().size()));
        System.out.println("isola iniziale : "+posIniz+ "  pos finale :"+ game.getMotherNature().getPosition());

    }

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

    public void testIsUnifiableNext() {
        int pos;
        pos = game.getIslands().size()-1;//works in all position

        game.getIsland(pos).setTower(new Tower(TowerColor.black,1));
        game.getIsland(Math.floorMod(pos+1,game.getIslandList().size())).setTower(new Tower(TowerColor.black,3));
        assertTrue(turn.isUnifiableNext(pos));
    }

    public void testIsUnifiableBefore() {
        int pos = 1;
        game.getIsland(pos).setTower(new Tower(TowerColor.black,1));
        game.getIsland(Math.floorMod(pos-1,game.getIslandList().size())).setTower(new Tower(TowerColor.black,3));
        assertTrue(turn.isUnifiableBefore(1));
    }

    public void testUnifyNext() {
        game.getIsland(1).setTower(new Tower(TowerColor.black,1));
        game.getIsland(2).setTower(new Tower(TowerColor.black,3));
        if(turn.isUnifiableNext(1))
            turn.unifyNext(1);
        assertTrue("size isl: "+game.getIsland(1).getIslandSize(),game.getIsland(1).getIslandSize()==2);
    }

    public void testUnifyBefore() {
        game.getIsland(1).setTower(new Tower(TowerColor.black,1));
        game.getIsland(0).setTower(new Tower(TowerColor.black,3));
        if(turn.isUnifiableBefore(1))
            turn.unifyBefore(1);
        assertNotNull(game.getIsland(1));

        assertTrue("size isl: "+game.getIsland(1).getIslandSize(),game.getIsland(1).getIslandSize()==2);
       // System.out.println(game.getMotherNature().getPosition()+ " size islands "+ game.getIslands().size() + " isola ");
    }

    public void testCanHaveTeacher() {

        for (Player player: game.getPlayers()) {
            turn.moveInHall(player.getId(), player.getBoard().getEntrance().get(0).getId());
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

        int influnce = turn.calculateInfluence(1);
        System.out.println(influnce);
    }


    public void testIslandConquest() {
        for (Island island: game.getIslandList()) {
            turn.moveMotherNature(1);
            System.out.println("id ISola : "+island.getID());
            for (Tower tower:island.getTowers()) {
                System.out.println( "torre : "+ tower.getId()+" "+tower.getColor());
            }
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
        turn.useCharacter(1,par,1);
        for (Student student:isl.getStudents()) {
            System.out.println(student.getId());
        }
        assertTrue(game.getIsland(1).getStudents().stream().map(Student::getId).toList().contains(studIdOnCard));
    }

    public void testConquerIsland() {
        Student stud=game.getPlayer(1).getBoard().getEntrance().get(0);
        turn.moveInHall(1,stud.getId() );
        int inf;
         for (Island isl: game.getIslands()) {
             inf=0;
             for (Student student: isl.getStudents()) {
                 if(stud.getColor()==student.getColor())
                     inf++;
             }
            turn.moveMotherNature(1);
            if(isl.getTowers().size()>0)
                assertTrue(inf>0);
        }
    }

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
            turn.getTeacher(TokenColor.blue,pl.getId());
        assertTrue(pl.getBoard().hasProfessor(TokenColor.blue));
        turn.moveMotherNature(7);
        assertTrue(isl.getTowers().get(0).getColor()==TowerColor.black);


    }
}