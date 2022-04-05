package it.polimi.ingsw.model;

import junit.framework.TestCase;
public class RoundTest extends TestCase {
    Game gameTest=new Game(false,4,12);

    public void testSetCloud(){
        int cloudNum=1;
        int[] setted=Round.SetCloud(cloudNum,gameTest);
        System.out.print("on cloud "+ cloudNum+" are setted tokens: ");
        for(int i=0;i<setted.length;i++)
            System.out.print(setted[i]+", ");

    }
    public void testCardPlayedValue(){
        int numPlayer=1;
        int cardPlayed=9;
        int turnValue=Round.CardPlayedValue(numPlayer,cardPlayed,gameTest);
        System.out.println("player "+numPlayer+" has played card "+cardPlayed+" and his turn value is "+turnValue);
    }
   public void testMotherMovement() {
       int numPlayer=1;
       int cardPlayed=3;
       int motherValue=Round.MotherMovement(numPlayer,cardPlayed,gameTest);
       System.out.println("player "+numPlayer+" has played card "+cardPlayed+" and his mother value is "+motherValue);   }
}