package it.polimi.ingsw.messages.clientmessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.util.ParameterObject;

public class CharacterCardMessage extends ClientMessage{
    ParameterObject parameters;
    int cardId;
    public CharacterCardMessage(String json) {
        super(json);
    }

    @Override
    public void doAction(Controller controller) {
        controller.getControllerTurn().playCharacter(this);
    }

    public CharacterCardMessage(int playerId, ParameterObject parameters,int cardId) {
        super(playerId);
        this.parameters=parameters;
        this.cardId= cardId;
        super.serialize();
    }

    public ParameterObject getParameters() {
        return parameters;
    }

    public int getCardId() {
        return cardId;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.parameters= gson.fromJson(gg,CharacterCardMessage.class).getParameters();
        this.cardId= gson.fromJson(gg,CharacterCardMessage.class).getCardId();

    }
}
