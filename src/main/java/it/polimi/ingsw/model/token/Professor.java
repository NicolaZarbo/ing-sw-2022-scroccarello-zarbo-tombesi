package it.polimi.ingsw.model.token;

public class Professor extends Token {
    private final int id;
    private final TokenColor color;

    public Professor(int id, TokenColor col){
        this.id=id;
        this.color =col;
    }

    public int getId() {
        return id;
    }

    public TokenColor getColor() {
        return color;
    }
}
