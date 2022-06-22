package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.messages.ModelToViewTranslate;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.characters.TokensCharacter;
import it.polimi.ingsw.model.tokens.Token;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedIsland;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**The message which contains the part of the model to save on the view.*/
public class WholeGameMessage extends ServerMessage{
    private List<SimplifiedPlayer> players;
    private boolean easy;
    private List<Integer[]> clouds;
    private int motherNature;
    private List<SimplifiedIsland> islands;
    private List<Integer> characters;
    private HashMap<Integer,List<Integer>> cardStudents;
    private HashMap<Integer,Integer> cardCosts;
    private int activeCharacter;

    /**It builds the message starting from the json string.
     * @param json the string message*/
    public WholeGameMessage(String json) {
        super(json);
    }

    /**It builds the message starting from the model.
     * @param game the model game*/
    public WholeGameMessage(Game game) {
        super(game);
        this.easy=game.isEasy();
        this.players= ModelToViewTranslate.translatePlayer(game.getPlayers());
        this.islands=ModelToViewTranslate.translateIsland(game.getIslands());
        this.motherNature=game.getMotherNature().getPosition();
        this.clouds= ModelToViewTranslate.translateClouds(game.getClouds());
        this.activeCharacter=game.getCardBonusActive();
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

    /**@return the id of the active character card*/
    public int getActiveCharacter() {
        return activeCharacter;
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
        this.activeCharacter= mex.activeCharacter;
    }

    @Override
    public void doAction(CentralView view) {
        view.setView(this);
    }

    /**@return •true: the game is in easy mode
     * <p>•false: the game is in expert mode</p>*/
    public boolean isEasy() {
        return easy;
    }

    /**@return the list of simplified players of the game*/
    public List<SimplifiedPlayer> getModelPlayers() {
        return players;
    }

    /**@return the list of simplified clouds of the game*/
    public List<Integer[]> getClouds() {
        return clouds;
    }

    /**@return the current position of mother nature*/
    public int getMotherNature() {
        return motherNature;
    }

    /**@return the list of simplified islands of the game*/
    public List<SimplifiedIsland> getIslands() {
        return islands;
    }

    /**@return the list of ids of the characters*/
    public List<Integer> getCharacters() {
        return characters;
    }

    /**@return the association between character and its cost*/
    public HashMap<Integer, Integer> getCardCosts() {
        return cardCosts;
    }

    /**@return the association between token character card and its tokens' ids */
    public HashMap<Integer, List<Integer>> getCardStudents() {
        return cardStudents;
    }
}
