package it.polimi.ingsw.messages;
import com.google.gson.*;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;

import java.util.Arrays;

public class CloudMessage extends ServerMessage {
    private String json;
    private final Cloud[] clouds;

    public static CloudMessage serialize(Game game) {
        Gson gson=new Gson();
        Cloud[] clouds= game.getClouds();
        String json = gson.toJson(game.getClouds(), clouds.getClass());
        return  new CloudMessage(json,clouds);

    }
    public CloudMessage(String json, Cloud[] clouds){
        this.clouds=clouds;
        this.json=json;
    }
    public String getJson(){
        return json;
    }
    public CloudMessage(Cloud[] clouds){
        this.clouds=clouds;
    }
    public Cloud[] getClouds(){
        return this.clouds;
    }

    public static CloudMessage parseMessage(String json) {
        //deserialize
        Gson gson= new Gson();

        Cloud[] cloud = new Cloud[0];
        cloud= gson.fromJson(json, cloud.getClass());
        return new CloudMessage(cloud);
    }
}
