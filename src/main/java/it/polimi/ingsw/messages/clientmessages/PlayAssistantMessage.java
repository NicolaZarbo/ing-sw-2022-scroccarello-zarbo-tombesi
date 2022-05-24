package it.polimi.ingsw.messages.clientmessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.Controller;

public class PlayAssistantMessage extends ClientMessage{
    private int playedCard;
    public PlayAssistantMessage(String json) {
        super(json);
    }

    @Override
    public void doAction(Controller controller) {
        controller.getControllerRound().playAssistantCard(this);
    }

    public PlayAssistantMessage(int playerId, int playedCard) {
        super(playerId);
        this.playedCard=playedCard;
        super.serialize();
    }

    public int getPlayedCard() {
        return playedCard;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.playedCard=gson.fromJson(gg,this.getClass()).getPlayedCard();
    }
}
