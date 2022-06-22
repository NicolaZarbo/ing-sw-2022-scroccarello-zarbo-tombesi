package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

/**The message which contains the assistant played from the player, so it contains id of the card and id of the player.*/
public class PlayedAssistantMessage extends ServerMessage{
    int playedCardId;
    int playerId;

    /**@return the id of the played card*/
    public int getPlayedCardId() {
        return playedCardId;
    }

    /**@return the id of the player*/
    public int getPlayerId() {
        return playerId;
    }

    /**It builds the message starting from the model.
     * @param game the model game*/
    public PlayedAssistantMessage(Game game) {
        super(game);
        this.playerId= game.getCurrentPlayerId();
        this.playedCardId=game.getPlayedCard(playerId).getId();
        super.serialize();
    }

    /**It builds the message starting from the json string.
     * @param json the string message*/
    public PlayedAssistantMessage(String json) {
        super(json);
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.playedCardId=gson.fromJson(gg, PlayedAssistantMessage.class).getPlayedCardId();
        this.playerId=gson.fromJson(gg, PlayedAssistantMessage.class).getPlayerId();
    }

    @Override
    public void doAction(CentralView view) {
        view.playedAssistantUpdate(this);
    }
}
