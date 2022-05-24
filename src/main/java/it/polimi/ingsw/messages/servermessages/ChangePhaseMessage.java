package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

public class ChangePhaseMessage extends ServerMessage{
    private GameState state;
    public ChangePhaseMessage(String json) {
        super(json);
    }

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

    public GameState getState() {
        return state;
    }
}
