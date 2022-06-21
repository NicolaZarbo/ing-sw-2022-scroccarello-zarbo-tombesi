package it.polimi.ingsw.exceptions;

/**Thrown when the bag is invoked but there are no tokens left inside it.*/
public class EmptyBagException extends RuntimeException{
    public EmptyBagException(String message){super(message);}
}
