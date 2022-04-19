package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import junit.framework.TestCase;

import java.util.ArrayList;

import static it.polimi.ingsw.model.Mage.*;
import static it.polimi.ingsw.model.token.TowerColor.*;

public class ControllerRoundTest extends TestCase {
    int [] cardTest={9,9,1,4};
    Game game=new Game(false,4,10);
   // ControllerRound cTest=new ControllerRound(cardTest,4);

    public void testControllerCreation(){
        for(int i=0;i<this.cardTest.length;i++)
            System.out.println("player "+(i+1)+" wants to play card num "+this.cardTest[i]);
    }
    /*
    public void testPianification(){
        ArrayList<AssistantCard> a1=new ArrayList<AssistantCard>();
        ArrayList<AssistantCard> a2=new ArrayList<AssistantCard>();
        ArrayList<AssistantCard> a3=new ArrayList<AssistantCard>();
        ArrayList<AssistantCard> a4=new ArrayList<AssistantCard>();
        for(int i=0;i<10;i++){
            a1.add(i,new AssistantCard(i,i,i,mage1));
            a2.add(i, new AssistantCard(i,2*i,2*i,mage2));
            a3.add(i,new AssistantCard(i,3*i,3*i,mage3));
            a4.add(i,new AssistantCard(i,4*i,4*i,mage4));
        }
        Hand h1=new Hand(a1);
        Hand h2=new Hand(a2);
        Hand h3=new Hand(a3);
        Hand h4=new Hand(a4);
        Board b1=new Board(4,4,black,1);
        Board b2=new Board(4,4,grey,2);
        Board b3=new Board(4,4,black,3);
        Board b4=new Board(4,4,white,4);
        Player p1=new Player("io",1,mage1,h1,b1,black);
        Player p2=new Player("sss",2,mage2,h2,b2,grey);
        Player p3=new Player("ss",3,mage3,h3,b3,black);
        Player p4=new Player("sdasf",4,mage4,h4,b4,white);
        Player [] testSet={p1,p2,p3,p4};
        /* cTest.controlPianification();
        System.out.print("the order is: ");    momentarily put in comments for test coverage, the controller part is going to change
        for(int i=0;i<cTest.getActualOrder().length;i++)
            System.out.print("giocatore "+cTest.getActualOrder()[i]+" ");

    }*/

}