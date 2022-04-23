package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

import javax.swing.text.View;

public abstract class ServerMessage extends GenericMessage {
    /** Called by factory, creates the message from a json string  */
    public ServerMessage(String json){
        super(json);
    }
    public ServerMessage(RuntimeException error) {
        if(error==null)
            throw new NullPointerException("missing error");
    }
    public ServerMessage(Game game){
        if(game==null)
            throw new NullPointerException();
    }
    public ServerMessage(ServerMessage message){}
    public String getJson(){
        return  json;
    }
    /** Checks the message type, further parsing in subclasses */
    protected abstract void parseMessage(JsonObject gg) ;
    public abstract void doAction(CentralView view);


}