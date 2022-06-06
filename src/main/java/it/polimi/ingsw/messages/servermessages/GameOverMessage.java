package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.CentralView;

public class GameOverMessage extends ServerMessage{
    private int winnerID;
    private int winnerTeam;
    private int winningCondition;
    //todo maybe we could add the reason that triggered the end of game
    public GameOverMessage(String json) {
        super(json);
    }

    public GameOverMessage(Game game,int winner) {
        super(game);
        winningConditions(game);
        if(game.getNPlayers()==4){
            winnerTeam=winner;
            winnerID=-1;
        }else {
            winnerTeam=-1;
            winnerID=winner;
        }
        serialize();
    }
    /** Checks used:
     * 1 bag is empty
     * 2 someone used every tower in their board
     * 3 someone used his last card
     * 4 there are only 3 cluster of islands left*/
    private void winningConditions(Game game){
        if(game.getIslands().size()==3)
            winningCondition=4;
        if(game.getPlayer(0).getHand().getAssistant().size()==0)
            winningCondition=3;
        int nCheck;
        if(game.getNPlayers()==4)
            nCheck=2;
        else nCheck=game.getNPlayers();
        for (int i=0; i<nCheck;i++) {
            if(game.getPlayer(i).getBoard().towersLeft()==0)
                winningCondition=2;
        }
        if(game.getBag().isEmpty())
            winningCondition=1;
    }

    public int getWinningCondition() {
        return winningCondition;
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
        this.winningCondition=mex.winningCondition;
        this.winnerID=mex.winnerID;
        this.winnerTeam=mex.winnerTeam;
    }

    @Override
    public void doAction(CentralView view) {
        view.gameOver(this);
    }
}
