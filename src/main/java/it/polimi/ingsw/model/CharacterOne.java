package it.polimi.ingsw.model;

import java.util.ArrayList;

public class CharacterOne extends TokensCharacter{
    private int id;
    private int cost;
    private ArrayList<Student> students;

    public CharacterOne(int id, ArrayList<Student> studs) {
        super(id, studs);
    }


    @Override
    public void cardEffect() {
        incrementCost();
    }
}
