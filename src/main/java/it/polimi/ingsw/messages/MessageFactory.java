package it.polimi.ingsw.messages;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.messages.client.*;
import it.polimi.ingsw.messages.server.*;

public class MessageFactory {
    public static ServerMessage getMessageFromServer(String json){
        JsonObject jj = JsonParser.parseString(json).getAsJsonObject();
        String type= jj.get("messageType").getAsString();
        if(type==null)
            throw new MessageErrorException("missing message Type");
        return switch (type) {
            case "CloudMessage" -> new CloudMessage(json);
            case "MotherPositionMessage" -> new MotherPositionMessage(json);
            case "IslandsMessage" -> new IslandsMessage(json);
            case "SingleBoardMessage" -> new SingleBoardMessage(json);
            case "ErrorMessageForClient"-> new ErrorMessageForClient(json);
            case "MultipleServerMessage" -> new MultipleServerMessage(json);
            default -> throw new MessageErrorException("no corresponding message type, found :" + type);

        };
    }

    public static ClientMessage getMessageFromClient(String json){
        JsonObject jj = JsonParser.parseString(json).getAsJsonObject();
        String type= jj.get("messageType").getAsString();
        if(type==null)
            throw new MessageErrorException("missing message Type");
        return switch (type) {
            case "ChooseCloudMessage" -> new ChooseCloudMessage(json);
            case "MoveMotherMessage"-> new MoveMotherMessage(json);
            case "StudentToHallMessage" -> new StudentToHallMessage(json);
            case "PlayAssistantMessage" -> new PlayAssistantMessage(json);
            case "StudentToIslandMessage" -> new StudentToIslandMessage(json);
            default -> throw new MessageErrorException("no corresponding message type, found :" + type);
        };
    }
}
