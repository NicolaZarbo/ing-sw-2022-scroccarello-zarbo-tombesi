package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Character10 extends CharacterCard{
    @Override /** @param parameters : first playerId, then students in dining room, then students in entrance*/
    public void cardEffect(List<Integer> parameters, Game game) {
        int size = parameters.size();
        int nstud = size/2;
        Board board ;

        if(size<=5 && size%2==1){
            board= game.getPlayer(parameters.get(0)).getBoard();
            for(int i=0; i<nstud;i++){
                board.putStudent(board.getFromDiningRoom(parameters.get(1+i)));
                board.moveToDiningRoom(board.getStudent(parameters.get(1+nstud+i)));
            }
        }
    }

}
