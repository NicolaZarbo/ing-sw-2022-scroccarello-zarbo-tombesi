package it.polimi.ingsw.exceptions;

/**Thrown when a player wants to insert a token where there are no available spots.*/
public class NoPlaceAvailableException extends IllegalMoveException{


    public NoPlaceAvailableException(String message) {
        super(message);
    }
}
