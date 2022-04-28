package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.SimplifiedIsland;

import java.util.List;

public class IslandsMessage extends ServerMessage{

    private List<SimplifiedIsland> islandList;

    public IslandsMessage(String json) {
        super(json);
    }

    public IslandsMessage(Game game) {
        super(game);
        this.islandList=ModelToViewTranslate.translateIsland(game.getIslandList());
        super.serialize();
    }

    public List<SimplifiedIsland> getIslandList() {
        return islandList;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.islandList=gson.fromJson(gg,this.getClass()).getIslandList();
    }

    @Override
    public void doAction(CentralView view) {
        view.islandsUpdate(this);
    }
}
