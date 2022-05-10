package it.polimi.ingsw.model.character;

import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.Game;

public class TurnEffectCharacter extends CharacterCard {

    public TurnEffectCharacter(int id) {
        super(id);
    }

    @Override
    public void cardEffect(ParameterObject parameters, Game game) {
        if(this.getCost()>game.getPlayer(game.getCurrentPlayerId()).getHand().getCoin()){
            throw new IllegalMoveException("not enough money");
        }
        else {
            game.setCardBonusActive(this.getId());
            this.incrementCost();
        }
    }
}
