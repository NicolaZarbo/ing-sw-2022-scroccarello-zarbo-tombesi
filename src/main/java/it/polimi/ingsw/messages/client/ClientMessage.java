package it.polimi.ingsw.messages.client;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.GenericMessage;

public abstract class ClientMessage extends GenericMessage {
    protected int playerId; // ip, id, string of characters?


    public int getPlayerId() {
        return playerId;
    }
    protected ClientMessage(String json){
        super(json);
    }
    public ClientMessage(int playerId){
        this.playerId=playerId;
    }
    public abstract void doAction(Controller controller);

}