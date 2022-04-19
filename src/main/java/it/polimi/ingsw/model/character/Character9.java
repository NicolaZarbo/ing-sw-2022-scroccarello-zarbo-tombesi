package it.polimi.ingsw.model.character;

import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.token.TokenColor;

public class Character9 extends TurnEffectCharacter{
    public Character9(int id) {
        super(id);
    }
    @Override
    public void cardEffect(ParameterObject parameters, Game game){
        super.cardEffect(parameters,game);
        if(parameters.getnParam()!=1)
            throw new CharacterErrorException("wrong");
        game.setTargetColor(TokenColor.getColor(parameters.getOtherTargetId()));
    }

}
