package it.polimi.ingsw.exceptions;

public class CharacterErrorException extends RuntimeException{
    public CharacterErrorException(String message) {
        super(message);
    }

    public CharacterErrorException() {
    }
}
