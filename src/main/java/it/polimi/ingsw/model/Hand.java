package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.LinkedList;

public class Hand {
    private int coin;
    private ArrayList<AssistantCard> assistant;
    private LinkedList<AssistantCard> discarded;

    public Hand(ArrayList<AssistantCard> assistentiIniz){
        coin =0;
        assistant =new ArrayList<>();
        discarded=new LinkedList<>();
        for(int i=0;i<assistentiIniz.size();i++)
            assistant.add(assistentiIniz.get(i));
    }

    public LinkedList<AssistantCard> getDiscarded() {
        return discarded;
    }

    public ArrayList<AssistantCard> getAssistant() {
        return assistant;
    }

    public int getCoin() {
        return coin;
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
        for(int i=0;i<assistant.size();i++){
            if(i==n && assistant.get(i)!=null) {
                played=assistant.get(i);
                discarded.add(assistant.get(i));
                assistant.remove(i) ;
            }
        }
        return played;
    }
}
