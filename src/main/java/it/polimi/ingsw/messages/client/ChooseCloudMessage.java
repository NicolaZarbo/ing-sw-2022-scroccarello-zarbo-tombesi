package it.polimi.ingsw.messages.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.Controller;

public class ChooseCloudMessage extends ClientMessage{
    private int cloudId;

    public int getCloudId() {
        return cloudId;
    }

    public ChooseCloudMessage(String json) {
        super(json);
    }

    @Override
    public void doAction(Controller controller) {
        controller.getControllerTurn().chooseCloud(this);
    }

    public ChooseCloudMessage(int cloudId, int playerId) {
        super(playerId);
        this.cloudId = cloudId;
        super.serialize();
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.cloudId=gson.fromJson(gg,this.getClass()).getCloudId();
    }
}
