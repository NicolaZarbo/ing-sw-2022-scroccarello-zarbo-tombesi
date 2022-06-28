package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.util.ParameterObject;

/**The character card number 11. It moves one target student from this card to the dining room of the player, then the list of students is refilled.*/
public class Character11 extends TokensCharacter{

    /**It builds the card number 11.
     * @param id id of the card which will always be 11*/
    public Character11(int id) {
        super(id);
    }

    /**It moves one target student from this card to the dining room of the player, then the list of students is refilled.
     * @param parameters parameter object with 2 parameters
     * @param game the model
     * @see ParameterObject */
    @Override
    public void cardEffect(ParameterObject parameters, Game game) {
        if(parameters.getnParam()!=2)
                throw new CharacterErrorException("wrong parameters");
            Board board = game.getPlayer(parameters.getOtherTargetId()).getBoard();
            board.moveToDiningRoom(this.getStudent(parameters.getTargetStudentId()));
            if(board.foundCoin(this.getStudent(parameters.getTargetStudentId())))
                game.getPlayer(parameters.getOtherTargetId()).getHand().addCoin();
            this.addStudents(game.getBag().getToken());
            if(game.getBag().isEmpty())
                game.gameOver();
            incrementCost();
    }
}
