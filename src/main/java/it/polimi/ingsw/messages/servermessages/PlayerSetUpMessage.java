package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Setup;
import it.polimi.ingsw.view.CentralView;


import java.util.List;

/**The message which contains information about player customization.*/
public class PlayerSetUpMessage extends ServerMessage {
    private List<Integer> availableColor;
    private List<Integer> availableMages;
    private String turnOf;
    private int newId;
    private boolean teamPlay;

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson = new Gson();
        this.availableColor= gson.fromJson(gg,PlayerSetUpMessage.class).getAvailableColor();
        this.availableMages=gson.fromJson(gg,PlayerSetUpMessage.class).getAvailableMages();
        this.turnOf =gson.fromJson(gg,PlayerSetUpMessage.class).getTurnOf().toLowerCase();
        this.newId =gson.fromJson(gg,PlayerSetUpMessage.class).getNewId();
        teamPlay=gson.fromJson(gg,PlayerSetUpMessage.class).isTeamPlay();
    }

    @Override
    public void doAction(CentralView view) {
        view.personalizePlayer(this);
    }

    /**@return the nickname of the player in charge to play*/
    public String getTurnOf() {
        return turnOf;
    }
    /** @return the newly assigned id of the player who is choosing his customs right now*/
    public int getNewId() {
        return newId;
    }

    /**@return if the game is team game or not */
    public boolean isTeamPlay() {
        return teamPlay;
    }

    /**@return the list of tower color which have not been chosen yet*/
    public List<Integer> getAvailableColor() {
        return availableColor;
    }

    /**@return the list of magicians which have not been chosen yet*/
    public List<Integer> getAvailableMages() {
        return availableMages;
    }

    /**It builds the message starting from the json string.
     * @param json the string message*/
    public PlayerSetUpMessage(String json) {
        super(json);
    }

    /**It builds the message starting from the model.
     * @param game the model game
     * @param newId the newly assigned id*/
    public PlayerSetUpMessage(Game game,int newId) {
        super(game);
        teamPlay=game.getNPlayers()==4;
        this.newId=newId;
        Setup set = game.getSetupPhase();
        this.availableColor = set.getAvailableColor().stream().map(Enum::ordinal).toList();
        this.availableMages = set.getAvailableMages().stream().map(Enum::ordinal).toList();
        this.turnOf =set.getPreGameTurnOf();
        super.serialize();
    }
}
