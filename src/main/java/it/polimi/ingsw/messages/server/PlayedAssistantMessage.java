package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

public class PlayedAssistantMessage extends ServerMessage{
    int playedCardId;
    int playerId;

    public int getPlayedCardId() {
        return playedCardId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public PlayedAssistantMessage(Game game) {
        super(game);
        this.playerId= game.getCurrentPlayerId();
        this.playedCardId=game.getPlayedCard(playerId).getId();
        super.serialize();
    }

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
