package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.view.CentralView;

/**The message which invokes character's update after invoking its effect.*/
public class CharacterUpdateMessage extends ServerMessage{
    int cardCost;
    int cardID;
    /**It builds the message starting from the model.
     * @param game the model game */
    public CharacterUpdateMessage(Game game){
        super(game);
    }

    /**It builds the message by filling it with the relative card id and cost.
     * @param game the model game
     * @param cardId the card id*/
    public CharacterUpdateMessage(int cardId, Game game){
        super(game);
        CharacterCard card;
        card=game.getCharacter(cardId);
        this.cardID=card.getId();
        this.cardCost=card.getCost();
        super.serialize();
    }

    /**It builds the message starting from a json string.
     * @param json the string message*/
    public CharacterUpdateMessage(String json) {
        super(json);
    }

    /**@return the cost of the card*/
    public int getCardCost() {
        return cardCost;
    }

    /**@return the id of the card*/
    public int getCardID() {
        return cardID;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
       this.cardCost=gson.fromJson(gg, CharacterUpdateMessage.class).getCardCost();
       this.cardID=gson.fromJson(gg, CharacterUpdateMessage.class).getCardID();
    }

    @Override
    public void doAction(CentralView view) {

    }
}
