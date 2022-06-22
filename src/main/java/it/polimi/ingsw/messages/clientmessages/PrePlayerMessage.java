package it.polimi.ingsw.messages.clientmessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.LobbyPlayer;
import it.polimi.ingsw.enumerations.Mage;
import it.polimi.ingsw.enumerations.TowerColor;

/**The message containing custom parameters of the player from lobby.*/
public class PrePlayerMessage extends ClientMessage {
    private int towerColor, mage;
    private String name;

    /**It builds the message from the json string.
     * @param json the string message*/
    public PrePlayerMessage(String json) {
        super(json);
    }

    @Override
    public void doAction(Controller controller) {
        controller.getControllerSetup().createPlayer(this);
    }

    /**It builds the message starting from custom information of the player.
     * @param playerId the id of the player
     * @param towerColor the team color
     * @param mage the selected mage
     * @param name the unique username*/
    public PrePlayerMessage(int playerId, int towerColor,  int mage, String name) {
        super(playerId);
        this.mage=mage;
        this.towerColor=towerColor;
        this.name=name;
        super.serialize();
    }

    /**@return the lobby player*/
    public LobbyPlayer getPrePlayer(){return new LobbyPlayer(TowerColor.getColor(towerColor), Mage.getMage(mage),name);}

    /**@return the player's nickname*/
    public String getName(){
        return name;
    }

    /**@return the id of the tower color of the player(team)*/
    public int getTowerColor() {
        return towerColor;
    }
    /**@return the value of the chosen magician*/
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
