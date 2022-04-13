package it.polimi.ingsw.messages.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MoveMotherMessage extends ClientMessage {
    private  int steps;
    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.steps=gson.fromJson(gg,this.getClass()).getSteps();
    }

    public MoveMotherMessage(String json) {
        super(json);
    }

    public MoveMotherMessage(int steps) {
        this.steps = steps;
        super.serialize();
    }

    public int getSteps() {
        return steps;
    }
}
