package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.exceptions.MessageErrorException;

public abstract class GenericMessage {
    protected String json;
    protected String messageType;

    public GenericMessage(){}
    /** Serialize to json the message*/
    protected void serialize(){
        Gson gson=new Gson();
        JsonObject jj ;
        messageType = this.getClass().getSimpleName();
        jj=gson.toJsonTree(this,this.getClass()).getAsJsonObject();
        this.json= jj.toString();
    }

    /**Used to create an instance of a message from a json string */
    public GenericMessage(String json){
        this.messageType = this.getClass().getSimpleName();
        JsonObject gg = JsonParser.parseString(json).getAsJsonObject();
        if (!this.messageType.equals(gg.get("messageType").getAsString()))
            throw new MessageErrorException("needed "+ messageType +", found " + gg.get("messageType").getAsString());
        this.json=gg.toString();
        parseMessage(gg);
    }
    /** Parse the message fields from the json*/
    protected abstract void parseMessage(JsonObject gg);
    /** @return the className of the actual message*/
    public String getType() {
        return messageType;
    }

    /**
      * @return the serialized message as a json string
     */
    public String getJson(){
        return  json;
    }



}
