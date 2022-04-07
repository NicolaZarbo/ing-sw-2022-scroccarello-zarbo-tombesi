package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;

import java.util.List;

public class Character11 extends TokensCharacter{


    public Character11(int id) {
        super(id);
    }

    @Override/** @param parameters : 1 idplayer, 2 idStudent to get*/
    public void cardEffect(ParameterObject parameters, Game game) {

            Board board = game.getPlayer(parameters.getOtherTargetId()).getBoard();
            board.moveToDiningRoom(this.getStudent(parameters.getTargetStudentId()));
            this.addStudents(game.getBag().getToken());
            incrementCost();
    }
}
