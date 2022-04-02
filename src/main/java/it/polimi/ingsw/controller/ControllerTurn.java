package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Turn;

import java.util.ArrayList;
import java.util.List;

public class ControllerTurn {
    //in case we decide to use  observer pattern:
    // every method that modifies something in the model could call a notify call to an observer in controller and exchange the information through that
    //instead of having to return the new state
    private int idPlayerNow;
    private final Game game;
    public int characterActive;

    public void moveStudentToHall( int idStudent){
        Turn.moveInHall(this.idPlayerNow,idStudent, this.game);
    }
    public void moveStudentToIsland(int idStudent, int idIsland){
        Turn.moveToIsland(this.idPlayerNow,idStudent,idIsland,this.game);
    }

    public int moveMotherNature(int steps){
        Turn.moveMotherNature(steps, game);
        int position=game.getMotherNature().getPosition();
        Turn.islandConquest(position,game);
        if(Turn.isUnifiableNext(game,position)){
            Turn.unifyNext(game, position);
            Main.islands.remove(position+1);
            
        }
        if(Turn.isUnifiableBefore(game,position)){
            Turn.unifyBefore(game, position);
            Main.islands.remove(position-1);

        }
        return position;
    }

    public void chooseCloud(int cloudId){
        Turn.moveFromCloudToEntrance(this.game,cloudId,this.idPlayerNow);
    }
    public void changeTurn(int idPlayer){
        this.idPlayerNow=idPlayer;
    }
    public ControllerTurn(Game game){
        this.game=game;
        this.idPlayerNow=0;
    }
    /*
    public boolean canUseCharacter(){
        int cost=0;
        // int cost= game.getCharacters(character).getCost();
        return game.getPlayer(idPlayerNow).getHand().enoughCoin(cost);
    }
    public void playCharacter(int character, ArrayList<Integer> inputPar) {
        Turn.useCharacter(character,inputPar,game);
        //to notify changes in the game an observer is needed

    }
    */

}
