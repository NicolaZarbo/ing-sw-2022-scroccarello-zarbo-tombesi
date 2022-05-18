package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.exceptions.MessageErrorException;

public abstract class GenericMessage {
    protected String json;
    protected String messageType;

    public GenericMessage(){}

    protected void serialize(){
        Gson gson=new Gson();
        JsonObject jj ;
        messageType = this.getClass().getSimpleName();
        jj=gson.toJsonTree(this,this.getClass()).getAsJsonObject();
        this.json= jj.toString();
    }

    public GenericMessage(String json){
        this.messageType = this.getClass().getSimpleName();
        JsonObject gg = JsonParser.parseString(json).getAsJsonObject();
        if (!this.messageType.equals(gg.get("messageType").getAsString()))
            throw new MessageErrorException("needed "+ messageType +", found " + gg.get("messageType").getAsString());
        this.json=gg.toString();
        parseMessage(gg);
    }

    protected abstract void parseMessage(JsonObject gg);

    public String getType() {
        return messageType;
    }

    public String getJson(){
        return  json;
    }



}
