package it.polimi.ingsw.messages;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.messages.clientmessages.*;
import it.polimi.ingsw.messages.servermessages.*;

/**The factory class to build each of the possible messages to exchange.*/
public class MessageFactory {

    /**It is used to parse a message from the server received in a string format.
     * @param json the string message
     * @return the corresponding message from the server*/
    public static ServerMessage getMessageFromServer(String json){
        String type;
        try {
            JsonObject jj = JsonParser.parseString(json).getAsJsonObject();
            type= jj.get("messageType").getAsString();
        }catch (RuntimeException e){
            //System.out.println(json);
            throw new MessageErrorException(json);
        }
        if(type==null)
            throw new MessageErrorException("missing message Type");
        return switch (type) {
            case "CloudMessage" -> new CloudMessage(json);
            case "MotherPositionMessage" -> new MotherPositionMessage(json);
            case "IslandsMessage" -> new IslandsMessage(json);
            case "SingleBoardMessage" -> new SingleBoardMessage(json);
            case "ErrorMessageForClient"-> new ErrorMessageForClient(json);
            case "MultipleServerMessage" -> new MultipleServerMessage(json);
            case "CharacterTokenMessage" -> new CharacterTokenMessage(json);
            case "CharacterUpdateMessage" -> new CharacterUpdateMessage(json);
            case "PlayerSetUpMessage" -> new PlayerSetUpMessage(json);
            case "PlayedAssistantMessage" -> new PlayedAssistantMessage(json);
            case "WholeGameMessage"-> new WholeGameMessage(json);
            case "ChangePhaseMessage"-> new ChangePhaseMessage(json);
            case "ChangeTurnMessage"-> new ChangeTurnMessage(json);
            case "GameOverMessage"-> new GameOverMessage(json);
            default -> throw new MessageErrorException("no corresponding message type, found :" + type);

        };
    }

    /**It is used to parse a message from the client received in a string format.
     * @param json the string message
     * @return the corresponding message from the client*/
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
            case "CharacterCardMessage"-> new CharacterCardMessage(json);
            case "PrePlayerMessage"-> new PrePlayerMessage(json);
            default -> throw new MessageErrorException("no corresponding message type, found:" + type);
        };
    }

}
