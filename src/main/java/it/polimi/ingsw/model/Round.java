package it.polimi.ingsw.model;
import java.util.ArrayList;

public class Round {

    private final int numRound;
    private Player[] players;
    private int[] orderForController;//l'ordine verrà ricostruito e rimandato al controller
    private int[] motherMovements; //movimento di madre natura dell'iesimo giocatore
    private Game game;

    public Round(int n,int numPlayer, Player[] players, Game g){
        this.numRound=n;
        this.players=players;
        this.orderForController=new int[numPlayer];
        this.motherMovements=new int[numPlayer];
        this.game=g;
    }

    public void pianification(int numPlayer, int cardPlayed){
        Hand actualHand=players[numPlayer].getHand();
        AssistantCard played=actualHand.playAssistant(cardPlayed);
        orderForController[numPlayer]=played.getValTurn();
        motherMovements[numPlayer]=played.getMoveMother();
    }

    public int[] setCloud(int cloudNum){
            Student[] tmp=new Student[3];
            int[] studID=new int[3];
            for(int i=0;i<3;i++){
                tmp[i]=game.getBag().getToken();
                studID[i]=tmp[i].getId();
            }
            game.getClouds()[cloudNum].setStud(tmp);
            return studID;
    }

    public int getNumRound(){return this.numRound;}
    public int[] getOrderForController(){return this.orderForController;}
    public int[] getMotherMovements(){return this.motherMovements;}
}
