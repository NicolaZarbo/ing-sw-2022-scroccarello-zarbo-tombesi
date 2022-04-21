package model;



public abstract class CharacterCard extends Card {
    private int id;
    private int cost;

    public abstract void cardEffect();
    public void incrementCost(){
        this.cost ++;
    }
}
