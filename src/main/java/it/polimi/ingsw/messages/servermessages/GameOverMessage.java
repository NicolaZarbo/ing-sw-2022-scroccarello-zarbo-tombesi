package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

public class GameOverMessage extends ServerMessage{
    private int winnerID;
    private int winnerTeam;
    //todo maybe we could add the reason that triggered the end of game
    public GameOverMessage(String json) {
        super(json);
    }

    public GameOverMessage(Game game,int winner) {
        super(game);
        if(game.getNPlayers()==4){
            winnerTeam=winner;
            winnerID=-1;
        }else {
            winnerTeam=-1;
            winnerID=winner;
        }
        serialize();
    }

    public int getWinnerID() {
        return winnerID;
    }

    public int getWinnerTeam() {
        return winnerTeam;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        GameOverMessage mex= gson.fromJson(gg,GameOverMessage.class);
        this.winnerID=mex.winnerID;
        this.winnerTeam=mex.winnerTeam;
    }

    @Override
    public void doAction(CentralView view) {
        view.gameOver(this);
    }
}
