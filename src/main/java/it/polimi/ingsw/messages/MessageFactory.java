package it.polimi.ingsw.messages;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.exceptions.MessageErrorException;

public class MessageFactory {
    public static FromServerMessage getMessageFromServer(String json){
        JsonObject jj = JsonParser.parseString(json).getAsJsonObject();
        String type= jj.get("MessageType").getAsString();
        if(type==null)
            throw new MessageErrorException("missing message Type");
        switch (type){
            case "CloudMessage":
                return new CloudMessage(json);
            default:
                throw new MessageErrorException("no corresponding message type, found :"+type);
        }
    }

    public static FromClientMessage getMessageFromClient(String json){
        JsonObject jj = JsonParser.parseString(json).getAsJsonObject();
        String type= jj.get("MessageType").getAsString();
        if(type==null)
            throw new MessageErrorException("missing message Type");
        switch (type){
            case "":

            default:
                throw new MessageErrorException("no corresponding message type, found :"+type);
        }
    }
}
