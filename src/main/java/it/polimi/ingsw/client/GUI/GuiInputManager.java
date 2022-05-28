package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.InputManager;
import it.polimi.ingsw.view.CentralView;

public class GuiInputManager extends InputManager {

    private CentralView game;
    private boolean canDoAction;
    private static boolean isLobbyAvailable;
    private static GUI gui;

    public GuiInputManager(CentralView game,GUI gui) {
        this.isLobbyAvailable=false;
        this.game = game;
        GuiInputManager.gui =gui;
    }
    public static GUI getGui(){
        return gui;
    }

    @Override
    public void decodeInput() {
        canDoAction=true;    //  after every time you send a message set canDoAction to false
    }
/** prints in a label in the connection page info from server*/
    @Override
    public void printToScreen(String string) {
        if(string.equals("connected to lobby")){
            isLobbyAvailable=true;
            System.out.println("lemao connection");
        }

        if(string.contains("connection closed")){
            //TODO
        }
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

    public static boolean isLobbyAvailable(){
        return isLobbyAvailable;
    }
}
