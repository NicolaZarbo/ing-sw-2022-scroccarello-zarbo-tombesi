package it.polimi.ingsw.model.token;

public abstract class Token {
    private int id;
    private TokenColor color;

    public int getId() {
        return id;
    }

    public TokenColor getColor() {
        return color;
    }
}

