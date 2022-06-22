package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.exceptions.MessageErrorException;

/**The generic message exchanged between client and server. It is serialized and then exchanged through a socket. It can contain a certain text message (especially messages for client).*/
public abstract class GenericMessage {
    protected String json;
    protected String messageType;

    /**It builds an instance of message object.*/
    public GenericMessage(){}

    /** It serializes the message into json format.*/
    protected void serialize(){
        Gson gson=new Gson();
        JsonObject jj ;
        messageType = this.getClass().getSimpleName();
        jj=gson.toJsonTree(this,this.getClass()).getAsJsonObject();
        this.json= jj.toString();
    }

    /**It builds an instance of a message from a json string.
     * @param json the message in json format*/
    public GenericMessage(String json){
        this.messageType = this.getClass().getSimpleName();
        JsonObject gg = JsonParser.parseString(json).getAsJsonObject();
        if (!this.messageType.equals(gg.get("messageType").getAsString()))
            throw new MessageErrorException("needed "+ messageType +", found " + gg.get("messageType").getAsString());
        this.json=gg.toString();
        parseMessage(gg);
    }

    /**It parses the message fields from the json.
     * @param gg the json to parse*/
    protected abstract void parseMessage(JsonObject gg);

    /** @return the class name of the message*/
    public String getType() {
        return messageType;
    }

    /**@return the serialized message as a json string*/
    public String getJson(){
        return  json;
    }



}
