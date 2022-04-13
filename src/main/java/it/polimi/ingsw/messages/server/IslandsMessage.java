package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import java.util.List;

public class IslandsMessage extends ServerMessage{

    private List<Island> islandList;

    public IslandsMessage(String json) {
        super(json);
    }

    public IslandsMessage(Game game) {
        super(game);
        this.islandList=game.getIslandList();
        super.serialize();
    }

    public List<Island> getIslandList() {
        return islandList;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.islandList=gson.fromJson(gg,this.getClass()).getIslandList();
    }
}
