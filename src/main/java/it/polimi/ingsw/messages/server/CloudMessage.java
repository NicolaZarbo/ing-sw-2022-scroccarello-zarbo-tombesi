package it.polimi.ingsw.messages.server;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import javax.swing.text.View;
import java.awt.*;


public class CloudMessage extends ServerMessage {//
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
