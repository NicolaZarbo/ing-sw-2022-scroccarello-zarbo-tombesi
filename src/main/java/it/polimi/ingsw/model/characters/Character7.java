package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.tokens.Student;

import java.util.ArrayList;

/**The character card number 7.*/
public class Character7 extends TokensCharacter{

    /**It builds the card number 1.
     * @param id id of the card which will always be 7*/
    public  Character7(int id){
        super(id);
    }

    /**It can move up to three students from this card to the player's entrance and exchange them with the same number of its.
     * @param parameters parameter object
     * @param game the game*/
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
