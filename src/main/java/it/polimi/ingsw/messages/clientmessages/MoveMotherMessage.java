package it.polimi.ingsw.messages.clientmessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.Controller;

/**The message to move mother nature of certain amount of steps.*/
public class MoveMotherMessage extends ClientMessage {
    private  int steps;
    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.steps=gson.fromJson(gg,this.getClass()).getSteps();
    }
    /**It builds the message starting from the json message.
     * @param json the string message*/
    public MoveMotherMessage(String json) {
        super(json);
    }

    @Override
    public void doAction(Controller controller) {
        controller.getControllerTurn().moveMotherNature(this);
    }

    /**It builds the message starting from information sent by the player.
     * @param playerId the id of the player
     * @param steps the amount of steps to move mother*/
    public MoveMotherMessage(int steps, int playerId) {
        super(playerId);
        this.steps = steps;
        super.serialize();
    }

    /**@return the amount of theps to move mother*/
    public int getSteps() {
        return steps;
    }
}
