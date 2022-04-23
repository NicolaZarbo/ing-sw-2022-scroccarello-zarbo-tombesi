package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.token.TokenColor;
import it.polimi.ingsw.view.CentralView;

import java.util.List;

public class WholeGameMessage extends ServerMessage{
    private Player[] modelPlayers;
    private Cloud[] clouds;
    private MotherNature motherNature;
    private List<Island> islands;
    private List<CharacterCard> characters;

    public WholeGameMessage(String json) {
        super(json);
    }

    public WholeGameMessage(Game game) {
        super(game);
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        WholeGameMessage mex= gson.fromJson(gg,WholeGameMessage.class);
        this.modelPlayers=mex.modelPlayers;
        this.clouds= mex.clouds;
        this.islands=mex.islands;
        this.motherNature=mex.motherNature;
        this.characters=mex.characters;
    }

    @Override
    public void doAction(CentralView view) {
        view.setView(this);
    }

    public Player[] getModelPlayers() {
        return modelPlayers;
    }

    public Cloud[] getClouds() {
        return clouds;
    }

    public MotherNature getMotherNature() {
        return motherNature;
    }

    public List<Island> getIslands() {
        return islands;
    }

    public List<CharacterCard> getCharacters() {
        return characters;
    }
}
