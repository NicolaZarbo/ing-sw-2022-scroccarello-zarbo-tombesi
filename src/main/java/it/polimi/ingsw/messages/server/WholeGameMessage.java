package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.SimplifiedIsland;
import it.polimi.ingsw.view.SimplifiedPlayer;

import java.util.List;

public class WholeGameMessage extends ServerMessage{
    private List<SimplifiedPlayer> players;
    private List<Integer[]> clouds;
    private int motherNature;
    private List<SimplifiedIsland> islands;
    private List<CharacterCard> characters;

    public WholeGameMessage(String json) {
        super(json);
    }

    public WholeGameMessage(Game game) {
        super(game);
        this.players=ModelToViewTranslate.translatePlayer(game.getPlayers());
        this.islands=ModelToViewTranslate.translateIsland(game.getIslands());
        this.motherNature=game.getMotherNature().getPosition();
        this.clouds= ModelToViewTranslate.translateClouds(game.getClouds());
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        WholeGameMessage mex= gson.fromJson(gg,WholeGameMessage.class);
        this.players=mex.players;
        this.clouds= mex.clouds;
        this.islands=mex.islands;
        this.motherNature=mex.motherNature;
        this.characters=mex.characters;
    }

    @Override
    public void doAction(CentralView view) {
        view.setView(this);
    }

    public List<SimplifiedPlayer> getModelPlayers() {
        return players;
    }

    public List<Integer[]> getClouds() {
        return clouds;
    }

    public int getMotherNature() {
        return motherNature;
    }

    public List<SimplifiedIsland> getIslands() {
        return islands;
    }

    public List<CharacterCard> getCharacters() {
        return characters;
    }
}
