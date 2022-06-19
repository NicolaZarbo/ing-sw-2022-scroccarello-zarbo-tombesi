package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.util.ParameterObject;

public class Character11 extends TokensCharacter{

    /**It builds the card number 1.
     * @param id id of the card which will always be 11*/
    public Character11(int id) {
        super(id);
    }

    /**It moves one target student from this card to the dining room of the player, then the list of students is refilled.
     * @param parameters parameter object
     * @param game the game
     * @see ParameterObject */
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
