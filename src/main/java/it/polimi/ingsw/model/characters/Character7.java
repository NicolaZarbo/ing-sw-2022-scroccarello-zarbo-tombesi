package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.tokens.Student;
import it.polimi.ingsw.util.ParameterObject;

import java.util.ArrayList;

public class Character7 extends TokensCharacter{

    public  Character7(int id){
        super(id);
    }

    /**@param parameters : first playerId, then students in entrance then on studs cards*/
    @Override
    public void cardEffect(ParameterObject parameters, Game game) {
        if(parameters.getnParam()!=3)
            throw new CharacterErrorException("wrong parameters");
        int size = parameters.getTargetStudentsOnEntrance().length;
        Board board ;
        ArrayList<Student>  fromBoard = new ArrayList<>();
        board = game.getPlayer(parameters.getOtherTargetId()).getBoard();
        for (int i =0; i<size;i++) {
            fromBoard.add(board.getStudentFromEntrance(parameters.getTargetStudentsOnEntrance()[i]));
            board.putStudentOnEntrance(this.getStudent(parameters.getTargetStudentsForExchange()[i]));
        }
        this.addStudents(fromBoard);
        incrementCost();

    }
}