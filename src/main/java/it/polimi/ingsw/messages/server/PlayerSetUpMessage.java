package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Mage;
import it.polimi.ingsw.model.Setup;
import it.polimi.ingsw.model.token.TowerColor;
import it.polimi.ingsw.view.CentralView;


import java.util.ArrayList;

public class PlayerSetUpMessage extends ServerMessage {
    private ArrayList<TowerColor> availableColor;
    private ArrayList<Mage> availableMages;
    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.availableColor= gson.fromJson(gg,PlayerSetUpMessage.class).getAvailableColor();
        this.availableMages=gson.fromJson(gg,PlayerSetUpMessage.class).getAvailableMages();
    }

    @Override
    public void doAction(CentralView view) {
        //
    }

    public ArrayList<TowerColor> getAvailableColor() {
        return availableColor;
    }

    public ArrayList<Mage> getAvailableMages() {
        return availableMages;
    }

    public PlayerSetUpMessage(String json) {
        super(json);
    }

    public PlayerSetUpMessage(Game game) {
        super(game);
        Setup set = game.getSetupPhase();
        this.availableColor = set.getAvailableColor();
        this.availableMages = set.getAvailableMages();
        super.serialize();
    }
}
