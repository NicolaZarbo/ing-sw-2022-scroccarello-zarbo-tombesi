package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.messages.ModelToViewTranslate;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedBoard;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SimpleTimeZone;

public class AllBoardsMessage extends ServerMessage{
    private ArrayList<SimplifiedBoard> boards;
    private ArrayList<Integer> boardCoin;
    public AllBoardsMessage(String json) {
        super(json);

    }

    public AllBoardsMessage(Game game) {
        super(game);
        boards=new ArrayList<>();
        boardCoin=new ArrayList<>();
        for (Player player: game.getPlayers()) {
            boards.add(ModelToViewTranslate.translateBoard(player.getBoard()));
            boardCoin.add(player.getHand().getCoin());
        }
        serialize();
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        AllBoardsMessage mex= gson.fromJson(gg,AllBoardsMessage.class);
        boards=mex.boards;
        boardCoin=mex.boardCoin;
    }

    @Override
    public void doAction(CentralView view) {
        view.everyBoardUpdate(this);
    }

    public List<SimplifiedBoard> getBoards() {
        return boards;
    }

    public List<Integer> getBoardCoin() {
        return boardCoin;
    }
}
