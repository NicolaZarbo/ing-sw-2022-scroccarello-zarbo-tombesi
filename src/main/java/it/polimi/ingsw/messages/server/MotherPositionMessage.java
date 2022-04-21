package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;

public class MotherPositionMessage extends ServerMessage {
    private int motherPosition;
    public int getMotherPosition() {
        return motherPosition;
    }

    public MotherPositionMessage(Game game) {
        super(game);
        this.motherPosition=game.getMotherNature().getPosition();
        super.serialize();
    }

    public MotherPositionMessage(String json) {
        super(json);
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        motherPosition= gson.fromJson(gg,this.getClass()).getMotherPosition();
    }

}
