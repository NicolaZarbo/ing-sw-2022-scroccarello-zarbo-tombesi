package it.polimi.ingsw.model.character;


import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Card;

// we will have to use a strategy Pattern with the method isInstanceOff() to show the view information about 4 particular cards
public abstract class CharacterCard extends Card {
    private final int id;
    private int cost;

    public CharacterCard(int id){
        this.id=id;
        this.cost=((id-1)%3)+1;
    }
    public int getCost() {
        return cost;
    }

    public int getId() {
        return id;
    }

    //a way to make this work is to use some kind of generic type wich encapsulates the parameters used for the different operations + the game as param.
    public void cardEffect(ParameterObject parameters, Game game){
        if(this.getCost()>game.getPlayer(game.getCurrentPlayerId()).getHand().getCoin()){
            throw new IllegalMoveException("not enough money");
        }
        incrementCost();
    }
    public void incrementCost(){
        this.cost ++;
    }
}
