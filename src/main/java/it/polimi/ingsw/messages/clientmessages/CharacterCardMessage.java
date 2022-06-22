package it.polimi.ingsw.messages.clientmessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.util.ParameterObject;

/**The message to play a character card.*/
public class CharacterCardMessage extends ClientMessage{
    ParameterObject parameters;
    int cardId;

    /**It builds the message starting from the json message.
     * @param json the string message*/
    public CharacterCardMessage(String json) {
        super(json);
    }

    @Override
    public void doAction(Controller controller) {
        controller.getControllerTurn().playCharacter(this);
    }

    /**It builds the message starting from the player's id, card to play and its parameters.
     * @param playerId the player's id
     * @param parameters the parameters to activate the card
     * @see ParameterObject
     * @param cardId the id of the card to activate*/
    public CharacterCardMessage(int playerId, ParameterObject parameters,int cardId) {
        super(playerId);
        this.parameters=parameters;
        this.cardId= cardId;
        super.serialize();
    }
    /**@return the parameters to activate the card*/
    public ParameterObject getParameters() {
        return parameters;
    }

    /**@return the id of the card to activate*/
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
