package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class LobbySetupMessage extends GenericMessage{

    private int lobbyDimension;
    private boolean easy;

    public LobbySetupMessage(int lobbyDimension, boolean easy) {
        this.lobbyDimension = lobbyDimension;
        this.easy = easy;
        super.serialize();
    }

    public LobbySetupMessage(String json) {
        super(json);
    }

    public int getLobbyDimension() {
        return lobbyDimension;
    }

    public boolean isEasy() {
        return easy;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson=new Gson();
        this.lobbyDimension = gson.fromJson(gg,LobbySetupMessage.class).getLobbyDimension();
        this.easy = gson.fromJson(gg,LobbySetupMessage.class).isEasy();
    }
}
