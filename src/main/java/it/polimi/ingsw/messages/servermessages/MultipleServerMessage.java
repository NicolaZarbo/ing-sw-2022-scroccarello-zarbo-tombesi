package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.view.CentralView;

import java.util.ArrayList;
import java.util.List;

public class MultipleServerMessage extends ServerMessage{
    //private ArrayList<ServerMessage> messages;
    private ArrayList<String> serializedMessages;
    private boolean serialized;
    public MultipleServerMessage(String json) {
        super(json);
    }

    public MultipleServerMessage(ServerMessage firstMessage) {
        super(firstMessage);
        this.serialized=false;
        serializedMessages=new ArrayList<>();
        serializedMessages.add(firstMessage.getJson());
        this.json="not serialized";
    }
    /** after adding all the messages remember to call this object's serialize() method*/
    public void addMessage(ServerMessage anotherMessage){
        if(serialized) {
            throw new MessageErrorException("message already serialized");
        }
       // messages.add(anotherMessage);
        serializedMessages.add(anotherMessage.getJson());
    }
    public void serialize(){
        super.serialize();
        this.serialized=true;
    }

    public boolean isSerialized() {
        return serialized;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.serializedMessages=gson.fromJson(gg, MultipleServerMessage.class).getSerializedMessages();
        /*messages=new ArrayList<>();
        for (String son :serializedMessages) {
            messages.add(MessageFactory.getMessageFromServer(son));
        }

         */
    }

    @Override
    public void doAction(CentralView view) {
        for (String message :this.serializedMessages) {
            MessageFactory.getMessageFromServer(message).doAction(view);
        }
    }

    public ArrayList<String> getSerializedMessages() {
        return serializedMessages;
    }

    public List<ServerMessage> getMessages() {
        return serializedMessages.stream().map(MessageFactory::getMessageFromServer).toList();
    }
}
