package it.polimi.ingsw.model.tokens;

import it.polimi.ingsw.enumerations.TokenColor;

/**The professor token of the game. It joins the player's school according to the number of tokens of its color he has.
 * @see Token
 * @see it.polimi.ingsw.model.Board*/
public class Professor extends Token {
    /**It builds the professor token
     * @param id id of the token
     * @param col color of the token*/
    public Professor(int id, TokenColor col){
        super(id,col);
    }
}
