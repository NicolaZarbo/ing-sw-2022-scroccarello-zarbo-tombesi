package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.messages.ModelToViewTranslate;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedBoard;

/**The message which contains the simplified board of the player.*/
public class SingleBoardMessage extends ServerMessage {
    private SimplifiedBoard board;
    private int boardPlayerId;
    private int nCoin;
    /**It builds the message from the json string.
     * @param json the string message*/
    public SingleBoardMessage(String json) {
        super(json);
    }

    /**It builds the message starting from the model.
     * @param game the model game
     * @param boardPlayerId the id of the player*/
    public SingleBoardMessage(Game game,int boardPlayerId) {
        super(game);
        this.boardPlayerId = boardPlayerId;
        this.board= ModelToViewTranslate.translateBoard(game.getPlayer(boardPlayerId).getBoard());
        this.nCoin=game.getPlayer(boardPlayerId).getHand().getCoin();
        serialize();
    }

    @Override
    public void parseMessage(JsonObject gg){
        Gson gson= new Gson();
        this.board= gson.fromJson(gg,this.getClass()).getBoard();
        this.boardPlayerId =gson.fromJson(gg,this.getClass()).getBoardPlayerId();
        this.nCoin=gson.fromJson(gg,this.getClass()).getCoin();
    }

    @Override
    public void doAction(CentralView view) {
        view.singleBoardUpdate(this);
    }

    /**@return the number of coins of the player*/
    public int getCoin() {
        return nCoin;
    }

    /**@return the id of the player*/
    public int getBoardPlayerId() {
        return boardPlayerId;
    }

    /**@return the simplified board*/
    public SimplifiedBoard getBoard() {
        return board;
    }
}
