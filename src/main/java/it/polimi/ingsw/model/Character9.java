package it.polimi.ingsw.model;

import java.util.List;

public class Character9 extends TurnEffectCharacter{
    public Character9(int id) {
        super(id);
    }
    @Override
    public void cardEffect(List<Integer> parameters, Game game){
        super.cardEffect(parameters,game);
        game.setTargetColor(TokenColor.getColor(parameters.get(0)));
    }

}
