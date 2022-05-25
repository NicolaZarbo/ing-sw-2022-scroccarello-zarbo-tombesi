package it.polimi.ingsw.exceptions;

public class NoPlaceAvailableException extends RuntimeException{
    public NoPlaceAvailableException() {}

    public NoPlaceAvailableException(String message) {
        super(message);
    }
}
