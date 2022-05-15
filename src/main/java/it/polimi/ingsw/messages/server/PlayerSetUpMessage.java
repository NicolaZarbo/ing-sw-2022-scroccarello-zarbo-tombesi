package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Setup;
import it.polimi.ingsw.view.CentralView;


import java.util.List;

public class PlayerSetUpMessage extends ServerMessage {
    private List<Integer> availableColor;
    private List<Integer> availableMages;
    private String turnOf;
    private int newId;
    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.availableColor= gson.fromJson(gg,PlayerSetUpMessage.class).getAvailableColor();
        this.availableMages=gson.fromJson(gg,PlayerSetUpMessage.class).getAvailableMages();
        this.turnOf =gson.fromJson(gg,PlayerSetUpMessage.class).getTurnOf().toLowerCase();
        this.newId =gson.fromJson(gg,PlayerSetUpMessage.class).getNewId();
    }

    @Override
    public void doAction(CentralView view) {
        view.personalizePlayer(this);
    }

    public String getTurnOf() {
        return turnOf;
    }
    /** the newly assigned id of the player who is choosing is customs right now*/
    public int getNewId() {
        return newId;
    }

    public List<Integer> getAvailableColor() {
        return availableColor;
    }

    public List<Integer> getAvailableMages() {
        return availableMages;
    }

    public PlayerSetUpMessage(String json) {
        super(json);
    }

    public PlayerSetUpMessage(Game game,int newId) {
        super(game);
        this.newId=newId;
        Setup set = game.getSetupPhase();
        this.availableColor = set.getAvailableColor().stream().map(Enum::ordinal).toList();
        this.availableMages = set.getAvailableMages().stream().map(Enum::ordinal).toList();
        this.turnOf =set.getPreGameTurnOf();
        super.serialize();
    }
}
