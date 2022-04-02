package it.polimi.ingsw.model;
import java.util.LinkedList;

public class Hand {
    int coin;
    AssistantCard[] assistant;
    LinkedList<AssistantCard> discarded;

    public Hand(AssistantCard[] assistentiIniz){
        coin =0;
        assistant =new AssistantCard[assistentiIniz.length];
        discarded=new LinkedList<>();
        for(int i=0;i<assistentiIniz.length;i++)
            assistant[i]=assistentiIniz[i];
    }

    public void addCoin(){
        coin++;
    }
    public void payCoin(int cost){
        this.coin=this.coin-cost;
    }
    public boolean enoughCoin(int cost){
        return (this.coin-cost>=0);
    }

    public AssistantCard playAssistant(int n){
        AssistantCard played=null;
        for(int i=0;i<assistant.length;i++){
            if(i==n && assistant[i]!=null) {
                played=assistant[i];
                discarded.add(assistant[i]);
                assistant[i] = null;
            }
        }
        return played;
    }
}
