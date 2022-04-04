package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;

import java.util.List;

public class Character10 extends CharacterCard{
    public Character10(int id) {
        super(id);
    }

    @Override /** @param parameters : first playerId, then students in dining room, then students in entrance*/
    public void cardEffect(List<Integer> parameters, Game game) {
        int size = parameters.size();
        int nstud = size/2;
        Board board ;

        if(size<=5 && size%2==1){
            board= game.getPlayer(parameters.get(0)).getBoard();
            for(int i=0; i<nstud;i++){
                board.putStudentOnEntrance(board.getFromDiningRoom(parameters.get(1+i)));
                board.moveToDiningRoom(board.getStudentFromEntrance(parameters.get(1+nstud+i)));
            }
        }
    }

}
