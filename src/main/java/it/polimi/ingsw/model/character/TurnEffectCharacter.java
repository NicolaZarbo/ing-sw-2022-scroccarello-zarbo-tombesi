package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.Game;

public class TurnEffectCharacter extends CharacterCard {

    public TurnEffectCharacter(int id) {
        super(id);
    }

    @Override
    public void cardEffect(ParameterObject parameters, Game game) {
        game.setCardBonusActive(this.getId());
        this.incrementCost();
    }
}
