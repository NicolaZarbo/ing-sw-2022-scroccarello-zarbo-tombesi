package it.polimi.ingsw.model;


import java.util.List;
// we will have to use a strategy Pattern with the method isInstanceOff() to show the view information about 4 particular cards
public abstract class CharacterCard extends Card{
    private int id;
    private int cost;

    public CharacterCard(int id){
        this.id=id;
        this.cost=(id%3)+1;
    }
    public int getCost() {
        return cost;
    }

    public int getId() {
        return id;
    }

    //a way to make this work is to use some kind of generic type wich encapsulates the parameters used for the different operations + the game as param.
    public abstract void cardEffect(List<Integer> parameters, Game game);
    public void incrementCost(){
        this.cost ++;
    }
}
