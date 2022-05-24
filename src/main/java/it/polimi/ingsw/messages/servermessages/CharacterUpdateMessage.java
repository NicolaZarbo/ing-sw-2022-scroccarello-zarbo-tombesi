package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.view.CentralView;

public class CharacterUpdateMessage extends ServerMessage{
    int cardCost;
    int cardID;
    /** default constructor, only checks if game is valid */
    public CharacterUpdateMessage(Game game){
        super(game);
    }
    public CharacterUpdateMessage(int cardId, Game game){
        super(game);
        CharacterCard card;
        card=game.getCharacter(cardId);
        this.cardID=card.getId();
        this.cardCost=card.getCost();
        super.serialize();
    }

    public CharacterUpdateMessage(String json) {
        super(json);
    }

    public int getCardCost() {
        return cardCost;
    }

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
