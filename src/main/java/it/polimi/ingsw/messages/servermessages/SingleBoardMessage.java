package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.messages.ModelToViewTranslate;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedBoard;


public class SingleBoardMessage extends ServerMessage {
    private SimplifiedBoard board;
    private int boardPlayerId;
    private int nCoin;
    public SingleBoardMessage(String json) {
        super(json);
    }

    public SingleBoardMessage(Game game,int boardPlayerId) {
        super(game);
        this.boardPlayerId = boardPlayerId;
        this.board= ModelToViewTranslate.translateBoard(game.getPlayer(boardPlayerId).getBoard());
        this.nCoin=game.getPlayer(boardPlayerId).getHand().getCoin();
        serialize();
    }
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

    public int getCoin() {
        return nCoin;
    }

    public int getBoardPlayerId() {
        return boardPlayerId;
    }

    public SimplifiedBoard getBoard() {
        return board;
    }
}
