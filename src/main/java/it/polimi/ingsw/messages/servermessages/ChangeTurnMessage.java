package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

/**The message which swaps the action phase from the current player to the following one.*/
public class ChangeTurnMessage extends ServerMessage {
    private int player;

    /**It builds the message starting from the model.
     * @param game the model game*/
    public ChangeTurnMessage(Game game){
        super(game);
        this.player=game.getCurrentPlayerId();
        super.serialize();
    }

    /**It builds the message starting from a json string.
     * @param json the string message*/
    public ChangeTurnMessage(String json) {
        super(json);
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        this.player=gson.fromJson(gg,ChangeTurnMessage.class).getPlayer();
    }

    /**@return the currently playing player*/
    public int getPlayer() {
        return player;
    }

    @Override
    public void doAction(CentralView view) {
        view.changeTurn(this);
    }
}
