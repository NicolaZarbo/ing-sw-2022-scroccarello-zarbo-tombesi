package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import junit.framework.TestCase;
import static it.polimi.ingsw.model.Mage.*;
import static it.polimi.ingsw.model.TowerColor.*;

public class ControllerRoundTest extends TestCase {
    int [] cardTest={1,2,3,4};
    ControllerRound cTest=new ControllerRound(cardTest,4);

    public void testControllerCreation(){
        for(int i=0;i<this.cardTest.length;i++)
            System.out.println("player "+(i+1)+" wants to play card num "+this.cardTest[i]);
    }
    public void testPianification(){
        AssistantCard[] a1=new AssistantCard[10];
        AssistantCard[] a2=new AssistantCard[10];
        AssistantCard[] a3=new AssistantCard[10];
        AssistantCard[] a4=new AssistantCard[10];
        for(int i=0;i<a1.length;i++){
            a1[i]=new AssistantCard(i,i,i,mage1);
            a2[i]=new AssistantCard(i,2*i,2*i,mage2);
            a3[i]=new AssistantCard(i,3*i,3*i,mage3);
            a4[i]=new AssistantCard(i,4*i,4*i,mage4);
        }
        Hand h1=new Hand(a1);
        Hand h2=new Hand(a2);
        Hand h3=new Hand(a3);
        Hand h4=new Hand(a4);
        Board b1=new Board(4,4,black,1);
        Board b2=new Board(4,4,grey,2);
        Board b3=new Board(4,4,black,3);
        Board b4=new Board(4,4,white,4);
        Player p1=new Player(1,mage1,h1,b1,black);
        Player p2=new Player(2,mage2,h2,b2,grey);
        Player p3=new Player(3,mage3,h3,b3,black);
        Player p4=new Player(4,mage4,h4,b4,white);
        Player [] testSet={p1,p2,p3,p4};
        Round roundTest=new Round(1,4,testSet);
        cTest.game=new Game(false,4,10);
        cTest.controlPianification(roundTest);
        System.out.print("the order is: ");
        for(int i=0;i<cTest.getActualOrder().length;i++)
            System.out.print("giocatore "+cTest.getActualOrder()[i]+" ");
    }
}