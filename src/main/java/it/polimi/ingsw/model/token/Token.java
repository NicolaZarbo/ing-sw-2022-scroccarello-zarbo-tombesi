package it.polimi.ingsw.model.token;

public abstract class Token {
    private final int id;
    private final TokenColor color;

    public Token(int id, TokenColor color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public TokenColor getColor() {
        return color;
    }
}

