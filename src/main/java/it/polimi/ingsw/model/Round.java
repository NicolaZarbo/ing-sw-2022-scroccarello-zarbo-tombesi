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

    public void setClouds(ArrayList<int[]> clouds,int numTokens){
        for(int i=0;i< clouds.size();i++){
            Student[] tmp=new Student[3];
            for(int j=0;j<3;j++){
                tmp[j]=game.getBag().getToken();
                clouds.get(i)[j]=tmp[j].getId();
            }
            game.getClouds()[i].setStud(tmp);
            numTokens-=3;
        }

    }
    public int getNumRound(){return this.numRound;}
    public int[] getOrderForController(){return this.orderForController;}
    public int[] getMotherMovements(){return this.motherMovements;}
}
