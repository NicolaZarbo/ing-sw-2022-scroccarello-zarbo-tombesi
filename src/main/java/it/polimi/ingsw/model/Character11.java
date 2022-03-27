package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Character11 extends TokensCharacter{


    public Character11(int id, ArrayList<Student> studs) {
        super(id, studs);
    }

    @Override
    public void cardEffect() {
        incrementCost();
    }
}
