package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.messages.ModelToViewTranslate;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedIsland;

import java.util.List;

/**The message which contains simplified islands for the view.*/
public class IslandsMessage extends ServerMessage{

    private List<SimplifiedIsland> islandList;

    /**It builds the message starting from the json string.
     *@param json the string message */
    public IslandsMessage(String json) {
        super(json);
    }

    /**It builds the message starting from the model.
     * @param game the model game*/
    public IslandsMessage(Game game) {
        super(game);
        this.islandList= ModelToViewTranslate.translateIsland(game.getIslands());
        super.serialize();
    }

    /**@return the list of simplified islands on the view*/
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
