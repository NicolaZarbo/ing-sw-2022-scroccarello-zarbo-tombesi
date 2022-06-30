package it.polimi.ingsw.messages.clientmessages;

import com.google.gson.JsonParser;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.GenericMessage;

/**The generic message coming from the client.*/
public abstract class ClientMessage extends GenericMessage {
    protected int playerId;


    /**@return the id of the player which sent the message*/
    public int getPlayerId() {
        return playerId;
    }

    /**It builds the message starting from the json string.
     * @param json the string message*/
    protected ClientMessage(String json){
        super(json);
        playerId= JsonParser.parseString(json).getAsJsonObject().get("playerId").getAsInt();
    }

    /**It builds the message starting from the player's id.
     * @param playerId the player's id*/
    public ClientMessage(int playerId){
        this.playerId=playerId;
    }

    /**It resolves the atomic effect of the message.
     * @param controller the controller of MVC pattern*/
    public abstract void doAction(Controller controller);

}
