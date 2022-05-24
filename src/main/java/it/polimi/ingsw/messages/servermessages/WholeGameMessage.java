package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.messages.ModelToViewTranslate;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.character.TokensCharacter;
import it.polimi.ingsw.model.token.Token;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.objects.SimplifiedIsland;
import it.polimi.ingsw.view.objects.SimplifiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WholeGameMessage extends ServerMessage{
    private List<SimplifiedPlayer> players;
    private boolean easy;
    private List<Integer[]> clouds;
    private int motherNature;
    private List<SimplifiedIsland> islands;
    private List<Integer> characters;
    private HashMap<Integer,List<Integer>> cardStudents;
    private HashMap<Integer,Integer> cardCosts;

    public WholeGameMessage(String json) {
        super(json);
    }

    public WholeGameMessage(Game game) {
        super(game);
        this.easy=game.isEasy();
        this.players= ModelToViewTranslate.translatePlayer(game.getPlayers());
        this.islands=ModelToViewTranslate.translateIsland(game.getIslands());
        this.motherNature=game.getMotherNature().getPosition();
        this.clouds= ModelToViewTranslate.translateClouds(game.getClouds());
        if(!game.isEasy()){
            this.characters=game.getCharacters().stream().map(CharacterCard::getId).toList();
            cardStudents= new HashMap<>();
            cardCosts= new HashMap<>();
            for (CharacterCard card:game.getCharacters()) {
                cardCosts.put(card.getId(),card.getCost());
                if(card instanceof TokensCharacter)
                    cardStudents.put(card.getId(),((TokensCharacter) card).getStudents().stream().map(Token::getId).toList());
            }
        }
        else this.characters=new ArrayList<>();
        super.serialize();
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        WholeGameMessage mex= gson.fromJson(gg,WholeGameMessage.class);
        this.easy= mex.easy;
        this.players=mex.players;
        this.clouds= mex.clouds;
        this.islands=mex.islands;
        this.motherNature=mex.motherNature;
        this.characters=mex.characters;
        this.cardStudents=mex.cardStudents;
        this.cardCosts=mex.cardCosts;
    }

    @Override
    public void doAction(CentralView view) {
        view.setView(this);
    }

    public boolean isEasy() {
        return easy;
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

    public List<Integer> getCharacters() {
        return characters;
    }

    public HashMap<Integer, Integer> getCardCosts() {
        return cardCosts;
    }

    public HashMap<Integer, List<Integer>> getCardStudents() {
        return cardStudents;
    }
}
