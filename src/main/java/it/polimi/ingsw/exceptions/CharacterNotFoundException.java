package it.polimi.ingsw.exceptions;

public class CharacterNotFoundException extends RuntimeException{
    public CharacterNotFoundException(){super();}
    public CharacterNotFoundException(String message){super(message);}
}
