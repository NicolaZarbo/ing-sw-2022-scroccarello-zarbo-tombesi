package it.polimi.ingsw.messages.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.exceptions.MessageErrorException;

public abstract class ClientMessage {
    protected int playerId; // ip, id, string of characters?
    protected String json;
    protected String messageType;

    public int getPlayerId() {
        return playerId;
    }

    public String getJson() {
        return json;
    }

    public String getMessageType() {
        return messageType;
    }

    protected ClientMessage(String json){
        this.messageType = this.getClass().getSimpleName();
        JsonObject gg = JsonParser.parseString(json).getAsJsonObject();
        if (!this.messageType.equals(gg.get("messageType").getAsString()))
            throw new MessageErrorException("needed "+ messageType +", found " + gg.get("messageType").getAsString());
        this.json=gg.toString();
        parseMessage(gg);
    }

    protected void serialize(){
        Gson gson=new Gson();
        JsonObject jj;
        messageType = this.getClass().getSimpleName();
        jj=gson.toJsonTree(this,this.getClass()).getAsJsonObject();
        this.json= jj.toString();
    }
    protected abstract void parseMessage(JsonObject gg) ;


    public ClientMessage(int playerId){
        this.playerId=playerId;
    }


}
