package it.polimi.ingsw.model.tokens;

import it.polimi.ingsw.enumerations.TokenColor;

/**The student token of the game, which is the core token of the game. All of them are gathered into the bag at the beginning of the game. They can be found in the board on the entrance or in the dining room as well as in the map either on the islands or on the clouds.
 * @see Token
 * @see it.polimi.ingsw.model.Board
 * @see it.polimi.ingsw.model.Cloud
 * @see it.polimi.ingsw.model.Bag
 * @see it.polimi.ingsw.model.Island*/
public class Student extends Token {
    /**It builds the student token
     * @param id id of the student
     * @param col color of the token*/
    public Student(int id, TokenColor col){
        super(id,col);
    }
}
