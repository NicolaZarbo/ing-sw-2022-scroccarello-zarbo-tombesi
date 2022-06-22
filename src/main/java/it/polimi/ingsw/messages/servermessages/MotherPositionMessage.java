package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

/**The message which contains the id of the island on which mother nature currently is.*/
public class MotherPositionMessage extends ServerMessage {
    private int motherPosition;
   /**@return the id of the island mother nature is on*/
    public int getMotherPosition() {
        return motherPosition;
    }

    /**It builds the message starting from the model.
     * @param game the model game*/
    public MotherPositionMessage(Game game) {
        super(game);
        this.motherPosition=game.getMotherNature().getPosition();
        super.serialize();
    }

    /**It builds the message starting from the json string.
     * @param json the string message*/
    public MotherPositionMessage(String json) {
        super(json);
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        motherPosition= gson.fromJson(gg,this.getClass()).getMotherPosition();
    }

    @Override
    public void doAction(CentralView view) {
        view.motherPositionUpdate(this);
    }

}
