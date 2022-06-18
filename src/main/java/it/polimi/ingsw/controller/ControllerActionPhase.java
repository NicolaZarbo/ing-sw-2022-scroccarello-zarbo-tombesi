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
    /**Performs the movement of a student from the entrance to dining room, changes the state of the model after third movement */
    public void moveStudentToHall(StudentToHallMessage message){
        if(game.getActualState()!= GameState.actionMoveStudent || studentMoved>2)
            throw new IllegalMoveException("incorrect action, or too many students moved");
        try {
            modelTurn.moveInDiningRoom(message.getPlayerId(), message.getStudentId());
            studentMoved++;
            if (studentMoved == 3)
                game.moveToNextPhase();
        }
        catch(NoTokenFoundException e){
            throw new IllegalMoveException(e.getMessage());
        }
    }
    /**Performs the movement of a student from the entrance to an Island, changes the state of the model after third movement  */

    public void moveStudentToIsland(StudentToIslandMessage message){
        if(game.getActualState()!= GameState.actionMoveStudent || studentMoved>=3)
            throw new IllegalMoveException();

        modelTurn.moveToIsland(message.getPlayerId(), message.getStudentId(),message.getFinalPositionId());
        studentMoved++;
        if(studentMoved==3)
            game.moveToNextPhase();
    }
    /** Moves motherNature in the model*/
    public void moveMotherNature(MoveMotherMessage message){
        if(game.getActualState()!= GameState.actionMoveMother)
            throw new IllegalMoveException();
        if(message.getSteps()>game.getPlayedCard(game.getCurrentPlayerId()).getMoveMother())
            throw new IllegalMoveException("too many steps!");
        else {
                modelTurn.moveMotherNature(message.getSteps());
                game.moveToNextPhase();
        }
    }
    /** Used to move students from a cloud to an entrance, acts as the last state of an action phase for the player */
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
    /**Used to change turn in model, also resets the students' movement counter*/
    public void changeTurn(){
        game.changePlayerTurn();
        studentMoved =0;
    }
    /** Used to activate a character during action phase*/
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
