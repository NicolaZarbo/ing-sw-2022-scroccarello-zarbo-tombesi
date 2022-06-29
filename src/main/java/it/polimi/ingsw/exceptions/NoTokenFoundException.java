package it.polimi.ingsw.exceptions;

/**Thrown when a certain token is searched, but it is not present.*/
public class NoTokenFoundException extends IllegalMoveException{
    public NoTokenFoundException(String s){super(s);}
}
