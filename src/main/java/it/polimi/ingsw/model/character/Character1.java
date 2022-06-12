package it.polimi.ingsw.model.character;

import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.messages.servermessages.CharacterTokenMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.util.ParameterObject;

public class Character1 extends TokensCharacter{


    public Character1(int id) {
        super(id);
    }

    /**idStudent from card, id target island*/

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
        game.groupMultiMessage(new CharacterTokenMessage(1,game));
    }


}
