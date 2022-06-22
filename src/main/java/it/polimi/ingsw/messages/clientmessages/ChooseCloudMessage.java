package it.polimi.ingsw.messages.clientmessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.Controller;

/**The message to select the cloud which refills the entrance with tokens.*/
public class ChooseCloudMessage extends ClientMessage{
    private int cloudId;

    /**@return the id of the chosen cloud*/
    public int getCloudId() {
        return cloudId;
    }

    /**It builds the message starting from json string.
     * @param json the string message*/
    public ChooseCloudMessage(String json) {
        super(json);
    }

    @Override
    public void doAction(Controller controller) {
        controller.getControllerTurn().chooseCloud(this);
    }

    /**It builds the message starting from the selected cloud and the player which sends it.
     * @param playerId the id of the sending player
     * @param cloudId the selected cloud's id*/
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
