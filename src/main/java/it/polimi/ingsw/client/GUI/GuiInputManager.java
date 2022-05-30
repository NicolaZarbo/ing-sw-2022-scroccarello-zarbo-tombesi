package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.InputManager;
import it.polimi.ingsw.view.CentralView;

import java.util.ArrayList;

public class GuiInputManager extends InputManager {

    private CentralView game;
    private boolean canDoAction;
    private static boolean isLobbyAvailable;
    private static boolean isConnected;
    private static GUI gui;
    private ArrayList<Integer> selectedStudents;
    private int selectedIsland;
    private int selectedCloud;


    public GuiInputManager(CentralView game,GUI gui) {
        isLobbyAvailable=false;
        isConnected=false;
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
        if(string.equals("no lobby available. Creating new lobby, number of players?"))
            isConnected=true;
        if(string.equals("connected to lobby")){
            isLobbyAvailable=true;
            isConnected=true;
            System.out.println("lemao connection");
        }

        if(string.contains("connection closed")){
            //TODO
        }
    }
    /** used to refrain the user from spamming actions before receiving the state update*/
    private void waitForAnswer(){this.canDoAction=false;}
    public static boolean isIsConnected() {
        return isConnected;
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
    public void moveToBoard(int color){
        if(!canDoAction)
            return;
        game.moveStudentToHall(color);
        waitForAnswer();
    }
    public void moveToIsland(int islandId){
        if(!canDoAction)
            return;;
        int studColor=selectedStudents.get(0);
        game.moveStudentToIsland(studColor,islandId);
        selectedStudents=new ArrayList<>();//cleaned
        waitForAnswer();

    }
    /** used to keep track of the selected students when you need to move to a different scene*/
    public void saveSelectedStud(ArrayList<Integer> selectedStudents){this.selectedStudents=selectedStudents;}

    @Override
    protected void caseActionChooseCloud() {

    }

    public static boolean isLobbyAvailable(){
        return isLobbyAvailable;
    }
}
