package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.tokens.Student;
import it.polimi.ingsw.util.ParameterObject;

/**The character card number 1.*/
public class Character1 extends TokensCharacter{

    /**It builds the card number 1.
     * @param id id of the card which will always be 1*/
    public Character1(int id) {
        super(id);
    }

    /**idStudent from card, id target island*/

    /**It draws one student from the card and puts it on a target island. Then, the students' list is refilled.
     * @param parameters parameter object with 2 parameters*
     * @param game the model
     * @see ParameterObject */
    @Override
    public void cardEffect(ParameterObject parameters, Game game){
        Student stud;
        Island island;
        if(parameters.getnParam()!=2)
            throw new CharacterErrorException("Wrong parameters");
        stud = this.getStudent(parameters.getTargetStudentId());
        island = game.getIsland(parameters.getOtherTargetId());
        island.addStudent(stud);
        this.addStudents(game.getBag().getToken());
        if(game.getBag().isEmpty())
            game.gameOver();
        incrementCost();
    }


}
