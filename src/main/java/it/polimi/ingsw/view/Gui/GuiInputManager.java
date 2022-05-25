package it.polimi.ingsw.view.Gui;

import it.polimi.ingsw.client.CLI.Cli;
import it.polimi.ingsw.client.CLI.InputManagerCli;
import it.polimi.ingsw.client.InputManager;
import it.polimi.ingsw.view.CentralView;

public class GuiInputManager extends InputManager {

    private CentralView game;
    private boolean canDoAction;

    public GuiInputManager(CentralView game) {
        this.game = game;
    }

    @Override
    public void decodeInput() {
        canDoAction=true;    //  after every time you send a message set canDoAction to false
    }
/** prints in a label in the connection page info from server*/
    @Override
    public void printToScreen(String string) {

    }
//these methods could be either put insides the scenes controllers or kept here
    @Override
    protected void caseSetupPlayers() {

    }

    @Override
    protected void casePlanPlayCard() {

    }

    @Override
    protected void caseActionMoveMother() {

    }

    @Override
    protected void caseActionMoveStudent() {

    }

    @Override
    protected void caseActionChooseCloud() {

    }
}
