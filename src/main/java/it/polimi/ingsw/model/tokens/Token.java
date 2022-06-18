package it.polimi.ingsw.model.tokens;

import it.polimi.ingsw.enumerations.TokenColor;

/**The generic token of the game. It is characterized by a unique id and a color.*/
public abstract class Token {
    private final int id;
    private final TokenColor color;

    /**It sets the id and the color of the token.
     * @param id id of the token
     * @param color color of the token*/
    public Token(int id, TokenColor color) {
        this.id = id;
        this.color = color;
    }

    /**@return the id of the token*/
    public int getId() {
        return id;
    }

    /**@return the color of the token*/
    public TokenColor getColor() {
        return color;
    }
}

