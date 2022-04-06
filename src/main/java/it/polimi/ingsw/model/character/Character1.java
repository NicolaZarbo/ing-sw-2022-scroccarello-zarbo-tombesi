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
    public void cardEffect(ParameterObject parameters, Game game) throws NullPointerException{
        Student stud;
        Island island;
        stud = this.getStudent(parameters.getTargetStudentId());
        island = game.getIsland(parameters.getOtherTargetId());
        island.addStudent(stud);
        this.addStudents(game.getBag().getToken());
        incrementCost();

    }


}
