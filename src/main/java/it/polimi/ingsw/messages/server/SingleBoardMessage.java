package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;


public class SingleBoardMessage extends ServerMessage {
    private Board board;
    private int boardPlayerId;
    public SingleBoardMessage(String json) {
        super(json);
    }

    public SingleBoardMessage(Game game,int boardPlayerId) {
        super(game);
        this.boardPlayerId = boardPlayerId;
        board=game.getPlayer(boardPlayerId).getBoard();
        serialize();
    }
    public void parseMessage(JsonObject gg){
        Gson gson= new Gson();
        this.board= gson.fromJson(gg,this.getClass()).getBoard();
        this.boardPlayerId =gson.fromJson(gg,this.getClass()).getBoardPlayerId();
    }

    @Override
    public void doAction(CentralView view) {
        view.singleBoardUpdate(this);
    }

    public int getBoardPlayerId() {
        return boardPlayerId;
    }

    public Board getBoard() {
        return board;
    }
}
