package it.polimi.ingsw.controller;
import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.messages.clientmessages.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Action;

/**The sub-controller which manages action phase. It is responsible of receiving input from the client (View) during all its action phase and translating it to a model action.*/
public class ControllerActionPhase  {

    private final Game game;
    private final Action modelTurn;
    private int studentMoved;
    private final int movableStudentPerTurn;

    /**It builds the action controller.
     * @param game part of the model to interact with*/
    public ControllerActionPhase(Game game){
        this.game=game;
        this.modelTurn =game.getActionPhase();
        if(game.getNPlayers()==3)
            movableStudentPerTurn=4;
        else movableStudentPerTurn=3;
    }

    /**It performs the movement of a student from the entrance to dining room, then it changes the state of the model after the third movement.
     * @param message the input of the client which wants to move a student from entrance to dining room
     * @exception IllegalMoveException if the player wants to move more than two tokens, or a token which he doesn't own, or the game is not in action move student phase*/
    public void moveStudentToHall(StudentToHallMessage message){
        if(game.getActualState()!= GameState.actionMoveStudent || studentMoved>movableStudentPerTurn-1)
            throw new IllegalMoveException("incorrect action, or too many students moved");
        try {
            modelTurn.moveInDiningRoom(message.getPlayerId(), message.getStudentId());
            studentMoved++;
            if (studentMoved == movableStudentPerTurn)
                game.moveToNextPhase();
        }
        catch(NoTokenFoundException e){
            throw new IllegalMoveException(e.getMessage());
        }
    }

    /**It performs the movement of a student from the entrance to an island, then it changes the state of the game after the third movement.
     * @param message the input of the client containing the id of the student to move and the island on which move
     * @exception IllegalMoveException if the game is not in the right phase or the player wants to move more than two students*/
    public void moveStudentToIsland(StudentToIslandMessage message){
        if(game.getActualState()!= GameState.actionMoveStudent || studentMoved>=movableStudentPerTurn)
            throw new IllegalMoveException();

        modelTurn.moveToIsland(message.getPlayerId(), message.getStudentId(),message.getFinalPositionId());
        studentMoved++;
        if(studentMoved==movableStudentPerTurn)
            game.moveToNextPhase();
    }

    /**It moves mother nature in the model of the amount indicated from the player.
     * @param message the input from the client containing the amount of steps to move mother nature
     * @exception IllegalMoveException if the game is not in the right phase or the player wants to move mother nature of more steps than he could*/
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

    /**It is used to move the students from one of the clouds to the player's entrance. Since it is the last part of action phase, it swaps the player's turn.
     * @param message the input from the client which contains the id of the chosen cloud
     * @exception IllegalMoveException if the game is not in the right phase or some runtime errors are caught*/
    public void chooseCloud(ChooseCloudMessage message){
        if(game.getActualState()!= GameState.actionChooseCloud)
            throw new IllegalMoveException();
        else {
            try {
                modelTurn.moveFromCloudToEntrance(message.getCloudId(), message.getPlayerId());
            } catch (RuntimeException e) {
                throw new IllegalMoveException(e.getMessage());
            }
            changeTurn();
        }
    }

    /**It is used to change player's turn in model, also resets the students' movement counter*/
    public void changeTurn(){
        game.changePlayerTurn();
        studentMoved =0;
    }

    /** It is used to activate a character's effect during action phase (expert mode only).
     * @param message the input from the client containing the character card selected
     * @exception IllegalMoveException if the game is not in the right state or the card is activated with wrong parameters*/
    public void playCharacter(CharacterCardMessage message) {
        if(game.getActualState()!=GameState.actionMoveMother && game.getActualState()!=GameState.actionMoveStudent)
            throw new IllegalMoveException();
        try {
            modelTurn.useCharacter(message.getCardId(), message.getParameters(), message.getPlayerId());
        }catch (CharacterErrorException e){
            throw new IllegalMoveException("you selected the wrong parameters for this character");
        }
    }

    /**@return the model on which the controller is operating*/
    public Game getGame(){return this.game;}

}
