package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.characters.TokensCharacter;
import it.polimi.ingsw.model.tokens.Token;

import java.util.List;

public class CharacterTokenMessage extends CharacterUpdateMessage {
    private List<Integer> students;
    public CharacterTokenMessage(int cardId, Game game) {
        super( game);
        CharacterCard card;
        card=game.getCharacter(cardId);
        String supType=card.getClass().getSuperclass().getSimpleName();
        if(!"TokensCharacter".equals(supType))
            throw new MessageErrorException("provided id corresponds to a "+card.getClass().getSuperclass().getSimpleName()+ " object, not tokensCharacter");
        super.cardID=card.getId();
        super.cardCost=card.getCost();
        students= ((TokensCharacter) card).getStudents().stream().map(Token::getId).toList();
        super.serialize();
    }

    public CharacterTokenMessage(String json) {
        super(json);
    }

    public List<Integer> getStudents() {
        return students;
    }
    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        this.cardCost=gson.fromJson(gg,CharacterTokenMessage.class).getCardCost();
        this.cardID=gson.fromJson(gg,CharacterTokenMessage.class).getCardID();
        this.students=gson.fromJson(gg,CharacterTokenMessage.class).getStudents();
    }}
