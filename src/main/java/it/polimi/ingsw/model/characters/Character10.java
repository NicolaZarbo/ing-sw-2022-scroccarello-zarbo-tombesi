package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.util.ParameterObject;

/**The character card number 10.*/
public class Character10 extends CharacterCard{
    /**It builds the card number 1.
     * @param id id of the card which will always be 10*/
    public Character10(int id) {
        super(id);
    }

    /**It exchanges up to 2 students between entrance and dining room of the player.
     * @param parameters parameter object with 3 parameters
     * @param game the model
     * @see ParameterObject */
    @Override
    public void cardEffect(ParameterObject parameters, Game game) {
        Board board ;
        if(parameters.getnParam()!=3)
            throw new CharacterErrorException("wrong parameters");
        int nStudent =parameters.getTargetStudentsOnEntrance().length;
        board= game.getPlayer(parameters.getOtherTargetId()).getBoard();
        for(int i=0; i<nStudent;i++){
            board.moveToDiningRoom(board.getStudentFromEntrance(parameters.getTargetStudentsOnEntrance()[i]));
            board.putStudentOnEntrance(board.getFromDiningRoom(parameters.getTargetStudentsForExchange()[i]));
        }
        incrementCost();
    }

}
