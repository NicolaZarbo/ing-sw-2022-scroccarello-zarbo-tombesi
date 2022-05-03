package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

public class ChangeTurnMessage extends ServerMessage {
    private int player;

    public ChangeTurnMessage(Game game){
        super(game);
        this.player=game.getCurrentPlayerId();
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        this.player=gson.fromJson(gg,ChangeTurnMessage.class).getPlayer();
    }

    public int getPlayer() {
        return player;
    }

    @Override
    public void doAction(CentralView view) {
        //TODO
    }
}
