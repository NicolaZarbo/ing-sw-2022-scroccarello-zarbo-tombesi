package it.polimi.ingsw.model;

import java.util.List;

public class TurnEffectCharacter extends CharacterCard {

    public TurnEffectCharacter(int id) {
        super(id);
    }

    @Override
    public void cardEffect(List<Integer> parameters, Game game) {
        game.setCardBonusActive(this.getId());
    }
}
