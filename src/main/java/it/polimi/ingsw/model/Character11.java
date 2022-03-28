package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Character11 extends TokensCharacter{


    public Character11(int id, ArrayList<Student> studs) {
        super(id, studs);
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
