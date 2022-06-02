package it.polimi.ingsw.controller;
import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.messages.clientmessages.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Turn;


public class ControllerActionPhase  {

    //private int idPlayerNow;
    private final Game game;
    private final Turn modelTurn;
    private int studentMoved;

    public ControllerActionPhase(Game game){
        this.game=game;
        this.modelTurn =game.getActionPhase();
    }

    public void moveStudentToHall(StudentToHallMessage message){
        if(game.getActualState()!= GameState.actionMoveStudent || studentMoved>2)
            throw new IllegalMoveException("incorrect action, or too many students moved");
        try {
            modelTurn.moveInHall(message.getPlayerId(), message.getStudentId());
            studentMoved++;
            if (studentMoved == 3)
                game.moveToNextPhase();
        }
        catch(NoTokenFoundException e){
            throw new IllegalMoveException(e.getMessage());
        }
    }
    public void moveStudentToIsland(StudentToIslandMessage message){
        if(game.getActualState()!= GameState.actionMoveStudent || studentMoved>=3)
            throw new IllegalMoveException();

        modelTurn.moveToIsland(message.getPlayerId(), message.getStudentId(),message.getFinalPositionId());
        studentMoved++;
        if(studentMoved==3)
            game.moveToNextPhase();
    }

    public void moveMotherNature(MoveMotherMessage message){
        if(game.getActualState()!= GameState.actionMoveMother)
            throw new IllegalMoveException();
        if(message.getSteps()>game.getPlayedCard(game.getCurrentPlayerId()).getMoveMother())//this can be moved to a thrown exception in moveMotherNature
            throw new IllegalMoveException("too many steps!");
        else {
                modelTurn.moveMotherNature(message.getSteps());
                game.moveToNextPhase();
        }
    }

    public void chooseCloud(ChooseCloudMessage message){
        if(game.getActualState()!= GameState.actionChooseCloud)
            throw new IllegalMoveException();
        try{
            modelTurn.moveFromCloudToEntrance(message.getCloudId(), message.getPlayerId());
        }
        catch(RuntimeException e) {
            throw new IllegalMoveException(e.getMessage());
        }
        changeTurn();
    }

    public void changeTurn(){
        game.changePlayerTurn();
        studentMoved =0;
       // this.idPlayerNow= game.getCurrentPlayerId();

    }

    public void playCharacter(CharacterCardMessage message) {
        if(game.getActualState()!=GameState.actionMoveMother && game.getActualState()!=GameState.actionMoveStudent)
            throw new IllegalMoveException();
        try {
            modelTurn.useCharacter(message.getCardId(), message.getParameters(), message.getPlayerId());
        }catch (CharacterErrorException e){
            throw new IllegalMoveException("you selected the wrong parameters for this character");
        }
    }

    public Game getGame(){return this.game;}

}
