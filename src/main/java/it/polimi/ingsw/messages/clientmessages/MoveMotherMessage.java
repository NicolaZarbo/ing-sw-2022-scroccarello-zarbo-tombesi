package it.polimi.ingsw.messages.clientmessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.Controller;

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

    @Override
    public void doAction(Controller controller) {
        controller.getControllerTurn().moveMotherNature(this);
    }

    public MoveMotherMessage(int steps, int playerId) {
        super(playerId);
        this.steps = steps;
        super.serialize();
    }

    public int getSteps() {
        return steps;
    }
}
