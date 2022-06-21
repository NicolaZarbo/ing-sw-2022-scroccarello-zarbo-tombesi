package it.polimi.ingsw.exceptions;

/**Thrown when a character card's effect is activated by passing it the wrong parameters.*/
public class CharacterErrorException extends RuntimeException{
    public CharacterErrorException(String message) {
        super(message);
    }

}
