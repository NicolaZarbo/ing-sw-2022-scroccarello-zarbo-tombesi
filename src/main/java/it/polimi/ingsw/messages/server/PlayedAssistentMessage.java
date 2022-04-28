package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

public class PlayedAssistentMessage extends ServerMessage{
    int playedCardId;
    int playerId;

    public int getPlayedCardId() {
        return playedCardId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public PlayedAssistentMessage(Game game) {
        super(game);
        this.playerId= game.getCurrentPlayerId();
        this.playedCardId=game.getPlayedCard(playerId).getId();
        super.serialize();
    }

    public PlayedAssistentMessage(String json) {
        super(json);
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.playedCardId=gson.fromJson(gg,PlayedAssistentMessage.class).getPlayedCardId();
        this.playerId=gson.fromJson(gg,PlayedAssistentMessage.class).getPlayerId();
    }

    @Override
    public void doAction(CentralView view) {
        view.playedAssistentUpdate(this);
    }
}
