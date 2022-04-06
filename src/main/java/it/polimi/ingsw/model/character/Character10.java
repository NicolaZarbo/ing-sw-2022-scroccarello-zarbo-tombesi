package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;

public class Character10 extends CharacterCard{
    public Character10(int id) {
        super(id);
    }

    @Override /** param player id, students ID from entrance and from hall */
    public void cardEffect(ParameterObject parameters, Game game) {
        Board board ;
        int nStudent =parameters.getTargetStudentsOnEntrance().length;
        board= game.getPlayer(parameters.getOtherTargetId()).getBoard();
        for(int i=0; i<nStudent;i++){
            board.moveToDiningRoom(board.getStudentFromEntrance(parameters.getTargetStudentsOnEntrance()[i]));
            board.putStudentOnEntrance(board.getFromDiningRoom(parameters.getTargetStudentsForExchange()[i]));


        }
        incrementCost();
    }

}
