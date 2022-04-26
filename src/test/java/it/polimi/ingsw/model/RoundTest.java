package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.EmptyBagException;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Random;

import static it.polimi.ingsw.model.Mage.*;
import static it.polimi.ingsw.model.token.TowerColor.*;

public class RoundTest extends TestCase {

    private Game gameTest;
    private Round roundTest;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new Game(false,4,12);
        this.roundTest=new Round(gameTest);
    }

    public void testSetCloud(){
        try {
            roundTest.SetCloud();
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

    public void testOrder() {
        int i =0;
        for (Player player : gameTest.getPlayers()) {
            Random r= new Random();
            gameTest.addCardPlayedThisRound(player.getId(), player.getHand().playAssistant(i+r.nextInt(9)));
            i+=10;
        }
        roundTest.roundOrder();
        for (Integer inttt: gameTest.getPlayIngOrder()) {
            System.out.println(inttt);
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