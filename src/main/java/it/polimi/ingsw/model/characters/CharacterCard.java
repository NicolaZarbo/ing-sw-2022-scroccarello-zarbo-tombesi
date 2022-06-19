package it.polimi.ingsw.model.characters;


import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Card;

/**The generic character card of the game (for expert mode only). It has a cost to pay to activate its effect which incrementally grows at every activation.*/
public abstract class CharacterCard extends Card {
    private int cost;

    /**It builds the card by assigning it the id and the initial cost value.
     * @param id the id of the card*/
    public CharacterCard(int id){
        super(id);
        this.cost=((id-1)%3)+1;
    }
    /**@return the actual cost value of the card*/
    public int getCost() {
        return cost;
    }


    /**It activates the card effect. As a default operation for every character, the cost of the card is incremented.
     * @param parameters parameter object to trigger the effect
     * @param game the game
     * @see ParameterObject*/
    public void cardEffect(ParameterObject parameters, Game game){
        incrementCost();
    }

    /**It increments the cost of the card.*/
    public void incrementCost(){
        this.cost ++;
    }
}
