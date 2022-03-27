package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Character7 extends TokensCharacter{

    public  Character7(int id,ArrayList<Student> studs){
        super(id, studs);

    }
    @Override
    public void cardEffect() {
        incrementCost();
    }
}
