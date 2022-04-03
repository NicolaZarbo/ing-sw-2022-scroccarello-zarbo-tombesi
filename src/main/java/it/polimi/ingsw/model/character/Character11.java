package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;

import java.util.List;

public class Character11 extends TokensCharacter{


    public Character11(int id) {
        super(id);
    }

    @Override/** @param parameters : 1 idplayer, 2 idStudent to get*/
    public void cardEffect(List<Integer> parameters, Game game) {
        if(parameters.size()==2) {
            Board board = game.getPlayer(parameters.get(0)).getBoard();
            board.moveToDiningRoom(getStudent(parameters.get(1)));
            incrementCost();
        }
    }
}
