package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.util.ParameterObject;

public class Character10 extends CharacterCard{
    public Character10(int id) {
        super(id);
    }
    /** param player id, students ID from entrance and from hall */
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
