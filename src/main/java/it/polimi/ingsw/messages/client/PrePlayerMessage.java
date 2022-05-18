package it.polimi.ingsw.messages.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.LobbyPlayer;
import it.polimi.ingsw.enumerations.Mage;
import it.polimi.ingsw.enumerations.TowerColor;

public class PrePlayerMessage extends ClientMessage {
    private int towerColor, mage;
    private String name;

    public PrePlayerMessage(String json) {
        super(json);
    }

    @Override
    public void doAction(Controller controller) {
        controller.getControllerSetup().createPlayer(this);
    }
    //are id already available??
    public PrePlayerMessage(int playerId, int towerColor,  int mage, String name) {
        super(playerId);
        this.mage=mage;
        this.towerColor=towerColor;
        this.name=name;
        super.serialize();
    }
    public LobbyPlayer getPrePlayer(){return new LobbyPlayer(TowerColor.getColor(towerColor), Mage.getMage(mage),name);}
    public String getName(){
        return name;
    }
    public int getTowerColor() {
        return towerColor;
    }

    public int getMage() {
        return mage;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        towerColor=gson.fromJson(gg, PrePlayerMessage.class).getTowerColor();
        mage=gson.fromJson(gg, PrePlayerMessage.class).getMage();
        name=gson.fromJson(gg, PrePlayerMessage.class).getName();
    }
}
