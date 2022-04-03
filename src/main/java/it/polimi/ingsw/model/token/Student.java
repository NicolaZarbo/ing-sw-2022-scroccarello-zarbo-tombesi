package it.polimi.ingsw.model.token;

public class Student extends Token {
    private final int id;
    private final TokenColor color;

    public Student(int id, TokenColor col){
        this.id=id;
        this.color =col;
    }

    public TokenColor getColor() {
        return color;
    }

    public int getId() {
        return id;
    }
}
