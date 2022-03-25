package it.polimi.ingsw.model;

public class Hand {
    int coin;
    AssistantCard[] assistant;
    AssistantCard[] discarded;

    public Hand(AssistantCard[] assistentiIniz){
        coin =0;
        assistant =new AssistantCard[assistentiIniz.length];
        for(int i=0;i<assistentiIniz.length;i++)
            assistant[i]=assistentiIniz[i];
    }

    public void addCoin(){
        coin++;
    }
}
