package it.polimi.ingsw.exceptions;

public class IllegalMoveException extends RuntimeException{
    public IllegalMoveException() {
    }

    public IllegalMoveException(String message) {
        super(message);
    }
}
