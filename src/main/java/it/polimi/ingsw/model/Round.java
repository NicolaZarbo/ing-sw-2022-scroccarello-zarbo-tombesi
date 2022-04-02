package it.polimi.ingsw.model;
import java.util.Arrays;
import java.util.LinkedList;

public class Round {

    private final int numRound;
    private Player[] players;
    private int[] orderForController;//l'ordine verrà ricostruito e rimandato al controller
    private int[] motherMovements; //movimento di madre natura dell'iesimo giocatore

    public Round(int n,int numPlayer, Player[] players){
        this.numRound=n;
        this.players=players;
        this.orderForController=new int[numPlayer];
        this.motherMovements=new int[numPlayer];
    }

    public void pianification(int numPlayer, int cardPlayed){
        Hand actualHand=players[numPlayer].getHand();
        AssistantCard played=actualHand.playAssistant(cardPlayed);
        orderForController[numPlayer]=played.getValTurn();
        motherMovements[numPlayer]=played.getMoveMother();
    }
    //codice da modificare,specializzato solo per il gioco a due
    public void setClouds(Cloud[]clouds,Bag b){
        Student[] tmp1=new Student[3];
        Student[] tmp2=new Student[3];
        for(int i=0;i<3;i++){
            tmp1[i]=b.getToken();
            tmp2[i]=b.getToken();
        }
        clouds[0].setStud(tmp1);
        clouds[1].setStud(tmp2);
    }
    public int getNumRound(){return this.numRound;}
    public int[] getOrderForController(){return this.orderForController;}
    public int[] getMotherMovements(){return this.motherMovements;}
}
