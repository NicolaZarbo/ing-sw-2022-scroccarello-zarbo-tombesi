package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.model.token.TokenColor;
import junit.framework.TestCase;

import java.util.ArrayList;

public class CharactersTest extends TestCase {

    Game gameTest;
    CharacterCard cardTester;

    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new Game(false,4,12);
    }

    public void testCharacter1(){
        cardTester=FactoryCharacter.createCharacter(1,gameTest.getBag());
        int initialCost=cardTester.getCost();
        ArrayList<Student> list=((Character1)cardTester).getStudents();
        ParameterObject parameters=new ParameterObject(list.get(1).getId(),1);
        cardTester.cardEffect(parameters,gameTest);
        assertEquals(4,((Character1) cardTester).getStudents().size());
        assertEquals(initialCost+1,cardTester.getCost());

    }

    public void testNotEnoughMoneyCharacter1(){
        cardTester=FactoryCharacter.createCharacter(1,gameTest.getBag());
        ArrayList<Student> list=((Character1)cardTester).getStudents();
        while(gameTest.getPlayer(0).getHand().getCoin()>= cardTester.getCost()){
            ParameterObject parameters=new ParameterObject(list.get(1).getId(),1);
            cardTester.cardEffect(parameters,gameTest);
        }
        try{
            ParameterObject parameters=new ParameterObject(list.get(1).getId(),1);
            cardTester.cardEffect(parameters,gameTest);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    public void testCharacter2(){
        cardTester=FactoryCharacter.createCharacter(2,gameTest.getBag());
        gameTest.getPlayer(gameTest.getCurrentPlayerId()).getHand().addCoin();
        ParameterObject noParameter=new ParameterObject();
        try {
            cardTester.cardEffect(noParameter, this.gameTest);
            assertTrue(gameTest.isBonusActive(2));
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    public void testNotEnoughMoneyCharacter2(){
        cardTester=FactoryCharacter.createCharacter(2,gameTest.getBag());
        ParameterObject noParameter=new ParameterObject();
        try {
            cardTester.cardEffect(noParameter, this.gameTest);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
        assertFalse(gameTest.isBonusActive(2));
    }

    public void testCharacter6(){
        cardTester=FactoryCharacter.createCharacter(6,gameTest.getBag());
        for(int i=0;i<2;i++)
            gameTest.getPlayer(gameTest.getCurrentPlayerId()).getHand().addCoin();
        ParameterObject noParameter=new ParameterObject();
        try {
            cardTester.cardEffect(noParameter, this.gameTest);
            assertTrue(gameTest.isBonusActive(6));
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    public void testNotEnoughMoneyCharacter6(){
        cardTester=FactoryCharacter.createCharacter(6,gameTest.getBag());
        ParameterObject noParameter=new ParameterObject();
        try {
            cardTester.cardEffect(noParameter, this.gameTest);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
        assertFalse(gameTest.isBonusActive(6));
    }

    public void testCharacter7(){
        cardTester=FactoryCharacter.createCharacter(7,gameTest.getBag());
        int initialCost=cardTester.getCost();
        int dim=(int)((Math.random()*(3-1))+1); //in this way you can get a random result in a range of values
        int[] targets=new int[dim];
        int[] exchange=new int[dim];
        for(int i=0;i<dim;i++){
            targets[i]=gameTest.getPlayer(0).getBoard().getEntrance().get(i).getId();
            exchange[i]=((Character7)cardTester).getStudents().get(i).getId();
        }
        ParameterObject parameters=new ParameterObject(0,targets,exchange);
        cardTester.cardEffect(parameters,gameTest);
        assertEquals(initialCost+1,cardTester.getCost());
    }

    public void testNotEnoughMoneyCharacter7(){
        cardTester=FactoryCharacter.createCharacter(7,gameTest.getBag());
        while(gameTest.getPlayer(0).getHand().getCoin()>=cardTester.getCost()){
            int dim=(int)((Math.random()*(3-1))+1);
            int[] targets=new int[dim];
            int[] exchange=new int[dim];
            for(int i=0;i<dim;i++){
                targets[i]=gameTest.getPlayer(0).getBoard().getEntrance().get(i).getId();
                exchange[i]=((Character7)cardTester).getStudents().get(i).getId();
            }
            ParameterObject parameters=new ParameterObject(0,targets,exchange);
            cardTester.cardEffect(parameters,gameTest);
        }
        try{
            int dim=(int)((Math.random()*(3-1))+1); //in this way you can get a random result in a range of values
            int[] targets=new int[dim];
            int[] exchange=new int[dim];
            for(int i=0;i<dim;i++){
                targets[i]=gameTest.getPlayer(0).getBoard().getEntrance().get(i).getId();
                exchange[i]=((Character7)cardTester).getStudents().get(i).getId();
            }
            ParameterObject parameters=new ParameterObject(0,targets,exchange);
            cardTester.cardEffect(parameters,gameTest);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    public void testCharacter8(){
        cardTester=FactoryCharacter.createCharacter(8,gameTest.getBag());
        gameTest.getPlayer(gameTest.getCurrentPlayerId()).getHand().addCoin();
        ParameterObject noParameter=new ParameterObject();
        try {
            cardTester.cardEffect(noParameter, this.gameTest);
            assertTrue(gameTest.isBonusActive(8));
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    public void testNotEnoughMoneyCharacter8(){
        cardTester=FactoryCharacter.createCharacter(8,gameTest.getBag());
        ParameterObject noParameter=new ParameterObject();
        try {
            cardTester.cardEffect(noParameter, this.gameTest);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
        assertFalse(gameTest.isBonusActive(8));
    }

    public void testCharacter9(){
        cardTester=FactoryCharacter.createCharacter(9,gameTest.getBag());
        for(int i=0;i<2;i++)
                gameTest.getPlayer(gameTest.getCurrentPlayerId()).getHand().addCoin();
        int colorIndex=(int)(Math.random()*4+1);
        ParameterObject parameters=new ParameterObject(colorIndex);
        try{
            cardTester.cardEffect(parameters,gameTest);
            assertEquals(TokenColor.getColor(colorIndex),gameTest.getTargetColor());
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }
    public void testNotEnoughMoneyCharacter9(){
        cardTester=FactoryCharacter.createCharacter(9,gameTest.getBag());
        int colorIndex=(int)(Math.random()*4+1);
        ParameterObject parameters=new ParameterObject(colorIndex);
        try{
            cardTester.cardEffect(parameters,gameTest);
            assertEquals(TokenColor.getColor(colorIndex),gameTest.getTargetColor());
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
        assertNull(gameTest.getTargetColor());
    }

    /*public void testCharacter10(){
        cardTester=FactoryCharacter.createCharacter(10,gameTest.getBag());
        int initialCost=cardTester.getCost();
        int dim=(int)((Math.random()*2)+1); //in this way you can get a random result in a range of values
        int[] targets=new int[dim];
        int[] exchange=new int[dim];
        for(int i=0;i<dim;i++){
            targets[i]=gameTest.getPlayer(0).getBoard().getEntrance().get(i).getId();
            int j=0,k=0;
            Student stud=null;
            while(stud==null||j==gameTest.getPlayer(0).getBoard().getDiningRoom().length){
                stud=gameTest.getPlayer(0).getBoard().getDiningRoom()[j][k];
                if(k<gameTest.getPlayer(0).getBoard().getDiningRoom().length-1){
                    k++;
                }
                else{
                    k=0;
                    j++;
                }
            }
            exchange[i]=stud.getId();
        }
        ParameterObject parameters=new ParameterObject(0,targets,exchange);
        cardTester.cardEffect(parameters,gameTest);
        assertEquals(initialCost+1,cardTester.getCost());
    }

         */
}