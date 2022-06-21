package it.polimi.ingsw.exceptions;

/**Thrown when is received a message instead of the right message.*/
public class MessageErrorException extends RuntimeException{
    public MessageErrorException(String message){
        super(message);
    }
}
