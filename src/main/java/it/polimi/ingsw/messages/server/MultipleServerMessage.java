package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.model.Game;

import java.util.ArrayList;

public class MultipleServerMessage extends ServerMessage{
    private ArrayList<ServerMessage> messages;
    private boolean serialized;
    public MultipleServerMessage(String json) {
        super(json);
    }

    public MultipleServerMessage(ServerMessage firstMessage) {
        messages= new ArrayList<>();
        messages.add(firstMessage);
        this.serialized=false;
    }
    public void addMessage(ServerMessage anotherMessage){
        if(serialized) {
            throw new MessageErrorException("message already serialized");
        }
        messages.add(anotherMessage);
    }
    public void Serialize(){
        super.serialize();
        this.serialized=true;
    }

    public boolean isSerialized() {
        return serialized;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.messages=gson.fromJson(gg, MultipleServerMessage.class).getMessages();
    }

    public ArrayList<ServerMessage> getMessages() {
        return messages;
    }
}
