package it.polimi.ingsw.messages;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;


public class CloudMessage extends FromServerMessage {//
    private  Cloud[] clouds;

    public CloudMessage(Game game){
        super(game);
        this.clouds= game.getClouds();
        serialize();
    }
    public Cloud[] getClouds(){
        return this.clouds;
    }
    public CloudMessage(String json){
        super(json);
    }
    @Override
    protected void parseMessage(JsonObject gg){
        super.parseMessage(gg);
        Gson gson=new Gson();
        this.clouds= gson.fromJson(gg.get("MessageContent"),this.getClass()).getClouds();
    }

}
