package it.polimi.ingsw.model;

public class Student extends Token {
    private final int id;
    private final TokenColor col;

    public Student(int id, TokenColor col){
        this.id=id;
        this.col=col;
    }

    public TokenColor getCol() {
        return col;
    }

    public int getId() {
        return id;
    }
}
