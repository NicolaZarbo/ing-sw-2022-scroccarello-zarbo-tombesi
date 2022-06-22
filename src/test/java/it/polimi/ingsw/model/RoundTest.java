package it.polimi.ingsw.model;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.exceptions.EmptyBagException;
import junit.framework.TestCase;

import java.util.List;

public class RoundTest extends TestCase {

    private Game gameTest;
    private Planning roundTest;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new GameStub(false,4,12);
        this.roundTest=new Planning(gameTest);
    }

    public void testSetCloud(){
        try {
            roundTest.setCloud();
            for (int i = 0; i < gameTest.getClouds().length; i++) {
                System.out.println("on cloud " + i + " are setted tokens: ");
                for (int j = 0; j < gameTest.getClouds()[i].getStud().length; j++)
                    System.out.print(gameTest.getClouds()[i].getStud()[j].getId() + ", ");
                System.out.println();
            }
        }
        catch(EmptyBagException e){
            e.printStackTrace();
        }
    }

    public void testGetRoundOrder() {
        try{
            List<Integer> list=roundTest.getRoundOrder(gameTest);
            System.out.println("ok");
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
    }


    public void testRoundOrder(){
        int [] cardTest={8,18,21,34};
        for(Player p : gameTest.getPlayers()){
            roundTest.playCard(p.getId(),cardTest[p.getId()]);
        }
        roundTest.roundOrder();
        System.out.print("the order is: ");
        for(int i=0;i<gameTest.getPlayIngOrder().size();i++)
            System.out.print("giocatore "+gameTest.getPlayIngOrder().get(i)+" ");
    }
}