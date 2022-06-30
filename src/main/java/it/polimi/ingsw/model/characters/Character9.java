package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.util.ParameterObject;

/**The character card number 9. It lets the player select a target color, then it excludes it from the influence calculation for the whole turn.*/
public class Character9 extends TurnEffectCharacter{

    /**It builds the card number 1.
     * @param id id of the card which will always be 9*/
    public Character9(int id) {
        super(id);
    }

    /**It excludes from the influence calculation the target color for the whole turn.
     * @param game the model
     * @param parameters parameter object with 1 parameter
     * @see ParameterObject */
    @Override
    public void cardEffect(ParameterObject parameters, Game game){
        if(parameters.getnParam()!=1)
            throw new CharacterErrorException("wrong parameters");
        game.setTargetColor(TokenColor.getColor(parameters.getOtherTargetId()));
        incrementCost();
        game.setCardBonusActive(this.getId());
    }

}
