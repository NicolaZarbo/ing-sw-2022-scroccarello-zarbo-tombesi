package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class CharacterOne extends TokensCharacter{


    public CharacterOne(int id, ArrayList<Student> studs) {
        super(id, studs);
    }


    @Override
    /** @param parameters : idStudent from card, id target island*/
    public void cardEffect(List<Integer> parameters, Game game) {
        Student stud;
        Island island;
        if (parameters.size()==2 ){
            stud = getStudent(parameters.get(0));
            island = game.getIsland(parameters.get(1));
            if (stud == null || island == null){}
                // eccezione TODO
            island.addStudent(stud);
            addStudents(game.getBag().getToken());
            incrementCost();
        }

    }


}
