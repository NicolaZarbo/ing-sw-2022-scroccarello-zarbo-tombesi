package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.util.ParameterObject;

/**The generic character card which sets its effect as active during the whole turn. Characters: 2, 6, 8.*/
public class TurnEffectCharacter extends CharacterCard {

    /**It builds the turn effect card with its id
     * @param id id of the card*/
    public TurnEffectCharacter(int id) {
        super(id);
    }


    /**Version used for the cards that have an influence on how the game rules
     * @param game the game
     * @param parameters the parameter object to trigger the effect
     * @see ParameterObject */
    @Override
    public void cardEffect(ParameterObject parameters, Game game) {
        game.setCardBonusActive(this.getId());
        this.incrementCost();
    }
}
