package it.polimi.ingsw.exceptions;

public class MessageErrorException extends RuntimeException{
    public MessageErrorException(String message){
        super(message);
    }
}
