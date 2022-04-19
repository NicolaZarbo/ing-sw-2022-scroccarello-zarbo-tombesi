package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.character.TokensCharacter;
import it.polimi.ingsw.model.token.Student;

import java.util.ArrayList;

public class CharacterTokenMessage extends CharacterUpdateMessage {
    private ArrayList<Student> students;
    public CharacterTokenMessage(int cardId, Game game) {
        super( game);
        CharacterCard card;
        card=game.getCharacter(cardId);
        String supType=card.getClass().getSuperclass().getSimpleName();
        if(!"TokensCharacter".equals(supType))
            throw new MessageErrorException("provided id corresponds to a "+card.getClass().getSuperclass().getSimpleName()+ " object, not tokensCharacter");
        super.cardID=card.getId();
        super.cardCost=card.getCost();
        students= ((TokensCharacter) card).getStudents();
        super.serialize();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        this.cardCost=gson.fromJson(gg,CharacterTokenMessage.class).getCardCost();
        this.cardID=gson.fromJson(gg,CharacterTokenMessage.class).getCardID();
        this.students=gson.fromJson(gg,CharacterTokenMessage.class).getStudents();
    }}
