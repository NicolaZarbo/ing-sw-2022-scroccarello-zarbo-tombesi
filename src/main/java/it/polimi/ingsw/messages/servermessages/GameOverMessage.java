package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.enumerations.WinCondition;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;

/**The message to notify the end of the game. It contains information about the winner(s).*/
public class GameOverMessage extends ServerMessage{
    private int winnerID;
    private int winnerTeam;
    private WinCondition winningCondition;

   /**It builds the message starting from a json string.
    * @param json the string message*/
    public GameOverMessage(String json) {
        super(json);
    }

    /**It builds the message starting from the model.
     * @param game the model game
     * @param winner the id of the winner*/
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

    /** It checks winning conditions:
     * <p>• bag is empty</p>
     * <p>• someone has placed every tower in his board</p>
     * <p>• someone has played his last card</p>
     * <p>• there are only 3 clusters of islands left</p>*/
    private void winningConditions(Game game){
        if(game.getIslands().size()==3)
            winningCondition=WinCondition.ThreeIslandsLeft;
        if(game.getPlayer(0).getHand().getAssistant().size()==0)
            winningCondition=WinCondition.TenTurnsPassed;
        int nCheck;
        if(game.getNPlayers()==4)
            nCheck=2;
        else nCheck=game.getNPlayers();
        for (int i=0; i<nCheck;i++) {
            if(game.getPlayer(i).getBoard().towersLeft()==0)
                winningCondition=WinCondition.NoTowersLeft;
        }
        if(game.getBag().isEmpty())
            winningCondition=WinCondition.BagEmpty;
    }

    /**@return the reason which triggered game over*/
    public WinCondition getWinningCondition() {
        return winningCondition;
    }

    /**@return the id of the winner*/
    public int getWinnerID() {
        return winnerID;
    }

    /**@return the winner team (team game only).*/
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
