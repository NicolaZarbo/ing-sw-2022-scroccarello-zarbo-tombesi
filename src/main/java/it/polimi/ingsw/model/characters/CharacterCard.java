package it.polimi.ingsw.model.characters;


import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.util.ParameterObject;

public abstract class CharacterCard extends Card {
    private int cost;

    public CharacterCard(int id){
        super(id);
        this.cost=((id-1)%3)+1;
    }
    public int getCost() {
        return cost;
    }



    public void cardEffect(ParameterObject parameters, Game game){
        incrementCost();
    }
    public void incrementCost(){
        this.cost ++;
    }
}
