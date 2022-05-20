package it.polimi.ingsw.view;

import it.polimi.ingsw.enumerations.GameState;


public abstract class InputManager {
    /** called only during your turn, used to activate a method based on the user input*/
    public abstract void decodeInput();
    /** used to show to the user a text sent by the server, used during lobby creation to ask the players name and other info*/
    public abstract void printToScreen(String string);
    protected void stateSwitch(GameState state){
        switch (state){
            case setupPlayers -> caseSetupPlayers();
            case planPlayCard -> casePlanPlayCard();
            case actionMoveMother -> caseActionMoveMother();
            case actionMoveStudent -> caseActionMoveStudent();
            case actionChooseCloud -> caseActionChooseCloud();

        }
    }
    protected abstract void caseSetupPlayers();
    protected abstract void casePlanPlayCard();
    protected abstract void caseActionMoveMother();
    protected abstract void caseActionMoveStudent();
    protected abstract void caseActionChooseCloud();
}
