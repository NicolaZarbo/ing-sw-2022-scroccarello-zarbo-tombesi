package it.polimi.ingsw.messages;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.messages.client.ClientMessage;
import it.polimi.ingsw.messages.server.CloudMessage;
import it.polimi.ingsw.messages.server.ServerMessage;

public class MessageFactory {
    public static ServerMessage getMessageFromServer(String json){
        JsonObject jj = JsonParser.parseString(json).getAsJsonObject();
        String type= jj.get("messageType").getAsString();
        if(type==null)
            throw new MessageErrorException("missing message Type");
        switch (type){
            case "CloudMessage":
                return new CloudMessage(json);
            default:
                throw new MessageErrorException("no corresponding message type, found :"+type);
        }
    }

    public static ClientMessage getMessageFromClient(String json){
        JsonObject jj = JsonParser.parseString(json).getAsJsonObject();
        String type= jj.get("messageType").getAsString();
        if(type==null)
            throw new MessageErrorException("missing message Type");
        switch (type){
            case "":

            default:
                throw new MessageErrorException("no corresponding message type, found :"+type);
        }
    }
}
