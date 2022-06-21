package it.polimi.ingsw.exceptions;

/**Thrown when an illegal move is invoked.*/
public class IllegalMoveException extends RuntimeException{
    public IllegalMoveException() {
    }

    public IllegalMoveException(String message) {
        super(message);
    }
}
