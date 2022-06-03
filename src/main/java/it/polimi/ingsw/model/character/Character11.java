package it.polimi.ingsw.model.character;

import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;

public class Character11 extends TokensCharacter{


    public Character11(int id) {
        super(id);
    }
    /** @param parameters : 1 idplayer, 2 idStudent to get*/
    @Override
    public void cardEffect(ParameterObject parameters, Game game) {
        if(parameters.getnParam()!=2)
                throw new CharacterErrorException("wrong parameters");
            Board board = game.getPlayer(parameters.getOtherTargetId()).getBoard();
            board.moveToDiningRoom(this.getStudent(parameters.getTargetStudentId()));
            this.addStudents(game.getBag().getToken());
            if(game.getBag().isEmpty())
                game.gameOver();
            incrementCost();
    }
}
