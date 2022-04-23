package it.polimi.ingsw.controller;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.messages.client.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Turn;


public class ControllerActionPhase  {

    private int idPlayerNow;
    private final Game game;
    private final Turn modelTurn;
    private int studentMoved;

    public void moveStudentToHall(StudentToHallMessage message){
        if(game.getActualState()!= GameState.actionMoveStudent || studentMoved>=3)
            throw new IllegalMoveException();
        modelTurn.moveInHall(this.idPlayerNow, message.getStudentId());
        studentMoved++;
        if(studentMoved==3)
            game.moveToNextPhase();
    }
    public void moveStudentToIsland(StudentToIslandMessage message){
        if(game.getActualState()!= GameState.actionMoveStudent || studentMoved>=3)
            throw new IllegalMoveException();
        modelTurn.moveToIsland(this.idPlayerNow,message.getStudentId(),message.getFinalPositionId());
        studentMoved++;
        if(studentMoved==3)
            game.moveToNextPhase();
    }

    public void moveMotherNature(MoveMotherMessage message){
        if(game.getActualState()!= GameState.actionMoveMother)
            throw new IllegalMoveException();
        if(message.getSteps()>game.getPlayedCard(game.getCurrentPlayerId()).getMoveMother())//this can be moved to a thrown exception in moveMotherNature
            throw new RuntimeException("too many steps!");
        else {
                modelTurn.moveMotherNature(message.getSteps());
                game.moveToNextPhase();
        }
    }

    public void chooseCloud(ChooseCloudMessage message){
        if(game.getActualState()!= GameState.actionChooseCloud)
            throw new IllegalMoveException();
        modelTurn.moveFromCloudToEntrance(message.getCloudId(), this.idPlayerNow);
        if(game.isLastPlayerTurn())
            game.moveToNextPhase();
        else {
            changeTurn();
        }
    }
    public void changeTurn(){
        game.changePlayerTurn();
        this.idPlayerNow= game.getCurrentPlayerId();
        studentMoved =0;
    }
    public ControllerActionPhase(Game game){
        this.game=game;
        this.modelTurn =game.getActionPhase();
    }


    public void playCharacter(CharacterCardMessage message) {
        if(game.getActualState()!=GameState.actionMoveMother || game.getActualState()!=GameState.actionMoveStudent)
            throw new IllegalMoveException();
        modelTurn.useCharacter(message.getCardId(), message.getParameters(), message.getPlayerId());
    }




}
