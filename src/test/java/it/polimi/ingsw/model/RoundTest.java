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
        int [] cardTest={8,8,1,4};
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
        LobbyPlayer pp1=new LobbyPlayer(black,mage1,"player1");
        LobbyPlayer pp2=new LobbyPlayer(grey,mage2,"player2");
        LobbyPlayer pp3=new LobbyPlayer(black,mage2,"player3");
        LobbyPlayer pp4=new LobbyPlayer(white,mage4,"player4");
        Player p1=new Player(pp1,1,h1,b1);
        Player p2=new Player(pp2,2,h2,b2);
        Player p3=new Player(pp3,3,h3,b3);
        Player p4=new Player(pp4,4,h4,b4);
        Player [] testSet={p1,p2,p3,p4};
        for(int i=0;i<4;i++){
            roundTest.playCard(testSet[i].getId(),cardTest[i]);
        }
        roundTest.roundOrder();
        System.out.print("the order is: ");
        for(int i=0;i<gameTest.getPlayIngOrder().size();i++)
            System.out.print("giocatore "+gameTest.getPlayIngOrder().get(i)+" ");

    }
}