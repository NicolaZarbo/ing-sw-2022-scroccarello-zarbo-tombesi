package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Action;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.tokens.Student;
import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.util.ParameterObject;
import junit.framework.TestCase;

import java.util.ArrayList;

/**It tests character cards of the game.*/
public class CharactersTest extends TestCase {

    Game gameTest;
    CharacterCard cardTester;
    Action turn;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new GameStub(false,4,12);
        this.turn= gameTest.getActionPhase();
    }

    /**It tests character 1 effect resolution.
     * @see Character1*/
    public void testCharacter1(){
        cardTester=FactoryCharacter.createCharacter(1,gameTest.getBag());
        assertEquals(1,cardTester.getId());
        int initialCost=cardTester.getCost();
        ArrayList<Student> list=((Character1)cardTester).getStudents();
        ParameterObject parameters=new ParameterObject(list.get(1).getId(),1);
        cardTester.cardEffect(parameters,gameTest);
        assertEquals(4,((Character1) cardTester).getStudents().size());
        assertEquals(initialCost+1,cardTester.getCost());

    }

    /**It tests activation of character 1 in the case player has not enough money.*/
    public void testNotEnoughMoneyCharacter1(){
        cardTester=FactoryCharacter.createCharacter(1,gameTest.getBag());
        ArrayList<Student> list=((Character1)cardTester).getStudents();
        ((GameStub)gameTest).setManuallyGamePhase(GameState.actionMoveStudent);
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

    /**It tests character 2 effect resolution.
     * @see TurnEffectCharacter*/
    public void testCharacter2(){
        cardTester=FactoryCharacter.createCharacter(2,gameTest.getBag());
        assertEquals(2,cardTester.getId());
        ((GameStub)gameTest).setManuallyGamePhase(GameState.actionMoveStudent);
        gameTest.getPlayer(gameTest.getCurrentPlayerId()).getHand().addCoin();
        ParameterObject noParameter=new ParameterObject();
        try {
            turn.useCharacter(cardTester.getId(), noParameter,0);
            assertTrue(gameTest.isBonusActive(2));
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    /**It tests activation of character 2 in the case player has not enough money.*/
    public void testNotEnoughMoneyCharacter2(){
        cardTester=FactoryCharacter.createCharacter(2,gameTest.getBag());
        ParameterObject noParameter=new ParameterObject();
        try {
            turn.useCharacter(cardTester.getId(), noParameter,0);
        }
        catch(RuntimeException e){
            assertFalse(gameTest.isBonusActive(2));
        }

    }

    /**It tests character 6 effect resolution.
     * @see TurnEffectCharacter*/
    public void testCharacter6(){
        cardTester=FactoryCharacter.createCharacter(6,gameTest.getBag());
        assertEquals(6,cardTester.getId());
        ((GameStub)gameTest).setManuallyGamePhase(GameState.actionMoveStudent);
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

    /**It tests activation of character 6 in the case player has not enough money.*/
    public void testNotEnoughMoneyCharacter6(){
        cardTester=FactoryCharacter.createCharacter(6,gameTest.getBag());
        ParameterObject noParameter=new ParameterObject();
        try {
            turn.useCharacter(cardTester.getId(), noParameter,0);
        }
        catch(RuntimeException e){
            assertFalse(gameTest.isBonusActive(6));
        }
    }

    /**It tests character 7 effect resolution.
     * @see Character7*/
    public void testCharacter7(){
        cardTester=FactoryCharacter.createCharacter(7,gameTest.getBag());
        assertEquals(7,cardTester.getId());
        int initialCost=cardTester.getCost();
        int dim=(int)((Math.random()*(3-1))+1); //in this way you can get a random result in a range of values
        int[] targets=new int[dim];
        int[] exchange=new int[dim];
        ((GameStub)gameTest).setManuallyGamePhase(GameState.actionMoveStudent);
        for(int i=0;i<dim;i++){
            targets[i]=gameTest.getPlayer(0).getBoard().getEntrance().get(i).getId();
            exchange[i]=((Character7)cardTester).getStudents().get(i).getId();
        }
        ParameterObject parameters=new ParameterObject(0,targets,exchange);
        cardTester.cardEffect(parameters,gameTest);
        assertEquals(initialCost+1,cardTester.getCost());
    }

    /**It tests activation of character 7 in the case player has not enough money.*/
    public void testNotEnoughMoneyCharacter7(){
        cardTester=FactoryCharacter.createCharacter(7,gameTest.getBag());
        ((GameStub)gameTest).setManuallyGamePhase(GameState.actionMoveStudent);
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

    /**It tests character 8 effect resolution.
     * @see TurnEffectCharacter*/
    public void testCharacter8(){
        cardTester=FactoryCharacter.createCharacter(8,gameTest.getBag());
        assertEquals(8,cardTester.getId());
        ((GameStub)gameTest).setManuallyGamePhase(GameState.actionMoveStudent);
        gameTest.getPlayer(gameTest.getCurrentPlayerId()).getHand().addCoin();
        ParameterObject noParameter=new ParameterObject();
        try {
            turn.useCharacter(cardTester.getId(), noParameter,0);
            assertTrue(gameTest.isBonusActive(8));
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    /**It tests activation of character 8 in the case player has not enough money.*/
    public void testNotEnoughMoneyCharacter8(){
        cardTester=FactoryCharacter.createCharacter(8,gameTest.getBag());
        ParameterObject noParameter=new ParameterObject();
        try {
            turn.useCharacter(cardTester.getId(), noParameter,0);
        }
        catch(RuntimeException e){
            assertFalse(gameTest.isBonusActive(8));
        }

    }

    /**It tests character 9 effect resolution.
     * @see Character9*/
    public void testCharacter9(){
        cardTester=FactoryCharacter.createCharacter(9,gameTest.getBag());
        assertEquals(9,cardTester.getId());
        ((GameStub)gameTest).setManuallyGamePhase(GameState.actionMoveStudent);
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

    /**It tests activation of character 9 in the case player has not enough money.*/
    public void testNotEnoughMoneyCharacter9(){
        cardTester=FactoryCharacter.createCharacter(9,gameTest.getBag());
        int colorIndex=(int)(Math.random()*4+1);
        ParameterObject parameters=new ParameterObject(colorIndex);
        try{
            cardTester.cardEffect(parameters,gameTest);
            assertEquals(TokenColor.getColor(colorIndex),gameTest.getTargetColor());
        }
        catch(RuntimeException e){
            assertNull(gameTest.getTargetColor());
        }

    }

    /**It tests character 10 effect resolution.
     * @see Character10*/
    public void testCharacter10(){
        cardTester=FactoryCharacter.createCharacter(10,gameTest.getBag());
        assertEquals(10,cardTester.getId());
        int initialCost=cardTester.getCost();
        int dim=(int)((Math.random()*2)); //in this way you can get a random result in a range of values
        int[] targets=new int[dim];
        int[] exchange=new int[dim];
        ((GameStub)gameTest).setManuallyGamePhase(GameState.actionMoveStudent);
        for(int i=0;i<dim;i++){
            gameTest.getPlayer(0).getBoard().moveToDiningRoom(gameTest.getPlayer(0).getBoard().getEntrance().get(gameTest.getPlayer(0).getBoard().getEntrance().size()-1-i));
        }
        for(int i=0;i<dim;i++){
            targets[i]=gameTest.getPlayer(0).getBoard().getEntrance().get(i).getId();
            int j=0,k=0;
            Student stud=null;
            while(stud==null && j<gameTest.getPlayer(0).getBoard().getDiningRoom().length){
                stud=gameTest.getPlayer(0).getBoard().getDiningRoom()[j][k];
                if(k<gameTest.getPlayer(0).getBoard().getDiningRoom()[j].length-1){
                    k++;
                }
                else{
                    k=0;
                    j++;
                }
            }
            if(stud!=null){
                exchange[i]=stud.getId();
            }
            else
                throw new RuntimeException("not enough tokens on the hall");
        }
        ParameterObject parameters=new ParameterObject(0,targets,exchange);
        cardTester.cardEffect(parameters,gameTest);
        assertEquals(initialCost+1,cardTester.getCost());
    }

    /**It tests activation of character 10 in the case player has not enough money.*/
    public void testNotEnoughMoneyCharacter10(){
        cardTester=FactoryCharacter.createCharacter(10,gameTest.getBag());
        int currentCost=cardTester.getCost();
        ((GameStub)gameTest).setManuallyGamePhase(GameState.actionMoveStudent);
        while(cardTester.getCost()<=gameTest.getPlayer(gameTest.getCurrentPlayerId()).getHand().getCoin()) {
            int dim = (int) ((Math.random() * 2)); //in this way you can get a random result in a range of values
            int[] targets = new int[dim];
            int[] exchange = new int[dim];
            for (int i = 0; i < dim; i++) {
                gameTest.getPlayer(0).getBoard().moveToDiningRoom(gameTest.getPlayer(0).getBoard().getEntrance().get(gameTest.getPlayer(0).getBoard().getEntrance().size() - 1 - i));
            }
            for (int i = 0; i < dim; i++) {
                targets[i] = gameTest.getPlayer(0).getBoard().getEntrance().get(i).getId();
                int j = 0, k = 0;
                Student stud = null;
                while (stud == null && j < gameTest.getPlayer(0).getBoard().getDiningRoom().length) {
                    stud = gameTest.getPlayer(0).getBoard().getDiningRoom()[j][k];
                    if (k < gameTest.getPlayer(0).getBoard().getDiningRoom()[j].length - 1) {
                        k++;
                    } else {
                        k = 0;
                        j++;
                    }
                }
                if (stud != null) {
                    exchange[i] = stud.getId();
                } else
                    throw new RuntimeException("not enough tokens on the hall");
            }
            ParameterObject parameters = new ParameterObject(0, targets, exchange);
            cardTester.cardEffect(parameters, gameTest);
            currentCost++;
        }
        try{
            int dim = (int) ((Math.random() * 2) + 1); //in this way you can get a random result in a range of values
            int[] targets = new int[dim];
            int[] exchange = new int[dim];
            for (int i = 0; i < dim; i++) {
                gameTest.getPlayer(0).getBoard().moveToDiningRoom(gameTest.getPlayer(0).getBoard().getEntrance().get(gameTest.getPlayer(0).getBoard().getEntrance().size() - 1 - i));
            }
            for (int i = 0; i < dim; i++) {
                targets[i] = gameTest.getPlayer(0).getBoard().getEntrance().get(i).getId();
                int j = 0, k = 0;
                Student stud = null;
                while (stud == null && j < gameTest.getPlayer(0).getBoard().getDiningRoom().length) {
                    stud = gameTest.getPlayer(0).getBoard().getDiningRoom()[j][k];
                    if (k < gameTest.getPlayer(0).getBoard().getDiningRoom()[j].length - 1) {
                        k++;
                    } else {
                        k = 0;
                        j++;
                    }
                }
                if (stud != null) {
                    exchange[i] = stud.getId();
                } else
                    throw new RuntimeException("not enough tokens on the hall");
            }
            ParameterObject parameters = new ParameterObject(0, targets, exchange);
            cardTester.cardEffect(parameters, gameTest);
        }
        catch(RuntimeException e){
            assertEquals(currentCost,cardTester.getCost());

        }
    }

    /**It tests character 11 effect resolution.
     * @see Character11*/
    public void testCharacter11(){
        cardTester=FactoryCharacter.createCharacter(11,gameTest.getBag());
        assertEquals(11,cardTester.getId());
        ((GameStub)gameTest).setManuallyGamePhase(GameState.actionMoveStudent);
        gameTest.getPlayer(gameTest.getCurrentPlayerId()).getHand().addCoin();
        int initialCost= cardTester.getCost();
        int target=((int)(Math.random()*4))*((Character11)cardTester).getStudents().size();
        ParameterObject parameters=new ParameterObject(((Character11)cardTester).getStudents().get(target).getId(),0);
        try{
            cardTester.cardEffect(parameters,gameTest);
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
        assertEquals(3,((Character11) cardTester).getStudents().size());
        assertEquals(initialCost+1,cardTester.getCost());
    }

    /**It tests activation of character 11 in the case player has not enough money.*/
    public void testNotEnoughMoneyCharacter11(){
        cardTester=FactoryCharacter.createCharacter(11,gameTest.getBag());
        assertEquals(11,cardTester.getId());
        ((GameStub)gameTest).setManuallyGamePhase(GameState.actionMoveStudent);
        while(cardTester.getCost()<=gameTest.getPlayer(gameTest.getCurrentPlayerId()).getHand().getCoin()){
            int target = ((int) (Math.random()*4)) * ((Character11) cardTester).getStudents().size();
            ParameterObject parameters = new ParameterObject(((Character11) cardTester).getStudents().get(target).getId(), 0);
            try {
                cardTester.cardEffect(parameters, gameTest);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        try{
            int target = ((int) (Math.random()*4)) * ((Character11) cardTester).getStudents().size();
            ParameterObject parameters = new ParameterObject(((Character11) cardTester).getStudents().get(target).getId(), 0);
            cardTester.cardEffect(parameters, gameTest);
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }

    }
}