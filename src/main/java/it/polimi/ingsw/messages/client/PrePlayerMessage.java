package it.polimi.ingsw.messages.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.model.LobbyPlayer;

public class PrePlayerMessage extends ClientMessage {
    private LobbyPlayer prePlayer;

    public PrePlayerMessage(String json) {
        super(json);
    }

    @Override
    public void doAction(Controller controller) {
        controller.getControllerSetup().createPlayer(this);
    }
    //are id already available??
    public PrePlayerMessage(int playerId, LobbyPlayer player) {
        super(playerId);
        this.prePlayer=player;
        super.serialize();
    }

    public LobbyPlayer getPrePlayer() {
        return prePlayer;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        gson.fromJson(gg, PrePlayerMessage.class).getPrePlayer();
    }
}
