package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.token.Student;

import java.util.List;

public class Character1 extends TokensCharacter{


    public Character1(int id) {
        super(id);
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
