package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Character7 extends TokensCharacter{

    public  Character7(int id,ArrayList<Student> studs){
        super(id, studs);

    }
    @Override /**@param parameters : first playerId, then students in entrance then on studs cards*/
    public void cardEffect(List<Integer> parameters, Game game) {
        int size = parameters.size();
        int nstud = size/2;
        Board board ;
        ArrayList<Student>  fromCard = new ArrayList<>(), fromBoard = new ArrayList<>();
        if(size<=7 && size%2==1){
            board = game.getPlayer(parameters.get(0)).getBoard();
            for (int i =0; i<nstud;i++) {
                fromBoard.add(board.getStudent(parameters.get(i+1)));
            }
            for (int i =0; i<nstud;i++) {
                board.putStudent(this.getStudent(parameters.get(i+1+nstud)));
            }
            addStudents(fromBoard);
        incrementCost();
        }
    }
}
