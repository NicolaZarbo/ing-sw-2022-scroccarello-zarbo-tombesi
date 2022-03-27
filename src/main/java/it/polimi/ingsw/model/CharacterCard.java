package it.polimi.ingsw.model;



public abstract class CharacterCard extends Card {
    private int id;
    private int cost;
    //a way to make this work is to use some kind of generic type wich encapsulates the parameters used for the different operations + the game as param.
    public abstract void cardEffect();
    public void incrementCost(){
        this.cost ++;
    }
}
