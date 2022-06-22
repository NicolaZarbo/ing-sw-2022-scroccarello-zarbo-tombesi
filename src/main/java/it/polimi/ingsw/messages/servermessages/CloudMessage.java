package it.polimi.ingsw.messages.servermessages;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.messages.ModelToViewTranslate;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

import java.util.List;

/**The message to set the simplified clouds.*/
public class CloudMessage extends ServerMessage {//
    private List<Integer[]> clouds;

    /**It builds the message starting from the model.
     * @param game the model game*/
    public CloudMessage(Game game){
        super(game);
        this.clouds= ModelToViewTranslate.translateClouds(game.getClouds());
        serialize();
    }

    /**@return the simplified clouds*/
    public List<Integer[]> getClouds(){
        return this.clouds;
    }

    /**It builds the message starting from the json string
     * @param json the string message*/
    public CloudMessage(String json){
        super(json);
    }

    @Override
   protected void parseMessage(JsonObject gg){
        Gson gson=new Gson();
        this.clouds= gson.fromJson(gg,this.getClass()).getClouds();
    }

    @Override
    public void doAction(CentralView view) {
        view.cloudUpdate(this);
    }




}
