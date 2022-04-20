package it.polimi.ingsw.exceptions;

public class CardNotFoundException extends RuntimeException{
    public CardNotFoundException() {
    }

    public CardNotFoundException(String message) {
        super(message);
    }
}
