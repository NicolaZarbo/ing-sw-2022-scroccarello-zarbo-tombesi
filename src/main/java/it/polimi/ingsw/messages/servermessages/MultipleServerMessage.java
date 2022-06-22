package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.view.CentralView;

import java.util.ArrayList;
import java.util.List;

/**The message which contains multiple information to send.*/
public class MultipleServerMessage extends ServerMessage{

    private ArrayList<String> serializedMessages;
    private boolean serialized;

    /**It builds the message starting from the json string.
     * @param json the string message*/
    public MultipleServerMessage(String json) {
        super(json);
    }

    /**It builds the message starting from a generic server message.
     * @param firstMessage the starting message to build*/
    public MultipleServerMessage(ServerMessage firstMessage) {
        super(firstMessage);
        this.serialized=false;
        serializedMessages=new ArrayList<>();
        serializedMessages.add(firstMessage.getJson());
        this.json="not serialized";
    }

    /**It adds the message to the multi-message.
     * @param anotherMessage the message to add*/
    public void addMessage(ServerMessage anotherMessage){
        if(serialized) {
            throw new MessageErrorException("message already serialized");
        }
        serializedMessages.add(anotherMessage.getJson());
    }

    @Override
    public void serialize(){
        super.serialize();
        this.serialized=true;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.serializedMessages=gson.fromJson(gg, MultipleServerMessage.class).getSerializedMessages();

    }

    @Override
    public void doAction(CentralView view) {
        for (String message :this.serializedMessages) {
            MessageFactory.getMessageFromServer(message).doAction(view);
        }
    }

    /**@return the serialized message*/
    public ArrayList<String> getSerializedMessages() {
        return serializedMessages;
    }

    /**@return the list of messages*/
    public List<ServerMessage> getMessages() {
        return serializedMessages.stream().map(MessageFactory::getMessageFromServer).toList();
    }
}
