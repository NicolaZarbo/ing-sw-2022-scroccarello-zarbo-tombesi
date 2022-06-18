package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.util.ParameterObject;

public class TurnEffectCharacter extends CharacterCard {

    public TurnEffectCharacter(int id) {
        super(id);
    }

    /** Version used for the cards that have an influence on how the game rules*/
    @Override
    public void cardEffect(ParameterObject parameters, Game game) {
        game.setCardBonusActive(this.getId());
        this.incrementCost();
    }
}
