package it.polimi.ingsw.messages.servermessages;

import com.google.gson.JsonObject;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

/**The generic message coming from the server.*/
public abstract class ServerMessage extends GenericMessage {

    /**It creates a message from a json string.
     * @param json the string message*/
    public ServerMessage(String json){
        super(json);
    }

    /**It builds an error message.
     * @param error the exception related to the error
     * @exception NullPointerException if a null exception is thrown*/
    public ServerMessage(RuntimeException error) {
        if(error==null)
            throw new NullPointerException("missing error");
    }

    /**It builds a message starting from a valid model.
     * @param game the model game
     * @exception NullPointerException if the game doesn't exist*/
    public ServerMessage(Game game){
        if(game==null)
            throw new NullPointerException();
    }
    public ServerMessage(ServerMessage message){}

    @Override
    protected abstract void parseMessage(JsonObject gg) ;

    /**It resolves the atomic effect of the message.
     * @param view the central view of the game*/
    public abstract void doAction(CentralView view);

}
