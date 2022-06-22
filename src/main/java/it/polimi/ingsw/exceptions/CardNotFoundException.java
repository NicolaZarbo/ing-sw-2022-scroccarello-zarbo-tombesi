package it.polimi.ingsw.exceptions;

/**Thrown when a player wants to play a card that is not available.*/
public class CardNotFoundException extends RuntimeException{
    public CardNotFoundException() {
    }

    public CardNotFoundException(String message) {
        super(message);
    }
}
