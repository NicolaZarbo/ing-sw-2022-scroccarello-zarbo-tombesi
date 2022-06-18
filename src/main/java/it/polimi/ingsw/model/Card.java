package it.polimi.ingsw.model;
/**The generic card in the game. It is characterized by a unique and constant id.*/
public abstract class Card {
    private final int id;

    /**It assigns the relative id to the card*/
    protected Card(int ID){
        this.id=ID;
    }

    /**@return the id of the card*/
    public int getId(){return this.id;}
}
