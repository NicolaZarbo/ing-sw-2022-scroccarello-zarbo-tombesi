package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

/**The message to change the game state to its following.*/
public class ChangePhaseMessage extends ServerMessage{
    private GameState state;

    /**It builds the message starting from a json string.
     * @param json the string message*/
    public ChangePhaseMessage(String json) {
        super(json);
    }

    /**It builds the message starting from the model.
     * @param game the model game*/
    public ChangePhaseMessage(Game game) {
        super(game);
        this.state=game.getActualState();
        serialize();
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        this.state= gson.fromJson(gg,ChangePhaseMessage.class).getState();

    }

    @Override
    public void doAction(CentralView view) {
        view.changePhase(this);
    }

    /**@return the actual state of the game*/
    public GameState getState() {
        return state;
    }
}
