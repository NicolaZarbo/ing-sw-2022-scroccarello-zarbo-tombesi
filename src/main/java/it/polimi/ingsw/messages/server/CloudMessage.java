package it.polimi.ingsw.messages.server;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

import java.util.List;


public class CloudMessage extends ServerMessage {//
    private List<Integer[]> clouds;

    public CloudMessage(Game game){
        super(game);
        this.clouds= ModelToViewTranslate.translateClouds(game.getClouds());
        serialize();
    }
    public List<Integer[]> getClouds(){
        return this.clouds;
    }
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
    /*public static CloudMessage parse(String json){
        Gson gson= new Gson();
        return gson.fromJson(json,CloudMessage.class);
    }
    another potential way to parse a message
     */



}
